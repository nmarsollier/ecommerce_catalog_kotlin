package model.security

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import model.security.dto.User
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.util.EntityUtils
import utils.env.Environment
import utils.errors.SimpleError
import utils.gson.jsonToObject
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit

/**
 * @apiDefine AuthHeader
 *
 * @apiExample {String} Header Autorización
 * Authorization=bearer {token}
 *
 * @apiErrorExample 401 Unauthorized
 * HTTP/1.1 401 Unauthorized
 */

private val UnauthorizedError = SimpleError(401, "Unauthorized")

object TokenService {

    var map = CacheBuilder
        .newBuilder()
        .maximumSize(1000)
        .expireAfterWrite(60, TimeUnit.MINUTES)
        .build(object : CacheLoader<String, User>() {
            override fun load(key: String): User {
                return retrieveUser(key) ?: throw UnauthorizedError
            }
        })


    fun validateAdmin(token: String) {
        validate(token)

        try {
            val cachedUser = map[token]

            cachedUser ?: throw UnauthorizedError

            if (cachedUser.permissions?.contains("admin") != true) {
                throw UnauthorizedError
            }
        } catch (e: ExecutionException) {
            throw e.cause as Exception
        } catch (e: Exception) {
            throw e
        }
    }

    fun validate(token: String) {
        if (token.isBlank()) {
            throw UnauthorizedError
        }
        try {
            map[token]
        } catch (e: ExecutionException) {
            throw e.cause as Exception
        } catch (e: Exception) {
            throw e
        }
    }

    fun invalidate(token: String) {
        map.invalidate(token)
    }

    private fun retrieveUser(token: String?): User? {
        val client: HttpClient = HttpClientBuilder.create().build()
        val request = HttpGet(Environment.env.securityServerUrl + "/v1/users/current")
        request.addHeader("Authorization", token)

        return try {
            val response = client.execute(request)
            if (response.statusLine.statusCode != 200) {
                return null
            }

            val responseEntity = response.entity ?: return null

            EntityUtils.toString(responseEntity).jsonToObject<User>()
        } catch (e: Exception) {
            null
        }
    }
}