package me.sleightofmind.hungergames.commands;

import me.sleightofmind.hungergames.Config;
import me.sleightofmind.hungergames.Main;
import me.sleightofmind.hungergames.kits.DefaultKit;
import me.sleightofmind.hungergames.kits.Kit;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Kit_CommandExecutor implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		if (args.length == 0) {
			if (sender instanceof Player) {
				
				Player p = (Player) sender;
				
				Kit k = Main.playerkits.get(p.getName());
				if (k == null || k instanceof DefaultKit) {
					p.sendMessage(Config.noKitMessage);
					return true;
				}
				p.sendMessage(Config.kitSelectMessage.replaceAll("<kitname>", k.getName()));
				return true;
			}
		}
		
		if (args.length == 1) {
			if(args[0].equalsIgnoreCase("list")){
				Player p = (Player) sender;
				
				p.sendMessage(ChatColor.BLUE + "Choose your kit with '/kit [kitname]'");
				String haslist = "";
				String otherlist = "";
				for(Kit k : Main.defaultkits){
					if(p.hasPermission("HungerGames.kits." + k.getName()) || p.isOp()){
						haslist += k.getName() + ", ";
					}else{
						otherlist += haslist += k.getName() + ", ";
					}
				}
				if(haslist.length() > 2)haslist = haslist.substring(0, haslist.length() - 2);
				if(otherlist.length() > 2)otherlist = otherlist.substring(0, otherlist.length() - 2);
				p.sendMessage(ChatColor.GREEN + "Your Kits: " + ChatColor.WHITE + haslist);
				p.sendMessage(ChatColor.GREEN + "Other Kits: " + ChatColor.WHITE + otherlist);
				return true;
			}
			else{
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if(Main.inProgress){
						p.sendMessage(ChatColor.GOLD + "You may not change kits while the game is in progress.");
						return true;
					}
					
					if (p.isOp() ||p.hasPermission("HungerGames.Kits." + args[0].toLowerCase())) {
						for (Kit k : Main.defaultkits) {
							if (k.getName().equalsIgnoreCase(args[0])) {
								try {
									Main.playerkits.put(p.getName(), k.getClass().newInstance());
								} catch (Exception e) {
									e.printStackTrace();
								}								
								p.sendMessage(Config.kitSelectMessage.replaceAll("<kitname>", k.getName()));
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
