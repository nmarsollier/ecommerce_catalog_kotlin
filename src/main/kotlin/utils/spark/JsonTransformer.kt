package utils.spark

import spark.ResponseTransformer
import spark.Route
import spark.Spark
import utils.gson.toJson

object JsonTransformer : ResponseTransformer {
    override fun render(model: Any?): String {
        return model?.toJson() ?: ""
    }
}

fun jsonGet(path: String, route: Route) {
    Spark.get(path, route, JsonTransformer);
}

fun jsonPost(path: String, route: Route) {
    Spark.post(path, route, JsonTransformer);
}

fun jsonPut(path: String, route: Route) {
    Spark.put(path, route, JsonTransformer);
}

fun jsonDelete(path: String, route: Route) {
    Spark.delete(path, route, JsonTransformer);
}
