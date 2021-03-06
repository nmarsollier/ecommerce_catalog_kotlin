package rest

import model.article.ArticleRepository
import model.security.TokenService
import spark.Request
import spark.Response
import spark.Spark
import utils.spark.JsonTransformer

class DeleteArticleId {
    companion object {
        fun init() {
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
        private fun deleteArticle(req: Request, res: Response): String {
            TokenService.validateAdmin(req.headers("Authorization"))

            ArticleRepository.findById(req.params(":articleId")).also {
                it.disable()
                ArticleRepository.save(it)
            }

            return ""
        }
    }
}