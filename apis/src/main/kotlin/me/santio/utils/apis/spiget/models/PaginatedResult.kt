package me.santio.utils.apis.spiget.models

import me.santio.utils.apis.spiget.types.SpigetType

data class PaginatedResult<T: SpigetType>(
    private val page: Int,
    private val pages: Int,
    private val results: List<T>
) {

    fun page() = page
    fun pages() = pages
    fun results() = results

}
