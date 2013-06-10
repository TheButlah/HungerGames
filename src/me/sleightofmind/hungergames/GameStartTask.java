package me.sleightofmind.hungergames;

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
