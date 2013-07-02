package me.sleightofmind.hungergames.tasks;

import me.sleightofmind.hungergames.Config;
import me.sleightofmind.hungergames.Main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class VictoryTask extends BukkitRunnable{

	@Override
	public void run() {
		for(Player p : Bukkit.getOnlinePlayers()){
			p.kickPlayer("Server being restarted to reset the map for the next game!");
		}
		Main.resetMap(Config.hgWorld);
	}

}
