package rabbit

import model.article.repository.ArticlesRepository
import utils.env.Log
import utils.errors.ValidationError
import utils.gson.jsonToObject
import utils.rabbit.DirectConsumer
import utils.rabbit.RabbitEvent
import utils.validator.validate

class ConsumeCatalogArticleExist private constructor(
    private val repository: ArticlesRepository = ArticlesRepository.instance()
) {
    private fun init() {
        DirectConsumer("catalog", "catalog").apply {
            addProcessor("model.article-exist") { e: RabbitEvent? -> processArticleExist(e) }
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
     *      "type": "model.article-exist",
     *      "exchange" : "{Exchange name to reply}"
     *      "queue" : "{Queue name to reply}"
     *      "message" : {
     *          "referenceId": "{redId}",
     *          "articleId": "{articleId}",
     *      }
     */
    private fun processArticleExist(event: RabbitEvent?) {
        event?.message?.toString()?.jsonToObject<EventArticleExist>()?.let {
            try {
                Log.info("RabbitMQ Consume model.article-exist : ${it.articleId}")
                it.validate()
                val article = repository.findById(it.articleId!!) ?: return
                EmitArticleValidation.sendArticleValidation(event, it.copy(valid = article.isEnabled()))
            } catch (validation: ValidationError) {
                EmitArticleValidation.sendArticleValidation(event, it.copy(valid = false))
            } catch (e: Exception) {
                Log.error(e)
            }
        }
    }

    companion object {
        private var currentInstance: ConsumeCatalogArticleExist? = null

        fun init() {
            currentInstance ?: ConsumeCatalogArticleExist().also {
                it.init()
                currentInstance = it
            }
        }
    }
}