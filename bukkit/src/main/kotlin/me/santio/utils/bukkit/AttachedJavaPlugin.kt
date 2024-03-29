package me.santio.utils.bukkit

import me.santio.utils.bukkit.features.BukkitFeature
import org.bukkit.plugin.java.JavaPlugin

/**
 * Fetches the most-recent instance of AttachedJavaPlugin created with the library.
 * You can use this for registering listeners easily, however keep in mind that if the plugin gets unloaded,
 * this variable will not change!
 */
val plugin: AttachedJavaPlugin
    get() = pluginInstance

private lateinit var pluginInstance: AttachedJavaPlugin

/**
    This class is used to attach the plugin instance to the SantioUtils library.
    This is required to use a lot of the functionalities in the library.
 */
@Suppress("unused")
abstract class AttachedJavaPlugin: JavaPlugin() {

    /**
     * This method is called when the plugin is loaded.
     * This is the equivalent of the onLoad method in JavaPlugin.
     */
    open fun onPluginLoad() { /* Do nothing */ }

    final override fun onLoad() {
        pluginInstance = this
        onPluginLoad()
    }

    /**
     * Enables certain features in the library that require a plugin instance to work.
     */
    fun enableFeature(vararg features: BukkitFeature) {
        features.forEach {
            it.enable(this)
            BukkitUtils.featuresEnabled.add(it)
        }
    }

}