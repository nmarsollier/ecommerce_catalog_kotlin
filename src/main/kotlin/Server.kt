import rabbit.Consumers
import rest.Routes
import utils.db.MongoStore
import utils.env.Environment
import utils.env.Log

fun main() {
    Server().start()
}

class Server {
    fun start() {
        Log.info("Order Service escuchando en el puerto : ${Environment.env.serverPort}")

        MongoStore.init()
        Routes.init()
        Consumers.init()
    }
}