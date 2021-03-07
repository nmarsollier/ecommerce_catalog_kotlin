package rest

import model.security.TokenService
import model.security.validateAdminUser
import org.bson.types.ObjectId
import spark.Request
import spark.Response
import utils.env.Log
import utils.errors.ValidationError
import utils.spark.NextFun

val validateAdminUser = { req: Request, res: Response, next: NextFun ->
    TokenService.instance().validateAdminUser(req.headers("Authorization"))
}

val validateArticleId = { req: Request, res: Response, next: NextFun ->
    try {
        val id = req.params(":articleId")

        if (id.isNullOrBlank()) {
            throw ValidationError().addPath("id", "Not found")
        }

        ObjectId(id)
    } catch (e: Exception) {
        Log.error(e)
        throw ValidationError().addPath("id", "Not found")
    }
    Unit
}