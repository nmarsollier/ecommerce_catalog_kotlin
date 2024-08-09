package rabbit

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val rabbitModule = module {
    singleOf(::ConsumeCatalogCatalog)
    singleOf(::ConsumeAuthLogout)
    singleOf(::ConsumeCatalogOrderPlaced)
    singleOf(::EmitArticleData)
    singleOf(::EmitArticleValidation)
    singleOf(::Consumers)
}