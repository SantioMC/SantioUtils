package me.santio.utils.apis.spiget.models

import me.santio.utils.apis.spiget.types.SpigetType

interface SpigetModel<T: SpigetType> {
    fun convert(): T
    fun convertPaginated(): PaginatedResult<T>
}