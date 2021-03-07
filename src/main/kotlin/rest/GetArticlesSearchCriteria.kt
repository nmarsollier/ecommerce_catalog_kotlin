package rest

import model.article.Article
import model.article.repository.ArticlesRepository
import spark.Request
import spark.Response
import utils.errors.ValidationError
import utils.spark.NextFun
import utils.spark.jsonGet
import utils.spark.route

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
class GetArticlesSearchCriteria private constructor(
    private val repository: ArticlesRepository = ArticlesRepository.instance()
) {
    private fun init() {
        jsonGet(
            "/v1/articles/search/:criteria",
            route(
                validateCriteria
            ) { req, _ ->
                repository.findByCriteria(req.params(":criteria"))
                    .map { article: Article -> article.value() }
            }
        )
    }

    private val validateCriteria = { req: Request, res: Response, next: NextFun ->
        val id = req.params(":criteria")

        if (id.isNullOrBlank()) {
            throw ValidationError().addPath("criteria", "Must be provided")
        }

        Unit
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