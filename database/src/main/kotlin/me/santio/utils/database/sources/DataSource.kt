package me.santio.utils.database.sources

abstract class DataSource {

    abstract fun table(name: String): TableSource

}