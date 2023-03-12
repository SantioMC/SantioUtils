package me.santio.utils.command.annotations

/**
 * Tells the parser to completely ignore this field/method/class.
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ParserIgnore
