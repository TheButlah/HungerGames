package me.sleightofmind.hungergames.commands;

import me.sleightofmind.hungergames.Main;
import me.sleightofmind.hungergames.kits.Kit;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Kit_CommandExecutor implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("kit") && args.length == 2) {
			
			if (args[0].equalsIgnoreCase("select")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if (p.hasPermission("hungergames.kits." + args[1])) {
						for (Kit k : Main.defaultkits) {
							if (k.getName().equalsIgnoreCase(args[1])) {
								Main.playerkits.put(p.getName(), k);
								p.sendMessage(k.getName());
								return true;
							}
						}
					} else {
						p.sendMessage(ChatColor.GOLD + "You dont have access to that kit!");
					}
				} else {
					sender.sendMessage("You must be in-game to perform this command!");
				}
			}
					
		}
		return false;
	}

}
