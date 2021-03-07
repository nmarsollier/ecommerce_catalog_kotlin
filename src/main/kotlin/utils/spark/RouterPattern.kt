package utils.spark

import spark.Request
import spark.Response

typealias NextFun = () -> Any?
typealias RouteFunc = (req: Request, res: Response, next: NextFun) -> Unit
typealias HandlerFunc<T> = (req: Request, res: Response) -> T

/**
 * Chain of responsibility router pattern
 *
 * call function route to create an spark handler function
 * @param routes Are the route middleware, yout can throw an exception to abort.
 *              next() function is provided to do actions before and after next executions,
 *              you don't need to call next() for normal execution, all middlewares will
 *              be executed sequentially
 *
 * @param handler The final handler, here you answer the route response
 *
 */
fun <T> route(vararg routes: RouteFunc, handler: HandlerFunc<T?>): HandlerFunc<T?> {
    return { req, res ->
        var result: T? = null
        var called = false
        val functionList = mutableListOf<NextFun>()
        functionList.add(0) {
            handler(req, res).also {
                result = it
            }
        }

        for (i in routes.indices.reversed()) {
            val next = functionList.first()

            val nextCalled = {
                next()
                called = true
            }

            functionList.add(0) {
                routes[i](req, res, nextCalled)
                if (!called) {
                    next()
                } else {
                    null
                }
            }
        }

        functionList.first().invoke()

        @Suppress("UNCHECKED_CAST")
        result
    }
}
