package rabbit

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import model.article.ArticlesRepository
import utils.env.Log
import utils.errors.ValidationError
import utils.rabbit.DirectConsumer
import utils.rabbit.RabbitEvent
import utils.validator.validate

class ConsumeCatalogArticleData(
    private val repository: ArticlesRepository
) {
    fun init() {
        DirectConsumer("catalog", "catalog").apply {
            addProcessor("article-data") { e: RabbitEvent? -> processArticleData(e) }
            start()
        }
    }

    /**
     *
     * @api {direct} catalog/model.article-data Validación de Artículos
     *
     * @apiGroup RabbitMQ GET
     *
     * @apiDescription Escucha de mensajes model.article-data desde cart. Valida artículos
     *
     * @apiExample {json} Mensaje
     * {
     *      "type": "article-exist",
     *      "exchange" : "{Exchange name to reply}"
     *      "queue" : "{Queue name to reply}"
     *      "message" : {
     *              "referenceId": "{redId}",
     *              "articleId": "{articleId}"
     *      }
     */
    private fun processArticleData(event: RabbitEvent?) {
        event?.asEventArticleExist?.let {
            try {
                Log.info("RabbitMQ Consume model.article-data : ${it.articleId}")
                it.validate
                CoroutineScope(Dispatchers.IO).launch {
                    (repository.findById(it.articleId!!) ?: return@launch)
                        .asEventArticleData(it.referenceId)
                        .publishOn(event.exchange, event.queue)
                }
            } catch (validation: ValidationError) {
                CoroutineScope(Dispatchers.IO).launch {
                    it.asEventArticleData()
                        .publishOn(event.exchange, event.queue)
                }
            } catch (e: Exception) {
                Log.error(e)
            }
        }
    }
}