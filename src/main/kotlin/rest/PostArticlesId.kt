package rest

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import model.article.ArticlesRepository
import model.article.dto.NewArticleData
import model.article.saveIn
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
    private val repository: ArticlesRepository
) {
    fun init(app: Routing) = app.apply {
        post<NewArticleData>("/v1/articles/{articleId}") { newData ->
            val id = this.call.parameters["articleId"].asArticleId

            val data = (repository.findById(id) ?: throw ValidationError("id" to "Not found"))
                .updateDescription(newData)
                .updatePrice(newData.price)
                .updateStock(newData.stock)
                .saveIn(repository)

            this.call.respond(data)
        }
    }
}