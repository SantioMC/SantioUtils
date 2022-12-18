package me.santio.utils.reflection.types

class PackageReflection(private val instance: Package) {

    fun name(): String = instance.name
    fun get(): Package = instance
    fun classes(): Array<Class<*>> = instance.javaClass.classes
    fun classesOf(type: Class<*>): Array<Class<*>> = instance.javaClass.classes.filter { type.isAssignableFrom(it) }.toTypedArray()

}