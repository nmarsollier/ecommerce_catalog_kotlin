package utils.env

import java.util.logging.Level
import java.util.logging.Logger

object Log {
    fun error(msg: String) {
        Logger.getGlobal().log(Level.SEVERE, msg)
    }

    fun info(msg: String) {
        Logger.getGlobal().log(Level.INFO, msg)
    }
}