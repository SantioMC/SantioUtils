package me.santio.utils.apis.mojang

import me.santio.utils.apis.mojang.real.RealMojangAPI
import me.santio.utils.apis.mojang.unified.UnifiedMojangAPI

object MojangAPI {

    fun useUnifiedAPI() = UnifiedMojangAPI
    fun useMojangAPI() = RealMojangAPI

}