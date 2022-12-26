package me.santio.utils.apis.spiget.types.impl

import me.santio.utils.apis.spiget.models.review.SpigetReviewModel
import me.santio.utils.apis.spiget.types.SpigetType

data class SpigetReview(
    private val author: SpigetAuthor,
    private val model: SpigetReviewModel
): SpigetType {

    fun author() = author
    fun encoded() = model.encoded()
    fun message() = model.message()
    fun version() = model.version()
    fun date() = model.date()
    fun resource() = model.resource()
    fun id() = model.id()

    override fun toString(): String {
        return message()
    }

}