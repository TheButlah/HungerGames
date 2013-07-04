package me.sleightofmind.hungergames.tasks;

import me.sleightofmind.hungergames.Main;
import me.sleightofmind.hungergames.kits.Kit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class KitInformTask extends BukkitRunnable {

	@Override
	public void run() {
		if(Main.inProgress) return;
		for(Player p : Bukkit.getOnlinePlayers()){
			for(int i = 0; i < 10; i++) p.sendMessage(" ");
			p.sendMessage(ChatColor.BLUE + "Choose your kit now: /kit [kitname]");
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
			
		}
	}

}
