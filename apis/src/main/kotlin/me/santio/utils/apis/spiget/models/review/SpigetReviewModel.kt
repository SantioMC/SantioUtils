package me.santio.utils.apis.spiget.models.review

import me.santio.utils.apis.spiget.SpigetAPI
import me.santio.utils.apis.spiget.models.PaginatedResult
import me.santio.utils.apis.spiget.models.SpigetModel
import me.santio.utils.apis.spiget.models.common.SpigetStandaloneModel
import me.santio.utils.apis.spiget.types.SpigetType
import me.santio.utils.apis.spiget.types.impl.SpigetReview
import java.util.Base64

/*
{
    "author": {
        "id": 106510
    },
    "rating": {
        "count": 1,
        "average": 3
    },
    "message": "Looks pretty good, the download page doesn't work to :( (It just keeps timing out. (Extra characters))",
    "version": "v44",
    "date": 1447971180,
    "resource": 1626,
    "id": 47412
}
 */

@Suppress("MemberVisibilityCanBePrivate")
data class SpigetReviewModel(
    private val author: SpigetStandaloneModel,
    private val rating: SpigetRatingModel,
    private val message: String,
    private val version: String,
    private val date: Long,
    private val resource: Int,
    private val id: Int,
): SpigetModel<SpigetReview> {

    fun author() = author.getObject { SpigetAPI.getAuthor(it) }
    fun encoded() = message
    fun message() = Base64.getDecoder().decode(encoded()).toString(Charsets.UTF_8)
    fun version() = version
    fun date() = date
    fun resource() = resource
    fun id() = id

    @Suppress("UNCHECKED_CAST")
    override fun convert() = SpigetReview(author(), this)
    override fun convertPaginated() = PaginatedResult(1, 1, listOf(convert()))

    override fun toString(): String {
        return message()
    }

}
