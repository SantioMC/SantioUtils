package me.santio.utils.apis.spiget

import kong.unirest.Unirest
import me.santio.utils.apis.APIUtils.asListObject
import me.santio.utils.apis.spiget.models.PaginatedResult
import me.santio.utils.apis.spiget.models.SpigetModel
import me.santio.utils.apis.spiget.types.SpigetType

class SearchBuilder<T: SpigetType, M : SpigetModel<*>>(
    private val clazz: Class<M>,
    private val type: Class<T>,
    category: String,
    query: String,
    fieldsAvailable: Set<String>
) {

    private var limit: Int = 10
    private var page: Int = 1
    private var sort: String = "+name"
    private var searchField: String = "name"
    private var fields: Set<String> = fieldsAvailable
    private var uri = "${SpigetAPI.BASE_URL}/search/$category/$query"

    fun limit() = limit
    fun page() = page
    fun sort() = sort
    fun field() = searchField
    fun fields() = fields

    fun field(field: String): SearchBuilder<T, M> {
        searchField = field
        return this
    }

    fun limit(limit: Int): SearchBuilder<T, M> {
        this.limit = limit
        return this
    }

    fun page(page: Int): SearchBuilder<T, M> {
        this.page = page
        return this
    }

    fun sort(sort: String): SearchBuilder<T, M> {
        this.sort = sort
        return this
    }

    fun fields(vararg fields: String): SearchBuilder<T, M> {
        this.fields = fields.toSet()
        return this
    }

    fun removeField(vararg fields: String): SearchBuilder<T, M> {
        this.fields = this.fields - fields.toSet()
        return this
    }

    fun uri(uri: String): SearchBuilder<T, M> {
        this.uri = uri
        return this
    }

    @Suppress("UNCHECKED_CAST")
    fun search(): PaginatedResult<T>? {
        val results = Unirest.get(uri)
            .queryString("field", searchField)
            .queryString("size", limit)
            .queryString("page", page)
            .queryString("sort", sort)
            .queryString("fields", fields.joinToString(","))
            .asJson()

        if (!results.isSuccess) return null
        val headers = results.headers

        @Suppress("UNCHECKED_CAST")
        val types = results.asListObject(clazz) as List<SpigetModel<*>>

        return PaginatedResult(
            headers.getFirst("x-page-index").toInt(),
            headers.getFirst("x-page-count").toInt(),
            types.map { it.convert() } as List<T>
        )
    }

}