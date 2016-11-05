package com.github._josephcw.hiddenlorax.handler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.github._josephcw.hiddenlorax.Main;

public class Planter extends BukkitRunnable{

	Main plugin;
	ItemSpawnEvent e;
	Material mat;
	BukkitTask id;
	
	public Planter(Main plugin, ItemSpawnEvent e, Material mat) {
		this.plugin = plugin;
		this.e = e;
		this.mat = mat;
	}

	@Override
	public void run() {
		// The event will always be cancelled. (e.isCancelled())
		// Create a new BukkitRunnable and run Asyncronus every 10 seconds for this sapling.
		id = new BukkitRunnable() {
			@Override
			public void run() {
				
				// We check to see if the item has been picked up / deleted
				// If it was then we no longer need to check it every X amount of ticks
				// Otherwise try to see if we can plant it.
				if (e.getEntity().isDead()) id.cancel(); else handleSapling() ;
			}
		}.runTaskTimerAsynchronously(plugin, 20l, 100l);
		
		
	}
	
	private void handleSapling() {
		Bukkit.broadcastMessage(ChatColor.GREEN + "~ Saplings ~");
	}
}
