package me.santio.utils.bukkit.plugin

import me.santio.utils.bukkit.plugin
import me.santio.utils.reflection.reflection
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandMap
import org.bukkit.command.PluginCommand
import org.bukkit.command.SimpleCommandMap
import org.bukkit.event.Event
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.RegisteredListener
import java.io.File
import java.net.URLClassLoader
import java.util.*
import kotlin.io.path.Path
import kotlin.io.path.name

@Suppress("unused", "MemberVisibilityCanBePrivate")
object PluginUtils {
    @JvmStatic
    fun getLoadedPlugins(): List<Plugin> {
        return Bukkit.getPluginManager().plugins.toList()
    }

    @JvmStatic
    fun getLoadedPlugin(name: String): Plugin? {
        return getLoadedPlugins().firstOrNull { it.name.lowercase() == name.lowercase() }
    }

    @JvmStatic
    fun isLoaded(name: String): Boolean {
        return getLoadedPlugins().any { it.name.lowercase() == name.lowercase() }
    }

    @JvmStatic
    fun getPluginDescription(file: File): PluginDescriptionFile {
        return plugin.pluginLoader.getPluginDescription(file)
    }

    @JvmStatic
    @JvmOverloads
    fun getPlugins(directory: String = plugin.dataFolder.parentFile.absolutePath): List<PluginInfo> {
        val folder = File(directory)
        val plugins = mutableListOf<PluginInfo>()

        folder.listFiles()?.forEach {
            if (!it.isDirectory && it.extension == "jar") {
                val description = getPluginDescription(it)
                plugins.add(PluginInfo(Path(it.absolutePath), description.name, description.version, description))
            }
        }

        return plugins
    }

    @JvmStatic
    fun getPlugin(name: String): PluginInfo? {
        return getPlugins().firstOrNull { it.name.lowercase() == name.lowercase() || it.path.fileName.name.lowercase() == name.lowercase() }
    }

    @JvmStatic
    fun disablePlugin(plugin: Plugin) {
        Bukkit.getPluginManager().disablePlugin(plugin)
    }

    @JvmStatic
    fun enablePlugin(name: String) {
        Bukkit.getPluginManager().enablePlugin(getLoadedPlugin(name)!!)
    }

    @JvmStatic
    @Suppress("UNCHECKED_CAST")
    fun unloadPlugin(plugin: Plugin) {
        val listeners = Bukkit.getPluginManager()
            .reflection()
            .field("listeners")?.value()
                as MutableMap<Event, SortedSet<RegisteredListener>>?

        val commandMap = Bukkit.getServer()
            .reflection()
            .field("commandMap")!!.value()
                as SimpleCommandMap

        val knownCommands = commandMap.reflection()
            .method("getKnownCommands")!!.invoke()
                as MutableMap<String, Command>

        // Disable Plugin
        disablePlugin(plugin)

        // Remove from maps
        (Bukkit.getPluginManager()
            .reflection()
            .field("plugins")!!.value()!! as MutableList<Plugin>)
            .remove(plugin)

        (Bukkit.getPluginManager()
            .reflection()
            .field("lookupNames")!!.value() as MutableMap<String, Plugin>)
            .remove(plugin.name)

        // Unregister listeners & commands
        listeners?.values?.forEach {
            it.removeIf { listener -> listener.plugin == plugin }
        }

        knownCommands.entries
            .filter { it.value is PluginCommand }
            .filter { (it.value as PluginCommand).plugin == plugin }
            .forEach {
                it.value.unregister(commandMap)
                knownCommands.remove(it.key)
            }

        // Remove from classloader
        val cl = plugin.javaClass.classLoader
        if (cl is URLClassLoader) {
            cl.reflection().field("plugin")!!.set(null)
            cl.reflection().field("pluginInit")!!.set(null)

            cl.close()
        }

        // Run garbage collector
        System.gc()
    }

    @JvmStatic
    fun loadPlugin(plugin: PluginInfo): Plugin? {
        return try {
            val pluginFile = plugin.path.toFile()
            val instance = Bukkit.getPluginManager().loadPlugin(pluginFile)!!

            instance.onLoad()
            Bukkit.getPluginManager().enablePlugin(instance)

            instance
        } catch (e: Exception) {
            null
        }
    }

    @JvmStatic
    fun reloadPlugin(plugin: PluginInfo) {
        getLoadedPlugin(plugin.name)?.let {
            unloadPlugin(it)
        }
        loadPlugin(plugin)
    }

    @JvmStatic
    fun reloadPlugin(plugin: Plugin) {
        unloadPlugin(plugin)
        loadPlugin(PluginInfo(plugin.dataFolder.toPath(), plugin.name, plugin.description.version, plugin.description))
    }

}