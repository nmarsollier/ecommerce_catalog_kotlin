package model.security.dao

import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.util.EntityUtils
import utils.env.Environment
import utils.env.Log
import utils.gson.jsonToObject

class TokenDao private constructor() {
    fun retrieveUser(token: String?): User? {
        return try {
            httpClient().execute(
                HttpGet("${Environment.env.securityServerUrl}/v1/users/current").apply {
                    addHeader("Authorization", token)
                }
            ).let {
                if (it.statusLine.statusCode != 200) {
                    return null
                }

                it.entity ?: return null

                EntityUtils.toString(it.entity).jsonToObject<User>()
            }
        } catch (e: Exception) {
            Log.error(e)
            null
        }
    }

    private fun httpClient() = HttpClientBuilder.create().build()

    companion object {
        private var currentInstance: TokenDao? = null

        fun instance(): TokenDao {
            return currentInstance ?: TokenDao().also {
                currentInstance = it
            }
        }
    }
}