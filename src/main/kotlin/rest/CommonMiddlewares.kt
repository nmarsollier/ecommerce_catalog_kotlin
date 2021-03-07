package rest

import model.security.TokenService
import model.security.validateAdminUser
import spark.Request
import spark.Response
import utils.spark.NextFun

val validateAdminUser = { req: Request, res: Response, next: NextFun ->
    TokenService.instance().validateAdminUser(req.headers("Authorization"))
}