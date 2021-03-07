package utils.errors

import com.google.gson.annotations.SerializedName
import utils.gson.toJson

/**
 * Es un error simple que se puede serializar como Json.
 */
data class UnauthorizedError(
    val error: String = "Unauthorized"
) : Exception() {

    fun toJson(): String {
        return SerializedMessage(error).toJson()
    }

    data class SerializedMessage(
        @SerializedName("error")
        var error: String? = null
    )
}