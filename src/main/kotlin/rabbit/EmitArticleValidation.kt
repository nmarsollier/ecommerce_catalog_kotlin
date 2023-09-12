package rabbit

import com.google.gson.annotations.SerializedName
import utils.rabbit.DirectPublisher
import utils.rabbit.RabbitEvent
import utils.validator.Required

class EmitArticleValidation {
    companion object {
        /**
         *
         * @api {direct} cart/model.article-exist Validación de Artículos
         *
         * @apiGroup RabbitMQ POST
         *
         * @apiDescription Enviá de mensajes model.article-exist desde cart. Valida artículos
         *
         * @apiSuccessExample {json} Mensaje
         * {
         *      "type": "model.article-exist",
         *      "message" : {
         *          "cartId": "{cartId}",
         *          "articleId": "{articleId}",
         *          "valid": True|False
         *      }
         * }
         */
        fun sendArticleValidation(event: RabbitEvent, send: EventArticleExist) {
            val eventToSend = RabbitEvent(
                type = "model.article-exist",
                message = send
            )
            DirectPublisher.publish(event.exchange, event.queue, eventToSend)
        }
    }
}

data class EventArticleExist(
    @Required
    @SerializedName("articleId")
    val articleId: String? = null,

    @Required
    @SerializedName("referenceId")
    val referenceId: String? = null,

    @SerializedName("valid")
    val valid: Boolean = false
)