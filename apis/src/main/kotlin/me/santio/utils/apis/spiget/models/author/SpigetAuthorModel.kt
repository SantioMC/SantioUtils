package me.santio.utils.apis.spiget.models.author

import me.santio.utils.apis.spiget.types.impl.SpigetAuthor
import me.santio.utils.apis.spiget.models.PaginatedResult
import me.santio.utils.apis.spiget.models.SpigetModel
import me.santio.utils.apis.spiget.models.common.SpigetImageModel
import me.santio.utils.apis.spiget.types.SpigetType

data class SpigetAuthorModel(
    val id: Int,
    val name: String,
    val icon: SpigetImageModel,
): SpigetModel<SpigetAuthor> {

    override fun convert() = SpigetAuthor(id, name, icon.url)
    override fun convertPaginated() = PaginatedResult(1, 1, listOf(convert()))

    override fun toString(): String {
        return name
    }

}
