package rabbit

import model.article.repository.ArticlesRepository
import utils.env.Log
import utils.errors.ValidationError
import utils.gson.jsonToObject
import utils.rabbit.DirectConsumer
import utils.rabbit.RabbitEvent
import utils.validator.validate

class ConsumeCatalogArticleData private constructor(
    private val repository: ArticlesRepository = ArticlesRepository.instance()
) {
    private fun init() {
        DirectConsumer("catalog", "catalog").apply {
            addProcessor("model.article-data") { e: RabbitEvent? -> processArticleData(e) }
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
     *      "type": "model.article-exist",
     *      "exchange" : "{Exchange name to reply}"
     *      "queue" : "{Queue name to reply}"
     *      "message" : {
     *              "referenceId": "{redId}",
     *              "articleId": "{articleId}"
     *      }
     */
    private fun processArticleData(event: RabbitEvent?) {
        event?.message?.toString()?.jsonToObject<EventArticleExist>()?.let {
            try {
                Log.info("RabbitMQ Consume model.article-data : ${it.articleId}")
                it.validate()
                val article = repository.findById(it.articleId!!)?.value() ?: return
                val data = EventArticleData(
                    articleId = article.id,
                    price = article.price,
                    referenceId = it.referenceId,
                    stock = article.stock,
                    valid = article.enabled
                )
                EmitArticleData.sendArticleData(event, data)
            } catch (validation: ValidationError) {
                val data = EventArticleData(
                    articleId = it.articleId,
                    referenceId = it.referenceId,
                    valid = false
                )
                EmitArticleData.sendArticleData(event, data)
            } catch (e: Exception) {
                Log.error(e)
            }
        }
    }

    companion object {
        private var currentInstance: ConsumeCatalogArticleData? = null

        fun init() {
            currentInstance ?: ConsumeCatalogArticleData().also {
                it.init()
                currentInstance = it
            }
        }
    }
}