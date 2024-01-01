package com.me.oyuntarayici;

import com.me.oyuntarayici.api.ConfigurationFile;
import com.me.oyuntarayici.commands.CommandMineCoop;
import com.me.oyuntarayici.managers.BlockCoopManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class BlockMineCoopClass extends JavaPlugin {

    private BlockMineCoopClass instance;

    public BlockMineCoopClass getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance=this;

        registerCommands();
        registerListeners();
        registerFiles();

    }

    private void registerFiles() {
        if (!new File("plugin/BlockMineCool/translateblock.yml").exists()){
            new ConfigurationFile(this,"plugins/BlockMineCool/","translateblock.yml");
        }

        if (!new File("plugin/BlockMineCool/config.yml").exists()){
            new ConfigurationFile(this,"plugins/BlockMineCool/","config.yml");
        }
    }

    private void registerListeners() {
        Bukkit.getServer().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void event(BlockBreakEvent e){
                if (BlockCoopManager.playersUUID.contains(e.getPlayer().getUniqueId())){
                    new BlockCoopManager().mineBlockCoop(e);
                }
            }
        },this);
    }

    private void registerCommands() {
        getCommand("minecoop").setExecutor(new CommandMineCoop(this));
    }

    @Override
    public void onDisable() {

    }

    public static String getColor(String message){
        return message.replaceAll("&","§");
    }

}
