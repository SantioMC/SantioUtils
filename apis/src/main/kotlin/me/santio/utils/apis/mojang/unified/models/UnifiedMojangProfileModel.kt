package me.santio.utils.apis.mojang.unified.models

import com.google.gson.annotations.SerializedName

data class UnifiedMojangProfileModel(
    val uuid: String,
    @SerializedName("username")
    val name: String,
    @SerializedName("username_history")
    val usernameHistory: List<NameHistoryModel>,
    val textures: TexturesModel,
    @SerializedName("created_at")
    val createdAt: String?
) {

    override fun toString(): String {
        return name
    }

}