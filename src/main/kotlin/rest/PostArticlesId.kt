package rest

import model.article.dto.DescriptionData
import model.article.dto.NewData
import model.article.repository.ArticlesRepository
import model.article.updateDescription
import model.article.updatePrice
import model.article.updateStock
import spark.Request
import spark.Response
import utils.errors.SimpleError
import utils.errors.ValidationError
import utils.gson.jsonToObject
import utils.spark.NextFun
import utils.spark.jsonPost
import utils.spark.route

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
class PostArticlesId private constructor(
    private val repository: ArticlesRepository = ArticlesRepository.instance()
) {
    private fun init() {
        jsonPost(
            "/v1/articles/:articleId",
            route(
                validateAdminUser,
                validateBody,
                handler = getArticleById
            )
        )
    }

    private val validateBody = { req: Request, res: Response, next: NextFun ->
        req.body().jsonToObject<DescriptionData>() ?: throw SimpleError("Invalid body")
        req.body().jsonToObject<NewData>() ?: throw SimpleError("Invalid body")
        Unit
    }

    private val getArticleById = { req: Request, res: Response ->
        repository.findById(req.params(":articleId"))?.let {
            val description = req.body().jsonToObject<DescriptionData>()!!
            val otherParams = req.body().jsonToObject<NewData>()!!

            it.updateDescription(description)
            it.updatePrice(otherParams.price)
            it.updateStock(otherParams.stock)

            repository.save(it)
            it.value()
        } ?: ValidationError().addPath("id", "Not found")
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