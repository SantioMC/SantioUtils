package me.santio.utils.database.sources.impl.sqlite

import me.santio.utils.database.sources.TableSource

class SqliteTableSource(
    override val table: String,
): TableSource(table) {
    override fun create() {
        TODO("Not yet implemented")
    }

    override fun drop() {
        TODO("Not yet implemented")
    }

    override fun <T : Any> get(id: Any, clazz: Class<T>): T? {
        TODO("Not yet implemented")
    }

    override fun insert(entity: Any) {
        TODO("Not yet implemented")
    }

    override fun delete(id: Any) {
        TODO("Not yet implemented")
    }

    override fun insertMany(entities: List<Any>) {
        TODO("Not yet implemented")
    }

    override fun count(): Int {
        TODO("Not yet implemented")
    }

    override fun <T : Any> all(clazz: Class<T>): List<T> {
        TODO("Not yet implemented")
    }


}