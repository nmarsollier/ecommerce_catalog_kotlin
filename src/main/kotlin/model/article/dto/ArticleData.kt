package model.article.dto

import com.google.gson.annotations.SerializedName
import utils.validator.MaxLen
import utils.validator.MinLen
import utils.validator.Required

/**
 * Objeto valor para artículos.
 */
data class ArticleData(
    @SerializedName("_id")
    val id: String? = null,

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

    @SerializedName("price")
    val price: Double = 0.0,

    @SerializedName("stock")
    val stock: Int = 0,

    @SerializedName("enabled")
    val enabled: Boolean = true
)