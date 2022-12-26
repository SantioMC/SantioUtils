package me.santio.utils.apis.spiget.models.resource

import me.santio.utils.apis.spiget.models.PaginatedResult
import me.santio.utils.apis.spiget.models.SpigetModel
import me.santio.utils.apis.spiget.models.common.SpigetImageModel
import me.santio.utils.apis.spiget.models.common.SpigetStandaloneModel
import me.santio.utils.apis.spiget.models.review.SpigetRatingModel
import me.santio.utils.apis.spiget.types.impl.SpigetResource

data class SpigetResourceModel(
    private val id: Int,
    private val name: String,
    private val tag: String,
    private val contributors: String,
    private val likes: Int,
    private val file: SpigetFileModel,
    private val testedVersions: List<String>,
    private val links: Map<String, String>,
    private val rating: SpigetRatingModel,
    private val releaseDate: Long,
    private val updateDate: Long,
    private val downloads: Int,
    private val external: Boolean,
    private val icon: SpigetImageModel,
    private val premium: Boolean,
    private val price: Double,
    private val currency: String?,
    private val author: SpigetStandaloneModel,
    private val category: SpigetStandaloneModel,
    private val version: SpigetStandaloneModel,
    private val versions: List<SpigetStandaloneModel> = emptyList(),
    private val reviews: List<SpigetStandaloneModel> = emptyList(),
    private val updates: List<SpigetStandaloneModel> = emptyList(),
    private val sourceCodeLink: String,
    private val donationLink: String,
    private val supportedLanguages: String?,
): SpigetModel<SpigetResource> {
    fun id() = id
    fun name() = name
    fun tag() = tag
    fun contributors() = contributors
    fun likes() = likes
    fun file() = file
    fun testedVersions() = testedVersions
    fun links() = links
    fun rating() = rating
    fun releaseDate() = releaseDate
    fun updateDate() = updateDate
    fun downloads() = downloads
    fun external() = external
    fun icon() = icon
    fun premium() = premium
    fun price() = price
    fun currency() = currency
    fun author() = author
    fun category() = category
    fun version() = version
    fun versions() = versions
    fun reviews() = reviews
    fun updates() = updates
    fun sourceCodeLink() = sourceCodeLink
    fun donationLink() = donationLink
    fun languages() = supportedLanguages

    override fun toString(): String {
        return name()
    }

    override fun convert(): SpigetResource = SpigetResource(this)
    override fun convertPaginated() = PaginatedResult(1, 1, listOf(convert()))

}