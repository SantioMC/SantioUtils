package me.santio.utils.database.sources

abstract class TableSource(open val table: String) {

    /**
     * Create the table if it doesn't exist with the supplied name.
     */
    abstract fun create()

    /**
     * Completely delete the table.
     */
    abstract fun drop()

    /**
     * Get an entry from the table based on the supplied id.
     */
    abstract fun <T: Any> get(id: Any, clazz: Class<T>): T?

    /**
     * Insert an entity into the table.
     * @param entity The entity to insert. This must be annotated by Entity
     */
    abstract fun insert(entity: Any)

    /**
     * Delete an entry from the table based on the supplied id.
     */
    abstract fun delete(id: Any)

    /**
     * Insert many entities into the table.
     */
    abstract fun insertMany(entities: List<Any>)

    /**
     * Count the amount of entries in the table.
     */
    abstract fun count(): Int

    /**
     * Get all entries from the table.
     */
    abstract fun <T: Any> all(clazz: Class<T>): List<T>

}