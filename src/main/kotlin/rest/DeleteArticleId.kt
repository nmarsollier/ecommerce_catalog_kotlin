package rest

import io.javalin.Javalin
import model.article.disable
import model.article.repository.ArticlesRepository
import utils.errors.ValidationError
import utils.javalin.route

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
class DeleteArticleId private constructor(
    private val repository: ArticlesRepository = ArticlesRepository.instance()
) {
    private fun init(app: Javalin) {
        app.delete(
            "/v1/articles/:articleId",
            route(
                validateAdminUser,
                validateArticleId
            ) {
                val result = repository.findById(it.pathParam("articleId"))?.also {
                    it.disable()
                    repository.save(it)
                } ?: throw ValidationError().addPath("id", "Not found")

                it.json(result)
            }
        )
    }

    companion object {
        var currentInstance: DeleteArticleId? = null

        fun init(app: Javalin) {
            currentInstance ?: DeleteArticleId().also {
                it.init(app)
                currentInstance = it
            }
        }
    }
}