package model.article

import java.util.*


/**
 * Deshabilita el articulo para que no se pueda usar mas
 */
fun Article.disable() {
    enabled = false
    updated = Date()
}
