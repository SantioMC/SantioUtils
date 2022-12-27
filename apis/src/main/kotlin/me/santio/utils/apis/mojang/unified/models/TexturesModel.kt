package me.santio.utils.apis.mojang.unified.models

data class TexturesModel(
    val custom: Boolean,
    val slim: Boolean,
    val skin: SkinModel,
    val raw: RawSkinModel
)
