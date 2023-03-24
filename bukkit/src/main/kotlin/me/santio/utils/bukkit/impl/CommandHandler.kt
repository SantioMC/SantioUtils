package me.santio.utils.bukkit.impl

import io.github.classgraph.ClassGraph
import me.santio.utils.bukkit.plugin
import me.santio.utils.command.CommandParser
import me.santio.utils.command.annotations.Command
import me.santio.utils.command.models.AutomaticParameter
import me.santio.utils.command.models.CommandEvaluation
import org.bukkit.Bukkit
import org.bukkit.command.CommandMap
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import org.bukkit.command.Command as BukkitCommand

/**
 * Main implementation of the command handler for Bukkit.
 */
object CommandHandler {

    private var loaded: Boolean = false
    private lateinit var label: String
    private val autoParam = SenderParameter()

    /**
     * Registers a class as a command.
     * Note: A new instance of the class will be created internally.
     * @param command The class instance to register.
     */
    @JvmStatic
    fun registerCommand(command: Any) {
        load()
        registerBukkitCommand(
            CommandParser.registerCommand(command::class.java)
        )
    }

    /**
     * Registers a class as a command.
     * @param command The class to register.
     */
    @JvmStatic
    fun registerCommand(command: Class<*>) {
        load()
        registerBukkitCommand(
            CommandParser.registerCommand(command)
        )
    }

    /**
     * Scans a package for commands and registers them.
     * @param packageName The package name to scan.
     */
    @JvmStatic
    fun registerPackage(packageName: String) {
        load()
        ClassGraph()
            .enableAllInfo()
            .acceptPackages(packageName)
            .scan()
            .getClassesWithAnnotation(Command::class.java).forEach {
                registerBukkitCommand(
                    CommandParser.registerCommand(it.loadClass())
                )
            }
    }

    /**
     * Executes the command, this is automatically called by the command handler.
     * @param sender The sender of the command.
     * @param command The command to execute.
     * @param message The message to execute.
     */
    @JvmStatic
    fun execute(sender: CommandSender, command: String, message: String) {
        val query = "$command $message"
        val cmd = CommandParser.findCommand(query)
        if (cmd == null) {
            sender.sendMessage("§cUnknown command. Type \"/help\" for help.")
            return
        }

        val args = CommandParser.adapt(query.substring(cmd.path.length + 1))
        val autoParams = CommandParser.getAutomaticParameters(cmd)

        // Satisfy automatic parameters
        val senderType = autoParams.firstOrNull() ?: return
        if (!autoParam.isValid(sender::class.java)) return

        val satisfy = when (senderType) {
            Player::class.java -> (sender as? Player)?.let { autoParam.satisfy(it) }
            ConsoleCommandSender::class.java -> (sender as? ConsoleCommandSender)?.let { autoParam.satisfy(it) }
            else -> autoParam.satisfy(sender)
        }

        if (satisfy == null) {
            sender.sendMessage("§cYou can't execute this command. This command is limited to ${
                when (senderType) {
                    Player::class.java -> "players"
                    ConsoleCommandSender::class.java -> "the console"
                    else -> "players and the console"
                }
            }")
            return
        }

        CommandParser.evaluate(cmd, listOf(satisfy), *args.toList().toTypedArray())
    }

    /**
     * Gets the bukkit command map.
     * @return The bukkit command map.
     */
    @JvmStatic
    fun getCommandMap(): CommandMap {
        val server = Bukkit.getServer()
        val serverClass = server.javaClass
        return serverClass.getMethod("getCommandMap").invoke(server) as CommandMap
    }

    /**
     * Gets the command label.
     * @return The command label.
     */
    @JvmStatic
    fun label(): String {
        return label
    }

    /**
     * Sets the command label. This only works before the commands are registered.
     * @param label The new command label.
     */
    @JvmStatic
    fun label(label: String) {
        this.label = label
    }

    private fun load() {
        if (loaded) return
        loaded = true

        label = plugin.name.lowercase().replace(" ", "")
        CommandParser.registerAutomaticParameter(autoParam)
    }

    private fun registerBukkitCommand(command: CommandEvaluation) {
        val cmd = object : BukkitCommand(
            command.name,
            command.rootAnnotation.description.ifEmpty { "No description provided" },
            "/${command.name}",
            command.rootAnnotation.aliases.toList()
        ) {
            override fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean {

                execute(sender, command.name, args.joinToString(" "))
                return true

            }
        }

        getCommandMap().register(label, cmd)
    }

    private class SenderParameter: AutomaticParameter() {
        override fun isValid(argument: Class<*>): Boolean {
            return argument.simpleName.endsWith("Player") || argument.simpleName.endsWith("CommandSender")
        }
    }

}