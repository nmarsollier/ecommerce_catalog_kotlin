package model.db

import com.mongodb.MongoClient
import org.bson.types.ObjectId
import org.mongodb.morphia.Datastore
import org.mongodb.morphia.Key
import org.mongodb.morphia.Morphia
import org.mongodb.morphia.query.Query
import utils.env.Environment.env

/**
 * Permite la configuración del acceso a la db
 */
class MongoStore {
    val dataStore: Datastore

    init {
        val morphia = Morphia()
        val client = MongoClient(env.databaseUrl)
        dataStore = morphia.createDatastore(client, "catalog_kotlin").also {
            it.ensureIndexes()
        }
    }

    fun <T> save(entity: T): Key<T> {
        return dataStore.save(entity)
    }

    inline fun <reified T> findById(id: String): T {
        return dataStore[T::class.java, ObjectId(id)]
    }

    inline fun <reified T> createQuery(): Query<T>? {
        return dataStore.createQuery(T::class.java)
    }
}