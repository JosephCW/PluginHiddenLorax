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
	public boolean isEnabled;
	public Long tickWaitTimerDelay;
	
	
	BukkitTask taskID;
	LoraxTimer lTimer;
	List<Entity> serverEntities;
	
	private Listener[] listeners = {
			new SaplingDroppedEvent()
	};
	
	@Override
	public void onEnable() {
		handleConfig();
		registerCommands();
		registerListeners();
		
		//launchTask();
	}

	private void registerListeners() {
		PluginManager pm = Bukkit.getPluginManager();
		for (Listener l : listeners) {
			pm.registerEvents(l, this);
		}
	}

	private void registerCommands() {
		getCommand("hiddenlorax").setExecutor(new LoraxCommand());
	}

	private void handleConfig() {
		this.saveDefaultConfig();
		
		isEnabled = this.getConfig().getBoolean("enabled");
		tickWaitTimerDelay = this.getConfig().getLong("delay");
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
