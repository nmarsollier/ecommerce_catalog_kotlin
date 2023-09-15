package rest

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import utils.http.ErrorHandler

val articlesRoutesModule = module {
    singleOf(::PostArticles)
    singleOf(::PostArticlesId)
    singleOf(::GetArticleId)
    singleOf(::DeleteArticleId)
    singleOf(::GetArticlesSearchCriteria)
}

val routesModule = module {
    singleOf(::ErrorHandler)
    singleOf(::Routes)
}