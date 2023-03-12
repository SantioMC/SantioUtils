package me.santio.utils.apis

import com.fasterxml.jackson.databind.type.TypeFactory
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kong.unirest.HttpResponse
import kong.unirest.JsonNode
import me.santio.utils.apis.spiget.SpigetAPI

object APIUtils {

    @JvmStatic
    fun spiget() = SpigetAPI

    fun HttpResponse<JsonNode>.asListObject(clazz: Class<*>): List<*> {
        val mapper = jacksonObjectMapper()
        val typeFactory = TypeFactory.defaultInstance()
        val javaType = typeFactory.constructCollectionType(List::class.java, clazz)
        return mapper.readValue(this.body.toString(), javaType)
    }

}
