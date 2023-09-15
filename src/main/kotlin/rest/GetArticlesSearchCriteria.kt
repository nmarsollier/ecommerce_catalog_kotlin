package rest

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import model.article.ArticlesRepository
import model.article.dto.asArticleData
import utils.errors.ValidationError

/**
 * @api {get} /v1/articles/search/:criteria Buscar Artículo
 * @apiName Buscar Artículo
 * @apiGroup Artículos
 * @apiDescription Busca artículos por nombre o descripción
 *
 * @apiSuccessExample {json} Respuesta
 * HTTP/1.1 200 OK
 * [
 *      {
 *          "_id": "{id de articulo}"
 *          "name": "{nombre del articulo}",
 *          "description": "{descripción del articulo}",
 *          "image": "{id de imagen}",
 *          "price": {precio actual},
 *          "stock": {stock actual}
 *          "updated": {fecha ultima actualización}
 *          "created": {fecha creación}
 *          "enabled": {activo}
 *      },
 * ...
 * ]
 *
 * @apiUse Errors
 */
class GetArticlesSearchCriteria(
    private val repository: ArticlesRepository
) {
    fun init(app: Routing) = app.apply {
        get("/v1/articles/search/{criteria}") {
            val criteria = this.call.parameters["criteria"].validateAsSearchCriteria()

            val response = repository.findByCriteria(criteria).map { it.asArticleData }
            this.call.respond(response)
        }
    }
}

private fun String?.validateAsSearchCriteria(): String {
    if (this.isNullOrBlank()) {
        throw ValidationError("criteria" to "Must be provided")
    }
    return this
}