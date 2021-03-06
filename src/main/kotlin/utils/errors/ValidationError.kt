package utils.errors

import com.google.gson.annotations.SerializedName
import utils.gson.toJson
import java.util.*

/**
 * Un error de validaciones de atributos de una clase.
 * Estos errores se pueden serializar como Json.
 */
data class ValidationError(
    var messages: MutableList<ValidationMessage> = mutableListOf()
) : Exception() {
    val isEmpty: Boolean
        get() = messages.size == 0

    fun addPath(path: String?, message: String?): ValidationError {
        messages.add(ValidationMessage(path, message))
        return this
    }

    fun toJson() = SerializedMessage(messages).toJson()

    data class ValidationMessage(
        @SerializedName("path")
        var path: String?,
        @SerializedName("message")
        var message: String?
    )

    data class SerializedMessage(
        @SerializedName("messages")
        var messages: MutableList<ValidationMessage> = mutableListOf()
    )
}