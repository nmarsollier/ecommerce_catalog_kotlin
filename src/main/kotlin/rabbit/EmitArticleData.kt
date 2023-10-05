package rabbit

import com.google.gson.annotations.SerializedName
import model.article.Article
import model.article.dto.asArticleData
import utils.rabbit.DirectPublisher
import utils.rabbit.RabbitEvent
import utils.validator.Required

class EmitArticleData {

    companion object {

        /**
         *
         * @api {direct} cart/model.article-exist Validación de Articulos
         *
         * @apiGroup RabbitMQ POST
         *
         * @apiDescription Enviá de mensajes model.article-data desde cart. Valida articulos
         *
         * @apiSuccessExample {json} Mensaje
         * {
         *      "type": "article-data",
         *      "message" : {
         *          "cartId": "{cartId}",
         *          "articleId": "{articleId}",
         *          "valid": True|False,
         *          "stock": {stock}
         *          "price": {price}
         *      }
         * }
         */
        fun sendArticleData(exchange: String?, queue: String?, send: EventArticleData) {
            val eventToSend = RabbitEvent(
                type = "article-data",
                message = send
            )
            DirectPublisher.publish(exchange, queue, eventToSend)
        }
    }
}

data class EventArticleData(
    @Required
    @SerializedName("articleId")
    val articleId: String? = null,

    @Required
    @SerializedName("referenceId")
    val referenceId: String? = null,

    @SerializedName("valid")
    val valid: Boolean = false,

    @SerializedName("price")
    val price: Double = 0.0,

    @SerializedName("stock")
    val stock: Int = 0
) {
    fun publishOn(exchange: String?, queue: String?) {
        EmitArticleData.sendArticleData(exchange, queue, this)
    }
}

fun Article.asEventArticleData(referenceId: String?): EventArticleData {
    val articleData = this.asArticleData
    return EventArticleData(
        articleId = articleData.id,
        price = articleData.price,
        referenceId = referenceId,
        stock = articleData.stock,
        valid = articleData.enabled
    )
}

fun EventArticleExist.asEventArticleData(): EventArticleData {
    return EventArticleData(
        articleId = this.articleId,
        referenceId = this.referenceId,
        price = 0.0,
        stock = 0,
        valid = false
    )
}