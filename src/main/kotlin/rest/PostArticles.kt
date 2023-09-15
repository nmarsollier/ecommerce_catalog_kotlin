package rest

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import model.article.ArticlesRepository
import model.article.dto.NewArticleData
import model.article.dto.asArticleData
import model.article.dto.toNewArticle
import model.article.saveIn
import model.security.TokenService
import model.security.validateTokenIsAdminUser

/**
 * @api {post} /v1/articles/ Crear Artículo
 * @apiName Crear Artículo
 * @apiGroup Artículos
 *
 * @apiUse AuthHeader
 *
 * @apiExample {json} Body
 *  {
 *      "name": "{nombre del articulo}",
 *      "description": "{descripción del articulo}",
 *      "image": "{id de imagen}",
 *      "price": {precio actual},
 *      "stock": {stock actual}
 *  }
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
class PostArticles(
    private val repository: ArticlesRepository,
    private val tokenService: TokenService
) {
    fun init(app: Routing) = app.apply {
        post<NewArticleData>("/v1/articles") {
            this.call.authHeader.validateTokenIsAdminUser(tokenService)

            val result = it.toNewArticle.saveIn(repository).asArticleData

            this.call.respond(result)
        }
    }
}
