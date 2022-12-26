package me.santio.utils.apis.spiget.models.resource

data class SpigetFileModel(
    val type: String,
    val size: Int,
    val sizeUnit: String,
    val url: String,
    val externalUrl: String?,
)