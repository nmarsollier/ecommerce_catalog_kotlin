package rest

import io.javalin.Javalin
import io.javalin.http.Context
import model.article.Article
import model.article.repository.ArticlesRepository
import utils.errors.ValidationError
import utils.javalin.NextFun
import utils.javalin.route

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
    private fun init(app: Javalin) {
        app.get(
            "/v1/articles/search/:criteria",
            route(
                logTimes,
                validateCriteria
            ) {
                val result = repository.findByCriteria(it.pathParam("criteria"))
                    .map { article: Article -> article.value() }
                it.json(result)
            }
        )
    }

    private val validateCriteria = { ctx: Context, next: NextFun ->
        val id = ctx.pathParam("criteria")

        if (id.isNullOrBlank()) {
            throw ValidationError().addPath("criteria", "Must be provided")
        }

        Unit
    }

    companion object {
        var currentInstance: GetArticlesSearchCriteria? = null

        fun init(app: Javalin) {
            currentInstance ?: GetArticlesSearchCriteria().also {
                it.init(app)
                currentInstance = it
            }
        }
    }
}