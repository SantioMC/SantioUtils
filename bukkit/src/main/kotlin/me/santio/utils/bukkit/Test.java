package me.santio.utils.bukkit;

import me.santio.utils.bukkit.features.BukkitFeature;
import me.santio.utils.bukkit.impl.MessageUtils;
import me.santio.utils.bukkit.inventory.item.CustomItem;
import me.santio.utils.bukkit.inventory.menu.Slots;
import me.santio.utils.bukkit.inventory.menu.menus.VirtualMenu;
import me.santio.utils.minecraft.message.Message;
import me.santio.utils.reflection.Reflection;
import me.santio.utils.reflection.ReflectionUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Test extends AttachedJavaPlugin {
    
    @Override
    public void onEnable() {
        this.enableFeature(BukkitFeature.INVENTORY);
    }
    
    public void openInventory(Player player) {
        VirtualMenu menu = new VirtualMenu(3, "Idek")
            .setSlots(Slots.ALL(), new CustomItem(Material.GRAY_STAINED_GLASS_PANE, " "))
            .setSlot(13, new CustomItem(Material.DIAMOND, "Diamond"), (event) -> {
                event.setCancelled(true);
                player.getInventory().addItem(new CustomItem(Material.DIAMOND, "Diamond"));
            })
            .open(player);
        
        menu.simulateClick(player, 13);
    }
    
    public void sendMessage(Player player) {
        MessageUtils.send(
    new Message("&aHello ")
                .add(new Message("world!").hover("&aHover!").command("&aClick!"))
        , player);
    }
    
}
