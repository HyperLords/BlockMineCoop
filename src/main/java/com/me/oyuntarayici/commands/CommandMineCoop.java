package com.me.oyuntarayici.commands;

import com.me.oyuntarayici.BlockMineCoopClass;
import com.me.oyuntarayici.managers.BlockCoopManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CommandMineCoop implements CommandExecutor, TabCompleter {

    private File configFile=new File("plugins/BlockMineCool/config.yml");
    private FileConfiguration configuration= YamlConfiguration.loadConfiguration(configFile);

    public CommandMineCoop(JavaPlugin plugin){
        plugin.getCommand("minecoop").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){

            Player player=(Player) sender;

            if (player.hasPermission("mine.minecoop.command")){

                if (args.length==0){
                    player.sendMessage(BlockMineCoopClass.getColor(configuration.getString("message.help-message")));
                return true;}

                if (args[0].equalsIgnoreCase("mine")){

                    if (BlockCoopManager.playersUUID.contains(player.getUniqueId())){
                        BlockCoopManager.playersUUID.remove(player.getUniqueId());
                        player.sendMessage(BlockMineCoopClass.getColor(configuration.getString("message.success-disabled-mineblock")));
                    }else {
                        BlockCoopManager.playersUUID.add(player.getUniqueId());
                        player.sendMessage(BlockMineCoopClass.getColor(configuration.getString("message.success-activated-mineblock")));
                    }

                return true;}

                if (player.hasPermission("mine.coopblockinventory.permission")){
                    if (args[0].equalsIgnoreCase("inventory")){
                        new BlockCoopManager().playerInventoryBlockCoop(player);
                        player.sendMessage(BlockMineCoopClass.getColor(configuration.getString("message.success-hot-all-inventory-item")));
                    return true;}
                return true;}


            return true;}else player.sendMessage(BlockMineCoopClass.getColor(configuration.getString("message.not-permission-message")));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> tabs=new ArrayList<>();
        if (sender.hasPermission("minecoop.commands.completer.inventory")){
            tabs.add("inventory");
        }
        if (sender.hasPermission("minecoop.commands.completer.mine")){
            tabs.add("mine");
        }
        return tabs;
    }
}
