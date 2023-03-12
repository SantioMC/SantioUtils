package me.santio.utils.reflection.types

import me.santio.utils.reflection.reflection
import java.lang.reflect.Modifier


@Suppress("MemberVisibilityCanBePrivate", "UNCHECKED_CAST", "unused")
class ClassReflection<T : Any>(private val obj: T): BaseReflection<T>(obj) {

    fun name(): String = obj.javaClass.simpleName
    fun cast(obj: Any): T = obj.javaClass.cast(obj) as T
    fun loader(): ClassLoader = obj.javaClass.classLoader
    fun pkg(): PackageReflection = obj.javaClass.`package`.reflection()

    fun superClass(): ClassReflection<*>? {
        return obj.javaClass.superclass?.reflection()
    }

    fun interfaces(): List<ClassReflection<*>> {
        return obj.javaClass.interfaces.map { it.reflection() }
    }

    fun subClasses(): List<ClassReflection<*>> {
        return obj.javaClass.declaredClasses.map { it.reflection() }
    }

    fun subClass(name: String): ClassReflection<*>? {
        return obj.javaClass.declaredClasses.firstOrNull { it.simpleName == name }?.reflection()
    }

    override fun get(): Class<T> {
        return obj.javaClass
    }

    // Methods
    fun method(direct: Boolean, vararg methods: String): MethodReflection? {
        for (name in methods) {
            return method(name, direct) ?: continue
        }

        return null
    }

    fun method(name: String, direct: Boolean = true): MethodReflection? {
        return try {
            if (direct) {
                obj.javaClass.getDeclaredMethod(name).reflection()
            } else {
                obj.javaClass.getMethod(name).reflection()
            }
        } catch (e: Exception) {
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

    /*
        @JvmOverloads
    fun findField(datatype: Class<*>, index: Int, static: Boolean = true): FieldReflection? {
        var field = fields()
            .filter { if (!static) !it.hasModifier(Modifier.STATIC) else true }
            .filter { datatype.isAssignableFrom(it.get().type) }
            .getOrNull(index)

        if (field == null && superClass() != null) {
            field = superClass()!!.findField(datatype, index)
        }

        return field
    }
     */

    @JvmOverloads
    fun findMethod(datatype: Class<*>, index: Int, static: Boolean = true): MethodReflection? {
        var method = methods()
            .filter { if (!static) !it.hasModifier(Modifier.STATIC) else true }
            .filter { datatype.isAssignableFrom(it.get().returnType) }
            .getOrNull(index)

        if (method == null && superClass() != null) {
            method = superClass()!!.findMethod(datatype, index)
        }

        return method
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

    fun field(direct: Boolean, vararg fields: String): FieldReflection? {
        for (name in fields) {
            return field(name, direct) ?: continue
        }

        return null
    }

    fun field(index: Int): FieldReflection? {
        return try {
            FieldReflection(obj, obj.javaClass.fields[index])
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

    @JvmOverloads
    fun findField(datatype: Class<*>, index: Int, static: Boolean = true): FieldReflection? {
        var field = fields()
            .filter { if (!static) !it.hasModifier(Modifier.STATIC) else true }
            .filter { datatype.isAssignableFrom(it.get().type) }
            .getOrNull(index)

        if (field == null && superClass() != null) {
            field = superClass()!!.findField(datatype, index)
        }

        return field
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

    // Initialization
    fun newInstance(vararg args: Any): T? {
        return try {
            val constructor = obj.javaClass.getDeclaredConstructor(*args.map { it.javaClass }.toTypedArray())
            constructor.isAccessible = true
            constructor.newInstance(*args)
        } catch (e: Exception) {
            null
        }
    }

}