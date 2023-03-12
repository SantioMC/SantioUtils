package me.santio.utils.command.exceptions

open class NoAdapterException: IllegalArgumentException {
    constructor(): super()
    constructor(message: String): super(message)
}