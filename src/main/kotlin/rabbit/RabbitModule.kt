package rabbit

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val rabbitModule = module {
    singleOf(::ConsumeCatalogArticleData)
    singleOf(::ConsumeAuthLogout)
    singleOf(::ConsumeCatalogArticleExist)
    singleOf(::ConsumeCatalogOrderPlaced)
    singleOf(::EmitArticleData)
    singleOf(::EmitArticleValidation)
    singleOf(::Consumers)
}