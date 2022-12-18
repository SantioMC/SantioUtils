package me.santio.utils.bukkit.features

/**
 * Thrown when attempting to use a feature not registered.
 * See {@link BukkitFeature#enableFeature} for more information.
 */
class FeatureNotEnabledException(text: String? = null): IllegalStateException(text)