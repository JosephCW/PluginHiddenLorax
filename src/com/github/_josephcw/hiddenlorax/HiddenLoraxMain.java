package com.github._josephcw.hiddenlorax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.github._josephcw.hiddenlorax.configmanager.SimpleConfig;
import com.github._josephcw.hiddenlorax.configmanager.SimpleConfigManager;


public class HiddenLoraxMain extends JavaPlugin implements Listener {
	
	SimpleConfigManager manager = new SimpleConfigManager(this);
	SimpleConfig loraxConfig;
	
	ConfigLoader cl;
	LoraxTimer lTimer;
	List<Entity> serverEntities;
	
	@Override
	public void onEnable() {
		
		manager = new SimpleConfigManager(this);
		loraxConfig = manager.getNewConfig("loraxConfig.yml", new String[] {
				"Hidden Lorax Auto Planter Config"
		});
		
		String isEnabled =  String.valueOf(loraxConfig.get("enabled")) + " file";
		Bukkit.broadcastMessage(isEnabled);
		
		
		cl = new ConfigLoader(this);
		
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		
		serverEntities = new ArrayList<>();
		
		if(cl.isEnabled) {
			Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
				@Override
				public void run() {
					runLoraxTimer();
				}
			}, 0L, cl.getDelayTimer());
		}
	}

	private void runLoraxTimer() {
		if (!serverEntities.isEmpty())
			serverEntities.clear();

		for (Entity sEntity:Bukkit.getWorlds().get(0).getEntities())
			serverEntities.add(sEntity);
		
		lTimer = new LoraxTimer(this);
		lTimer.updateEntityList(serverEntities);
		lTimer.runTaskAsynchronously(this);
	}

	@SuppressWarnings("deprecation")
	public void plantTrees(HashMap<Entity, Byte> myMap) {
		if (!myMap.isEmpty()) {
			Set<Entry<Entity, Byte>> set = myMap.entrySet();
			Iterator<Entry<Entity, Byte>> iterator = set.iterator();
			
			while(iterator.hasNext()) {
				Entry<Entity, Byte> me = iterator.next();
				Block blockToSapling = me.getKey().getLocation().getBlock();
				blockToSapling.setType(Material.SAPLING);
				blockToSapling.setData(me.getValue());
				me.getKey().remove();
			}
		}
	}	
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		loraxConfig.set("enabled", true);
		loraxConfig.saveConfig();
		
		boolean isEnabled = loraxConfig.getBoolean("enabled");
		sender.sendMessage(String.valueOf(isEnabled));
		return true;
	}
}
