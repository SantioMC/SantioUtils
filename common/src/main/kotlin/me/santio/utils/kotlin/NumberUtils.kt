@file:JvmName("NumberUtils")
@file:Suppress("unused")

package me.santio.utils.kotlin

fun <T> Iterable<T>.average(selector: (T) -> Double): Double {
    var sum = 0.0
    var count = 0
    for (element in this) {
        sum += selector(element)
        count++
    }
    return if (count > 0) sum / count else 0.0
}

fun <T> Iterable<T>.average(): Double {
    var sum = 0.0
    var count = 0
    for (element in this) {
        sum += element as Double
        count++
    }
    return if (count > 0) sum / count else 0.0
}

fun <T> Iterable<T>.rollingAverage(selector: (T) -> Double, size: Int): List<Double> {
    val list = mutableListOf<Double>()
    var sum = 0.0
    var count = 0
    for (element in this) {
        sum += selector(element)
        count++
        if (count > size) {
            sum -= selector(element)
            count--
        }
        list.add(sum / count)
    }
    return list
}

fun <T> Iterable<T>.rollingAverage(size: Int): List<Double> {
    val list = mutableListOf<Double>()
    var sum = 0.0
    var count = 0
    for (element in this) {
        sum += element as Double
        count++
        if (count > size) {
            sum -= element
            count--
        }
        list.add(sum / count)
    }
    return list
}

fun Double.rollingAverage(average: Double, sensitivity: Int): Double {
    return (( average * (sensitivity - 1) ) + this) / sensitivity
}