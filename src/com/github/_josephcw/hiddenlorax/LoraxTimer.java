package com.github._josephcw.hiddenlorax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class LoraxTimer extends BukkitRunnable {
	
	HiddenLoraxMain plugin;
	List<Entity> serverEntities = new ArrayList<>();
	HashSet<Entity> saplingEntities = new HashSet<>();
	HashSet<Material> saplingTypes = new HashSet<>();
	
	private Item sItem;
	private ItemStack sItemStack;
	
	public LoraxTimer(HiddenLoraxMain hiddenLoraxMain) {
		this.plugin = hiddenLoraxMain;
	}

	@Override
	public void run() {
		saplingTypes.addAll(Arrays.asList(Material.ACACIA_SAPLING, Material.SPRUCE_SAPLING, Material.BIRCH_SAPLING, Material.DARK_OAK_SAPLING, Material.JUNGLE_SAPLING, Material.OAK_SAPLING));
		// Not necessary, just catches weird errors,
		// Possible wrong world's entities pulled
		if (!serverEntities.isEmpty()) {
			for (Entity sEntity : serverEntities) {
				if (assignAsItem(sEntity)) {
					if (itemStackIsSapling(sItemStack)) {
						Block blockBeneath = getBlockBeneath(sEntity);
						if (allowedSurface(blockBeneath)) {
							if (currentBlockAllowed(sEntity)) {
								saplingEntities.add(sEntity);
							}
						}
					}
				}
			}
			// only if there are entities, then run the plant trees method.
			// lambda to replace runnable anonymous inner class
			Runnable r = () -> { 
				plantTrees(saplingEntities); 
				
				};
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, r, 20L);
		}
	}
	
	public void plantTrees(Set<Entity> saplings) {
		if (!saplings.isEmpty()) {
			Iterator<Entity> iterator = saplings.iterator();
			
			while(iterator.hasNext()) {
				Entity me = iterator.next();
				Block blockToSapling = me.getLocation().getBlock();
				
				/*
				 * We need to check to see if the block is still valid now
				 * that we are on a syncronus task with the server.
				 */
				if (currentBlockAllowed(me)) {
					// We also need to check that the block beneath is still allowed.
					if (allowedSurface(getBlockBeneath(me))) { 
						blockToSapling.setType(getSaplingMaterialFromItem((Item) me));
					}
				}
				me.remove();
			}
		}
	}	

	private boolean currentBlockAllowed(Entity sEntity) {
		return sEntity.getLocation().getBlock().getType().equals(Material.AIR);
	}

	private Material getSaplingMaterialFromItem(Item treeSapling) {
		return treeSapling.getItemStack().getType();
	}
	
	private boolean allowedSurface(Block blockBeneathSapling) {
		return (blockBeneathSapling.getType().equals(Material.GRASS_BLOCK) ||
				blockBeneathSapling.getType().equals(Material.DIRT));
	}

	private Block getBlockBeneath(Entity sEntity) {
		return sEntity.getLocation().subtract(0, 1, 0).getBlock();
	}

	private boolean itemStackIsSapling(ItemStack sItemStack2) {
		return saplingTypes.contains(sItemStack2.getType());
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
