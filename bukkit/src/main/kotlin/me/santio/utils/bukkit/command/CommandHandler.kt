package me.santio.utils.bukkit.command

import me.santio.utils.bukkit.command.adapter.ArgumentAdapter
import me.santio.utils.bukkit.command.adapter.impl.*
import me.santio.utils.bukkit.command.annotation.Command
import me.santio.utils.bukkit.command.annotation.SubCommand
import me.santio.utils.reflection.reflection
import me.santio.utils.reflection.types.PackageReflection
import org.bukkit.Bukkit
import org.bukkit.command.CommandMap
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import java.lang.IllegalStateException
import java.lang.reflect.Method
import java.lang.reflect.Parameter

@Suppress("MemberVisibilityCanBePrivate")
object CommandHandler {

    val adapters: MutableSet<ArgumentAdapter<*>> = mutableSetOf(
        DoubleAdapter, FloatAdapter, IntAdapter, PlayerAdapter,
        StringAdapter, MaterialAdapter, OfflinePlayerAdapter
    )

    private val commands: MutableList<CommandInfo> = mutableListOf()

    @JvmStatic
    fun readAnnotation(clazz: Class<*>): CommandInfo? {
        val annotation = clazz.getAnnotation(Command::class.java) ?: return null

        var name = annotation.name
        if (name.isEmpty() && clazz.simpleName.endsWith("Command")) { // If the name is empty, use the class name (example: HelpCommand -> help)
            name = clazz.simpleName.substring(0, name.indexOf("Command"))
            if (name.isEmpty()) return null // We can't have an empty name
        } else if (name.isEmpty()) return null // We can't use the class name

        val info = CommandInfo(
            name,
            annotation.aliases.toList(),
            annotation.permission.ifEmpty { null },
            annotation.description,
            annotation.cooldown,
            annotation.owner
        )

        commands.add(info)
        return info
    }

    @JvmStatic
    fun getMethods(clazz: Any): Set<SubCommandInfo>? {
        val reflection = clazz.reflection()

        val subcommands = reflection.methodsWithAnnotation(SubCommand::class.java)
        if (subcommands.isEmpty()) return null

        return subcommands.mapNotNull {
            val annotation: SubCommand = it.annotation(SubCommand::class.java) ?: return@mapNotNull null

            val sender = when (getParameters(it.get()).first().type) {
                Player::class.java -> SubCommandInfo.CommandTarget.PLAYER
                ConsoleCommandSender::class.java -> SubCommandInfo.CommandTarget.CONSOLE
                CommandSender::class.java -> SubCommandInfo.CommandTarget.BOTH
                else -> {
                    throw IllegalStateException("The first parameter of a subcommand must be a CommandSender/Player/ConsoleCommandSender. Provided: ${getParameters(it.get()).first().type}")
                }
            }

            SubCommandInfo(
                it,
                annotation.name.ifEmpty { it.name().lowercase() },
                annotation.permission.ifEmpty { null },
                annotation.variant,
                sender,
                annotation.cooldown
            )
        }.toSet()
    }

    @JvmStatic
    private fun getParameters(method: Method): List<Parameter> {
        return method.parameters.toList()
    }

    @JvmStatic
    fun addAdapter(vararg adapters: ArgumentAdapter<*>) {
        for (adapter in adapters) {
            if (this.adapters.any { it.type == adapter.type }) continue
            this.adapters.add(adapter)
        }
    }

    @JvmStatic
    fun registerCommand(command: Any) {
        val annotation = command.javaClass.getAnnotation(Command::class.java) ?: return
        val info = commands.firstOrNull { it.name == annotation.name } ?: return

        getCommandMap().register(info.owner, generateCommand(info))
    }

    @JvmStatic
    fun getCommandMap(): CommandMap {
        return Bukkit.getServer().reflection().field("commandMap")!!.value()
            as CommandMap
    }

    @JvmStatic
    private fun generateCommand(info: CommandInfo): org.bukkit.command.Command {
        return object : org.bukkit.command.Command(
            info.name,
            info.description,
            "",
            info.aliases.toMutableList()
        ) {
            override fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean {

                CustomCommandExecutor.onCommand(info, label, sender, args)
                return true

            }
        }
    }

    @JvmStatic
    fun loadAllCommands(pkg: PackageReflection) {
        pkg.classes().filter {
            it.getAnnotation(Command::class.java) != null
        }.forEach {
            readAnnotation(it)
        }
    }

}