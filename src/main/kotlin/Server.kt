import model.articlesModule
import model.databaseModule
import model.securityModule
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
    private val routes: Routes by inject()
    private val consumers: Consumers by inject()

    fun start() {
        startKoin {
            modules(routesModule, databaseModule, rabbitModule, articlesModule, articlesRoutesModule, securityModule)
        }

        routes.init()
        consumers.init()
    }
}