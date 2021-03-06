package rabbit

class Consumers {
    companion object {
        fun init() {
            ConsuimeAuthLogout.init()
            ConsumeCatalogArticleExist.init()
            ConsumeCatalogArticleData.init()
            ConsumeCatalogOrderPlaced.init()
        }
    }
}