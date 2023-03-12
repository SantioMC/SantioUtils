package me.santio.utils.command

import me.santio.utils.command.annotations.Command

@Command
class MsgPlayer {

    fun main(test: String, duration: Float, vararg test2: String) {
        println("$test ${test2.joinToString(" ")} $duration")
    }

}