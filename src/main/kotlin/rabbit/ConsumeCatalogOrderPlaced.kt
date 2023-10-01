package rabbit

import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import model.article.ArticlesRepository
import model.article.saveIn
import utils.env.Log
import utils.errors.ValidationError
import utils.gson.jsonToObject
import utils.rabbit.RabbitEvent
import utils.rabbit.TopicConsumer
import utils.validator.Required
import utils.validator.validate

class ConsumeCatalogOrderPlaced(
    private val repository: ArticlesRepository
) {
    fun init() {
        TopicConsumer("sell_flow", "topic_catalog", "order_placed").apply {
            addProcessor("order-placed") { e: RabbitEvent? -> processOrderPlaced(e) }
            start()
        }
    }

    /**
     *
     * @api {topic} order/order-placed Orden Creada
     *
     * @apiGroup RabbitMQ
     *
     * @apiDescription Consume de mensajes order-placed desde Order con el topic "order_placed".
     *
     * @apiSuccessExample {json} Mensaje
     * {
     * "type": "order-placed",
     * "message" : {
     * "cartId": "{cartId}",
     * "orderId": "{orderId}"
     * "articles": [{
     * "articleId": "{model.article id}"
     * "quantity" : {quantity}
     * }, ...]
     * }
     * }
     */
    private fun processOrderPlaced(event: RabbitEvent?) {
        event?.asOrderPlacedEvent?.let {
            try {
                Log.info("RabbitMQ Consume order-placed : ${it.orderId}")

                it.validate.articles.forEach { a ->
                    try {
                        MainScope().launch {
                            (repository.findById(a.articleId!!) ?: return@launch)
                                .decreaseStock(a.quantity)
                                .saveIn(repository)
                                .asEventArticleData(referenceId = it.orderId)
                                .publishOn(event.exchange, event.queue)
                        }
                    } catch (validation: ValidationError) {
                        a.asEventArticleData(referenceId = it.orderId)
                            .publishOn(event.exchange, event.queue)
                    }
                }
            } catch (e: Exception) {
                Log.error(e)
            }
        }
    }
}

private data class OrderPlacedEvent(
    @SerializedName("orderId")
    var orderId: String? = null,

    @SerializedName("cartId")
    val cartId: String? = null,

    @SerializedName("articles")
    val articles: Array<Article> = emptyArray()
) {

    data class Article(
        @SerializedName("articleId")
        @Required
        val articleId: String? = null,

        @SerializedName("quantity")
        @Required
        val quantity: Int = 0
    )
}

private val RabbitEvent?.asOrderPlacedEvent
    get() = this?.message?.toString()?.jsonToObject<OrderPlacedEvent>()?.validate

private fun OrderPlacedEvent.Article.asEventArticleData(referenceId: String?) = EventArticleData(
    articleId = this.articleId,
    referenceId = referenceId,
    valid = false
)