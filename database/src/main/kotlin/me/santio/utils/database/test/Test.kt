package me.santio.utils.database.test

import me.santio.utils.database.Database
import me.santio.utils.database.annotations.Entity
import me.santio.utils.database.annotations.Id
import java.util.*

object Test {

    @JvmStatic
    fun main(args: Array<String>) {
        Database.mongo().connect("Daunted")
            .table("arozestinky")
            .insert(Aroze(aroze = "stinky"))
    }

    @Entity()
    data class Aroze(
        @Id
        var id: UUID = UUID.randomUUID(),
        var aroze: String
    )

}