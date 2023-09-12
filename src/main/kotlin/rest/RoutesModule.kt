package rest

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

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
    singleOf(::CommonValidations)
}