@file:JvmName("MathUtils")

package me.santio.utils.kotlin

import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.pow

fun getGcd(a: Double, b: Double): Double {
    if (a < b) return getGcd(b, a)
    return if (abs(b) < 0.001) a else getGcd(b, a - floor(a / b) * b)
}

fun Number.almostEquals(number: Number, sensitivity: Int = 5): Boolean {
    return abs(this.toDouble() - number) < 1 / 10.0.pow(sensitivity.toDouble())
}

private operator fun Number.minus(number: Number): Double {
    return this.toDouble() - number
}

private operator fun Number.div(number: Number): Double {
    return this.toDouble() / number
}

private operator fun Number.times(number: Number): Double {
    return this.toDouble() * number
}

private operator fun Number.plus(number: Number): Double {
    return this.toDouble() + number
}
