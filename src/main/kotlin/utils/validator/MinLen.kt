package utils.validator

/**
 * Validador que valida tamaño mínimo de una cadena de caracteres en una propiedad de una clase
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class MinLen(val value: Int)