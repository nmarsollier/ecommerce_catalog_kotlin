package utils.validator

/**
 * Validador que una propiedad es distinta de null
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class Required(val value: Boolean = true)