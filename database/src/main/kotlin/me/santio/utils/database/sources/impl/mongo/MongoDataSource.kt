package me.santio.utils.database.sources.impl.mongo

import com.mongodb.client.MongoDatabase
import me.santio.utils.database.sources.DataSource
import me.santio.utils.database.sources.TableSource
import org.litote.kmongo.KMongo

class MongoDataSource: DataSource() {

    private lateinit var database: MongoDatabase

    fun connect(database: String = "main"): MongoDataSource {
        System.setProperty("org.litote.mongo.test.mapping.service", "org.litote.kmongo.jackson.JacksonClassMappingTypeService")

        val client = KMongo.createClient()
        this.database = client.getDatabase(database)

        return this
    }

    override fun table(name: String): TableSource {
        TODO("Not yet implemented")
    }

}