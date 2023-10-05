package rabbit

import com.google.gson.annotations.SerializedName
import utils.gson.jsonToObject
import utils.rabbit.DirectPublisher
import utils.rabbit.RabbitEvent
import utils.validator.Required
import utils.validator.validate

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
         *      "type": "article-exist",
         *      "message" : {
         *          "cartId": "{cartId}",
         *          "articleId": "{articleId}",
         *          "valid": True|False
         *      }
         * }
         */
        fun sendArticleValidation(exchange: String?, queue: String?, send: EventArticleExist) {
            val eventToSend = RabbitEvent(
                type = "article-exist",
                message = send
            )
            DirectPublisher.publish(exchange, queue, eventToSend)
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
) {
    fun publishOn(exchange: String?, queue: String?) {
        EmitArticleValidation.sendArticleValidation(
            exchange, queue, this
        )
    }
}

val RabbitEvent?.asEventArticleExist: EventArticleExist?
    get() = this?.message?.toString()?.jsonToObject<EventArticleExist>()?.validate
