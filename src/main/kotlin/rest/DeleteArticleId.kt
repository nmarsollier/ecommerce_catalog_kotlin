package rest

import model.article.disable
import model.article.repository.ArticlesRepository
import model.security.TokenService
import model.security.validateAdminUser
import spark.Request
import spark.Response
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
class DeleteArticleId private constructor() {
    private fun init() {
        jsonDelete(
            "/v1/articles/:articleId",
            route(
                validateAdminUser,
            ) { req, _ ->
                ArticlesRepository.instance().findById(req.params(":articleId")).also {
                    it.disable()
                    ArticlesRepository.instance().save(it)
                }
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