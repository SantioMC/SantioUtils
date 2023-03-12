package me.santio.utils.database

import com.google.gson.Gson
import me.santio.utils.adapters.UUIDAdapter
import me.santio.utils.database.sources.impl.mongo.MongoDataSource
import java.util.*

object Database {

    @JvmStatic
    val json = Gson().newBuilder()
        .registerTypeAdapter(UUID::class.java, UUIDAdapter())
        .create()

    @JvmStatic
    fun mongo() = MongoDataSource()

}
