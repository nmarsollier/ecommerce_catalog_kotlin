package rest

import io.javalin.Javalin
import io.javalin.http.Context
import model.article.dto.DescriptionData
import model.article.dto.NewData
import model.article.repository.ArticlesRepository
import model.article.updateDescription
import model.article.updatePrice
import model.article.updateStock
import utils.errors.SimpleError
import utils.errors.ValidationError
import utils.gson.jsonToObject
import utils.javalin.NextFun
import utils.javalin.route

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
    private fun init(app: Javalin) {
        app.post(
            "/v1/articles/:articleId",
            route(
                validateAdminUser,
                validateBody
            ) {
                getArticleById(it)
            }
        )
    }

    private val validateBody = { ctx: Context, _: NextFun ->
        ctx.body().jsonToObject<DescriptionData>() ?: throw SimpleError("Invalid body")
        ctx.body().jsonToObject<NewData>() ?: throw SimpleError("Invalid body")
        Unit
    }

    private val getArticleById = { ctx: Context ->
        repository.findById(ctx.pathParam("articleId"))?.let {
            val description = ctx.body().jsonToObject<DescriptionData>()!!
            val otherParams = ctx.body().jsonToObject<NewData>()!!

            it.updateDescription(description)
            it.updatePrice(otherParams.price)
            it.updateStock(otherParams.stock)

            repository.save(it)

            ctx.json(it.value())
        } ?: ValidationError().addPath("id", "Not found")
    }

    companion object {
        var currentInstance: PostArticlesId? = null

        fun init(app: Javalin) {
            currentInstance ?: PostArticlesId().also {
                it.init(app)
                currentInstance = it
            }
        }
    }
}