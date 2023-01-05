package me.santio.utils.reflection.types

import org.reflections.Reflections

@Suppress("unused")
class PackageReflection(private val instance: Package) {

    fun name(): String = instance.name
    fun get(): Package = instance
    fun classesOf(type: Class<*>): Array<Class<*>> = instance.javaClass.classes.filter { type.isAssignableFrom(it) }.toTypedArray()
    fun parent(): PackageReflection = PackageReflection(Package.getPackage(name().substringBeforeLast(".")))

    fun classes(): Array<Class<*>> {
        val reflections = Reflections(name())
        return reflections.getSubTypesOf(Any::class.java).toTypedArray()
    }

}