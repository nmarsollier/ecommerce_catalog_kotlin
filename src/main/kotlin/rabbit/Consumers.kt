package rabbit

class Consumers private constructor() {
    companion object {
        fun init() {
            ConsumeAuthLogout.init()
            ConsumeCatalogArticleExist.init()
            ConsumeCatalogArticleData.init()
            ConsumeCatalogOrderPlaced.init()
        }
    }
}