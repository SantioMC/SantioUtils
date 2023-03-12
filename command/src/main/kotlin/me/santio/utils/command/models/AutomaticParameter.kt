package me.santio.utils.command.models

abstract class AutomaticParameter {
    abstract fun isValid(argument: Class<*>): Boolean

    fun satisfy(value: Any): SatisfiedValue {
        if (!isValid(value::class.java)) throw IllegalArgumentException("The value $value is not valid for this parameter.")
        return SatisfiedValue(value, value::class.java)
    }
}