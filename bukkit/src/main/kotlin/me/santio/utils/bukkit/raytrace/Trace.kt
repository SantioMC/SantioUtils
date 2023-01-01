package me.santio.utils.bukkit.raytrace

import me.santio.utils.bukkit.generic.async
import me.santio.utils.bukkit.generic.getAsyncEntities
import org.bukkit.World
import org.bukkit.util.Vector
import java.util.concurrent.CompletableFuture
import java.util.function.Predicate

@Suppress("MemberVisibilityCanBePrivate")
class Trace(
    var loc: Vector,
    private val direction: Vector,
    private val world: World
) {

    private var filter: Predicate<VectorData> = Predicate { false }
    private var maxDistance: Double = 20.0
    private var step: Double = 1.0 / 3.0

    fun filter(filter: Predicate<VectorData>): Trace {
        this.filter = filter
        return this
    }

    fun maxDistance(maxDistance: Double): Trace {
        this.maxDistance = maxDistance
        return this
    }

    fun step(step: Double): Trace {
        this.step = step
        return this
    }

    fun filter() = filter
    fun maxDistance() = maxDistance
    fun step() = step

    fun trace(): CompletableFuture<VectorData?> {
        val future = CompletableFuture<VectorData?>()

        // Setup
        val direction = direction.clone().normalize().multiply(step)
        var closeup = false

        async {
            for (i in 0..(maxDistance / step).toInt()) {
                loc.add(direction)

                val block = loc.toLocation(world).block

                val data = VectorData(
                    loc.toLocation(world).getAsyncEntities().filter {
                        it.boundingBox.contains(loc)
                    },
                    if (block.boundingBox.contains(loc)) block else null,
                    loc.toLocation(world)
                )

                if (filter.test(data)) {
                    if (closeup) {
                        future.complete(data)
                        return@async
                    } else {
                        step = 1.0 / 20.0
                        closeup = true
                        loc.add(direction.clone().multiply(-1))
                    }
                }
            }

            future.complete(null)
        }

        return future
    }

}