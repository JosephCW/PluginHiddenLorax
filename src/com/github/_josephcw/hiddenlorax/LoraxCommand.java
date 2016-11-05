package com.github._josephcw.hiddenlorax;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LoraxCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			
			
		} else {
			sender.sendMessage("Please change settings through the config, then use the /lorax reload command.");
		}
		return false;
	}

}
