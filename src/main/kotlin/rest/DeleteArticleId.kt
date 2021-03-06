package rest

import model.article.disable
import model.article.repository.ArticleRepository
import model.security.TokenService
import spark.Request
import spark.Response
import spark.Spark
import utils.spark.JsonTransformer

class DeleteArticleId private constructor() {
    private fun init() {
        Spark.delete(
            "/v1/articles/:articleId",
            { req: Request, res: Response ->
                deleteArticle(req, res)
            },
            JsonTransformer
        )
    }

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
    private fun deleteArticle(req: Request, res: Response): Unit {
        TokenService.instance().validateAdmin(req.headers("Authorization"))

        ArticleRepository.instance().findById(req.params(":articleId")).also {
            it.disable()
            ArticleRepository.instance().save(it)
        }
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