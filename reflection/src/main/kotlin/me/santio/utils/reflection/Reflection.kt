@file:Suppress("unused")

package me.santio.utils.reflection
import me.santio.utils.reflection.types.ClassReflection
import me.santio.utils.reflection.types.PackageReflection

object Reflection {
    @JvmStatic
    fun getClass(vararg names: String): ClassReflection<*>? {
        for (name in names) {
            try {
                return ClassReflection(Class.forName(name))
            } catch (ignored: Exception) {}
        }

        return null
    }

    @Suppress("DEPRECATION")
    @JvmStatic
    fun getPackage(vararg names: String): PackageReflection? {
        for (name in names) {
            try {
                return PackageReflection(Package.getPackage(name))
            } catch (ignored: Exception) {}
        }

        return null
    }
}