package rabbit

import com.google.gson.annotations.SerializedName
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
         *      "type": "model.article-data",
         *      "message" : {
         *          "cartId": "{cartId}",
         *          "articleId": "{articleId}",
         *          "valid": True|False,
         *          "stock": {stock}
         *          "price": {price}
         *      }
         * }
         */
        fun sendArticleData(event: RabbitEvent, send: EventArticleData) {
            val eventToSend = RabbitEvent(
                type = "model.article-data",
                message = send
            )
            DirectPublisher.publish(event.exchange, event.queue, eventToSend)
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
)