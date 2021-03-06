package rest

import model.article.repository.ArticlesRepository
import model.article.dto.ArticleData
import spark.Request
import spark.Response
import spark.Spark
import utils.spark.JsonTransformer

class GetArticleId private constructor() {
    private fun init() {
        Spark.get(
            "/v1/articles/:articleId",
            { req: Request?, res: Response? ->
                getArticle(req!!, res!!)
            },
            JsonTransformer
        )
    }

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
    private fun getArticle(req: Request, res: Response): ArticleData {
        return ArticlesRepository.instance().findById(req.params(":articleId")).value()
    }

    companion object {
        var currentInstance: GetArticleId? = null

        fun init() {
            currentInstance ?: GetArticleId().also {
                it.init()
                currentInstance = it
            }
        }
    }
}