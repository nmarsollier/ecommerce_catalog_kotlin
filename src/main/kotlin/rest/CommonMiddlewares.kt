package rest

import io.javalin.http.Context
import model.security.TokenService
import model.security.validateAdminUser
import org.bson.types.ObjectId
import utils.env.Log
import utils.errors.UnauthorizedError
import utils.errors.ValidationError
import utils.javalin.NextFun

val validateAdminUser = { ctx: Context, _: NextFun ->
    val authHeader = ctx.header("Authorization") ?: throw UnauthorizedError()
    TokenService.instance().validateAdminUser(authHeader)
}

val validateArticleId = { ctx: Context, _: NextFun ->
    try {
        val id = ctx.pathParam("articleId")

        if (id.isBlank()) {
            throw ValidationError().addPath("id", "Not found")
        }

        ObjectId(id)
    } catch (e: Exception) {
        Log.error(e)
        throw ValidationError().addPath("id", "Not found")
    }
    Unit
}