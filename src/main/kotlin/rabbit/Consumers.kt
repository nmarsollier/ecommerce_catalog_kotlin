package rabbit

class Consumers(
    private val consumeAuthLogout: ConsumeAuthLogout,
    private val consumeCatalogCatalog: ConsumeCatalogCatalog,
    private val consumeCatalogOrderPlaced: ConsumeCatalogOrderPlaced
) {
    fun init() {
        consumeAuthLogout.init()
        consumeCatalogCatalog.init()
        consumeCatalogOrderPlaced.init()
    }
}