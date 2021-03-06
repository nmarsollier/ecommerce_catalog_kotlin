package model.article

import utils.errors.ValidationError
import java.util.*


/**
 * Actualiza el stock actual de un articulo.
 */
fun Article.updateStock(stock: Int) {
    if (stock < 0) {
        throw ValidationError().addPath("stock", "Inválido")
    }

    this.stock = stock
    this.updated = Date()
}