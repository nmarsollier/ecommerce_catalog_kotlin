package model.article.dto

import com.google.gson.annotations.SerializedName
import model.article.Article
import model.article.ArticleEntity
import model.article.DescriptionEntity
import utils.validator.*

/**
 * Objeto valor para crear un articulo nuevo.
 */
data class NewArticleData(
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

    @MinValue(0)
    @SerializedName("price")
    val price: Double = 0.0,

    @SerializedName("stock")
    @MinValue(0)
    val stock: Int = 0
)

val NewArticleData.toNewArticle: Article
    get() {
        this.validate()

        return Article(
            ArticleEntity(
                description = DescriptionEntity(
                    name = this.name,
                    description = this.description,
                    image = this.image,
                ),
                price = this.price,
                stock = this.stock
            )
        )
    }
