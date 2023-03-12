package me.santio.utils.reflection.types

import java.lang.reflect.Field

@Suppress("unused")
class FieldReflection(private val obj: Any, private val field: Field) : BaseReflection<Field>(field) {

    fun name(): String = field.name
    fun type(): Class<*> = field.type

    override fun get(): Field {
        return field
    }

    @JvmOverloads
    fun value(obj: Any = this.obj): Any? {
        return try {
            field.isAccessible = true
            field.get(obj)
        } catch(e: Exception) { null }
    }

    @JvmOverloads
    fun set(value: Any?, obj: Any = this.obj) {
        try {
            field.isAccessible = true
            field.set(obj, value)
        } catch(_: Exception) { /* Ignore */ }
    }

    fun hasModifier(vararg modifiers: Int): Boolean {
        return modifiers.all { (field.modifiers and it) != 0 }
    }

}