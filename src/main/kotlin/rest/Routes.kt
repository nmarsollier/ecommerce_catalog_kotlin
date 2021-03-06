package rest

import spark.Request
import spark.Response
import spark.Spark
import utils.env.Environment
import utils.errors.SimpleError
import utils.errors.ValidationError
import utils.gson.toJson
import utils.spark.CorsFilter

class Routes {
    companion object {
        private val INTERNAL_ERROR = mapOf("error" to "Internal Server Error").toJson()

        fun init() {
            Spark.exception(ValidationError::class.java) { ex: ValidationError?, req: Request?, res: Response? ->
                res?.status(ex?.statusCode ?: 500)
                res?.body(ex?.toJson() ?: INTERNAL_ERROR)
            }

            Spark.exception(SimpleError::class.java) { ex: SimpleError?, req: Request?, res: Response? ->
                res?.status(ex?.statusCode ?: 500)
                res?.body(ex?.toJson() ?: INTERNAL_ERROR)
            }

            Spark.exception(Exception::class.java) { ex: Exception?, req: Request?, res: Response? ->
                res?.status(500)
                res?.body(INTERNAL_ERROR)
            }

            Spark.port(Environment.env.serverPort)
            Spark.staticFiles.location(Environment.env.staticLocation)

            CorsFilter.init()

            PostArticles.init()
            PostArticlesId.init()
            GetArticleId.init()
            DeleteArticleId.init()
            GetArticlesSearchCriteria.init()
        }
    }
}
