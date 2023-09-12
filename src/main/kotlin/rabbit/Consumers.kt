package rabbit

class Consumers(
    private val consumeAuthLogout: ConsumeAuthLogout,
    private val consumeCatalogArticleExist: ConsumeCatalogArticleExist,
    private val consumeCatalogArticleData: ConsumeCatalogArticleData,
    private val consumeCatalogOrderPlaced: ConsumeCatalogOrderPlaced
) {
    fun init() {
        consumeAuthLogout.init()
        consumeCatalogArticleExist.init()
        consumeCatalogArticleData.init()
        consumeCatalogOrderPlaced.init()
    }
}