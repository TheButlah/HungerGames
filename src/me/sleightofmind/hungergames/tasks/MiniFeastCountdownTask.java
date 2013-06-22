package me.sleightofmind.hungergames.tasks;

import me.sleightofmind.hungergames.feasts.FeastGen;
import me.sleightofmind.hungergames.feasts.MiniFeast;

import org.bukkit.scheduler.BukkitRunnable;

public class MiniFeastCountdownTask extends BukkitRunnable{

	
	private int minifeasttime = 2;
	
	public void run() {
		minifeasttime--;
		if(minifeasttime <= 0){
			MiniFeast.gen(FeastGen.FeastLoc.getLocation());
			this.cancel();
		}
	}
}