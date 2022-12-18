package me.santio.utils.reflection.types

import java.lang.reflect.Method

class MethodReflection(private val obj: Any, private val method: Method) {

    fun name(): String {
        return method.name
    }

    @JvmOverloads
    fun invoke(obj: Any = this.obj, vararg args: Any): Any? {
        method.isAccessible = true
        return method.invoke(obj, *args)
    }

    fun get(): Method {
        return method
    }

    fun hasAnnotation(annotation: Class<out Annotation>, direct: Boolean = true): Boolean {
        return if (direct) {
            method.declaredAnnotations.any { it.annotationClass == annotation }
        } else {
            method.annotations.any { it.annotationClass == annotation }
        }
    }

    fun hasAnnotation(annotation: String, direct: Boolean = true): Boolean {
        return if (direct) {
            method.declaredAnnotations.any { it.annotationClass.java.name == annotation }
        } else {
            method.annotations.any { it.annotationClass.java.name == annotation }
        }
    }

}