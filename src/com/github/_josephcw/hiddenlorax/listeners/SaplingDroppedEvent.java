package com.github._josephcw.hiddenlorax.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;


public class SaplingDroppedEvent implements Listener{

	@EventHandler
	public void worldGenerateSaplingEvent(ItemSpawnEvent e) {
		if (e.getEntityType().equals(EntityType.DROPPED_ITEM)) {
			
			if(e.getEntity().getItemStack().getData().equals(Material.SAPLING)) {
				// Add to list if the type of item is of the allowed kinds.
				Bukkit.broadcastMessage("World created a sapling @ " + e.getLocation());
				
				// Create a new ASync thread that will check to see if it can plant the sapling
				
			}
		}
	}
	
}
