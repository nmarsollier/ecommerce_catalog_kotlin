package model.article.repository

import com.mongodb.client.model.Filters
import com.mongodb.client.model.TextSearchOptions
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import model.article.Article
import model.db.MongoStore
import org.bson.types.ObjectId
import java.util.regex.Pattern

class ArticlesRepository(
    store: MongoStore
) {
    val collection = store.collection<Article>("articles")

    suspend fun save(article: Article): Article {
        if (article.id == null) {
            collection.insertOne(article)
        } else {
            collection.replaceOne(Filters.eq("_id", article.id), article)
        }
        return article
    }

    suspend fun findById(id: String): Article? {
        return collection.find(Filters.eq("_id", ObjectId(id))).firstOrNull()
    }

    suspend fun findByCriteria(criteria: String?): List<Article> {
        val filter = ".*?${Pattern.quote(criteria)}.*?"
        return collection.find(
            Filters.and(
                Filters.eq("enabled", true),
                Filters.or(
                    Filters.regex("description.name", filter),
                    Filters.regex("description.description", filter)
                )
            )
        ).limit(100).toList()
    }
}