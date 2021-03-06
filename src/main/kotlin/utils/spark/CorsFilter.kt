package utils.spark

import spark.Filter
import spark.Request
import spark.Response
import spark.Spark

/**
 * Filtro para habilitar Cors en Spark
 */
object CorsFilter {
    @JvmStatic
    fun init() {
        Spark.options("/*") { _, _ -> "" }
        Spark.before(object : Filter {
            override fun handle(request: Request?, response: Response?) {
                response?.header("Access-Control-Allow-Methods", "GET,HEAD,PUT,PATCH,POST,DELETE")
                response?.header("Access-Control-Allow-Origin", request?.headers("Origin"))
                response?.header("Access-Control-Allow-Headers", "authorization,content-type")
                response?.header("Access-Control-Allow-Credentials", "true")
                response?.header("Vary", "Origin, Access-Control-Request-Headers")
                response?.type("application/json")
            }
        })
    }
}