import model.article.articlesModule
import model.databaseModule
import model.security.securityModule
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import rabbit.Consumers
import rabbit.rabbitModule
import rest.Routes
import rest.articlesRoutesModule
import rest.routesModule
import utils.env.Environment
import utils.env.Log

fun main() {
    Server().start()
}

class Server : KoinComponent {
    val routes: Routes by inject()
    val consumers: Consumers by inject()

    fun start() {
        Log.info("Order Service escuchando en el puerto : ${Environment.env.serverPort}")

        startKoin {
            modules(routesModule, databaseModule, rabbitModule, articlesModule, articlesRoutesModule, securityModule)
        }

        routes.init()
        consumers.init()
    }
}