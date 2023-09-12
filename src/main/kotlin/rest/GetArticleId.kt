package rest

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import model.article.repository.ArticlesRepository
import utils.errors.ValidationError

/**
 * @api {get} /v1/articles/:articleId Buscar Artículo
 * @apiName Buscar Artículo
 * @apiGroup Articulos
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
 *      "enabled": {activo}
 * }
 *
 * @apiUse Errors
 */
class GetArticleId(
    private val repository: ArticlesRepository,
    private val commonValidations: CommonValidations
) {
    fun init(app: Routing) = app.apply {
        get("/v1/articles/{articleId}") {
            this.call.parameters["articleId"]?.let { id ->
                commonValidations.validateArticleId(id)

                repository.findById(id)?.let {
                    this.call.respond(it)
                } ?: throw ValidationError().addPath("id", "Not found")
            } ?: throw ValidationError().addPath("id", "Id is required")
        }
    }
}
