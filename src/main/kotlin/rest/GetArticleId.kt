package rest

import io.javalin.Javalin
import model.article.repository.ArticlesRepository
import utils.errors.ValidationError
import utils.javalin.route

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
class GetArticleId private constructor(
    private val repository: ArticlesRepository = ArticlesRepository.instance()
) {
    private fun init(app: Javalin) {
        app.get(
            "/v1/articles/:articleId",
            route(
                validateArticleId
            ) {
                val result = repository.findById(it.pathParam("articleId"))?.value()
                    ?: throw ValidationError().addPath("id", "Not found")
                it.json(result)
            })
    }

    companion object {
        var currentInstance: GetArticleId? = null

        fun init(app: Javalin) {
            currentInstance ?: GetArticleId().also {
                it.init(app)
                currentInstance = it
            }
        }
    }
}