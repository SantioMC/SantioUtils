package me.santio.utils.bukkit;

import me.santio.utils.bukkit.command.Variant;
import me.santio.utils.bukkit.command.annotation.Command;
import me.santio.utils.bukkit.command.annotation.SubCommand;
import org.bukkit.entity.Player;

public class Test extends AttachedJavaPlugin {
    
    @Override
    public void onEnable() {
        loadCommands();
    }
    
    @Command
    public static class TestCommand {
        
        @SubCommand(variant = Variant.MAIN)
        public void main(Player sender) {
            sender.sendMessage("Hello World!");
        }
        
        @SubCommand(variant = Variant.HELP_AND_FALLBACK)
        public void help(Player sender) {
            sender.sendMessage("Help!");
        }
        
        @SubCommand // Command: /test bob
        public void bob(Player sender, int num) {
            sender.sendMessage("Hello Bob! Number: " + num);
        }
        
    }
    
}
