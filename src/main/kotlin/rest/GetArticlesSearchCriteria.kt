package rest

import model.article.Article
import model.article.dto.ArticleData
import model.article.repository.ArticlesRepository
import spark.Request
import spark.Response
import utils.spark.jsonGet

/**
 * @api {get} /v1/articles/search/:criteria Buscar Artículo
 * @apiName Buscar Artículo
 * @apiGroup Artículos
 * @apiDescription Busca artículos por nombre o descripción
 *
 * @apiSuccessExample {json} Respuesta
 * HTTP/1.1 200 OK
 * [
 *      {
 *          "_id": "{id de articulo}"
 *          "name": "{nombre del articulo}",
 *          "description": "{descripción del articulo}",
 *          "image": "{id de imagen}",
 *          "price": {precio actual},
 *          "stock": {stock actual}
 *          "updated": {fecha ultima actualización}
 *          "created": {fecha creación}
 *          "enabled": {activo}
 *      },
 * ...
 * ]
 *
 * @apiUse Errors
 */
class GetArticlesSearchCriteria private constructor() {
    private fun init() {
        jsonGet("/v1/articles/search/:criteria") { req, _ ->
            ArticlesRepository.instance()
                .findByCriteria(req.params(":criteria"))
                .map { article: Article -> article.value() }
        }
    }

    companion object {
        var currentInstance: GetArticlesSearchCriteria? = null

        fun init() {
            currentInstance ?: GetArticlesSearchCriteria().also {
                it.init()
                currentInstance = it
            }
        }
    }
}