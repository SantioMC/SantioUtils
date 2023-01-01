@file:JvmName("RaytraceUtils")

package me.santio.utils.bukkit.raytrace

import org.bukkit.entity.LivingEntity
import java.util.concurrent.CompletableFuture
import java.util.function.Predicate

@Suppress("unused")
fun LivingEntity.raytrace(distance: Number, filter: Predicate<VectorData>): CompletableFuture<VectorData?> {
    return Trace(this.eyeLocation.toVector(), this.location.direction, this.world)
        .maxDistance(distance.toDouble())
        .filter(filter)
        .trace()
}