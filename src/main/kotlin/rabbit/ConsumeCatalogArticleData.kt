package rabbit

import model.article.repository.ArticleRepository
import utils.errors.ValidationError
import utils.gson.jsonToObject
import utils.rabbit.DirectConsumer
import utils.rabbit.RabbitEvent
import utils.validator.validate

object ConsumeCatalogArticleData {
    fun init() {
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
     * "type": "model.article-exist",
     * "exchange" : "{Exchange name to reply}"
     * "queue" : "{Queue name to reply}"
     * "message" : {
     * "referenceId": "{redId}",
     * "articleId": "{articleId}"
     * }
     */
    private fun processArticleData(event: RabbitEvent?) {
        event?.message?.toString()?.jsonToObject<EventArticleExist>()?.let {
            try {
                println("RabbitMQ Consume model.article-data : " + it.articleId)
                it.validate()
                val article = ArticleRepository.instance().findById(it.articleId).value()
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
                e.printStackTrace()
            }
        }
    }
}