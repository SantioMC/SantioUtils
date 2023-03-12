package me.santio.utils.nms

@Suppress("unused")
object NMSClasses {

    object Netty {
        @get:JvmName("BYTE_BUFFER") @JvmStatic val BYTE_BUFFER = NMS.getNettyClass("buffer.ByteBuf")
        @get:JvmName("NETTY_CHANNEL") @JvmStatic val NETTY_CHANNEL = NMS.getNettyClass("channel.Channel")
        @get:JvmName("CHANNEL_FUTURE") @JvmStatic val CHANNEL_FUTURE = NMS.getNettyClass("channel.ChannelFuture")
    }

    object Bukkit {
        @get:JvmName("WORLD") @JvmStatic val WORLD = NMS.getCraftBukkitClass("CraftWorld")
        @get:JvmName("PLAYER") @JvmStatic val PLAYER = NMS.getCraftBukkitClass("entity.CraftPlayer")
        @get:JvmName("ENTITY") @JvmStatic val ENTITY = NMS.getCraftBukkitClass("entity.CraftEntity")
        @get:JvmName("SERVER") @JvmStatic val SERVER = NMS.getCraftBukkitClass("CraftServer")
        @get:JvmName("ITEM_STACK") @JvmStatic val ITEM_STACK = NMS.getCraftBukkitClass("inventory.CraftItemStack")
    }

    object Minecraft {
        @get:JvmName("WORLD_MAP") @JvmStatic val WORLD_MAP = NMS.getNMSClass("WorldMap")
        @get:JvmName("SERVER") @JvmStatic val SERVER = NMS.getNMSClass("MinecraftServer", "server.MinecraftServer")
        @get:JvmName("SERVER_CONNECTION") @JvmStatic val SERVER_CONNECTION = NMS.getNMSClass("ServerConnection", "server.network.ServerConnection")
        @get:JvmName("BLOCK_POS") @JvmStatic val BLOCK_POS = NMS.getNMSClass("BlockPosition", "core.BlockPosition")
        @get:JvmName("ITEM_STACK") @JvmStatic val ITEM_STACK = NMS.getNMSClass("ItemStack", "world.item.ItemStack")
        @get:JvmName("ENTITY") @JvmStatic val ENTITY = NMS.getNMSClass("Entity", "world.entity.Entity")
        @get:JvmName("BOUNDING_BOX") @JvmStatic val BOUNDING_BOX = NMS.getNMSClass("AxisAlignedBB", "world.phys.AxisAlignedBB")
        @get:JvmName("ENTITY_PLAYER") @JvmStatic val ENTITY_PLAYER = NMS.getNMSClass("EntityPlayer", "server.level.EntityPlayer")
        @get:JvmName("ENTITY_BOUNDING_BOX") @JvmStatic val ENTITY_BOUNDING_BOX = ENTITY.findMethod(BOUNDING_BOX.get(), 0, false)
        @get:JvmName("HUMAN") @JvmStatic val HUMAN = NMS.getNMSClass("EntityHuman", "world.entity.player.EntityHuman")
        @get:JvmName("PLAYER_CONNECTION") @JvmStatic val PLAYER_CONNECTION = NMS.getNMSClass("PlayerConnection", "server.network.PlayerConnection")
        @get:JvmName("NETWORK_MANAGER") @JvmStatic val NETWORK_MANAGER = NMS.getNMSClass("NetworkManager", "network.NetworkManager")
        @get:JvmName("MOB_EFFECT_LIST") @JvmStatic val MOB_EFFECT_LIST = NMS.getNMSClass("MobEffectList", "world.effect.MobEffectList")
        @get:JvmName("PLAYER_INTERACT_MANAGER") @JvmStatic val PLAYER_INTERACT_MANAGER = NMS.getNMSClass("PlayerInteractManager", "server.level.PlayerInteractManager")
        @get:JvmName("BLOCK") @JvmStatic val BLOCK = NMS.getNMSClass("Block", "world.level.block.Block")
        @get:JvmName("IBLOCK_DATA") @JvmStatic val IBLOCK_DATA = NMS.getNMSClass("IBlockData", "world.level.block.state.IBlockData")
        @get:JvmName("WORLD") @JvmStatic val WORLD = NMS.getNMSClass("World", "world.level.World")
        @get:JvmName("SOUND_EFFECT") @JvmStatic val SOUND_EFFECT = NMS.getNMSClass("SoundEffect", "sounds.SoundEffect")
        @get:JvmName("MINECRAFT_KEY") @JvmStatic val MINECRAFT_KEY = NMS.getNMSClass("MinecraftKey", "resources.MinecraftKey")
        @get:JvmName("DATA_WATCHER") @JvmStatic val DATA_WATCHER = NMS.getNMSClass("DataWatcher", "network.syncher.DataWatcher")
        @get:JvmName("ITEM") @JvmStatic val ITEM = NMS.getNMSClass("Item", "world.item.Item")
        @get:JvmName("IMATERIAL") @JvmStatic val IMATERIAL = NMS.getNMSClass("IMaterial", "world.item.IMaterial")
        @get:JvmName("DEDICATED_SERVER") @JvmStatic val DEDICATED_SERVER = NMS.getNMSClass("DedicatedServer", "server.dedicated.DedicatedServer")
        @get:JvmName("PACKET_DATA_SERIALIZER") @JvmStatic val PACKET_DATA_SERIALIZER = NMS.getNMSClass("PacketDataSerializer", "network.protocol.PacketDataSerializer")
        @get:JvmName("DIMENSION_MANAGER") @JvmStatic val DIMENSION_MANAGER = NMS.getNMSClass("DimensionManager", "world.level.dimension.DimensionManager")
        @get:JvmName("GAME_PROFILE") @JvmStatic val GAME_PROFILE = NMS.getMojangAuthClass("GameProfile")
        @get:JvmName("ICHAT_BASE_COMPONENT") @JvmStatic val ICHAT_BASE_COMPONENT = NMS.getNMSClass("IChatBaseComponent", "network.chat.IChatBaseComponent")
        @get:JvmName("TILE_ENTITY_COMMAND") @JvmStatic val TILE_ENTITY_COMMAND = NMS.getNMSClass("TileEntityCommand", "world.level.block.entity.TileEntityCommand")
        @get:JvmName("DIFFICULTY") @JvmStatic val DIFFICULTY = NMS.getNMSClass("EnumDifficulty", "world.EnumDifficulty")
        @get:JvmName("CHAT_SERIALIZER") @JvmStatic val CHAT_SERIALIZER = ICHAT_BASE_COMPONENT.subClass("ChatSerializer") ?: NMS.getNMSClass("ChatSerializer")

        // Methods
        @get:JvmName("ICHAT_GET_MESSAGE") @JvmStatic val ICHAT_GET_MESSAGE = ICHAT_BASE_COMPONENT.findMethod(String::class.java, 0, false)
        @get:JvmName("ICHAT_GET_TEXT") @JvmStatic val ICHAT_FROM_MESSAGE = CHAT_SERIALIZER.findMethod(String::class.java, 0)
    }

    object Enums {
        @get:JvmName("GAMEMODE") @JvmStatic val GAMEMODE = NMS.getNMSClass("EnumGamemode", "world.level.EnumGamemode")
    }

}