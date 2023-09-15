package rest

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import model.article.ArticlesRepository
import model.article.saveIn
import model.security.TokenService
import model.security.validateTokenIsAdminUser
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
    private val tokenService: TokenService
) {
    fun init(app: Routing) = app.apply {
        delete("/v1/article/{articleId}") {
            this.call.authHeader.validateTokenIsAdminUser(tokenService)
            val id = this.call.parameters["articleId"].asArticleId

            repository.findById(id)?.disable()?.saveIn(repository) ?: throw ValidationError("id" to "Not found")

            this.call.respond(HttpStatusCode.OK)
        }
    }
}