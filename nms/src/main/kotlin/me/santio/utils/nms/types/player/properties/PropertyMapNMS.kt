package me.santio.utils.nms.types.player.properties

import com.google.common.collect.LinkedHashMultimap
import com.google.common.collect.Multimap
import me.santio.utils.nms.NMS
import me.santio.utils.reflection.reflection

class PropertyMapNMS(private val obj: Any) {

    companion object {
        @JvmStatic
        fun new() = PropertyMapNMS(NMS.getMojangAuthClass("PropertyMap").newInstance()!!)
    }

    fun get() = obj

    @Suppress("UNCHECKED_CAST")
    fun properties(): Multimap<String, PropertyNMS> {
        val map: Multimap<String, PropertyNMS> = LinkedHashMultimap.create()
        val properties = obj.reflection().field("properties")!!.value()!! as Multimap<String, Any>

        for ((key, value) in properties.entries()) {
            map.put(key, PropertyNMS(value))
        }

        return map
    }

}