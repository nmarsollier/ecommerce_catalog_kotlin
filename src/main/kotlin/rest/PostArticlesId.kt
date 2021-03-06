package rest

import model.article.ArticleRepository
import model.article.dto.ArticleData
import model.article.dto.DescriptionData
import model.article.dto.NewData
import model.security.TokenService
import spark.Request
import spark.Response
import spark.Spark
import utils.errors.SimpleError
import utils.gson.jsonToObject
import utils.spark.JsonTransformer

class PostArticlesId {
    companion object {
        fun init() {
            Spark.post(
                "/v1/articles/:articleId",
                { req: Request, res: Response ->
                    updateArticle(req, res)
                },
                JsonTransformer
            )
        }

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
        private fun updateArticle(req: Request, res: Response): ArticleData {
            TokenService.validateAdmin(req.headers("Authorization"))
            val article = ArticleRepository.findById(req.params(":articleId"))

            val description = req.body().jsonToObject<DescriptionData>() ?: throw SimpleError(400, "Invalid body")
            val otherParams = req.body().jsonToObject<NewData>() ?: throw SimpleError(400, "Invalid body")

            article.updateDescription(description)

            article.updatePrice(otherParams.price)
            article.updateStock(otherParams.stock)
            ArticleRepository.save(article)
            return article.value()
        }
    }

}