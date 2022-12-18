package me.santio.utils.bukkit.features

import me.santio.utils.bukkit.AttachedJavaPlugin
import me.santio.utils.bukkit.features.impl.InventoryListener

/**
 * Represents a feature that can be enabled or disabled.
 * These features require a plugin instance to work, which is why a plugin instance is passed.
 * If you attempt to use a feature which you do not enable, a FeatureNotEnabledException will be thrown.
 */
enum class BukkitFeature(val enable: (AttachedJavaPlugin) -> Unit) {
    INVENTORY({
        it.server.pluginManager.registerEvents(InventoryListener, it)
    }),
    ;
}