package me.sleightofmind.hungergames.tasks;

import me.sleightofmind.hungergames.Config;
import me.sleightofmind.hungergames.Main;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ForceFieldTask extends BukkitRunnable {

	@Override
	public void run() {
		World w = Main.instance.getServer().getWorld(Config.hgWorld);
		Location loc = w.getSpawnLocation();
		
		for(Player p : w.getPlayers()){
			if(Math.abs(p.getLocation().getZ() - loc.getZ()) > Config.forcefieldSideLength){
				p.damage(4);
			}if(Math.abs(p.getLocation().getX() - loc.getX()) > Config.forcefieldSideLength){
				p.damage(4);
			}
		}
		
	}
	
}
