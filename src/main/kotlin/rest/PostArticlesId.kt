package rest

import model.article.dto.ArticleData
import model.article.dto.DescriptionData
import model.article.dto.NewData
import model.article.repository.ArticlesRepository
import model.article.updateDescription
import model.article.updatePrice
import model.article.updateStock
import model.security.TokenService
import model.security.validateAdminUser
import spark.Request
import spark.Response
import spark.Spark
import utils.errors.SimpleError
import utils.gson.jsonToObject
import utils.spark.JsonTransformer

class PostArticlesId private constructor() {
    private fun init() {
        Spark.post(
            "/v1/articles/:articleId",
            { req: Request, res: Response ->
                updateArticle(req, res)
            },
            JsonTransformer
        )
    }

    /**
     * @api {post} /v1/articles/:articleId Actualizar Artículo
     * @apiName Actualizar Artículo
     * @apiGroup Artículos
     *
     * @apiUse AuthHeader
     *
     * @apiExample {json} Body
     * {
     *      "name": "{nombre del articulo}",
     *      "description": "{descripción del articulo}",
     *      "image": "{id de imagen}",
     *      "price": {precio actual},
     *      "stock": {stock actual}
     * }
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
    private fun updateArticle(req: Request, res: Response): ArticleData {
        TokenService.instance().validateAdminUser(req.headers("Authorization"))

        val description = req.body().jsonToObject<DescriptionData>() ?: throw SimpleError("Invalid body")
        val otherParams = req.body().jsonToObject<NewData>() ?: throw SimpleError("Invalid body")

        return ArticlesRepository.instance().findById(req.params(":articleId")).let {
            it.updateDescription(description)
            it.updatePrice(otherParams.price)
            it.updateStock(otherParams.stock)

            ArticlesRepository.instance().save(it)
            it.value()
        }
    }

    companion object {
        var currentInstance: PostArticlesId? = null

        fun init() {
            currentInstance ?: PostArticlesId().also {
                it.init()
                currentInstance = it
            }
        }
    }
}