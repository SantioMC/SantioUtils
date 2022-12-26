package me.santio.hssi.utils

import me.santio.utils.template.AttachedJavaPlugin
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.data.BlockData
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.FallingBlock
import org.bukkit.util.Vector
import java.util.function.Consumer
import kotlin.math.floor


@Suppress("unused")
class StableFallingBlock {
    private val data: BlockData
    private var gravity: Boolean = true
    private var damage: Boolean = false
    private var velocity: Vector = Vector(0.0, 0.0, 0.0)

    private var entity: FallingBlock? = null
    private var marker: ArmorStand? = null

    constructor(data: BlockData) : super() {
        this.data = data
    }

    constructor(material: Material) : super() {
        data = material.createBlockData()
    }

    fun gravity(gravity: Boolean): StableFallingBlock {
        this.gravity = gravity
        return this
    }

    fun hurt(damage: Boolean): StableFallingBlock {
        this.damage = damage
        return this
    }

    fun entity() = entity
    fun marker() = marker

    fun remove() {
        entity?.remove()
        marker?.remove()
    }

    fun move(velocity: Vector?, ticks: Int = -1, callback: Consumer<Unit>? = null): StableFallingBlock {
        this.velocity = velocity ?: Vector(0.0, 0.0, 0.0)
        if (velocity == null) return this

        var processed = 0
        AttachedJavaPlugin.scheduler!!.timer({
            if (entity == null || !entity!!.isValid || this.velocity != velocity) it.cancel()
            else {
                processed++
                marker?.let {
                    marker!!.teleport(marker!!.location.clone().add(velocity))
                    entity!!.teleport(marker!!.location)
                } ?: run {
                    entity?.let {
                        entity!!.teleport(entity!!.location.clone().add(velocity))
                    }
                }
                if (processed == ticks) {
                    this.velocity = Vector(0.0, 0.0, 0.0)
                    callback?.accept(Unit)
                }
            }
        }, ticks, 1)

        return this
    }

    private fun spawnBlock(location: Location) {
        val world = location.world ?: throw NullPointerException("World in location is null!")
        val block = world.spawnFallingBlock(location.clone().add(0.0, -0.01, 0.0), data)

        block.dropItem = false
        block.isInvulnerable = true
        block.setHurtEntities(damage)
        block.setGravity(gravity)

        entity?.remove()
        entity = block
    }

    fun spawn(location: Location): StableFallingBlock {
        spawnBlock(location)

        if (gravity) {
            AttachedJavaPlugin.scheduler!!.timer({ task ->
                if (entity == null || !entity!!.isValid) task.cancel() else {
                    val loc = entity!!.location
                    val y = loc.y

                    val under = location.world!!.getBlockAt(
                        loc.blockX,
                        loc.blockY - 1,
                        loc.blockZ
                    )

                    if (y - floor(y) < 0.8 && !under.type.isAir) {
                        entity!!.remove()
                        loc.block.blockData = data
                        task.cancel()
                    }
                }
            }, -1, 1)
        } else {
            marker = location.world!!.spawn(location, ArmorStand::class.java) {
                it.isInvulnerable = true
                it.isInvisible = true
                it.setGravity(gravity)
            }

            AttachedJavaPlugin.scheduler!!.timer({
                if (entity == null || marker == null || !entity!!.isValid || !marker!!.isValid) it.cancel()
                else spawnBlock(marker!!.location)
            }, -1, 1)
        }

        return this
    }
}