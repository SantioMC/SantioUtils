package me.santio.utils.apis.spiget.models.common

import me.santio.utils.apis.spiget.models.SpigetModel

data class SpigetStandaloneModel(
    val id: Int,
    val uuid: String?,
) {

    fun <T : SpigetModel<*>> useModel(model: (Int) -> T) = model(id)
    fun <T : Any> getObject(obj: (Int) -> T) = obj(id)

}
