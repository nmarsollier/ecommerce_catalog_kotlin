package model.security

import java.util.concurrent.ExecutionException

fun TokenService.validateUser(token: String) {
    if (token.isBlank()) {
        throw UnauthorizedError
    }

    getUserByToken(token)
}