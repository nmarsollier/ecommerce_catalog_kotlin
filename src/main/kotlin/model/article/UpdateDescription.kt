package model.article

import model.article.dto.NewData
import utils.validator.validate
import java.util.*

/**
 * Actualiza la descripción de un articulo.
 */
fun Article.updateDescription(data: NewData) {
    data.validate()
    this.description = Description(
        name = data.name,
        description = data.description,
        image = data.image
    )
    this.updated = Date()
}
