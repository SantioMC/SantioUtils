package me.santio.utils.reflection.types

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.TypeVariable

@Suppress("MemberVisibilityCanBePrivate")
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

    fun generics(): Map<Char, Type> {
        val map = mutableMapOf<Char, Type>()

        val superclassType = obj.javaClass.genericSuperclass

        if (superclassType is ParameterizedType) {
            val typeParameters: Array<out TypeVariable<Class<T>>> = obj.javaClass.typeParameters
            val typeArguments: Array<Type> = superclassType.actualTypeArguments
            for (i in typeParameters.indices) {
                val typeParameterName = typeParameters[i].name
                val typeArgument: Type = typeArguments[i]
                map[typeParameterName.first()] = typeArgument
            }
        }

        return map
    }

    fun generic(generic: Char): Type? {
        return generics()[generic]
    }

    fun bounds(): Map<Char, Type> {
        val map = mutableMapOf<Char, Type>()
        val typeParameters: Array<out TypeVariable<Class<T>>> = obj.javaClass.typeParameters

        for (i in typeParameters.indices) {
            val typeParameterName = typeParameters[i].name
            val bounds = typeParameters[i].bounds
            if (bounds.isNotEmpty()) map[typeParameterName.first()] = bounds[0]
        }

        return map
    }

    fun bound(generic: Char): Type? {
        return bounds()[generic]
    }

}