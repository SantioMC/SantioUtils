package me.santio.utils.apis.mojang.unified

import kong.unirest.Unirest
import me.santio.utils.apis.mojang.unified.models.UnifiedMojangProfileModel
import java.util.*

@Suppress("unused")
object UnifiedMojangAPI {

    @JvmStatic
    fun getProfile(username: String): UnifiedMojangProfileModel {
        return Unirest.get("https://api.ashcon.app/mojang/v2/user/$username")
            .asObject(UnifiedMojangProfileModel::class.java)
            .body
    }

    @JvmStatic
    fun getProfile(uuid: UUID): UnifiedMojangProfileModel {
        return Unirest.get("https://api.ashcon.app/mojang/v2/user/$uuid")
            .asObject(UnifiedMojangProfileModel::class.java)
            .body
    }

}