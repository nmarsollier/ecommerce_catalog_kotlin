package rest

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import model.article.repository.ArticlesRepository
import utils.errors.ValidationError

/**
 * @api {delete} /articles/:articleId Eliminar Artículo
 * @apiName Eliminar Artículo
 * @apiGroup Artículos
 *
 * @apiUse AuthHeader
 *
 * @apiSuccessExample {json} 200 Respuesta
 * HTTP/1.1 200 OK
 *
 * @apiUse Errors
 */
class DeleteArticleId(
    private val repository: ArticlesRepository,
    private val commonValidations: CommonValidations
) {
    fun init(app: Routing) = app.apply {
        delete("/v1/article/{articleId}") {
            this.call.parameters["articleId"]?.let { id ->
                commonValidations.validateArticleId(id)
                commonValidations.validateAdminUser(this.call.authHeader)

                repository.findById(id)?.let {
                    it.disable()
                    repository.save(it)
                    this.call.respond("")
                } ?: throw ValidationError().addPath("id", "Not found")
            } ?: throw ValidationError().addPath("id", "Id is required")
        }
    }
}