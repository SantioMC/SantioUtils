package me.santio.utils.nms.packets

import me.santio.utils.nms.NMS
import me.santio.utils.nms.NMS.nms
import me.santio.utils.nms.NMSClasses
import me.santio.utils.reflection.reflection
import org.bukkit.Bukkit
import org.bukkit.Difficulty
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector
import java.lang.reflect.InvocationTargetException

@Suppress("unused", "MemberVisibilityCanBePrivate")
class WrappedPacket() {

    private var packet: NMSPacket? = null

    fun packet() = packet

    constructor(packet: NMSPacket) : this() {
        this.packet = packet
    }

    constructor(packetType: Class<*>): this() {
        this.packet = NMSPacket(packetType.getDeclaredConstructor().newInstance() as Any)
    }

    fun send(player: Player) {
        player.nms().handle().connection().sendPacket(packet!!.get())
    }

    fun broadcast() {
        Bukkit.getOnlinePlayers().forEach { send(it) }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T: Any> read(index: Int, type: Class<T>): T? {
        if (packet == null) return null
        return packet!!.reflection().findField(type, index)?.value(packet!!.get()) as T?
    }

    fun write(index: Int, value: Any) {
        if (packet == null) return
        packet!!.reflection().findField(value.javaClass, index)?.set(packet!!.get(), value)
    }

    fun readString(index: Int) = read(index, String::class.java)
    fun readInt(index: Int) = read(index, Int::class.java)
    fun readBoolean(index: Int) = read(index, Boolean::class.java)
    fun readByte(index: Int) = read(index, Byte::class.java)
    fun readShort(index: Int) = read(index, Short::class.java)
    fun readLong(index: Int) = read(index, Long::class.java)
    fun readFloat(index: Int) = read(index, Float::class.java)
    fun readDouble(index: Int) = read(index, Double::class.java)
    fun readChar(index: Int) = read(index, Char::class.java)

    fun writeString(index: Int, value: String) = write(index, value)
    fun writeInt(index: Int, value: Int) = write(index, value)
    fun writeBoolean(index: Int, value: Boolean) = write(index, value)
    fun writeByte(index: Int, value: Byte) = write(index, value)
    fun writeShort(index: Int, value: Short) = write(index, value)
    fun writeLong(index: Int, value: Long) = write(index, value)
    fun writeFloat(index: Int, value: Float) = write(index, value)
    fun writeDouble(index: Int, value: Double) = write(index, value)
    fun writeChar(index: Int, value: Char) = write(index, value)

    fun readBlockPosition(index: Int): Vector? {
        val blockPosObj = read(index, NMSClasses.Minecraft.BLOCK_POS.get()) ?: return null
        val x = blockPosObj.reflection().method(true, "getX", "u")!!.invoke(blockPosObj) as Int
        val y = blockPosObj.reflection().method(true, "getY", "v")!!.invoke(blockPosObj) as Int
        val z = blockPosObj.reflection().method(true, "getZ", "w")!!.invoke(blockPosObj) as Int
        return Vector(x.toDouble(), y.toDouble(), z.toDouble())
    }

    fun writeBlockPosition(index: Int, value: Vector) {
        val blockPosObj = NMSClasses.Minecraft.BLOCK_POS.get().getConstructor(Int::class.java, Int::class.java, Int::class.java).newInstance(value.blockX, value.blockY, value.blockZ)
        write(index, blockPosObj)
    }

    fun readItemStack(index: Int): ItemStack? {
        val nmsItemStack = read(index, NMSClasses.Minecraft.ITEM_STACK.get()) ?: return null
        return NMS.toBukkitItemStack(nmsItemStack)
    }

    fun writeItemStack(index: Int, value: ItemStack) {
        write(index, value.nms())
    }

    fun readGameMode(index: Int): GameMode? {
        val enum = read(index, NMSClasses.Enums.GAMEMODE.get()) as Enum<*>? ?: return null
        val target = enum.ordinal - 1

        if (target < 0) return null
        return GameMode.values()[target]
    }

    fun writeGameMode(index: Int, value: GameMode) {
        val target = value.ordinal + 1
        val enum = NMSClasses.Enums.GAMEMODE.get().enumConstants[target]
        write(index, enum)
    }

    fun readDifficulty(index: Int): Difficulty? {
        val difficulty: Enum<*> = read(index, NMSClasses.Minecraft.DIFFICULTY.get()) as Enum<*>? ?: return null
        return Difficulty.values()[difficulty.ordinal]
    }

    fun writeDifficulty(index: Int, difficulty: Difficulty) {
        val value: Enum<*> =
            NMSClasses.Minecraft.DIFFICULTY.get().enumConstants[difficulty.ordinal] as Enum<*>? ?: return
        write(index, value)
    }

    fun readIChatBaseComponent(index: Int): String? {
        val iChatBaseComponent: Any = read(index, NMSClasses.Minecraft.ICHAT_BASE_COMPONENT.get())!!

        try {
            return NMSClasses.Minecraft.ICHAT_GET_MESSAGE?.invoke(iChatBaseComponent).toString()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }

        return null
    }

    fun writeIChatBaseComponent(index: Int, content: String) {
        var iChatBaseComponent: Any? = null

        try {
            iChatBaseComponent = NMSClasses.Minecraft.ICHAT_FROM_MESSAGE?.invokeSelf(null, content) ?: return
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }

        if (iChatBaseComponent != null) write(index, iChatBaseComponent)
    }

    fun readMinecraftKey(index: Int): String {
        val minecraftKey: Any = read(index, NMSClasses.Minecraft.MINECRAFT_KEY.get())!!
        val minecraftKeyWrapper = WrappedPacket(NMSPacket(minecraftKey))

        val fields = minecraftKey.reflection().fields()
            .filter { it.type() == String::class.java }

        val namespaceIndex = fields.size - 2
        val keyIndex = fields.size - 1

        return minecraftKeyWrapper.readString(namespaceIndex) + ":" + minecraftKeyWrapper.readString(keyIndex)
    }

    fun writeMinecraftKey(index: Int, content: String) {
        var minecraftKey: Any? = null

        try {
            minecraftKey = NMSClasses.Minecraft.MINECRAFT_KEY.newInstance(content)
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }

        if (minecraftKey != null) write(index, minecraftKey)
    }

    fun readList(index: Int): List<Any?>? {
        return read(index, List::class.java)
    }

    fun writeList(index: Int, list: List<Any>) {
        write(index, list)
    }

}