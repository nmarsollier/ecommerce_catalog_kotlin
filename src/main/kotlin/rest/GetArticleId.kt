package rest

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import model.article.ArticlesRepository
import model.article.dto.asArticleData
import utils.errors.NotFoundError

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
    private val repository: ArticlesRepository
) {
    fun init(app: Routing) = app.apply {
        get("/v1/articles/{articleId}") {
            val id = this.call.parameters["articleId"].asArticleId

            val data = (repository.findById(id) ?: throw NotFoundError("id"))
                .asArticleData

            this.call.respond(data)
        }
    }
}
