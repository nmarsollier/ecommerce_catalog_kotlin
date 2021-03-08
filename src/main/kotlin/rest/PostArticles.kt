package rest

import io.javalin.Javalin
import io.javalin.http.Context
import model.article.Article
import model.article.dto.NewData
import model.article.repository.ArticlesRepository
import utils.errors.SimpleError
import utils.gson.jsonToObject
import utils.javalin.NextFun
import utils.javalin.route

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
class PostArticles private constructor(
    private val repository: ArticlesRepository = ArticlesRepository.instance()
) {
    private fun init(app: Javalin) {
        app.post(
            "/v1/articles",
            route(
                validateAdminUser,
                validateBody,
            ) {
                val data = it.body().jsonToObject<NewData>()!!
                val result = Article.newArticle(data)
                    .also {
                        repository.save(it)
                    }
                    .value()
                it.json(result)
            })
    }

    private val validateBody = { ctx: Context, _: NextFun ->
        ctx.body().jsonToObject<NewData>() ?: throw SimpleError("Invalid body")
        Unit
    }

    companion object {
        var currentInstance: PostArticles? = null

        fun init(app: Javalin) {
            currentInstance ?: PostArticles().also {
                it.init(app)
                currentInstance = it
            }
        }
    }
}