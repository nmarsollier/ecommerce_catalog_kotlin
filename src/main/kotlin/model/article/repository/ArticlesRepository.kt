package model.article.repository

import model.article.Article
import model.db.MongoStore

class ArticlesRepository(
    private var store: MongoStore
) {
    fun save(article: Article): Article {
        store.save(article)
        return article
    }

    fun findById(id: String): Article? {
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
}