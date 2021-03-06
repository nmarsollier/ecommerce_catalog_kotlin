package utils.db

import com.mongodb.MongoClient
import org.mongodb.morphia.Datastore
import org.mongodb.morphia.Morphia
import utils.env.Environment.env
import java.util.logging.Level
import java.util.logging.Logger

/**
 * Permite la configuración del acceso a la db
 */
object MongoStore {
    private var store: Datastore? = null

    fun init() {
        val morphia = Morphia()
        val client = MongoClient(env.databaseUrl)
        store = morphia.createDatastore(client, "catalog_java").also {
            it.ensureIndexes()
        }
    }

    val dataStore: Datastore
        get() {
            if (store == null) {
                init()
            }
            return store!!
        }

    init {
        Logger.getLogger("org.mongodb.driver").level = Level.SEVERE
        Logger.getLogger("org.mongodb.morphia").level = Level.SEVERE
    }
}