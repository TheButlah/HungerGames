package me.sleightofmind.hungergames.tasks;

import me.sleightofmind.hungergames.Main;
import me.sleightofmind.hungergames.kits.DefaultKit;
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
			
			
			
			//Debug.debug("MessageDebug: " + p.getName() + " - " + Main.playerkits.get(p.getName()).getName());
			if (!(Main.playerkits.get(p.getName()) instanceof DefaultKit)) continue;
			
			
			p.sendMessage(ChatColor.GOLD + "Choose a kit before the game starts! Choose a kit with '/kit name', type '/kit list' to see the list of kits you can choose from.");
			
		}
	}

}
