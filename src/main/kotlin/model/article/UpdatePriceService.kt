package model.article

import utils.errors.ValidationError
import java.util.*

/**
 * Actualiza el precio de un articulo.
 */
fun Article.updatePrice(price: Double) {
    if (price < 0) {
        throw ValidationError().addPath("price", "Inválido")
    }

    this.price = price
    this.updated = Date()
}
