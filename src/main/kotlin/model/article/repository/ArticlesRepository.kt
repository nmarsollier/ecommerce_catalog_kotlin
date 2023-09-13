package model.article.repository

import com.mongodb.client.model.Filters
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import model.article.Article
import model.article.ArticleEntity
import model.db.MongoStore
import org.bson.types.ObjectId
import java.util.regex.Pattern

class ArticlesRepository(
    store: MongoStore
) {
    private val collection = store.collection<ArticleEntity>("articles")

    suspend fun save(article: Article): Article {
        return if (article.entity.id == null) {
            Article(
                article.entity.copy(
                    id = collection.insertOne(article.entity).insertedId?.toString()
                )
            )
        } else {
            collection.replaceOne(Filters.eq("_id", article.entity.id), article.entity)
            article
        }
    }

    suspend fun findById(id: String): Article? {
        return collection.find(Filters.eq("_id", ObjectId(id))).firstOrNull()?.let {
            Article(it)
        }
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
        ).limit(100).toList().map { Article(it) }
    }
}