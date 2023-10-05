package rabbit

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import model.article.ArticlesRepository
import utils.env.Log
import utils.errors.ValidationError
import utils.rabbit.DirectConsumer
import utils.rabbit.RabbitEvent
import utils.validator.validate

class ConsumeCatalogArticleExist(
    private val repository: ArticlesRepository
) {
    fun init() {
        DirectConsumer("catalog", "catalog").apply {
            addProcessor("article-exist") { e: RabbitEvent? -> processArticleExist(e) }
            start()
        }
    }

    /**
     *
     * @api {direct} catalog/model.article-exist Validación de Artículos
     *
     * @apiGroup RabbitMQ GET
     *
     * @apiDescription Escucha de mensajes model.article-exist desde cart. Valida artículos
     *
     * @apiExample {json} Mensaje
     * {
     *      "type": "article-exist",
     *      "exchange" : "{Exchange name to reply}"
     *      "queue" : "{Queue name to reply}"
     *      "message" : {
     *          "referenceId": "{redId}",
     *          "articleId": "{articleId}",
     *      }
     */
    private fun processArticleExist(event: RabbitEvent?) {
        event?.asEventArticleExist?.validate?.let { eventArticleExist ->
            try {
                Log.info("RabbitMQ Consume article-exist : ${eventArticleExist.articleId}")

                CoroutineScope(Dispatchers.IO).launch {
                    val article = repository.findById(eventArticleExist.articleId!!) ?: return@launch

                    eventArticleExist.copy(valid = article.entity.enabled)
                        .publishOn(event.exchange, event.queue)
                }
            } catch (validation: ValidationError) {
                CoroutineScope(Dispatchers.IO).launch {
                    eventArticleExist.copy(valid = false)
                        .publishOn(event.exchange, event.queue)
                }
            } catch (e: Exception) {
                Log.error(e)
            }
        }
    }
}