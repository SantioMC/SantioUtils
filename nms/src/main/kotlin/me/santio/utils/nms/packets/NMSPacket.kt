package me.santio.utils.nms.packets

class NMSPacket(private val packet: Any) {

    fun get() = packet
    fun name(): String = packet.javaClass.simpleName

}