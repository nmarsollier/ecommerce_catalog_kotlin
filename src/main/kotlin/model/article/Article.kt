package model.article

import model.article.dto.NewData
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
    fun disable() {
        entity = entity.copy(
            enabled = false,
            updated = Date()
        )
    }

    /**
     * Actualiza la descripción de un articulo.
     */
    fun updateDescription(data: NewData) {
        data.validate()
        entity = entity.copy(
            description = DescriptionEntity(
                name = data.name,
                description = data.description,
                image = data.image
            ),
            updated = Date()
        )
    }

    /**
     * Actualiza el precio de un articulo.
     */
    fun updatePrice(price: Double) {
        if (price < 0) {
            throw ValidationError().addPath("price", "Inválido")
        }

        entity = entity.copy(
            price = price,
            updated = Date()
        )
    }

    /**
     * Actualiza el stock actual de un articulo.
     */
    fun updateStock(stock: Int) {
        if (stock < 0) {
            throw ValidationError().addPath("stock", "Inválido")
        }

        entity = entity.copy(
            stock = stock,
            updated = Date()
        )
    }

    /**
     * Obtiene una representación interna de los valores.
     * Preserva la inmutabilidad de la entidad.
     */
    companion object {
        fun newArticle(data: NewData): Article {
            data.validate()
            return Article(
                ArticleEntity(
                    description = DescriptionEntity(
                        name = data.name,
                        description = data.description,
                        image = data.image,
                    ),
                    price = data.price,
                    stock = data.stock
                )
            )
        }
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
