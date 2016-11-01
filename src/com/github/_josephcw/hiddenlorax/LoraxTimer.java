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
	private ItemStack sItemStack;
	
	public LoraxTimer(HiddenLoraxMain hiddenLoraxMain) {
		this.plugin = hiddenLoraxMain;
	}

	@Override
	public void run() {
		// Not necessary, just catches weird errors,
		// Possible wrong world's entities pulled
		if (serverEntities.size() > 0) {
			for (Entity sEntity : serverEntities) {
				if (assignAsItem(sEntity)) {
					if (itemStackIsSapling(sItemStack)) {
						Block blockBeneath = getBlockBeneath(sEntity);
						if (allowedSurface(blockBeneath)) {
							if (currentBlockAllowed(sEntity)) {
								entityBytes.put(sEntity, getTreeByteFromName(sItem));
							}
						}
					}
				}
			}
			// only if there are entities, then run the plant trees method.
			// lambda to replace runnable anonymous inner class
			Runnable r = () -> { 
				plugin.plantTrees(entityBytes); 
				
				};
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, r, 20L);
		}
	}

	private boolean currentBlockAllowed(Entity sEntity) {
		return sEntity.getLocation().getBlock().getType().equals(Material.AIR);
	}

	private Byte getTreeByteFromName(Item treeSapling) {
		// item.tile.sapling.XYZ
		switch(treeSapling.getName().substring(18)) {
		case "spruce":
			return (byte) 1;
		case "birch":
			return (byte) 2;
		case "jungle":
			return (byte) 3;
		case "acacia":
			return (byte) 4;
		case "big_oak":
			return (byte) 5;
		default:
			return (byte) 0;
		}
	}

	private boolean allowedSurface(Block blockBeneathSapling) {
		if (blockBeneathSapling.getType().equals(Material.GRASS) ||
				blockBeneathSapling.getType().equals(Material.DIRT)) { 
			return true;
		}
		return false;
	}

	private Block getBlockBeneath(Entity sEntity) {
		return sEntity.getLocation().subtract(0, 1, 0).getBlock();
	}

	private boolean itemStackIsSapling(ItemStack sItemStack2) {
		if (sItemStack2.getData().getItemType().equals(Material.SAPLING)) {
			return true;
		}
		return false;
	}

	private boolean assignAsItem(Entity sEntity) {
		if (sEntity instanceof Item) {
			this.sItem = (Item) sEntity;
			this.sItemStack = sItem.getItemStack();
			return true;
		}
		return false;
	}

	public void updateEntityList(List<Entity> serverEntities) {
		this.serverEntities = serverEntities;
	}
}
