package me.sleightofmind.hungergames.tasks;

import me.sleightofmind.hungergames.Main;
import me.sleightofmind.hungergames.feasts.FeastGen;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FeastCountdownTask extends BukkitRunnable{

	
	private int feasttime = 22;
	
	public void run() {
		feasttime--;
		if(feasttime <= 5){
			for(Player p : Main.instance.getServer().getOnlinePlayers()){
				p.sendMessage(ChatColor.RED + Integer.toString(feasttime) + " minutes until the feast!");
			}
			if(feasttime <= 0) FeastGen.generateFeast();
		}
	}
}
