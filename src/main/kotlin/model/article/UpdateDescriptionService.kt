package model.article

import model.article.dto.DescriptionData
import utils.validator.validate
import java.util.*

/**
 * Actualiza la descripción de un articulo.
 */
fun Article.updateDescription(data: DescriptionData) {
    data.validate()
    this.description = Description(
        name = data.name,
        description = data.description,
        image = data.image
    )
    this.updated = Date()
}
