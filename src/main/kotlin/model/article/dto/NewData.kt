package model.article.dto

import com.google.gson.annotations.SerializedName
import utils.validator.MaxLen
import utils.validator.MinLen
import utils.validator.MinValue
import utils.validator.Required

/**
 * Objeto valor para crear un articulo nuevo.
 */
data class NewData(
    @SerializedName("name")
    @Required
    @MinLen(1)
    @MaxLen(60)
    val name: String? = null,

    @SerializedName("description")
    @MaxLen(2048)
    val description: String? = null,

    @SerializedName("image")
    @MinLen(30)
    @MaxLen(40)
    val image: String? = null,

    @JvmField
    @MinValue(0)
    @SerializedName("price")
    val price: Double = 0.0,

    @JvmField
    @SerializedName("stock")
    @MinValue(0)
    val stock: Int = 0
)