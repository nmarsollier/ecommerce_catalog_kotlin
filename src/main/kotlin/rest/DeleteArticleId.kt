package rest

import model.article.disable
import model.article.repository.ArticlesRepository
import utils.errors.ValidationError
import utils.spark.jsonDelete
import utils.spark.route

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
    private fun init() {
        jsonDelete(
            "/v1/articles/:articleId",
            route(
                validateAdminUser,
                validateArticleId
            ) { req, _ ->
                repository.findById(req.params(":articleId"))?.also {
                    it.disable()
                    repository.save(it)
                } ?: throw ValidationError().addPath("id", "Not found")
            }
        )
    }

    companion object {
        var currentInstance: DeleteArticleId? = null

        fun init() {
            currentInstance ?: DeleteArticleId().also {
                it.init()
                currentInstance = it
            }
        }
    }
}