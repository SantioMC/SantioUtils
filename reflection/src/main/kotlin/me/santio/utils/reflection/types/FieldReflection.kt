package me.santio.utils.reflection.types

import java.lang.reflect.Field

class FieldReflection(private val obj: Any, private val field: Field) {

    fun name(): String = field.name
    fun get(): Field = field

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
        } catch(_: Exception) {}
    }

    fun hasModifier(vararg modifiers: Int): Boolean {
        return modifiers.all { (field.modifiers and it) != 0 }
    }
    
    fun hasAnnotation(annotation: Class<out Annotation>, direct: Boolean = true): Boolean {
        return if (direct) {
            field.declaredAnnotations.any { it.annotationClass == annotation }
        } else {
            field.annotations.any { it.annotationClass == annotation }
        }
    }

    fun hasAnnotation(annotation: String, direct: Boolean = true): Boolean {
        return if (direct) {
            field.declaredAnnotations.any { it.annotationClass.java.name == annotation }
        } else {
            field.annotations.any { it.annotationClass.java.name == annotation }
        }
    }

}