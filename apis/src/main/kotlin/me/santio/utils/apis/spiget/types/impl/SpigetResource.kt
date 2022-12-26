package me.santio.utils.apis.spiget.types.impl

import me.santio.utils.apis.spiget.SpigetAPI
import me.santio.utils.apis.spiget.models.resource.SpigetResourceModel
import me.santio.utils.apis.spiget.types.SpigetType

class SpigetResource(private val resource: SpigetResourceModel): SpigetType {

    fun id() = resource.id()
    fun name() = resource.name()
    fun tag() = resource.tag()
    fun author() = resource.author()
    fun reviews() = resource.reviews()
    fun rating() = resource.rating()
    fun downloads() = resource.downloads()
    fun price() = resource.price()
    fun external() = resource.external()
    fun icon() = resource.icon()
    fun premium() = resource.premium()
    fun currency() = resource.currency()
    fun category() = resource.category()
    fun version() = resource.version()
    fun versions() = resource.versions()
    fun updates() = resource.updates()
    fun file() = resource.file()
    fun testedVersions() = resource.testedVersions()
    fun links() = resource.links()
    fun releaseDate() = resource.releaseDate()
    fun updateDate() = resource.updateDate()
    fun contributors() = resource.contributors()
    fun likes() = resource.likes()
    fun sourceCodeLink() = resource.sourceCodeLink()
    fun donationLink() = resource.donationLink()
    fun languages() = resource.languages()

    fun description() = SpigetAPI.getResourceDescription(id())

    override fun toString(): String {
        return name()
    }

}