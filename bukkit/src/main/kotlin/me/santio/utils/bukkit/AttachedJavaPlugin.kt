package me.santio.utils.bukkit

import me.santio.utils.bukkit.command.CommandHandler
import me.santio.utils.bukkit.features.BukkitFeature
import me.santio.utils.bukkit.generic.AsyncUtils
import me.santio.utils.reflection.reflection
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

    lateinit var sync: AsyncUtils

    /**
     * This method is called when the plugin is loaded.
     * This is the equivalent of the onLoad method in JavaPlugin.
     */
    open fun onPluginLoad() { /* Do nothing */ }

    final override fun onLoad() {
        pluginInstance = this
        sync = AsyncUtils(this)
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

    /**
     * Loads all commands in the package that this class is in.
     * Example: If this class is in the package "me.santio.test.bukkit", all commands in the package "me.santio.test.bukkit" (and childrens of) will be loaded.
     */
    @JvmOverloads
    fun loadCommands(pkg: Package = this::class.java.`package`) {
        CommandHandler.loadAllCommands(pkg.reflection())
    }

}