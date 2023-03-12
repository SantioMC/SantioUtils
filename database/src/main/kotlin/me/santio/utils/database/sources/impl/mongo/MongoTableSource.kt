package me.santio.utils.database.sources.impl.mongo

import com.mongodb.client.MongoDatabase
import me.santio.utils.database.Database
import me.santio.utils.database.annotations.Id
import me.santio.utils.database.sources.TableSource
import org.bson.Document
import java.lang.reflect.Field

class MongoTableSource(
    override val table: String,
    private val database: MongoDatabase
): TableSource(table) {
    private fun getIdField(entity: Any): Field? {
        val clazz = entity::class.java
        val fields = clazz.declaredFields

        var idField = fields.firstOrNull { it.isAnnotationPresent(Id::class.java) }
        if (idField == null) {
            idField = fields.firstOrNull { it.name == "id" || it.name == "_id" }
        }

        return idField
    }

    private fun getId(entity: Any): Any? {
        val idField = getIdField(entity) ?: return null

        idField.isAccessible = true
        val value = idField.get(entity)

        idField.set(entity, null)
        return value
    }

    private fun prepare(entity: Any): Document {
        val id = getId(entity) ?: Document.parse(Database.json.toJson(entity))

        val document = Document.parse(Database.json.toJson(entity))
        document["_id"] = id

        return document
    }

    private fun <T: Any> fromDocument(document: Document, clazz: Class<T>): T? {
        val id = document["_id"]
        document.remove("_id")

        val entity = Database.json.fromJson(document.toJson(), clazz)
        val idField = getIdField(entity) ?: return null

        idField.isAccessible = true
        idField.set(entity, id)

        return entity
    }

    override fun create() {/* Not required */}

    override fun drop() {
        database.getCollection(table).drop()
    }

    override fun <T: Any> get(id: Any, clazz: Class<T>): T? {
        val document = database.getCollection(table).find(Document("_id", id)).first() ?: return null
        return fromDocument(document, clazz)
    }

    override fun insert(entity: Any) {
        val document = prepare(entity)
        database.getCollection(table).insertOne(document)
    }

    override fun delete(id: Any) {
        database.getCollection(table).deleteOne(Document("_id", id))
    }

    override fun insertMany(entities: List<Any>) {
        val documents = entities.map { prepare(it) }
        database.getCollection(table).insertMany(documents)
    }

    override fun count(): Int {
        return database.getCollection(table).countDocuments().toInt()
    }

    override fun <T: Any> all(clazz: Class<T>): List<T> {
        val documents = database.getCollection(table).find().toList()
        return documents.mapNotNull { fromDocument(it, clazz) }
    }
}