@file:Suppress("unused")

package me.santio.utils.bukkit.command.annotation

/**
 * Marks a parameter as required.
 */
@Target(AnnotationTarget.TYPE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Optional
