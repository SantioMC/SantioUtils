package me.santio.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import me.santio.utils.adapters.UUIDAdapter
import java.util.*

object SantioUtils {
    val GSON: Gson = GsonBuilder().registerTypeAdapter(UUID::class.java, UUIDAdapter()).create()
}