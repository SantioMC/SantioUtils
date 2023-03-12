package me.santio.utils.nms.types.player

import me.santio.utils.nms.types.player.properties.PropertyMapNMS
import me.santio.utils.reflection.reflection
import java.util.*

class GameProfileNMS(private val profile: Any) {

    fun get() = profile
    fun id(): UUID = profile.reflection().field("id", false)?.value() as UUID
    fun name(): String = profile.reflection().field("name", false)?.value() as String
    fun legacy(): Boolean = profile.reflection().field("legacy", false)?.value() as Boolean
    fun propertyMap() = PropertyMapNMS(profile.reflection().field("properties", false)?.value()!!)

}