package com.me.oyuntarayici.managers;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class BlockCoopManager {

    public static ArrayList<UUID> playersUUID=new ArrayList<>();

    private File translateBlockFile=new File("plugins/BlockMineCool/translateblock.yml");
    private FileConfiguration translateBlockConfig= YamlConfiguration.loadConfiguration(translateBlockFile);

    public void playerInventoryBlockCoop(Player player) {
        Inventory playerInventory=player.getInventory();

        for (int slot=35;slot>-1;slot--){

            if (playerInventory.getItem(slot)!=null){
                detectCoopBlock(player,playerInventory.getItem(slot),playerInventory.getItem(slot).getAmount(),slot);
            }
        }
    }

    public void mineBlockCoop(BlockBreakEvent e){
        ConfigurationSection blocksSection=translateBlockConfig.getConfigurationSection("blocks");
        if (blocksSection!=null){
            for (String blocksKeys:blocksSection.getKeys(false)){

                String itemAuthentication=translateBlockConfig.getString("blocks."+blocksKeys+".detected-material");
                String itemTranslater=translateBlockConfig.getString("blocks."+blocksKeys+".translate");

                Material itemType=Material.getMaterial(itemAuthentication);
                Material itemTranslaterBlock=Material.getMaterial(itemTranslater);

                if (e.getBlock().getType().name().equals(itemType.name())){
                    e.setDropItems(false);

                    e.getPlayer().getInventory().addItem(new ItemStack(itemTranslaterBlock));
                }
            }
        } //If blocks key not null allright work to success
    }

    public void detectCoopBlock(Player player,ItemStack item,int amount,int slot){
        ConfigurationSection blocksSection=translateBlockConfig.getConfigurationSection("blocks");
        if (blocksSection!=null){
            for (String blocksKeys:blocksSection.getKeys(false)){

                String itemAuthentication=translateBlockConfig.getString("blocks."+blocksKeys+".detected-material");
                String itemTranslater=translateBlockConfig.getString("blocks."+blocksKeys+".translate");

                Material playerInventoryItem=item.getType();
                Material itemType=Material.getMaterial(itemAuthentication);

                if (playerInventoryItem.name().equals(itemType.name())){
                    Material itemTranslaterMat=Material.getMaterial(itemTranslater);
                    ItemStack itemTranslaterStack=new ItemStack(itemTranslaterMat);

                    if (amount!=-1)

                        itemTranslaterStack.setAmount(amount);

                    if (slot!=-1){
                        player.getInventory().setItem(slot,itemTranslaterStack);
                    }else {
                        player.getInventory().addItem(itemTranslaterStack);
                    }
                }
            }
        } //If blocks key not null allright work to success

    }


}
