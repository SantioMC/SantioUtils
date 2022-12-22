package me.santio.utils.bukkit.command

enum class Variant(val help: Boolean, val fallback: Boolean) {
    /**
     * The method will be executed as a regular argument.
     * Example: `/command help`
     */
    REGULAR(false, false),

    /**
     * This method will be executed as the main command.
     * Example: `/command`
     */
    MAIN(false, false),

    /**
     * This method will be executed as a subcommand. Use HELP_AND_FALLBACK to execute this method as a fallback.
     * Example: `/command help`
     */
    HELP(true, false),

    /**
     * This method will be executed if the user puts in an invalid subcommand.
     * Example: `/command nonExistingSubCommand`
     */
    FALLBACK(false, true),

    /**
     * This method will be executed as a help command and as a fallback.
     * See HELP and FALLBACK for more information.
     *
     * Example: `/command help` OR `/command nonExistingSubCommand`
     */
    HELP_AND_FALLBACK(true, true),
}