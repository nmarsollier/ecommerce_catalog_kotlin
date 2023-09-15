package model

import model.article.ArticlesRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val articlesModule = module {
    singleOf(::ArticlesRepository)
}