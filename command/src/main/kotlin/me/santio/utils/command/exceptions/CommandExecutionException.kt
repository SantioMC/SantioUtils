package me.santio.utils.command.exceptions

class CommandExecutionException: CommandValidationException {
    constructor(): super()
    constructor(message: String): super(message)
}