package me.sleightofmind.hungergames.tasks;

import me.sleightofmind.hungergames.Main;

import org.bukkit.scheduler.BukkitRunnable;

public class GameStartTask extends BukkitRunnable {

	@Override
	public void run() {
		if(Main.timeLeftToStart > 0){
			Main.timeLeftToStart--;
		}else{
			Main.startGame();
			cancel();
		}
		
	}

}
