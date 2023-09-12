package rest

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import model.article.Article
import model.article.dto.NewData
import model.article.repository.ArticlesRepository

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
    private val commonValidations: CommonValidations
) {
    fun init(app: Routing) = app.apply {
        post<NewData>("/v1/articles") {
            commonValidations.validateAdminUser(this.call.authHeader)

            val result = Article.newArticle(it)
                .also { article ->
                    repository.save(article)
                }
                .value()
            this.call.respond(result)
        }
    }
}
