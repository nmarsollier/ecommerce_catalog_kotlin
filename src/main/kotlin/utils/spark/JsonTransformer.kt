package utils.spark

import spark.ResponseTransformer
import utils.gson.toJson

object JsonTransformer : ResponseTransformer {
    override fun render(model: Any?): String {
        return model?.toJson() ?: ""
    }
}