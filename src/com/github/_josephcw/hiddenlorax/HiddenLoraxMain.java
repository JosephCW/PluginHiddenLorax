package com.github._josephcw.hiddenlorax;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;


public class HiddenLoraxMain extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		
		BukkitScheduler scheduler = getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @SuppressWarnings("deprecation")
			@Override
            public void run() {
            	// Check for all entities on the server
               	for (Entity serverEntity:Bukkit.getServer().getWorld("world").getEntities()) {
               		// If it is an item on the ground
               		if (serverEntity instanceof Item) {
               			Item groundItem = (Item) serverEntity;
               			ItemStack oStack = groundItem.getItemStack();
               			// If it is a sapling.
               			if (oStack.getData().getItemType().equals(Material.SAPLING)) {

               				//Have sapling, check if block below is grass.
               				Block iStackBlock = serverEntity.getLocation().getBlock();
               				Block blockBeneath = serverEntity.getLocation().subtract(new Location(serverEntity.getWorld(), 0, 1, 0)).getBlock();
               				if (blockBeneath.getType().equals(Material.GRASS)) {
               					iStackBlock.setType(Material.SAPLING);
               					//iStackBlock.setData((byte) 1);
               					
               					// Set block state type based off of item dropped name
               					switch(groundItem.getName()) {
               						case "item.tile.sapling.spruce":
               							iStackBlock.setData((byte) 1);
               							break;
               						case "item.tile.sapling.birch":
               							iStackBlock.setData((byte) 2);
               							break;
               						case "item.tile.sapling.jungle":
               							iStackBlock.setData((byte) 3);
               							break;
               						case "item.tile.sapling.acacia":
               							iStackBlock.setData((byte) 4);
               							break;
               						case "item.tile.sapling.big_oak":
               							iStackBlock.setData((byte) 5);
               							break;
               						default:
               							iStackBlock.setData((byte) 0);
               					}
               					//MaterialData state = iStackBlock.getState().getData();
               					// If the materialdata for the block is a sapling
               					//if (state instanceof Sapling) {
               					//Case Switch depending on oStack damage
               					//Sapling s = (Sapling) state;
               					//Bukkit.broadcastMessage(groundItem.getName());
               						
               					//s.setSpecies(TreeSpecies.BIRCH);
               					serverEntity.remove();
               					//}
               					//new Sapling(Material.SAPLING, TreeSpecies.ACACIA)
               					//iStackBlock.setMetadata("type", "oak");
               				}
               			}
               		}
               	}
            }
        }, 0L, (10L * 20L));
	}
}
