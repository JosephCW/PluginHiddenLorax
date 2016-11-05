package com.github._josephcw.hiddenlorax;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import com.github._josephcw.hiddenlorax.listeners.SaplingDroppedEvent;


public class Main extends JavaPlugin {
	// Settings
	private static boolean isEnabled;
	public Long tickWaitTimerDelay;
	
	
	BukkitTask taskID;
	LoraxTimer lTimer;
	List<Entity> serverEntities;
	
	private Listener[] eventListeners = {
			new SaplingDroppedEvent(this)
	};
	
	
	@Override
	public void onEnable() {
		
		isEnabled = this.getConfig().getBoolean("enabled");
		tickWaitTimerDelay = this.getConfig().getLong("delay");
		this.saveDefaultConfig();
		
		
		registerCommands();
		registerListeners();
		
		//launchTask();
	}
	
	public static boolean getPluginStatus() {
		return isEnabled;
	}

	private void registerListeners() {
		PluginManager pm = Bukkit.getPluginManager();
		for (Listener l : eventListeners) {
			pm.registerEvents(l, this);
		}
	}

	private void registerCommands() {
		getCommand("hiddenlorax").setExecutor(new LoraxCommand());
	}

	private void handleConfig() {
		// Save the default config if not present.
		this.saveDefaultConfig();
		// Set all of our variables necessary to that found in the config.
		Bukkit.broadcastMessage(String.valueOf(this.getConfig().getBoolean("enabled")));
		
	}
	
	// TODO Move this into our LoraxCommand / Config Class
	private void reloadConfigSettingsFromFile() {
		// reload the config, then reassign our variables
		this.reloadConfig();
		
		handleConfig();
	}

	
//	private void launchTask() {
//		taskID = Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
//			@Override
//			public void run() {
//				if(loraxConfig.getBoolean("enabled")) {
//					runLoraxTimer();
//				} else {
//					taskID.cancel();
//				}
//			}
//		}, 0L, Long.valueOf(loraxConfig.getString("delay")) * 20);
//	}

	private void runLoraxTimer() {
		if (!serverEntities.isEmpty())
			serverEntities.clear();

		for (Entity sEntity:Bukkit.getWorlds().get(0).getEntities())
			serverEntities.add(sEntity);
		
		lTimer = new LoraxTimer(this);
		lTimer.updateEntityList(serverEntities);
		lTimer.runTaskAsynchronously(this);
	}

}
