package rest

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import model.article.Article
import model.article.repository.ArticlesRepository
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
            this.call.parameters["criteria"]?.let { criteria ->
                validateCriteria(criteria)

                val response = repository.findByCriteria(criteria)
                    .map { article: Article -> article.value() }
                this.call.respond(response)
            } ?: throw ValidationError().addPath("criteria", "Criteria is required")
        }
    }


    private fun validateCriteria(criteria: String) {
        if (criteria.isNullOrBlank()) {
            throw ValidationError().addPath("criteria", "Must be provided")
        }
    }
}