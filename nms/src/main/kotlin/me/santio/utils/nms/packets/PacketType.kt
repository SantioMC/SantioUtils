package me.santio.utils.nms.packets

import me.santio.utils.nms.NMS
import me.santio.utils.reflection.Reflection
import me.santio.utils.reflection.reflection

@Suppress("unused", "MemberVisibilityCanBePrivate")
object PacketType {

    private fun getPacket(protocol: String, vararg packets: String): Class<*> {
        Reflection.getClass(
            *packets.map { "net.minecraft.network.protocol.$protocol.$it" }.toTypedArray(),
            *packets.map { NMS.getNMSPath(it) }.toTypedArray(),
            *packets.map { it }.toTypedArray()
        )!!.let { return it.get() }
    }

    private fun subClass(clazz: Class<*>, name: String): Class<*>? {
        return clazz.reflection().subClass(name)?.get()
    }

    object Status {
        object Client {
            @get:JvmName("PING") @JvmStatic val PING: Class<*> get() = getPacket("status","PacketStatusInPing")
            @get:JvmName("START") @JvmStatic val START: Class<*> get() = getPacket("status", "PacketStatusInStart")
        }

        object Server {
            @get:JvmName("PONG") @JvmStatic val PONG: Class<*> get() = getPacket("status", "PacketStatusOutPong")
            @get:JvmName("SERVER_INFO") @JvmStatic val SERVER_INFO: Class<*> get() = getPacket("status", "PacketStatusOutServerInfo")
        }
    }

    object Handshake {
        object Client {
            @get:JvmName("SET_PROTOCOL") @JvmStatic val SET_PROTOCOL: Class<*> get() = getPacket("handshake", "PacketHandshakingInSetProtocol")
        }
    }

    object Login {
        object Client {
            @get:JvmName("CUSTOM_PAYLOAD") @JvmStatic val CUSTOM_PAYLOAD: Class<*> get() = getPacket("login", "PacketLoginInCustomPayload")
            @get:JvmName("ENCRYPTION_BEGIN") @JvmStatic val ENCRYPTION_BEGIN: Class<*> get() = getPacket("login", "PacketLoginInEncryptionBegin")
            @get:JvmName("START") @JvmStatic val START: Class<*> get() = getPacket("login", "PacketLoginInStart")
        }

        object Server {
            @get:JvmName("CUSTOM_PAYLOAD") @JvmStatic val CUSTOM_PAYLOAD: Class<*> get() = getPacket("login", "PacketLoginOutCustomPayload")
            @get:JvmName("DISCONNECT") @JvmStatic val DISCONNECT: Class<*> get() = getPacket("login", "PacketLoginOutDisconnect")
            @get:JvmName("ENCRYPTION_BEGIN") @JvmStatic val ENCRYPTION_BEGIN: Class<*> get() = getPacket("login", "PacketLoginOutEncryptionBegin")
            @get:JvmName("SUCCESS") @JvmStatic val SUCCESS: Class<*> get() = getPacket("login", "PacketLoginOutSuccess")
            @get:JvmName("SET_COMPRESSION") @JvmStatic val SET_COMPRESSION: Class<*> get() = getPacket("login", "PacketLoginOutSetCompression")
        }
    }

    object Play {
        object Client {
            @get:JvmName("ABILITIES") @JvmStatic val ABILITIES: Class<*> get() = getPacket("game", "PacketPlayInAbilities")
            @get:JvmName("ADVANCEMENTS") @JvmStatic val ADVANCEMENTS: Class<*> get() = getPacket("game", "PacketPlayInAdvancements")
            @get:JvmName("ARM_ANIMATION") @JvmStatic val ARM_ANIMATION: Class<*> get() = getPacket("game", "PacketPlayInArmAnimation")
            @get:JvmName("AUTO_RECIPE") @JvmStatic val AUTO_RECIPE: Class<*> get() = getPacket("game", "PacketPlayInAutoRecipe")
            @get:JvmName("B_EDIT") @JvmStatic val B_EDIT: Class<*> get() = getPacket("game", "PacketPlayInBEdit")
            @get:JvmName("BEACON") @JvmStatic val BEACON: Class<*> get() = getPacket("game", "PacketPlayInBeacon")
            @get:JvmName("BLOCK_DIG") @JvmStatic val BLOCK_DIG: Class<*> get() = getPacket("game", "PacketPlayInBlockDig")
            @get:JvmName("BLOCK_PLACE") @JvmStatic val BLOCK_PLACE: Class<*> get() = getPacket("game", "PacketPlayInBlockPlace")
            @get:JvmName("BOAT_MOVE") @JvmStatic val BOAT_MOVE: Class<*> get() = getPacket("game", "PacketPlayInBoatMove")
            @get:JvmName("CHAT") @JvmStatic val CHAT: Class<*> get() = getPacket("game", "PacketPlayInChat")
            @get:JvmName("CLIENT_COMMAND") @JvmStatic val CLIENT_COMMAND: Class<*> get() = getPacket("game", "PacketPlayInClientCommand")
            @get:JvmName("CLOSE_WINDOW") @JvmStatic val CLOSE_WINDOW: Class<*> get() = getPacket("game", "PacketPlayInCloseWindow")
            @get:JvmName("CUSTOM_PAYLOAD") @JvmStatic val CUSTOM_PAYLOAD: Class<*> get() = getPacket("game", "PacketPlayInCustomPayload")
            @get:JvmName("DIFFICULTY_CHANGE") @JvmStatic val DIFFICULTY_CHANGE: Class<*> get() = getPacket("game", "PacketPlayInDifficultyChange")
            @get:JvmName("DIFFICULTY_LOCK") @JvmStatic val DIFFICULTY_LOCK: Class<*> get() = getPacket("game", "PacketPlayInDifficultyLock")
            @get:JvmName("ENCHANT_ITEM") @JvmStatic val ENCHANT_ITEM: Class<*> get() = getPacket("game", "PacketPlayInEnchantItem")
            @get:JvmName("ENTITY_ACTION") @JvmStatic val ENTITY_ACTION: Class<*> get() = getPacket("game", "PacketPlayInEntityAction")
            @get:JvmName("ENTITY_NBT_QUERY") @JvmStatic val ENTITY_NBT_QUERY: Class<*> get() = getPacket("game", "PacketPlayInEntityNBTQuery")
            @get:JvmName("FLYING") @JvmStatic val FLYING: Class<*> get() = getPacket("game", "PacketPlayInFlying")
            @get:JvmName("GROUND") @JvmStatic val GROUND: Class<*> get() = subClass(FLYING, "d") ?: FLYING
            @get:JvmName("HELD_ITEM_SLOT") @JvmStatic val HELD_ITEM_SLOT: Class<*> get() = getPacket("game", "PacketPlayInHeldItemSlot")
            @get:JvmName("ITEM_NAME") @JvmStatic val ITEM_NAME: Class<*> get() = getPacket("game", "PacketPlayInItemName")
            @get:JvmName("JIGSAW_GENERATE") @JvmStatic val JIGSAW_GENERATE: Class<*> get() = getPacket("game", "PacketPlayInJigsawGenerate")
            @get:JvmName("KEEP_ALIVE") @JvmStatic val KEEP_ALIVE: Class<*> get() = getPacket("game", "PacketPlayInKeepAlive")
            @get:JvmName("PICK_ITEM") @JvmStatic val PICK_ITEM: Class<*> get() = getPacket("game", "PacketPlayInPickItem")
            @get:JvmName("PONG") @JvmStatic val PONG: Class<*> get() = getPacket("game", "ServerboundPongPacket")
            @get:JvmName("POSITION") @JvmStatic val POSITION: Class<*> get() = subClass(FLYING, "PacketPlayInPosition") ?: getPacket("game", "PacketPlayInPosition")
            @get:JvmName("POSITION_LOOK") @JvmStatic val POSITION_LOOK: Class<*> get() = subClass(FLYING, "PacketPlayInPositionLook") ?: getPacket("game", "PacketPlayInPositionLook")
            @get:JvmName("LOOK") @JvmStatic val LOOK: Class<*> get() = subClass(FLYING, "PacketPlayInLook") ?: getPacket("game", "PacketPlayInLook")
            @get:JvmName("RECIPE_DISPLAYED") @JvmStatic val RECIPE_DISPLAYED: Class<*> get() = getPacket("game", "PacketPlayInRecipeDisplayed")
            @get:JvmName("RESOURCE_PACK_STATUS") @JvmStatic val RESOURCE_PACK_STATUS: Class<*> get() = getPacket("game", "PacketPlayInResourcePackStatus")
            @get:JvmName("SET_COMMAND_BLOCK") @JvmStatic val SET_COMMAND_BLOCK: Class<*> get() = getPacket("game", "PacketPlayInSetCommandBlock")
            @get:JvmName("SET_COMMAND_MINECART") @JvmStatic val SET_COMMAND_MINECART: Class<*> get() = getPacket("game", "PacketPlayInSetCommandMinecart")
            @get:JvmName("SET_CREATIVE_SLOT") @JvmStatic val SET_CREATIVE_SLOT: Class<*> get() = getPacket("game", "PacketPlayInSetCreativeSlot")
            @get:JvmName("SET_JIGSAW") @JvmStatic val SET_JIGSAW: Class<*> get() = getPacket("game", "PacketPlayInSetJigsaw")
            @get:JvmName("SPECTATE") @JvmStatic val SPECTATE: Class<*> get() = getPacket("game", "PacketPlayInSpectate")
            @get:JvmName("STEER_VEHICLE") @JvmStatic val STEER_VEHICLE: Class<*> get() = getPacket("game", "PacketPlayInSteerVehicle")
            @get:JvmName("SETTINGS") @JvmStatic val SETTINGS: Class<*> get() = getPacket("game", "PacketPlayInSettings")
            @get:JvmName("STRUCT") @JvmStatic val STRUCT: Class<*> get() = getPacket("game", "PacketPlayInStruct")
            @get:JvmName("TAB_COMPLETE") @JvmStatic val TAB_COMPLETE: Class<*> get() = getPacket("game", "PacketPlayInTabComplete")
            @get:JvmName("TELEPORT_ACCEPT") @JvmStatic val TELEPORT_ACCEPT: Class<*> get() = getPacket("game", "PacketPlayInTeleportAccept")
            @get:JvmName("TILE_NBT_QUERY") @JvmStatic val TILE_NBT_QUERY: Class<*> get() = getPacket("game", "PacketPlayInTileNBTQuery")
            @get:JvmName("TR_SEL") @JvmStatic val TR_SEL: Class<*> get() = getPacket("game", "PacketPlayInTrSel")
            @get:JvmName("TRANSACTION") @JvmStatic val TRANSACTION: Class<*> get() = getPacket("game", "PacketPlayInTransaction")
            @get:JvmName("UPDATE_SIGN") @JvmStatic val UPDATE_SIGN: Class<*> get() = getPacket("game", "PacketPlayInUpdateSign")
            @get:JvmName("USE_ENTITY") @JvmStatic val USE_ENTITY: Class<*> get() = getPacket("game", "PacketPlayInUseEntity")
            @get:JvmName("USE_ITEM") @JvmStatic val USE_ITEM: Class<*> get() = getPacket("game", "PacketPlayInUseItem")
            @get:JvmName("VEHICLE_MOVE") @JvmStatic val VEHICLE_MOVE: Class<*> get() = getPacket("game", "PacketPlayInVehicleMove")
            @get:JvmName("WINDOW_CLICK") @JvmStatic val WINDOW_CLICK: Class<*> get() = getPacket("game", "PacketPlayInWindowClick")
        }

        object Server {
            @get:JvmName("ADD_VIBRATION_SIGNAL") @JvmStatic val ADD_VIBRATION_SIGNAL: Class<*> get() = getPacket("game", "ClientboundAddVibrationSignalPacket")
            @get:JvmName("ADVANCEMENTS") @JvmStatic val ADVANCEMENTS: Class<*> get() = getPacket("game", "PacketPlayOutAdvancements")
            @get:JvmName("ANIMATION") @JvmStatic val ANIMATION: Class<*> get() = getPacket("game", "PacketPlayOutAnimation")
            @get:JvmName("AUTO_RECIPE") @JvmStatic val AUTO_RECIPE: Class<*> get() = getPacket("game", "PacketPlayOutAutoRecipe")
            @get:JvmName("BLOCK_BREAK_ANIMATION") @JvmStatic val BLOCK_BREAK_ANIMATION: Class<*> get() = getPacket("game", "PacketPlayOutBlockBreakAnimation")
            @get:JvmName("BLOCK_ACTION") @JvmStatic val BLOCK_ACTION: Class<*> get() = getPacket("game", "PacketPlayOutBlockAction")
            @get:JvmName("BLOCK_CHANGE") @JvmStatic val BLOCK_CHANGE: Class<*> get() = getPacket("game", "PacketPlayOutBlockChange")
            @get:JvmName("BOSS") @JvmStatic val BOSS: Class<*> get() = getPacket("game", "PacketPlayOutBoss")
            @get:JvmName("COMBAT_EVENT") @JvmStatic val COMBAT_EVENT: Class<*> get() = getPacket("game", "PacketPlayOutCombatEvent")
            @get:JvmName("CLEAR_TITLES") @JvmStatic val CLEAR_TITLES: Class<*> get() = getPacket("game", "ClientboundClearTitlesPacket")
            @get:JvmName("ENTITY") @JvmStatic val ENTITY: Class<*> get() = getPacket("game", "PacketPlayOutEntity")
            @get:JvmName("ENTITY_DESTROY") @JvmStatic val ENTITY_DESTROY: Class<*> get() = getPacket("game", "PacketPlayOutEntityDestroy")
            @get:JvmName("ENTITY_SOUND") @JvmStatic val ENTITY_SOUND: Class<*> get() = getPacket("game", "PacketPlayOutEntitySound")
            @get:JvmName("ENTITY_TELEPORT") @JvmStatic val ENTITY_TELEPORT: Class<*> get() = getPacket("game", "PacketPlayOutEntityTeleport")
            @get:JvmName("INITIALIZE_BORDER") @JvmStatic val INITIALIZE_BORDER: Class<*> get() = getPacket("game", "ClientboundInitializeBorderPacket")
            @get:JvmName("STATISTIC") @JvmStatic val STATISTIC: Class<*> get() = getPacket("game", "PacketPlayOutStatistic")
            @get:JvmName("SERVER_DIFFICULTY") @JvmStatic val SERVER_DIFFICULTY: Class<*> get() = getPacket("game", "PacketPlayOutServerDifficulty")
            @get:JvmName("CHAT") @JvmStatic val CHAT: Class<*> get() = getPacket("game", "PacketPlayOutChat", "ClientboundPlayerChatPacket")
            @get:JvmName("COMMANDS") @JvmStatic val COMMANDS: Class<*> get() = getPacket("game", "PacketPlayOutCommands")
            @get:JvmName("TAB_COMPLETE") @JvmStatic val TAB_COMPLETE: Class<*> get() = getPacket("game", "PacketPlayOutTabComplete")
            @get:JvmName("MULTI_BLOCK_CHANGE") @JvmStatic val MULTI_BLOCK_CHANGE: Class<*> get() = getPacket("game", "PacketPlayOutMultiBlockChange")
            @get:JvmName("TRANSACTION") @JvmStatic val TRANSACTION: Class<*> get() = getPacket("game", "PacketPlayOutTransaction")
            @get:JvmName("CLOSE_WINDOW") @JvmStatic val CLOSE_WINDOW: Class<*> get() = getPacket("game", "PacketPlayOutCloseWindow")
            @get:JvmName("OPEN_WINDOW") @JvmStatic val OPEN_WINDOW: Class<*> get() = getPacket("game", "PacketPlayOutOpenWindow")
            @get:JvmName("OPEN_BOOK") @JvmStatic val OPEN_BOOK: Class<*> get() = getPacket("game", "PacketPlayOutOpenBook")
            @get:JvmName("OPEN_WINDOW_HORSE") @JvmStatic val OPEN_WINDOW_HORSE: Class<*> get() = getPacket("game", "PacketPlayOutOpenWindowHorse")
            @get:JvmName("OPEN_WINDOW_MERCHANT") @JvmStatic val OPEN_WINDOW_MERCHANT: Class<*> get() = getPacket("game", "PacketPlayOutOpenWindowMerchant")
            @get:JvmName("WINDOW_ITEMS") @JvmStatic val WINDOW_ITEMS: Class<*> get() = getPacket("game", "PacketPlayOutWindowItems")
            @get:JvmName("WINDOW_DATA") @JvmStatic val WINDOW_DATA: Class<*> get() = getPacket("game", "PacketPlayOutWindowData")
            @get:JvmName("MAP_CHUNK") @JvmStatic val MAP_CHUNK: Class<*> get() = getPacket("game", "PacketPlayOutMapChunk", "ClientboundLevelChunkWithLightPacket")
            @get:JvmName("MAP_CHUNK_BULK") @JvmStatic val MAP_CHUNK_BULK: Class<*> get() = getPacket("game", "PacketPlayOutMapChunkBulk")
            @get:JvmName("SET_SLOT") @JvmStatic val SET_SLOT: Class<*> get() = getPacket("game", "PacketPlayOutSetSlot")
            @get:JvmName("SET_COOLDOWN") @JvmStatic val SET_COOLDOWN: Class<*> get() = getPacket("game", "PacketPlayOutSetCooldown")
            @get:JvmName("CUSTOM_PAYLOAD") @JvmStatic val CUSTOM_PAYLOAD: Class<*> get() = getPacket("game", "PacketPlayOutCustomPayload")
            @get:JvmName("CUSTOM_SOUND_EFFECT") @JvmStatic val CUSTOM_SOUND_EFFECT: Class<*> get() = getPacket("game", "PacketPlayOutCustomSoundEffect")
            @get:JvmName("KICK_DISCONNECT") @JvmStatic val KICK_DISCONNECT: Class<*> get() = getPacket("game", "PacketPlayOutKickDisconnect")
            @get:JvmName("ENTITY_STATUS") @JvmStatic val ENTITY_STATUS: Class<*> get() = getPacket("game", "PacketPlayOutEntityStatus")
            @get:JvmName("EXPLOSION") @JvmStatic val EXPLOSION: Class<*> get() = getPacket("game", "PacketPlayOutExplosion")
            @get:JvmName("UNLOAD_CHUNK") @JvmStatic val UNLOAD_CHUNK: Class<*> get() = getPacket("game", "PacketPlayOutUnloadChunk")
            @get:JvmName("GAME_STATE_CHANGE") @JvmStatic val GAME_STATE_CHANGE: Class<*> get() = getPacket("game", "PacketPlayOutGameStateChange")
            @get:JvmName("KEEP_ALIVE") @JvmStatic val KEEP_ALIVE: Class<*> get() = getPacket("game", "PacketPlayOutKeepAlive")
            @get:JvmName("REMOVE_ENTITY_EFFECT") @JvmStatic val REMOVE_ENTITY_EFFECT: Class<*> get() = getPacket("game", "PacketPlayOutRemoveEntityEffect")
            @get:JvmName("OPEN_SIGN_EDITOR") @JvmStatic val OPEN_SIGN_EDITOR: Class<*> get() = getPacket("game", "PacketPlayOutOpenSignEditor")
            @get:JvmName("PLAYER_INFO") @JvmStatic val PLAYER_INFO: Class<*> get() = getPacket("game", "PacketPlayOutPlayerInfo")
            @get:JvmName("ABILITIES") @JvmStatic val ABILITIES: Class<*> get() = getPacket("game", "PacketPlayOutAbilities")
            @get:JvmName("TAB_LIST") @JvmStatic val TAB_LIST: Class<*> get() = getPacket("game", "PacketPlayOutPlayerListHeaderFooter")
            @get:JvmName("SCOREBOARD_OBJECTIVE") @JvmStatic val SCOREBOARD_OBJECTIVE: Class<*> get() = getPacket("game", "PacketPlayOutScoreboardObjective")
            @get:JvmName("UPDATE_SCORE") @JvmStatic val UPDATE_SCORE: Class<*> get() = getPacket("game", "PacketPlayOutScoreboardScore")
            @get:JvmName("DISPLAY_SCOREBOARD") @JvmStatic val DISPLAY_SCOREBOARD: Class<*> get() = getPacket("game", "PacketPlayOutScoreboardDisplayObjective")
            @get:JvmName("TEAMS") @JvmStatic val TEAMS: Class<*> get() = getPacket("game", "PacketPlayOutScoreboardTeam")
            @get:JvmName("CUSTOM_SOUND_EFFECT_LEGACY") @JvmStatic val CUSTOM_SOUND_EFFECT_LEGACY: Class<*> get() = getPacket("game", "PacketPlayOutNamedSoundEffect")
            @get:JvmName("RESOURCE_PACK_SEND") @JvmStatic val RESOURCE_PACK_SEND: Class<*> get() = getPacket("game", "PacketPlayOutResourcePackSend")
            @get:JvmName("RECIPES") @JvmStatic val RECIPES: Class<*> get() = getPacket("game", "PacketPlayOutRecipes")
            @get:JvmName("RESPAWN") @JvmStatic val RESPAWN: Class<*> get() = getPacket("game", "PacketPlayOutRespawn")
            @get:JvmName("ENTITY_HEAD_ROTATION") @JvmStatic val ENTITY_HEAD_ROTATION: Class<*> get() = getPacket("game", "PacketPlayOutEntityHeadRotation")
            @get:JvmName("SELECT_ADVANCEMENT_TAB") @JvmStatic val SELECT_ADVANCEMENT_TAB: Class<*> get() = getPacket("game", "PacketPlayOutSelectAdvancementTab")
            @get:JvmName("WORLD_BORDER") @JvmStatic val WORLD_BORDER: Class<*> get() = getPacket("game", "PacketPlayOutWorldBorder")
            @get:JvmName("CAMERA") @JvmStatic val CAMERA: Class<*> get() = getPacket("game", "PacketPlayOutCamera")
            @get:JvmName("HELD_ITEM_SLOT") @JvmStatic val HELD_ITEM_SLOT: Class<*> get() = getPacket("game", "PacketPlayOutHeldItemSlot")
            @get:JvmName("VIEW_CENTRE") @JvmStatic val VIEW_CENTRE: Class<*> get() = getPacket("game", "PacketPlayOutViewCentre")
            @get:JvmName("VIEW_DISTANCE") @JvmStatic val VIEW_DISTANCE: Class<*> get() = getPacket("game", "PacketPlayOutViewDistance")
            @get:JvmName("SCOREBOARD_DISPLAY_OBJECTIVE") @JvmStatic val SCOREBOARD_DISPLAY_OBJECTIVE: Class<*> get() = getPacket("game", "PacketPlayOutScoreboardDisplayObjective")
            @get:JvmName("ENTITY_METADATA") @JvmStatic val ENTITY_METADATA: Class<*> get() = getPacket("game", "PacketPlayOutEntityMetadata")
            @get:JvmName("ATTACH_ENTITY") @JvmStatic val ATTACH_ENTITY: Class<*> get() = getPacket("game", "PacketPlayOutAttachEntity")
            @get:JvmName("ENTITY_VELOCITY") @JvmStatic val ENTITY_VELOCITY: Class<*> get() = getPacket("game", "PacketPlayOutEntityVelocity")
            @get:JvmName("ENTITY_EQUIPMENT") @JvmStatic val ENTITY_EQUIPMENT: Class<*> get() = getPacket("game", "PacketPlayOutEntityEquipment")
            @get:JvmName("EXPERIENCE") @JvmStatic val EXPERIENCE: Class<*> get() = getPacket("game", "PacketPlayOutExperience")
            @get:JvmName("UPDATE_HEALTH") @JvmStatic val UPDATE_HEALTH: Class<*> get() = getPacket("game", "PacketPlayOutUpdateHealth")
            @get:JvmName("SCOREBOARD_TEAM") @JvmStatic val SCOREBOARD_TEAM: Class<*> get() = getPacket("game", "PacketPlayOutScoreboardTeam")
            @get:JvmName("SCOREBOARD_SCORE") @JvmStatic val SCOREBOARD_SCORE: Class<*> get() = getPacket("game", "PacketPlayOutScoreboardScore")
            @get:JvmName("SET_ACTIONBAR_TEXT") @JvmStatic val SET_ACTIONBAR_TEXT: Class<*> get() = getPacket("game", "ClientboundSetActionBarTextPacket")
            @get:JvmName("SET_BORDER_CENTER") @JvmStatic val SET_BORDER_CENTER: Class<*> get() = getPacket("game", "ClientboundSetBorderCenterPacket")
            @get:JvmName("SET_BORDER_SIZE") @JvmStatic val SET_BORDER_SIZE: Class<*> get() = getPacket("game", "ClientboundSetBorderSizePacket")
            @get:JvmName("SET_BORDER_LERP_SIZE") @JvmStatic val SET_BORDER_LERP_SIZE: Class<*> get() = getPacket("game", "ClientboundSetBorderLerpSizePacket")
            @get:JvmName("SET_BORDER_WARNING_DELAY") @JvmStatic val SET_BORDER_WARNING_DELAY: Class<*> get() = getPacket("game", "ClientboundSetBorderWarningDelayPacket")
            @get:JvmName("SET_BORDER_WARNING_DISTANCE") @JvmStatic val SET_BORDER_WARNING_DISTANCE: Class<*> get() = getPacket("game", "ClientboundSetBorderWarningDistancePacket")
            @get:JvmName("SET_SUBTITLE_TEXT") @JvmStatic val SET_SUBTITLE_TEXT: Class<*> get() = getPacket("game", "ClientboundSetSubtitleTextPacket")
            @get:JvmName("SET_TITLE_TEXT") @JvmStatic val SET_TITLE_TEXT: Class<*> get() = getPacket("game", "ClientboundSetTitleTextPacket")
            @get:JvmName("SET_TITLES_ANIMATION") @JvmStatic val SET_TITLES_ANIMATION: Class<*> get() = getPacket("game", "ClientboundSetTitlesAnimationPacket")
            @get:JvmName("SPAWN_ENTITY") @JvmStatic val SPAWN_ENTITY: Class<*> get() = getPacket("game", "PacketPlayOutSpawnEntity")
            @get:JvmName("SPAWN_ENTITY_EXPERIENCE_ORB") @JvmStatic val SPAWN_ENTITY_EXPERIENCE_ORB: Class<*> get() = getPacket("game", "PacketPlayOutSpawnEntityExperienceOrb")
            @get:JvmName("SPAWN_ENTITY_PAINTING") @JvmStatic val SPAWN_ENTITY_PAINTING: Class<*> get() = getPacket("game", "PacketPlayOutSpawnEntityPainting")
            @get:JvmName("SPAWN_ENTITY_SPAWN") @JvmStatic val SPAWN_ENTITY_SPAWN: Class<*> get() = getPacket("game", "PacketPlayOutSpawnEntitySpawn")
            @get:JvmName("SPAWN_ENTITY_WEATHER") @JvmStatic val SPAWN_ENTITY_WEATHER: Class<*> get() = getPacket("game", "PacketPlayOutSpawnEntityWeather")
            @get:JvmName("SPAWN_ENTITY_LIVING") @JvmStatic val SPAWN_ENTITY_LIVING: Class<*> get() = getPacket("game", "PacketPlayOutSpawnEntityLiving")
            @get:JvmName("SPAWN_POSITION") @JvmStatic val SPAWN_POSITION: Class<*> get() = getPacket("game", "PacketPlayOutSpawnPosition")
            @get:JvmName("STOP_SOUND") @JvmStatic val STOP_SOUND: Class<*> get() = getPacket("game", "PacketPlayOutStopSound")
            @get:JvmName("SYSTEM_CHAT") @JvmStatic val SYSTEM_CHAT: Class<*> get() = getPacket("game", "ClientboundSystemChatPacket")
            @get:JvmName("TAGS") @JvmStatic val TAGS: Class<*> get() = getPacket("game", "PacketPlayOutTags")
            @get:JvmName("NAMED_ENTITY_SPAWN") @JvmStatic val NAMED_ENTITY_SPAWN: Class<*> get() = getPacket("game", "PacketPlayOutNamedEntitySpawn")
            @get:JvmName("NAMED_SOUND_EFFECT") @JvmStatic val NAMED_SOUND_EFFECT: Class<*> get() = getPacket("game", "PacketPlayOutNamedSoundEffect")
            @get:JvmName("COLLECT") @JvmStatic val COLLECT: Class<*> get() = getPacket("game", "PacketPlayOutCollect")
            @get:JvmName("TITLE") @JvmStatic val TITLE: Class<*> get() = getPacket("game", "PacketPlayOutTitle")
            @get:JvmName("WORLD_EVENT") @JvmStatic val WORLD_EVENT: Class<*> get() = getPacket("game", "PacketPlayOutWorldEvent")
            @get:JvmName("WORLD_PARTICLES") @JvmStatic val WORLD_PARTICLES: Class<*> get() = getPacket("game", "PacketPlayOutWorldParticles")
            @get:JvmName("LIGHT_UPDATE") @JvmStatic val LIGHT_UPDATE: Class<*> get() = getPacket("game", "PacketPlayOutLightUpdate")
            @get:JvmName("LOGIN") @JvmStatic val LOGIN: Class<*> get() = getPacket("game", "PacketPlayOutLogin")
            @get:JvmName("LOOK_AT") @JvmStatic val LOOK_AT: Class<*> get() = getPacket("game", "PacketPlayOutLookAt")
            @get:JvmName("MAP") @JvmStatic val MAP: Class<*> get() = getPacket("game", "PacketPlayOutMap")
            @get:JvmName("MOUNT") @JvmStatic val MOUNT: Class<*> get() = getPacket("game", "PacketPlayOutMount")
            @get:JvmName("NBT_QUERY") @JvmStatic val NBT_QUERY: Class<*> get() = getPacket("game", "PacketPlayOutNBTQuery")
            @get:JvmName("PING") @JvmStatic val PING: Class<*> get() = getPacket("game", "ClientboundPingPacket")
            @get:JvmName("POSITION") @JvmStatic val POSITION: Class<*> get() = getPacket("game", "PacketPlayOutPosition")
            @get:JvmName("PLAYER_COMBAT_END") @JvmStatic val PLAYER_COMBAT_END: Class<*> get() = getPacket("game", "ClientboundPlayerCombatEndPacket")
            @get:JvmName("PLAYER_COMBAT_ENTER") @JvmStatic val PLAYER_COMBAT_ENTER: Class<*> get() = getPacket("game", "ClientboundPlayerCombatEnterPacket")
            @get:JvmName("PLAYER_COMBAT_KILL") @JvmStatic val PLAYER_COMBAT_KILL: Class<*> get() = getPacket("game", "ClientboundPlayerCombatKillPacket")
            @get:JvmName("PLAYER_LIST_HEADER_FOOTER") @JvmStatic val PLAYER_LIST_HEADER_FOOTER: Class<*> get() = getPacket("game", "PacketPlayOutPlayerListHeaderFooter")
            @get:JvmName("RECIPE_UPDATE") @JvmStatic val RECIPE_UPDATE: Class<*> get() = getPacket("game", "PacketPlayOutRecipeUpdate")
            @get:JvmName("UPDATE_ATTRIBUTES") @JvmStatic val UPDATE_ATTRIBUTES: Class<*> get() = getPacket("game", "PacketPlayOutUpdateAttributes")
            @get:JvmName("UPDATE_TIME") @JvmStatic val UPDATE_TIME: Class<*> get() = getPacket("game", "PacketPlayOutUpdateTime")
            @get:JvmName("VEHICLE_MOVE") @JvmStatic val VEHICLE_MOVE: Class<*> get() = getPacket("game", "PacketPlayOutVehicleMove")
            @get:JvmName("ENTITY_LOOK") @JvmStatic val ENTITY_LOOK: Class<*> get() = subClass(ENTITY, "PacketPlayOutEntityLook") ?: getPacket("game", "PacketPlayOutRelEntityLook")
            @get:JvmName("REL_ENTITY_MOVE") @JvmStatic val REL_ENTITY_MOVE: Class<*> get() = subClass(ENTITY, "PacketPlayOutRelEntityMove") ?: getPacket("game", "PacketPlayOutRelEntityMove")
            @get:JvmName("REL_ENTITY_MOVE_LOOK") @JvmStatic val REL_ENTITY_MOVE_LOOK: Class<*> get() = subClass(ENTITY, "PacketPlayOutRelEntityMoveLook") ?: getPacket("game", "PacketPlayOutRelEntityMoveLook")
        }

    }
}
