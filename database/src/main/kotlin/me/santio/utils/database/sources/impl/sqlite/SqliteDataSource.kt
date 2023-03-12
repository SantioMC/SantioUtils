package me.santio.utils.database.sources.impl.sqlite

import me.santio.utils.database.sources.DataSource
import me.santio.utils.database.sources.TableSource

class SqliteDataSource: DataSource() {

    fun connect(database: String = "main"): SqliteDataSource {

    }

    override fun table(name: String): TableSource {
        TODO("Not yet implemented")
    }

}