package me.sleightofmind.hungergames.tasks;

import me.sleightofmind.hungergames.Config;
import me.sleightofmind.hungergames.Main;
import me.sleightofmind.hungergames.kits.DefaultKit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class KitInformTask extends BukkitRunnable {

	@Override
	public void run() {
		if(Main.inProgress) return;
		for(Player p : Bukkit.getOnlinePlayers()){
			
			
			
			//Debug.debug("MessageDebug: " + p.getName() + " - " + Main.playerkits.get(p.getName()).getName());
			if (!(Main.playerkits.get(p.getName()) instanceof DefaultKit)) continue;
			
			
			p.sendMessage(Config.kitInformMessage);
			
		}
	}

}
