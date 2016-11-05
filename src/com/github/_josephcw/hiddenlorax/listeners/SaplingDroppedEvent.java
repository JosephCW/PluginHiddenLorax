package com.github._josephcw.hiddenlorax.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;

import com.github._josephcw.hiddenlorax.Main;
import com.github._josephcw.hiddenlorax.handler.Planter;


public class SaplingDroppedEvent implements Listener{

	Main plugin;
	
	public SaplingDroppedEvent(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void worldGenerateSaplingEvent(ItemSpawnEvent e) {
		
		//Returns true if the plugin is set to enabled.
		if (Main.getPluginStatus()) {
			// If the ItemSpawnEvent  is a dropped item
			if (e.getEntityType().equals(EntityType.DROPPED_ITEM)) {
				// If it is a sapling
				if (e.getEntity().getItemStack().getType().equals(Material.SAPLING)) {
					// Create a new planter planter task for this sapling.
					new Planter(plugin, e, Material.SAPLING).runTaskAsynchronously(plugin);
					
					Bukkit.broadcastMessage(ChatColor.AQUA + "Sapling!");			
				}				
			}
		}
		
	}
}
