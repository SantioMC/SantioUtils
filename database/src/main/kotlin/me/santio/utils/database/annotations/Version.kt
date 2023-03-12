package me.santio.utils.database.annotations

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Version(
    val version: Int = 1
)
