package model.article.repository

import model.article.Article
import utils.db.MongoStore
import utils.errors.ValidationError

class ArticlesRepository private constructor(
    private var store: MongoStore = MongoStore.instance()
) {

    fun save(article: Article): Article {
        store.save(article)
        return article
    }

    fun findById(id: String?): Article {
        if (id.isNullOrBlank()) {
            throw ValidationError().addPath("id", "Not found")
        }

        return store.findById(id)
    }

    fun findByCriteria(criteria: String?): List<Article> {
        return store.createQuery<Article>()?.let {
            it.and(
                it.criteria("enabled").equal(true),
                it.or(
                    it.criteria("description.name").contains(criteria),
                    it.criteria("description.description").contains(criteria)
                )
            )
            it.fetch().toList()
        } ?: emptyList()
    }

    companion object {
        private var currentInstance: ArticlesRepository? = null

        fun instance(): ArticlesRepository {
            return currentInstance ?: ArticlesRepository().also {
                currentInstance = it
            }
        }
    }
}