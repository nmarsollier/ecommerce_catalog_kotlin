package model.article

import model.article.dto.NewArticleData
import org.bson.BsonType
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.codecs.pojo.annotations.BsonRepresentation
import utils.errors.ValidationError
import utils.validator.validate
import java.util.*

class Article(entityRoot: ArticleEntity) {
    var entity: ArticleEntity = entityRoot
        private set

    /**
     * Deshabilita el articulo para que no se pueda usar mas
     */
    fun disable(): Article {
        entity = entity.copy(
            enabled = false,
            updated = Date()
        )
        return this
    }

    /**
     * Actualiza la descripción de un articulo.
     */
    fun updateDescription(data: NewArticleData): Article {
        data.validate
        entity = entity.copy(
            description = DescriptionEntity(
                name = data.name,
                description = data.description,
                image = data.image
            ),
            updated = Date()
        )
        return this
    }

    /**
     * Actualiza el precio de un articulo.
     */
    fun updatePrice(price: Double): Article {
        if (price < 0) {
            throw ValidationError("price" to "Inválido")
        }

        entity = entity.copy(
            price = price,
            updated = Date()
        )

        return this
    }

    /**
     * Actualiza el stock actual de un articulo.
     */
    fun updateStock(stock: Int): Article {
        if (stock < 0) {
            throw ValidationError("stock" to "Inválido")
        }

        entity = entity.copy(
            stock = stock,
            updated = Date()
        )

        return this
    }

    fun decreaseStock(stock: Int): Article {
        val newStock = entity.stock - stock

        if (newStock < 0) {
            throw ValidationError("stock" to "Inválido")
        }

        entity = entity.copy(
            stock = newStock,
            updated = Date()
        )

        return this
    }
}

/**
 * Es el Agregado principal de Articulo.
 */
data class ArticleEntity(
    @BsonId
    @BsonRepresentation(BsonType.OBJECT_ID)
    val id: String? = null,
    val description: DescriptionEntity? = null,
    val price: Double = 0.0,
    val stock: Int = 0,
    val updated: Date = Date(),
    val created: Date = Date(),
    val enabled: Boolean = true
)

data class DescriptionEntity(
    val name: String? = null,
    val description: String? = null,
    val image: String? = null,
)
