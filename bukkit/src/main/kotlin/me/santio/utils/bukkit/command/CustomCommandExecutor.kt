package me.santio.utils.bukkit.command

import me.santio.utils.bukkit.command.annotation.Optional
import org.bukkit.command.CommandSender
import java.lang.reflect.Parameter

object CustomCommandExecutor {

    fun onCommand(info: CommandInfo, label: String, sender: CommandSender, args: Array<out String>) {
        if (info.permission != null && !sender.hasPermission(info.permission)) {
            sender.sendMessage("§cYou are not permitted to do this!")
            return
        }

        val methods = CommandHandler.getMethods(info)
        if (methods.isNullOrEmpty()) {
            sender.sendMessage("§cThis command provided no subcommands! Try annotating your commands with @SubCommand!")
            return
        }

        if (args.isEmpty()) {
            val entryMethod = findEntry(methods)
            if (entryMethod == null) {
                sender.sendMessage("§cThis command provided no entry subcommand! Try annotating your commands with @SubCommand(variant = Variant.MAIN)!")
                return
            }

            executeMethod(entryMethod, sender, label, listOf())
            return
        }

        val subcommand = args[0]

        if (subcommand.lowercase() == "help" || subcommand.lowercase() == "?") {
            methods.filter { it.variant.help }.randomOrNull()?.let {
                executeMethod(it, sender, label, listOf())
                return
            }
        }

        val methodInfo = methods.find { it.name == subcommand && (it.permission == null || sender.hasPermission(it.permission)) }
        if (methodInfo == null) {
            methods.filter { it.variant.fallback }.randomOrNull()?.let {
                executeMethod(it, sender, label, args.toList())
                return
            }

            sender.sendMessage("§c/${label} <${methods.joinToString("|") { it.name }}>")
            return
        }

        executeMethod(methodInfo, sender, label, args.toList().drop(1))
    }

    private fun executeMethod(info: SubCommandInfo, sender: CommandSender, label: String, args: List<String>) {
        val parameters = info.method.parameters()
        val arg = parameters.firstOrNull()
        if (arg == null) {
            info.method.invoke()
            return
        }

        if (!info.target.isAcceptable(sender)) {
            sender.sendMessage("§cYou are not permitted to do this! This command is only for ${info.target.name.lowercase()}")
            return
        }

        val adapted: MutableList<Any> = mutableListOf()

        for ((index, parameter) in parameters.drop(1).withIndex()) {
            val adapter = CommandHandler.adapters.firstOrNull { it.type == parameter.type }
            if (adapter == null) {
                sender.sendMessage("§cNo adapter was found for the type ${parameter}! Try adding one with CommandHandler.addAdapter() or change the parameter type.")
                return
            }

            val raw = args.getOrNull(index)
            if (raw == null) {
                if (!parameter.isAnnotationPresent(Optional::class.java)) {
                    sender.sendMessage("§cInvalid syntax: ${generateSyntax(label, parameters, args)}")
                    return
                }

                continue
            }

            val result = adapter.adapt(raw)
            if (result == null) {
                sender.sendMessage("§cInvalid syntax: ${generateSyntax(label, parameters, args)}")
                return
            }

            adapted.add(result)
        }

        info.method.invoke(sender, *adapted.toTypedArray())
    }

    /**
     * This method generates a colored string with the syntax of the command.
     * Example: §7/sudo <player> &c&n<command>
     *
     * @param label The label of the command.
     * @param parameters The parameters of the command.
     * @param args The command arguments.
     * @return The colored syntax string.
     */
    private fun generateSyntax(label: String, parameters: List<Parameter>, args: List<String>): String {
        val builder = StringBuilder("§7/$label")
        var supplied = true

        for ((index, parameter) in parameters.withIndex()) {
            val raw = args.getOrNull(index)
            val optional = parameter.isAnnotationPresent(Optional::class.java)

            if (raw == null && supplied) {
                supplied = false
                builder.append(" &c&n")
            } else builder.append(" ")

            if (optional) builder.append("[")
            else builder.append("")

            builder.append(parameter.name)

            if (optional) builder.append("]")
            else builder.append(">")
        }

        return builder.toString()
    }

    /**
     * Finds a random method flagged as the MAIN entry point. If none are found, we'll default to the help command
     * to show the user the help menu. This is so the user can opt to making /<command> show the help menu
     */
    private fun findEntry(methods: Set<SubCommandInfo>): SubCommandInfo? {
        val entryMethod = methods.filter { it.variant == Variant.MAIN }.randomOrNull()
        if (entryMethod != null) return entryMethod
        return methods.filter { it.variant.help }.randomOrNull()
    }

}