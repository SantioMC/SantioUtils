package me.santio.utils.apis.spiget.models.category

import me.santio.utils.apis.spiget.models.PaginatedResult
import me.santio.utils.apis.spiget.models.SpigetModel
import me.santio.utils.apis.spiget.types.SpigetType
import me.santio.utils.apis.spiget.types.impl.SpigetCategory

data class SpigetCategoryModel(
    val id: Int,
    val name: String
): SpigetModel<SpigetCategory> {

    @Suppress("UNCHECKED_CAST")
    override fun convert() = SpigetCategory(id, name)
    override fun convertPaginated() = PaginatedResult(1, 1, listOf(convert()))

    override fun toString(): String {
        return name
    }

}
