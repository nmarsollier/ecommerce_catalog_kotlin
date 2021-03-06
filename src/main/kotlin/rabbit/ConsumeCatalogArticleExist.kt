package rabbit

import model.article.repository.ArticleRepository
import utils.env.Log
import utils.errors.ValidationError
import utils.gson.jsonToObject
import utils.rabbit.DirectConsumer
import utils.rabbit.RabbitEvent
import utils.validator.validate

object ConsumeCatalogArticleExist {
    fun init() {
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
     * "type": "model.article-exist",
     * "exchange" : "{Exchange name to reply}"
     * "queue" : "{Queue name to reply}"
     * "message" : {
     * "referenceId": "{redId}",
     * "articleId": "{articleId}",
     * }
     */
    private fun processArticleExist(event: RabbitEvent?) {
        event?.message?.toString()?.jsonToObject<EventArticleExist>()?.let {
            try {
                Log.info("RabbitMQ Consume model.article-exist : ${it.articleId}")
                it.validate()
                val article = ArticleRepository.instance().findById(it.articleId)
                EmitArticleValidation.sendArticleValidation(event, it.copy(valid = article.isEnabled()))
            } catch (validation: ValidationError) {
                EmitArticleValidation.sendArticleValidation(event, it.copy(valid = false))
            } catch (e: Exception) {
                Log.error(e)
            }
        }
    }
}