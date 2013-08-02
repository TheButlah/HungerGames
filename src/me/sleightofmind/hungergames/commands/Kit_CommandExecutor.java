package me.sleightofmind.hungergames.commands;

import java.util.ArrayList;
import java.util.Collections;

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
				
				ArrayList<String> ownedKits = new ArrayList<String>();
				ArrayList<String> unOwnedKits = new ArrayList<String>();
				
				p.sendMessage(ChatColor.BLUE + "Choose your kit with '/kit [kitname]'");
				
				for(Kit k : Main.defaultkits){
					if(Config.canUseKit(p, k.getName().toLowerCase())){
						ownedKits.add(k.getName());
					}else{
						unOwnedKits.add(k.getName());
					}
				}
				Collections.sort(ownedKits);
				Collections.sort(unOwnedKits);
				String hasList = "";
				String otherList = "";
				for(String s : ownedKits){
					hasList += s + ", ";
				}
				if(hasList.length() > 2)hasList = hasList.substring(0, hasList.length() - 2);
				for(String s : unOwnedKits){
					otherList += s + ", ";
				}
				if(otherList.length() > 2)otherList = otherList.substring(0, otherList.length() - 2);
				
				p.sendMessage(ChatColor.GREEN + "Your Kits: " + ChatColor.WHITE + hasList);
				p.sendMessage(ChatColor.GREEN + "Other Kits: " + ChatColor.WHITE + otherList);
				return true;
			}
			else{
				if (sender instanceof Player) {
					Player p = (Player) sender;
					
					if(Main.inProgress){
						p.sendMessage(Config.cannotChangeInProgessMessage);
						return true;
					}
					
					if (Config.canUseKit(p, args[0].toLowerCase())) {
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
						p.sendMessage(Config.doNotHavePermissionMessage);
					}
				} else {
					sender.sendMessage("You must be in-game to perform this command!");
				}
			}
					
		}

		
		return true;
	}

}
