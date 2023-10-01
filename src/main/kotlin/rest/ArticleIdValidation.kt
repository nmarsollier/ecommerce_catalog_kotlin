package rest

import org.bson.types.ObjectId
import utils.errors.ValidationError

val String?.asArticleId: String
    get() {
        if (this.isNullOrBlank()) {
            throw ValidationError("id" to "Is Invalid")
        }

        try {
            ObjectId(this)
        } catch (e: Exception) {
            throw ValidationError("id" to "Is Invalid")
        }

        return this
    }