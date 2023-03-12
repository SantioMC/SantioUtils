package me.santio.utils.command.adapter

/**
 * An adapter to convert a string to a specific type.
 * Example: String -> Integer
 */
abstract class ArgumentAdapter<T> {

    /**
     * The type of the argument.
     * Example: Integer::class.java
     */
    abstract val type: Class<T>
    open val error: String = "Invalid argument: %s"

    /**
     * The function to check if the string should be converted using this adapter.
     * @param arg The argument to check.
     * @return True if the argument should be converted using this adapter.
     */
    abstract fun isValid(arg: String): Boolean

    /**
     * The function to convert the argument to the type.
     * Example: Bukkit.getPlayerExact(it)
     *
     * @param arg The argument to convert.
     * @return The converted argument. If null, an error message will be sent and the command execution will stop
     */
    abstract fun adapt(arg: String): T?

}