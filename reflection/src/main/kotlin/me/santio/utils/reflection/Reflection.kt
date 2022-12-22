@file:Suppress("unused")

package me.santio.utils.reflection
import me.santio.utils.reflection.types.ClassReflection
import me.santio.utils.reflection.types.PackageReflection

object Reflection {
    @JvmStatic
    fun getClass(name: String): ClassReflection<*>? {
        return try {
            ClassReflection(Class.forName(name))
        } catch (e: Exception) {
            return null
        }
    }

    @Suppress("DEPRECATION")
    @JvmStatic
    fun getPackage(name: String): PackageReflection? {
        return try {
            PackageReflection(Package.getPackage(name))
        } catch (e: Exception) {
            return null
        }
    }
}