package me.santio.utils.command.exceptions

open class CommandValidationException: IllegalArgumentException {
    constructor(): super()
    constructor(message: String): super(message)
}