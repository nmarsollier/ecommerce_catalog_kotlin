package rest

import io.javalin.Javalin
import io.javalin.plugin.json.FromJsonMapper
import io.javalin.plugin.json.JavalinJson
import io.javalin.plugin.json.ToJsonMapper
import utils.env.Environment
import utils.env.Log
import utils.errors.SimpleError
import utils.errors.UnauthorizedError
import utils.errors.ValidationError
import utils.gson.gson

class Routes private constructor() {
    companion object {
        private val INTERNAL_ERROR = mapOf("error" to "Internal Server Error")

        fun init() {
            val gson = gson()

            JavalinJson.fromJsonMapper = object : FromJsonMapper {
                override fun <T> map(json: String, targetClass: Class<T>) = gson.fromJson(json, targetClass)
            }

            JavalinJson.toJsonMapper = object : ToJsonMapper {
                override fun map(obj: Any): String = gson.toJson(obj)
            }

            val app = Javalin.create {
                it.enableCorsForAllOrigins()
                //it.addStaticFiles(Environment.env.staticLocation)
            }.start(Environment.env.serverPort)
            app.get("/") { ctx -> ctx.result("Hello World") }

            app.exception(ValidationError::class.java) { ex, ctx ->
                Log.error(ex)
                ctx.status(400).json(ex.json())
            }

            app.exception(SimpleError::class.java) { ex, ctx ->
                Log.error(ex)
                ctx.status(400).json(ex.json())
            }

            app.exception(UnauthorizedError::class.java) { ex, ctx ->
                Log.error(ex)
                ctx.status(401).json(ex.json())
            }

            app.exception(Exception::class.java) { ex, ctx ->
                Log.error(ex)
                ctx.status(500).json(INTERNAL_ERROR)
            }

            PostArticles.init(app)
            PostArticlesId.init(app)
            GetArticleId.init(app)
            DeleteArticleId.init(app)
            GetArticlesSearchCriteria.init(app)
        }
    }
}
