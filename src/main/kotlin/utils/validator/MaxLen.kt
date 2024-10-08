package utils.validator

/**
 * Validador que valida tamaño máximo de una cadena de caracteres en una propiedad de una clase
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class MaxLen(val value: Int)