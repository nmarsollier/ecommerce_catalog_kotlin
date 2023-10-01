package rest

import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.*
import io.ktor.server.routing.*
import utils.env.Environment
import utils.http.ErrorHandler
import java.io.File

class Routes(
    private val postArticles: PostArticles,
    private val postArticlesId: PostArticlesId,
    private val getArticleId: GetArticleId,
    private val deleteArticleId: DeleteArticleId,
    private val getArticlesSearchCriteria: GetArticlesSearchCriteria,
    private val errorHandler: ErrorHandler
) {

    fun init() {
        embeddedServer(
            Netty,
            port = Environment.env.serverPort,
            module = {
                install(CORS)
                install(ContentNegotiation) {
                    gson()
                }
                install(CallLogging)

                errorHandler.init(this)

                routing {
                    staticFiles("/", File(Environment.env.staticLocation))

                    postArticles.init(this)
                    postArticlesId.init(this)
                    getArticleId.init(this)
                    deleteArticleId.init(this)
                    getArticlesSearchCriteria.init(this)
                }
            }
        ).start(wait = true)
    }
}
