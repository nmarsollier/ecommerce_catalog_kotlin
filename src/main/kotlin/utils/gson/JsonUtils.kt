package utils.gson

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.util.logging.Level
import java.util.logging.Logger

/**
 * Convert json String to an Object T
 * T can be a list or an objet type
 */
inline fun <reified T> String?.jsonToObject(): T? {
    if (this == null) {
        return null
    }
    return try {
        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
        gson.fromJson(this, object : TypeToken<T>() {}.type)
    } catch (e: JsonSyntaxException) {
        null
    }
}

/**
 * Convert Object to Json
 * T can be a list or an objet type
 */
fun Any.toJson(): String {
    return Gson().toJson(this)
}

/**
 * Quickly create a json objet based on the pair param.
 *
 * @param params a pair objects with property-value map
 * @param includeNulls if false will exclude properties with null values
 */
fun jsonObject(vararg params: Pair<String, Any?>, includeNulls: Boolean = true): JsonObject {
    val json = JsonObject()
    params.forEach {
        val key = it.first
        when (val value = it.second) {
            null -> if (includeNulls) json.add(key, JsonNull.INSTANCE)
            is String -> json.addProperty(key, value)
            is Number -> json.addProperty(key, value)
            is Boolean -> json.addProperty(key, value)
            is List<*> -> json.add(key, jsonArray(*value.toTypedArray()))
            is JsonElement -> json.add(key, value)
            else -> {
                Logger.getGlobal().log(Level.SEVERE, "JsonUtils.jsonObject", "Invalid type ${value::class}")
            }
        }
    }
    return json
}

/**
 * Converts array of objects into a JsonArray
 * It can handle basic values, but you can add your own converters
 */
fun jsonArray(vararg entries: Any?): JsonArray {
    val json = JsonArray()
    entries.forEach {
        when (it) {
            null -> json.add(JsonNull.INSTANCE)
            is String -> json.add(it)
            is Number -> json.add(it)
            is Boolean -> json.add(it)
            is List<*> -> json.add(jsonArray(*it.toTypedArray()))
            is JsonElement -> json.add(it)
            else -> {
                Logger.getGlobal().log(Level.SEVERE, "JsonUtils.jsonArray", "Invalid value for key $it")
            }
        }
    }
    return json
}
