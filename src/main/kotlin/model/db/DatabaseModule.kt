package model.db

import model.db.MongoStore
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val databaseModule = module {
    singleOf(::MongoStore)
}