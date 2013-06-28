package me.sleightofmind.hungergames.tasks;

import me.sleightofmind.hungergames.Main;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class GameCountdownTask extends BukkitRunnable {

	@Override
	public void run() {
		Main.timeLeftToStart--;
		if(Main.timeLeftToStart > 0){
			if (Main.timeLeftToStart <= 10) {
				Main.instance.getServer().broadcastMessage(ChatColor.GOLD + Integer.toString(Main.timeLeftToStart) + " Second(s) Remaining!");
			} else if (Main.timeLeftToStart % 60 == 0) {
				Main.instance.getServer().broadcastMessage(ChatColor.GOLD + Integer.toString(Main.timeLeftToStart/60) + " Minute(s) Remaining");
			}
		}else{
			Main.instance.startGame();
		}
		
	}

}
