package me.santio.utils.apis.spiget.types.impl

import me.santio.utils.apis.spiget.types.SpigetType

class SpigetCategory(private val id: Int, private val name: String): SpigetType {

    fun id() = id
    fun name() = name

    override fun toString(): String {
        return name
    }

}