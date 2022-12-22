package me.santio.utils.reflection.types


@Suppress("MemberVisibilityCanBePrivate", "UNCHECKED_CAST")
class ClassReflection<T : Any>(private val obj: T): BaseReflection<T>(obj) {

    fun name(): String = obj.javaClass.simpleName
    fun cast(obj: Any): T = obj.javaClass.cast(obj) as T
    fun loader(): ClassLoader = obj.javaClass.classLoader
    fun pkg(): Package = obj.javaClass.`package`

    // Methods
    fun method(name: String): MethodReflection? {
        return try {
            MethodReflection(obj, obj.javaClass.getDeclaredMethod(name))
        } catch(e: Exception) {
            null
        }
    }

    @JvmOverloads
    fun methods(direct: Boolean = true): List<MethodReflection> {
        return if (direct) {
            obj.javaClass.declaredMethods.map { MethodReflection(obj, it) }
        } else {
            obj.javaClass.methods.map { MethodReflection(obj, it) }
        }
    }

    fun methodsWithAnnotation(annotation: Class<out Annotation>): List<MethodReflection> {
        return methods().filter { it.hasAnnotation(annotation) }
    }

    fun methodsWithAnnotation(annotation: String): List<MethodReflection> {
        return methods().filter { it.hasAnnotation(annotation) }
    }

    // Fields
    @JvmOverloads
    fun field(name: String, direct: Boolean = true): FieldReflection? {
        return try {
            FieldReflection(obj,
                if (direct) obj.javaClass.getDeclaredField(name)
                else obj.javaClass.getField(name)
            )
        } catch(e: Exception) {
            null
        }
    }

    @JvmOverloads
    fun fields(direct: Boolean = true): List<FieldReflection> {
        return if (direct) {
            obj.javaClass.declaredFields.map { FieldReflection(obj, it) }
        } else {
            obj.javaClass.fields.map { FieldReflection(obj, it) }
        }
    }

    fun fieldsWithAnnotation(annotation: Class<out Annotation>): List<FieldReflection> {
        return fields().filter { it.hasAnnotation(annotation) }
    }

    fun fieldsWithAnnotation(annotation: String): List<FieldReflection> {
        return fields().filter { it.hasAnnotation(annotation) }
    }

    fun hasModifier(vararg modifiers: Int): Boolean {
        return modifiers.all { (obj.javaClass.modifiers and it) != 0 }
    }

}