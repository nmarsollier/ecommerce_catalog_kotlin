package rest

import model.article.Article
import model.article.repository.ArticleRepository
import model.article.dto.ArticleData
import model.article.dto.NewData
import model.security.TokenService
import spark.Request
import spark.Response
import spark.Spark
import utils.errors.SimpleError
import utils.gson.jsonToObject
import utils.spark.JsonTransformer

class PostArticles private constructor() {
    private fun init() {
        Spark.post(
            "/v1/articles",
            { req: Request, res: Response ->
                addArticle(req, res)
            },
            JsonTransformer
        )
    }

    /**
     * @api {post} /v1/articles/ Crear Artículo
     * @apiName Crear Artículo
     * @apiGroup Artículos
     *
     * @apiUse AuthHeader
     *
     * @apiExample {json} Body
     *  {
     *      "name": "{nombre del articulo}",
     *      "description": "{descripción del articulo}",
     *      "image": "{id de imagen}",
     *      "price": {precio actual},
     *      "stock": {stock actual}
     *  }
     *
     * @apiSuccessExample {json} Respuesta
     * HTTP/1.1 200 OK
     * {
     *      "_id": "{id de articulo}"
     *      "name": "{nombre del articulo}",
     *      "description": "{descripción del articulo}",
     *      "image": "{id de imagen}",
     *      "price": {precio actual},
     *      "stock": {stock actual}
     *      "updated": {fecha ultima actualización}
     *      "created": {fecha creación}
     *      "enabled": {si esta activo}
     * }
     *
     * @apiUse Errors
     */
    private fun addArticle(req: Request, res: Response): ArticleData {
        TokenService.instance().validateAdmin(req.headers("Authorization"))

        val data = req.body().jsonToObject<NewData>()
        data ?: throw SimpleError("Invalid body")

        return Article.newArticle(data)
            .also {
                ArticleRepository.instance().save(it)
            }
            .value()
    }

    companion object {
        var currentInstance: PostArticles? = null

        fun init() {
            currentInstance ?: PostArticles().also {
                it.init()
                currentInstance = it
            }
        }
    }
}