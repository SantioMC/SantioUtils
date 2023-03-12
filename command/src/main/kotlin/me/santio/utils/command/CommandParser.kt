package me.santio.utils.command

import me.santio.utils.command.adapter.ArgumentAdapter
import me.santio.utils.command.adapter.impl.DoubleAdapter
import me.santio.utils.command.adapter.impl.FloatAdapter
import me.santio.utils.command.adapter.impl.IntAdapter
import me.santio.utils.command.adapter.impl.StringAdapter
import me.santio.utils.command.annotations.Command
import me.santio.utils.command.annotations.ParserIgnore
import me.santio.utils.command.exceptions.CommandExecutionException
import me.santio.utils.command.exceptions.CommandValidationException
import me.santio.utils.command.exceptions.NoAdapterException
import me.santio.utils.command.models.*
import org.jetbrains.annotations.Nullable
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.lang.reflect.Parameter
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KTypeProjection
import kotlin.reflect.KVisibility
import kotlin.reflect.full.createType
import kotlin.reflect.full.starProjectedType
import kotlin.reflect.full.valueParameters
import kotlin.reflect.jvm.kotlinFunction

/**
 * Handles parsing command classes and methods. This is a generic object and
 * can be used outside of Minecraft for other purposes such as JDA.
 */
@Suppress("unused")
object CommandParser {

    private val evaluations = mutableListOf<CommandEvaluation>()
    private val adapters = mutableListOf<ArgumentAdapter<*>>(
        IntAdapter, DoubleAdapter, FloatAdapter, StringAdapter
    )
    private val automaticParameters = mutableListOf<AutomaticParameter>()

    @JvmStatic
    val commands: List<CommandEvaluation>
        get() = evaluations.toList()

    /**
     * Registers a new argument adapter. This will be used to parse arguments
     * for the given type. If an adapter for the given type already exists,
     * it will be replaced with the new adapter. This change will apply immediately.
     * @param adapter The adapter to register.
     * @see ArgumentAdapter
     */
    @JvmStatic
    fun registerAdapter(adapter: ArgumentAdapter<*>) {
        adapters.removeIf { it.type == adapter.type }
        adapters.add(adapter)
    }

    /**
     * Makes all subcommands require the given parameters. These are added to the
     * beginning of the parameter list. This is so if you need to pass in a variable to
     * the subcommand scope, you can do so.
     * @param clazz The parameter types to add.
     */
    fun registerAutomaticParameter(vararg parameters: AutomaticParameter) {
        automaticParameters.addAll(parameters)
    }

    /**
     * Checks if the given class is a command class. This essentially checks if the class has the
     * command annotation. No validation checks are performed on the class.
     * @param clazz The class to check.
     * @return True if the class is a command class.
     * @see Command
     */
    @JvmStatic
    fun isCommandClass(clazz: Class<*>): Boolean {
        return clazz.isAnnotationPresent(Command::class.java) && !ignore(clazz)
    }

    /**
     * Checks if the given method should be completely ignored by the parser.
     * @param method The method to check.
     * @return True if the method should be ignored.
     * @see ParserIgnore
     */
    @JvmStatic
    private fun ignore(method: Method): Boolean {
        if (method.kotlinFunction != null) {
            val data = method.kotlinFunction!!
            if (data.visibility != KVisibility.PUBLIC || data.isSuspend) return true
        } else {
            if (!Modifier.isPublic(method.modifiers) || Modifier.isStatic(method.modifiers)) return true
        }

        if (method.parameterCount < automaticParameters.size) return true

        for (parameter in automaticParameters) {
            if (!parameter.isValid(method.parameterTypes[automaticParameters.indexOf(parameter)]))
                return true
        }

        return method.isAnnotationPresent(ParserIgnore::class.java)
            || method.isAnnotationPresent(JvmStatic::class.java)
    }

    /**
     * Checks if the given class should be completely ignored by the parser.
     * @param clazz The class to check.
     * @return True if the class should be ignored.
     * @see ParserIgnore
     */
    @JvmStatic
    fun ignore(clazz: Class<*>): Boolean {
        if (clazz.kotlin.isCompanion) return true
        if (!Modifier.isPublic(clazz.modifiers) || Modifier.isAbstract(clazz.modifiers)) return true
        return clazz.isAnnotationPresent(ParserIgnore::class.java)
    }

    /**
     * Gets the effective command annotation for the given method. This will return the annotation
     * on the method if it exists, otherwise it will return the annotation on the class, up until it
     * reaches the root class.
     * @param method The method to get the annotation for.
     * @return The effective command annotation.
     */
    @JvmStatic
    fun getEffectiveAnnotation(method: Method): Command? {
        return if (method.isAnnotationPresent(Command::class.java)) {
            method.getAnnotation(Command::class.java)
        } else {
            getEffectiveAnnotation(Command::class.java)
        }
    }

    /**
     * Gets the effective command annotation for the given class. This will return the annotation
     * on the class if it exists, otherwise it will return the annotation on the superclass, up until it
     * reaches the root class.
     * @param clazz The class to get the annotation for.
     * @return The effective command annotation.
     */
    @JvmStatic
    fun getEffectiveAnnotation(clazz: Class<*>): Command? {
        return if (clazz.isAnnotationPresent(Command::class.java)) {
            clazz.getAnnotation(Command::class.java)
        } else if (clazz.superclass != null) {
            getEffectiveAnnotation(clazz.superclass)
        } else null
    }

    /**
     * Gets the command name for the given class. This will return the name defined in the command
     * annotation if it exists, otherwise it will return the class name without the "Command" suffix.
     * @param clazz The class to get the name for.
     * @return The command name.
     */
    @JvmStatic
    fun getCommandName(clazz: Class<*>): String {
        val definedName = clazz.getAnnotation(Command::class.java)?.name ?: ""
        return definedName.ifEmpty {
            if (clazz.simpleName.endsWith("Command")) clazz.simpleName
                .substring(0, clazz.simpleName.length - 7).lowercase()
            else
                clazz.simpleName.substring(
                    0,
                    clazz.simpleName.substring(1).indexOfFirst { it.isUpperCase() } + 1
                ).lowercase()
        }.ifEmpty { clazz.simpleName.lowercase() }
    }

    /**
     * Gets the subcommand name for the given method. This will use the effective
     * command annotation to get the name of the subcommand, or default to the method name.
     * @param method The method to get the name for.
     * @return The subcommand name.
     */
    @JvmStatic
    fun getSubcommandName(method: Method): String {
        val definedName = method.getAnnotation(Command::class.java)?.name ?: ""
        return definedName.ifEmpty {
            method.name.lowercase().replace(Regex("[ _-]]"), "")
        }
    }

    /**
     * Finds the entry method for the given class. This will either be the method with the name
     * "main", if it doesn't exist it will look for a standalone method, if it still can't find
     * one it will take the first method it finds.
     * @param clazz The class to find the entry method for.
     * @return The entry method.
     */
    @JvmStatic
    fun getEntryMethod(clazz: Class<*>): Method? {
        val methods = clazz.declaredMethods.filter { !ignore(it) }
        return methods.firstOrNull { it.name == "main" }
            ?: methods.firstOrNull { it.parameterCount == automaticParameters.size }
            ?: methods.firstOrNull()
    }

    /**
     * Parses a method of a class and return parameter information for the method.
     * @param method The method to parse.
     * @return The list of parameters.
     */
    private fun parseMethod(method: Method): List<CommandArgument> {
        val arguments = mutableListOf<CommandArgument>()
        val parameters: List<Pair<Parameter, KParameter?>> = if (method.kotlinFunction != null) {
            val data = method.kotlinFunction!!
            method.parameters.zip(data.valueParameters)
        } else method.parameters.map { it to null }

        for (parameter in parameters) {
            val name = parameter.second?.name ?: parameter.first.name
            val optional = parameter.second?.type?.isMarkedNullable ?: parameter.first.type.isAnnotationPresent(Nullable::class.java)
            val infinite = parameter.second?.isVararg ?: parameter.first.isVarArgs

            val individual = parameter.first.type.componentType ?: parameter.first.type

            arguments.add(CommandArgument(
                name,
                parameter.first.type,
                optional,
                infinite,
                individual
            ))
        }

        return arguments
    }

    /**
     * Gets the path of a subcommand. This path look something like this: "command subcommand".
     * This is used for finding the correct subcommand to execute.
     * @param name The name of the sub command.
     * @param scope The scope of the subcommand.
     * @return The path of the subcommand.
     */
    @JvmStatic
    private fun getPath(name: String, scope: CommandScope): String {
        val parents = mutableListOf<String>()

        var parent: CommandScope? = scope
        while (parent != null) {
            parents.add(getCommandName(parent.clazz))
            parent = parent.parent
        }

        val subcommandName = if (name == "main") "" else " $name"
        return parents.reversed().joinToString(" ") + subcommandName
    }

    /**
     * Returns a human-readable string of the given command evaluation.
     * @param method The method to get a human-readable string from.
     * @return The human-readable string.
     */
    @JvmStatic
    fun getCommandFormat(method: Method): String {
        val arguments = parseMethod(method)
        val builder = StringBuilder(getSubcommandName(method) + " ")

        for (argument in arguments) {
            if (argument.optional) builder.append("[")
            builder.append("<${argument.name}")
            if (argument.infinite) builder.append("...")
            builder.append(">")
            if (argument.optional) builder.append("]")
            builder.append(" ")
        }

        return builder.toString().trim()
    }

    /**
     * Parses an inner class of the given class. This will recursively parse all inner classes
     * of the given class. This is used to parse subcommands and will return the evaluation for
     * the inner class.
     */
    private fun parseClass(clazz: Class<*>, parent: CommandScope?): CommandScope {
        val subcommands = mutableListOf<SubCommand>()
        val methods = clazz.declaredMethods.filter { !ignore(it) }
        val instance = clazz.getConstructor().newInstance()
        val scope = CommandScope(clazz, instance, parent, subcommands = subcommands)
        parent?.children?.add(scope)

        for (innerClass in clazz.declaredClasses.filter { !ignore(it) }) {
            parseClass(innerClass, scope)
        }

        for (method in methods) {
            val path = getPath(getSubcommandName(method), scope)
            subcommands.add(
                SubCommand(
                    getSubcommandName(method),
                    path,
                    scope,
                    method,
                    parseMethod(method)
                )
            )
        }

        return scope
    }

    /**
     * Registers the given class as a command class. This will parse the class and add it to the
     * list of registered commands. This won't actually register the command with any backend
     * service, that is up to the implementation.
     * @param clazz The class to register.
     * @return The command evaluation for the class.
     * @see CommandEvaluation
     */
    @JvmStatic
    fun registerCommand(clazz: Class<*>): CommandEvaluation {
        if (!isCommandClass(clazz)) throw CommandValidationException("Class is not a valid command class. Annotate it with @Command")
        val entry = getEntryMethod(clazz) ?: throw CommandValidationException("Class has no entry method, try making a method named 'main'")
        val command = clazz.getAnnotation(Command::class.java) ?: throw CommandValidationException("Class has no command annotation")
        val scope = parseClass(clazz, null)
        val mainIndex = scope.subcommands.indexOfFirst { it.method == entry }

        val evaluation = CommandEvaluation(
            scope,
            command,
            getCommandName(clazz),
            mainIndex
        )

        evaluations.add(evaluation)
        return evaluation
    }

    /**
     * Gets the list of every registered subcommand.
     * @return The list of subcommands.
     */
    private fun getAllSubcommands(scope: CommandScope? = null): List<SubCommand> {
        val list = mutableListOf<SubCommand>()
        val scopes = scope?.children ?: evaluations.map { it.scope }

        for (evaluation in scopes) {
            list.addAll(evaluation.subcommands)
            list.addAll(getAllSubcommands(evaluation))
        }

        return list
    }

    /**
     * Find the specific subcommand from the given string, an example of this would be
     * "help" or "infraction revoke".
     * @param query The string to find the subcommand with.
     * @return The subcommand, or null if it doesn't exist.
     */
    @JvmStatic
    fun findCommand(query: String): SubCommand? {
        if (query.isEmpty()) return null
        return getAllSubcommands()
            .find { it.path == query } ?: let {
                val path = query.split(" ").dropLast(1).joinToString(" ")
                return findCommand(path)
            }
    }

    /**
     * Evaluates the given subcommand.
     * @param command The sub command to evaluate.
     * @param autoParams The automatic parameters to use.
     * @param arguments The arguments to evaluate.
     */
    fun evaluate(command: SubCommand, autoParams: List<SatisfiedValue>, vararg arguments: Any?) {
        val args = mutableListOf<Any?>()
        var index = 0

        val parameters = command.arguments

        // Attach automatic parameters
        for (parameter in autoParams) {
            val argument = parameters.getOrNull(index) ?: throw CommandExecutionException("Too many automatic parameters for command ${command.path}")

            if (!automaticParameters[index].isValid(argument.type))
                throw CommandExecutionException("There is no registered automatic parameter for parameter ${argument.name} (type ${argument.type})")

            if (parameter.clazz != argument.type)
                throw CommandExecutionException("Automatic parameter (index $index) is not valid for parameter ${argument.name} (type ${argument.type})")

            args.add(parameter.value)
            index++
        }

        // Attach user parameters
        for (argument in parameters.drop(index)) {
            val provided = arguments.getOrNull(index - automaticParameters.size)

            if (argument.optional && provided == null) args.add(null)
            else if (provided == null) throw CommandExecutionException("Not enough parameters to satisfy parameter ${argument.name} (${argument.type}) - Can not be null!")
            else if (argument.type != provided.javaClass && argument.optional) args.add(null)
            else if (argument.infinite) {
                val include = arguments.drop(index - automaticParameters.size)
                    .filter { it?.javaClass == argument.individual }
                    .map { if (it == null) null else argument.individual.cast(it) }

                val list = createList(
                    argument.individual.kotlin,
                    include
                )

                args.add(convertListToArray(list, argument.individual))
                break
            }
            else if (argument.type != provided.javaClass) {
                val adapter = adapters.firstOrNull { it.type == argument.type && it.isValid(provided.toString()) }
                    ?: throw CommandExecutionException("Parameter ${argument.name} (${argument.type}) is not valid for provided argument (${provided.javaClass})")
                args.add(adapter.adapt(provided.toString()))
            }
            else args.add(provided)
        }

        // Invoke the method
        command.method.invoke(command.scope.instance, *args.toTypedArray())
    }

    /**
     * Adapts the given list to an array of the given type, this utilizes the adapters to
     * convert a string to the correct type.
     * @param query The first element of the list.
     * @param list The list to convert.
     * @return The converted array.
     */
    @JvmStatic
    fun adapt(query: String, vararg list: String): Array<Any?> {
        val adapted = mutableListOf<Any?>()

        for (element in listOf(query, *list)) {
            val adapter = adapters.firstOrNull { it.isValid(element) }
            if (adapter == null) throw NoAdapterException("No adapter found for type ${element.javaClass}")
            else adapted.add(adapter.adapt(element))
        }

        return adapted.toTypedArray()
    }

    /**
     * Adapts a given string, separating each token by a space.
     * @param query The string to adapt.
     * @return The adapted array.
     */
    @JvmStatic
    fun adapt(query: String): Array<Any?> {
        val tokens = query.split(" ")
        return adapt(tokens.first(), *tokens.drop(1).toTypedArray())
    }

    /**
     * Gets you the list of methods that are an automatic parameter.
     * @param command The subcommand to get the automatic parameters for.
     * @return The list of class types of the automatic parameters.
     */
    fun getAutomaticParameters(command: SubCommand): List<Class<*>> {
        val parameters = command.arguments
        val list = mutableListOf<Class<*>>()

        for (i in 0 until automaticParameters.size) {
            val parameter = parameters.getOrNull(i) ?: break
            if (!automaticParameters[i].isValid(parameter.type)) break
            list.add(parameter.type)
        }

        return list
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Any> createList(clazz: KClass<out T>, values: List<T?>): ArrayList<T> {
        val type = ArrayList::class.createType(listOf(KTypeProjection.invariant(clazz.starProjectedType)))
        val constructor = type.classifier as KClass<out ArrayList<T>>
        return constructor.java.getConstructor(Collection::class.java).newInstance(values)
    }

    @Suppress("UNCHECKED_CAST")
    private inline fun <reified T> convertListToArray(list: List<T>, type: Class<*>): Array<T?> {
        val array = java.lang.reflect.Array.newInstance(type, list.size) as Array<T?>
        for (i in list.indices) array[i] = list[i]
        return array
    }



}
