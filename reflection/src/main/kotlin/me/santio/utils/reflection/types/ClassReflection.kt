package me.santio.utils.reflection.types


@Suppress("MemberVisibilityCanBePrivate")
class ClassReflection<T>(private val clazz: Class<T>) {

    fun name(): String = clazz.simpleName
    fun get(): Class<T> = clazz
    fun cast(obj: Any): T = clazz.cast(obj)
    fun loader(): ClassLoader = clazz.classLoader
    fun pack(): Package = clazz.`package`

    // Methods
    fun method(name: String): MethodReflection? {
        return try {
            MethodReflection(clazz, clazz.javaClass.getDeclaredMethod(name))
        } catch(e: Exception) {
            null
        }
    }

    fun methods(): List<MethodReflection> {
        return clazz.javaClass.declaredMethods.map { MethodReflection(clazz, it) }
    }

    fun methodsWithAnnotation(annotation: Class<out Annotation>): List<MethodReflection> {
        return methods().filter { it.hasAnnotation(annotation) }
    }

    fun methodsWithAnnotation(annotation: String): List<MethodReflection> {
        return methods().filter { it.hasAnnotation(annotation) }
    }

    // Fields
    fun field(name: String): FieldReflection? {
        return try {
            FieldReflection(clazz, clazz.javaClass.getDeclaredField(name))
        } catch(e: Exception) {
            null
        }
    }

    fun fields(): List<FieldReflection> {
        return clazz.javaClass.declaredFields.map { FieldReflection(clazz, it) }
    }

    fun fieldsWithAnnotation(annotation: Class<out Annotation>): List<FieldReflection> {
        return fields().filter { it.hasAnnotation(annotation) }
    }

    fun fieldsWithAnnotation(annotation: String): List<FieldReflection> {
        return fields().filter { it.hasAnnotation(annotation) }
    }

    fun hasModifier(vararg modifiers: Int): Boolean {
        return modifiers.all { (clazz.modifiers and it) != 0 }
    }
    
    fun hasAnnotation(annotation: Class<out Annotation>, direct: Boolean = true): Boolean {
        return if (direct) {
            clazz.declaredAnnotations.any { it.annotationClass == annotation }
        } else {
            clazz.annotations.any { it.annotationClass == annotation }
        }
    }

    fun hasAnnotation(annotation: String, direct: Boolean = true): Boolean {
        return if (direct) {
            clazz.declaredAnnotations.any { it.annotationClass.java.name == annotation }
        } else {
            clazz.annotations.any { it.annotationClass.java.name == annotation }
        }
    }

}