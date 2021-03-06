package rest

import model.article.Article
import model.article.ArticleRepository
import model.article.dto.ArticleData
import model.article.dto.NewData
import model.security.TokenService
import spark.Request
import spark.Response
import spark.Spark
import utils.errors.SimpleError
import utils.gson.jsonToObject
import utils.spark.JsonTransformer

class PostArticles {
    companion object {
        fun init() {
            Spark.post(
                "/v1/articles",
                { req: Request, res: Response ->
                    addArticle(req, res)
                },
                JsonTransformer
            )
        }

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
        fun addArticle(req: Request, res: Response): ArticleData {
            TokenService.validateAdmin(req.headers("Authorization"))

            val data = req.body().jsonToObject<NewData>()
            data ?: throw SimpleError(400, "Invalid body")

            return Article.newArticle(data)
                .also {
                    ArticleRepository.save(it)
                }
                .value()
        }
    }
}