package rest

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import model.article.dto.NewData
import model.article.repository.ArticlesRepository
import model.article.updateDescription
import model.article.updatePrice
import model.article.updateStock
import utils.errors.ValidationError

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
class PostArticlesId(
    private val repository: ArticlesRepository,
    private val commonValidations: CommonValidations
) {
    fun init(app: Routing) = app.apply {
        post<NewData>("/v1/articles/{articleId}") { newData ->
            this.call.parameters["articleId"]?.let { id ->
                commonValidations.validateArticleId(id)

                repository.findById(id)?.let {
                    it.updateDescription(newData)
                    it.updatePrice(newData.price)
                    it.updateStock(newData.stock)

                    repository.save(it)

                    this.call.respond(it)
                } ?: throw ValidationError().addPath("id", "Not found")
            } ?: throw ValidationError().addPath("id", "Id is required")
        }
    }
}