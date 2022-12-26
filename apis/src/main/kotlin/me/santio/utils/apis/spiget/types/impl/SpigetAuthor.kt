package me.santio.utils.apis.spiget.types.impl

import me.santio.utils.apis.spiget.SpigetAPI
import me.santio.utils.apis.spiget.types.SpigetType

class SpigetAuthor(private val id: Int, private val name: String, private val url: String): SpigetType {

    fun id() = id
    fun name() = name
    fun url() = url

    fun reviews() = SpigetAPI.getReviews(this)

    override fun toString(): String {
        return name
    }

}