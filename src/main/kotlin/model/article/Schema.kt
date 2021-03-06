package model.article

import model.article.dto.ArticleData
import model.article.dto.NewData
import org.bson.types.ObjectId
import org.mongodb.morphia.annotations.Entity
import org.mongodb.morphia.annotations.Id
import utils.validator.validate
import java.util.*

/**
 * Es el Agregado principal de Articulo.
 */
@Entity("articles")
class Article {
    @Id
    val id: ObjectId? = null
    internal var description: Description? = null
    internal var price = 0.0
    internal var stock = 0
    internal var updated = Date()
    internal val created = Date()
    internal var enabled = true


    fun isEnabled(): Boolean {
        return enabled
    }

    /**
     * Obtiene una representación interna de los valores.
     * Preserva la inmutabilidad de la entidad.
     */
    fun value(): ArticleData {
        return ArticleData(
            id = id?.toHexString(),
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
