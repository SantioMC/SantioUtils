@file:JvmName("ReflectionUtils")

package me.santio.utils.reflection

import me.santio.utils.reflection.types.ClassReflection
import me.santio.utils.reflection.types.FieldReflection
import me.santio.utils.reflection.types.MethodReflection
import me.santio.utils.reflection.types.PackageReflection
import java.lang.reflect.Field
import java.lang.reflect.Method

fun Any.reflection() = ClassReflection(this)
fun Field.reflection() = FieldReflection(this, this)
fun Method.reflection() = MethodReflection(this, this)
fun Package.reflection() = PackageReflection(this)