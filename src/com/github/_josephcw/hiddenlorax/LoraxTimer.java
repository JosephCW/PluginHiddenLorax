package com.github._josephcw.hiddenlorax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class LoraxTimer extends BukkitRunnable {
	
	HiddenLoraxMain plugin;
	List<Entity> serverEntities = new ArrayList<>();
	HashMap<Entity, Byte> entityBytes = new HashMap<>();
	
	public LoraxTimer(HiddenLoraxMain hiddenLoraxMain) {
		this.plugin = hiddenLoraxMain;
	}

	@Override
	public void run() {
		if (!serverEntities.isEmpty()) {
			for (Entity sEntity:serverEntities) {
				if (sEntity instanceof Item) {
					Item sItem = (Item) sEntity; 
					ItemStack sIStack = sItem.getItemStack();
					if (sIStack.getData().getItemType().equals(Material.SAPLING)) {
						Block sIStackBlockBeneath = sEntity.getLocation().subtract(0, 1, 0).getBlock();
							
						if (sIStackBlockBeneath.getType().equals(Material.GRASS) 
								|| sIStackBlockBeneath.getType().equals(Material.DIRT)) {
							switch(sItem.getName()) {
	   							case "item.tile.sapling.spruce":
	   								entityBytes.put(sEntity, (byte) 1);
	   								break;
	   							case "item.tile.sapling.birch":
	   								entityBytes.put(sEntity, (byte) 2);
	   								break;
	   							case "item.tile.sapling.jungle":
	   								entityBytes.put(sEntity, (byte) 3);
	   								break;
	   							case "item.tile.sapling.acacia":
	   								entityBytes.put(sEntity, (byte) 4);
	   								break;
	   							case "item.tile.sapling.big_oak":
	   								entityBytes.put(sEntity, (byte) 5);
	   								break;
	   							default:
	   								entityBytes.put(sEntity, (byte) 0);
							}
						}
						
					}
				}
			}
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				@Override
				public void run() {
					plugin.plantTrees(entityBytes);
				}
			}, 20L);
		}
	}

	public void updateEntityList(List<Entity> serverEntities) {
		this.serverEntities = serverEntities;
	}
}
