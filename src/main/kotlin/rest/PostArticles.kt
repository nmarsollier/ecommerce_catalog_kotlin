package rest

import model.article.Article
import model.article.dto.NewData
import model.article.repository.ArticlesRepository
import spark.Request
import spark.Response
import utils.errors.SimpleError
import utils.gson.jsonToObject
import utils.spark.NextFun
import utils.spark.jsonPost
import utils.spark.route

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
class PostArticles private constructor(
    private val repository: ArticlesRepository = ArticlesRepository.instance()
) {
    private fun init() {
        jsonPost(
            "/v1/articles",
            route(
                validateAdminUser,
                validateBody,
            ) { req, _ ->
                val data = req.body().jsonToObject<NewData>()!!
                Article.newArticle(data)
                    .also {
                        repository.save(it)
                    }
                    .value()
            })
    }

    private val validateBody = { req: Request, _: Response, _: NextFun ->
        req.body().jsonToObject<NewData>() ?: throw SimpleError("Invalid body")
        Unit
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