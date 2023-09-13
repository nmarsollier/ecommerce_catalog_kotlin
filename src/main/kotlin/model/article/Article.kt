package model.article

import com.google.gson.annotations.SerializedName
import model.article.dto.ArticleData
import model.article.dto.NewData
import org.bson.BsonType
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonRepresentation
import org.bson.types.ObjectId
import utils.validator.validate
import java.util.*

/**
 * Es el Agregado principal de Articulo.
 */
data class Article(
    @BsonId
    @BsonRepresentation(BsonType.OBJECT_ID)
    val id: String? = null,
    internal var description: Description? = null,
    internal var price: Double = 0.0,
    internal var stock: Int = 0,
    internal var updated: Date = Date(),
    internal val created: Date = Date(),
    internal var enabled: Boolean = true
) {
    fun isEnabled(): Boolean {
        return enabled
    }

    /**
     * Obtiene una representación interna de los valores.
     * Preserva la inmutabilidad de la entidad.
     */
    fun value(): ArticleData {
        return ArticleData(
            id = id,
            name = description?.name,
            description = description?.description,
            image = description?.image,
            price = price,
            stock = stock,
            enabled = enabled
        )
    }

    companion object {
        /**
         * Crea un nuevo articulo
         */
        fun newArticle(data: NewData): Article {
            data.validate()
            return Article().apply {
                description = Description(
                    name = data.name,
                    description = data.description,
                    image = data.image,
                )
                price = data.price
                stock = data.stock
            }
        }
    }
}

data class Description(
    var name: String? = null,
    var description: String? = null,
    var image: String? = null,
)
