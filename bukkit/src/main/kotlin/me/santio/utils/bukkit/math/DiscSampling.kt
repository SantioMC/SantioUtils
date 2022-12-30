package me.santio.utils.bukkit.math

import org.bukkit.Location
import org.bukkit.util.Vector
import java.util.*
import java.util.stream.Collectors
import kotlin.math.roundToInt

@Suppress("unused", "MemberVisibilityCanBePrivate")
object DiscSampling {

    @JvmStatic
    fun createDiscs(start: Location, amount: Int, radius: Float, size: Double): List<Disc?> {
        val discs = ArrayList<Disc?>()
        for (created in 0 until amount) {
            var disc: Disc? = null
            val loc = getRandomLocation(start.toVector().clone(), size)
            var failed = false
            for (d in discs) if (d != null && d.isInRadius(loc)) failed = true
            if (!failed) disc = Disc(loc, radius)
            discs.add(disc)
        }
        return discs.stream().filter { obj: Disc? -> Objects.nonNull(obj) }.collect(Collectors.toList())
    }

    @JvmStatic
    fun createDiscs(start: Location, radius: Float, size: Double): List<Disc?> {
        return createDiscs(start, 100, radius, size)
    }

    private fun getRandomLocation(start: Vector, radius: Double): Vector {
        var v: Vector? = null
        while (v == null) {
            val x = start.x + Math.random() * (radius * 2) - radius
            val z = start.z + Math.random() * (radius * 2) - radius
            val test = Vector(x, 0.0, z)
            val variation = ((Math.random() * 2).roundToInt() - 1).toDouble()
            test.y = 25 + variation
            v = test
        }
        return v
    }

    @Suppress("MemberVisibilityCanBePrivate")
    class Disc(val location: Vector, val radius: Float) {
        fun isInRadius(vector: Vector): Boolean {
            return location.distance(vector) <= radius.toDouble()
        }
    }

}