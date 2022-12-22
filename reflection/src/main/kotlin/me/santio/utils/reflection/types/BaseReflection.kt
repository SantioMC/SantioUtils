package me.santio.utils.reflection.types

abstract class BaseReflection<T : Any>(private val obj: T) {

    fun get(): T {
        return obj
    }

    fun clazz(): ClassReflection<T> {
        return ClassReflection(obj)
    }

    fun annotations(): List<Annotation> {
        return obj.javaClass.annotations.toList()
    }

    fun <A : Annotation> annotation(annotation: Class<A>): A? {
        return try {
            obj.javaClass.getAnnotation(annotation) as A
        } catch(e: Exception) {
            null
        }
    }

    fun annotation(annotation: String): Annotation? {
        return try {
            annotations().first { it.annotationClass.java.name.lowercase() == annotation.lowercase() }
        } catch(e: Exception) {
            null
        }
    }

    @JvmOverloads
    fun hasAnnotation(annotation: Class<out Annotation>, direct: Boolean = true): Boolean {
        return if (direct) {
            obj.javaClass.declaredAnnotations.any { it.annotationClass == annotation }
        } else {
            obj.javaClass.annotations.any { it.annotationClass == annotation }
        }
    }

    @JvmOverloads
    fun hasAnnotation(annotation: String, direct: Boolean = true): Boolean {
        return if (direct) {
            obj.javaClass.declaredAnnotations.any { it.annotationClass.java.name == annotation }
        } else {
            obj.javaClass.annotations.any { it.annotationClass.java.name == annotation }
        }
    }

}