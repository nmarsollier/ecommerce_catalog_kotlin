package model.article

import org.bson.types.ObjectId
import utils.db.MongoStore
import utils.errors.ValidationError

class ArticleRepository {
    companion object {
        fun save(article: Article): Article {
            MongoStore.dataStore.save(article)
            return article
        }

        fun findById(id: String?): Article {
            if (id.isNullOrBlank()) {
                throw ValidationError().addPath("id", "Not found")
            }

            return MongoStore.dataStore[Article::class.java, ObjectId(id)]
                ?: throw ValidationError().addPath("id", "Not found")
        }

        fun findByCriteria(criteria: String?): List<Article> {
            val q = MongoStore.dataStore.createQuery(
                Article::class.java
            )

            q.and(
                q.criteria("enabled").equal(true),
                q.or(
                    q.criteria("description.name").contains(criteria),
                    q.criteria("description.description").contains(criteria)
                )
            )

            return q.fetch().toList()
        }
    }
}