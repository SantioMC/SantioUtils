package me.santio.utils.reflection.types

import java.lang.reflect.Method
import java.lang.reflect.Parameter

class MethodReflection(private val obj: Any, private val method: Method) : BaseReflection<Method>(method) {

    override fun get(): Method {
        return method
    }

    fun name(): String {
        return method.name
    }

    @JvmOverloads
    fun invoke(obj: Any = this.obj, vararg args: Any?): Any? {
        method.isAccessible = true
        return method.invoke(obj, *args)
    }

    fun invokeSelf(vararg args: Any?): Any? {
        return invoke(obj, *args)
    }

    fun parameters(): List<Parameter> {
        return method.parameters.toList()
    }

    fun hasModifier(vararg modifiers: Int): Boolean {
        return modifiers.all { (method.modifiers and it) != 0 }
    }

}