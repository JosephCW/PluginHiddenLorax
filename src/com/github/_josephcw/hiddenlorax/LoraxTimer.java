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
	
	private Item sItem;
	private ItemStack sIStack;
	
	public LoraxTimer(HiddenLoraxMain hiddenLoraxMain) {
		this.plugin = hiddenLoraxMain;
	}

	@Override
	public void run() {
		if (doEntitiesExist(serverEntities)) {
			
			for (Entity sEntity : serverEntities) {
				
				if (assignAsItem(sEntity)) {
					
					if (itemIsSapling(sItem)) {
						
						Block blockBeneath = getBlockBeneath(sEntity);
						
						if (allowedSurface(blockBeneath)) {
							
							entityBytes.put(sEntity, getTreeByteFromName(sItem));
							
						}
						
					}
					
				}
				
			}
			/*
			 *  only if there are entities, then run the plant trees method.
			 *  lambda to replace anonymous runnable inner class
			 */
			
			Runnable r = () -> { plugin.plantTrees(entityBytes); };
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, r, 20L);
		}
		
			
	}

	private Byte getTreeByteFromName(Item sItem2) {
		switch(sItem2.getName().substring(18)) {
		case "item.tile.sapling.spruce":
			return (byte) 1;
		case "item.tile.sapling.birch":
			return (byte) 2;
		case "item.tile.sapling.jungle":
			return (byte) 3;
		case "item.tile.sapling.acacia":
			return (byte) 4;
		case "item.tile.sapling.big_oak":
			return (byte) 5;
		default:
			return (byte) 0;
		}
	}

	private boolean allowedSurface(Block blockBeneath) {
		if (blockBeneath.getType().equals(Material.GRASS) ||
				blockBeneath.getType().equals(Material.DIRT)) { 
			return true;
		}
		return false;
	}

	private Block getBlockBeneath(Entity sEntity) {
		return sEntity.getLocation().subtract(0, 1, 0).getBlock();
	}

	private boolean itemIsSapling(Item sItem2) {
		if (sItem2.getType().equals(Material.SAPLING)) {
			return true;
		}
		return false;
	}

	private boolean assignAsItem(Entity sEntity) {
		if (sEntity instanceof Item) {
			this.sItem = (Item) sEntity;
			this.sIStack = this.sItem.getItemStack();
			return true;
		}
		return false;
	}

	private boolean doEntitiesExist(List<Entity> serverEntities2) {
		return serverEntities2.isEmpty();
	}

	public void updateEntityList(List<Entity> serverEntities) {
		this.serverEntities = serverEntities;
	}
}
