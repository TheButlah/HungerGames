package me.sleightofmind.hungergames.tasks;

import me.sleightofmind.hungergames.Config;
import me.sleightofmind.hungergames.Main;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

public class InvincibilityTask extends BukkitRunnable{
	@Override
	public void run() {
		Main.invincibilityTimeLeft--;
		if(Main.invincibilityTimeLeft == 0){
			Main.invinciblePeriod = false; 
			Main.instance.getServer().broadcastMessage(Config.invincibilityExpireMessage);
			Main.cancelInvincibilityTask();
		}else if(Main.invincibilityTimeLeft < 15 && Main.invincibilityTimeLeft > 0){
			Main.instance.getServer().broadcastMessage(ChatColor.RED + "" + Main.invincibilityTimeLeft + " seconds until invincibility wears off!");
		}
		
	}
}
