package model.article

import model.article.dto.ArticleData
import model.article.dto.DescriptionData
import model.article.dto.NewData
import org.bson.types.ObjectId
import org.mongodb.morphia.annotations.Entity
import org.mongodb.morphia.annotations.Id
import utils.errors.ValidationError
import utils.validator.validate
import java.util.*

/**
 * Es el Agregado principal de Articulo.
 */
@Entity("articles")
class Article {
    @Id
    private val id: ObjectId? = null
    private var description: Description? = null
    private var price = 0.0
    private var stock = 0
    private var updated = Date()
    private val created = Date()
    private var enabled = true

    /**
     * Actualiza la descripción de un articulo.
     */
    fun updateDescription(data: DescriptionData) {
        data.validate()
        description = Description(
            name = data.name,
            description = data.description,
            image = data.image
        )
        updated = Date()
    }

    /**
     * Actualiza el precio de un articulo.
     */
    fun updatePrice(price: Double) {
        if (price < 0) {
            throw ValidationError().addPath("price", "Inválido")
        }

        this.price = price
        updated = Date()
    }

    /**
     * Actualiza el stock actual de un articulo.
     */
    fun updateStock(stock: Int) {
        if (stock < 0) {
            throw ValidationError().addPath("stock", "Inválido")
        }

        this.stock = stock
        updated = Date()
    }

    /**
     * Deshabilita el articulo para que no se pueda usar mas
     */
    fun disable() {
        enabled = false
        updated = Date()
    }

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

    data class Description(
        var name: String? = null,
        var description: String? = null,
        var image: String? = null,
    )

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