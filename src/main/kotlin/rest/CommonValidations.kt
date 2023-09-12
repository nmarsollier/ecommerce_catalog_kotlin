package rest

import io.ktor.server.application.*
import io.ktor.server.request.*
import model.security.TokenService
import model.security.validateAdminUser
import utils.errors.UnauthorizedError
import utils.errors.ValidationError

class CommonValidations(
    val tokenService: TokenService
) {
    fun validateAdminUser(tokenHeader: String?) {
        val authHeader = tokenHeader ?: throw UnauthorizedError()

        tokenService.validateAdminUser(authHeader)
    }

    fun validateArticleId(articleId: String) {
        if (articleId.isBlank()) {
            throw ValidationError().addPath("id", "Not found")
        }
    }
}

val ApplicationCall.authHeader: String?
    get() = request.header("Authorization")