package me.santio.utils.bukkit.inventory.item

import me.santio.utils.bukkit.generic.normalcase
import me.santio.utils.bukkit.plugin
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.inventory.meta.SkullMeta

@Suppress("MemberVisibilityCanBePrivate", "unused")
class CustomItem(material: Material): ItemStack(material) {

    private var meta: ItemMeta? = this.itemMeta
    private fun isValid() = meta != null

    constructor(material: Material, amount: Int): this(material) {
        this.amount = amount
    }

    constructor(material: Material, name: String?): this(material) {
        if (name != null) name(name)
    }

    constructor(item: ItemStack): this(item.type) {
        this.amount = item.amount
        this.itemMeta = item.itemMeta
    }

    companion object {
        @JvmName("getSkull")
        @JvmStatic
        @JvmOverloads
        fun skull(player: OfflinePlayer, name: String? = null): CustomItem {
            return CustomItem(Material.PLAYER_HEAD, name).skull(player)
        }

        @JvmName("book")
        @JvmStatic
        fun book(): Book = Book()
    }

    override fun setItemMeta(itemMeta: ItemMeta?): Boolean {
        meta = itemMeta
        return super.setItemMeta(itemMeta)
    }

    fun name(name: String): CustomItem {
        if (!isValid()) return this
        meta!!.setDisplayName(name)
        this.itemMeta = meta
        return this
    }

    fun name(): String {
        if (!isValid()) return type.name.normalcase()
        return meta!!.displayName
    }

    fun lore(vararg lore: String): CustomItem {
        if (!isValid()) return this
        meta!!.lore = lore.toMutableList()
        this.itemMeta = meta
        return this
    }

    fun lore(lore: Iterable<String>): CustomItem {
        if (!isValid()) return this
        meta!!.lore = lore.toMutableList()
        this.itemMeta = meta
        return this
    }

    fun lore(): List<String> {
        if (!isValid()) return listOf()
        return meta!!.lore ?: listOf()
    }

    fun color(red: Int, green: Int, blue: Int): CustomItem {
        if (!isValid()) return this

        when (meta) {
            is LeatherArmorMeta -> {
                val leatherMeta = meta as LeatherArmorMeta
                leatherMeta.setColor(Color.fromRGB(red, green, blue))
                this.itemMeta = leatherMeta
            }
            is PotionMeta -> {
                val potionMeta = meta as PotionMeta
                potionMeta.color = Color.fromRGB(red, green, blue)
                this.itemMeta = potionMeta
            }
        }

        return this
    }

    @Suppress("DEPRECATION")
    fun skullAsync(name: String): CustomItem {
        if (!isValid() || meta !is SkullMeta) return this
        val skullMeta = meta as SkullMeta
        plugin.sync.async {
            skullMeta.owningPlayer = Bukkit.getOfflinePlayer(name)
            this.itemMeta = skullMeta
        }
        return this
    }

    fun skull(player: OfflinePlayer): CustomItem {
        if (!isValid() || meta !is SkullMeta) return this
        val skullMeta = meta as SkullMeta
        skullMeta.owningPlayer = player
        this.itemMeta = skullMeta
        return this
    }

    fun model(model: Int): CustomItem {
        if (!isValid()) return this
        meta!!.setCustomModelData(model)
        this.itemMeta = meta
        return this
    }

    fun resetModel(): CustomItem {
        if (!isValid()) return this
        meta!!.setCustomModelData(null)
        this.itemMeta = meta
        return this
    }

    fun model(): Int {
        if (!isValid() || !meta!!.hasCustomModelData()) return 0
        return meta!!.customModelData
    }

    fun reset(): CustomItem {
        this.itemMeta = ItemStack(type).itemMeta
        return this
    }

}