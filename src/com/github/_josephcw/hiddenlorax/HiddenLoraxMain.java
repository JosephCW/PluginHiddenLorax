package com.github._josephcw.hiddenlorax;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import com.github._josephcw.hiddenlorax.configmanager.SimpleConfig;
import com.github._josephcw.hiddenlorax.configmanager.SimpleConfigManager;


public class HiddenLoraxMain extends JavaPlugin implements Listener {
	final String cUsage = ChatColor.GOLD + "/<REPL> <on/off/delay/status>";
	SimpleConfigManager manager = new SimpleConfigManager(this);
	SimpleConfig loraxConfig;
	
	BukkitTask taskID;
	
	LoraxTimer lTimer;
	List<Entity> serverEntities;
	
	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		
		manager = new SimpleConfigManager(this);
		loraxConfig = manager.getNewConfig("loraxConfig.yml", new String[] {
				"Hidden Lorax Auto Planter Config"
		});
		
		if (loraxConfig.getKeys().size() != 2) {
			loadConfigDefautlsIfNotPresent();
		}
		
		serverEntities = new ArrayList<>();
		if(loraxConfig.getBoolean("enabled")) {
			launchTask();
		}
	}

	private void launchTask() {
		taskID = Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
			@Override
			public void run() {
				if(loraxConfig.getBoolean("enabled")) {
					runLoraxTimer();
				} else {
					taskID.cancel();
				}
			}
		}, 0L, Long.valueOf(loraxConfig.getString("delay")) * 20);
	}

	private void loadConfigDefautlsIfNotPresent() {
		
		Set<String> loraxKeys = loraxConfig.getKeys();
		if (!loraxKeys.contains("enabled")) {
			loraxConfig.set("enabled", true);
			loraxConfig.saveConfig();
		}
		if (!loraxKeys.contains("delay")) {
			loraxConfig.set("delay", 10L);
			loraxConfig.saveConfig();
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
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		for(String arg:args) {
			arg.toLowerCase();
		}
		
		String thisUsage = cUsage.replace("REPL", commandLabel);
		if (args.length == 1) {
			switch (args[0]) {
			case "help":
				sender.sendMessage(thisUsage);
				sender.sendMessage(ChatColor.GOLD + "/" + commandLabel + " <delay> <seconds>");
				break;
			case "status":
				sender.sendMessage(ChatColor.GOLD + "Status: \n"
						+ "Is Enabled: " + loraxConfig.getString("enabled") + "\n"
								+ "Delay: " + loraxConfig.getString("delay"));
				break;
			case "on":
				if (loraxConfig.getBoolean("enabled")) {
					sender.sendMessage(ChatColor.GOLD + "Hidden Lorax - Plugin is already enabled!");
				} else {
					loraxConfig.set("enabled", true);
					loraxConfig.saveConfig();
					sender.sendMessage(ChatColor.GOLD + "HiddenLorax - Plugin Enabled");
					launchTask();
				}
				break;
			case "off":
				loraxConfig.set("enabled", false);
				loraxConfig.saveConfig();
				sender.sendMessage(ChatColor.GOLD + "HiddenLorax - Plugin Disabled");
				break;
			case "delay":
				
				break;
			}
		} else if (args.length == 2) {
			switch(args[0]) {
			case "delay":
					try {
						loraxConfig.set("delay", Long.valueOf(args[1]));
						loraxConfig.saveConfig();
						
						sender.sendMessage(ChatColor.GOLD + "HiddenLorax - Delay for each run set to " + args[1]);
						sender.sendMessage(ChatColor.GOLD + "HiddenLorax - To prevent bugs, please manually restart the \n"
								+ "process with \"/lorax on\" in " + Long.valueOf(args[1]) + " seconds.");
						if (loraxConfig.getBoolean("enabled"))
							loraxConfig.set("enabled", false);
					} catch (Exception e) {
						sender.sendMessage(thisUsage + " <seconds>");
					}
					break;
			default:
				sender.sendMessage(thisUsage);
				break;
			}
		} else {
			sender.sendMessage(ChatColor.GOLD + "\"/HiddenLorax help\"");
		}
		return true;
	}
}
