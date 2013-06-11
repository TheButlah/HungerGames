package me.sleightofmind.hungergames.tasks;

import me.sleightofmind.hungergames.Main;

import org.bukkit.scheduler.BukkitRunnable;

public class GameCountdownTask extends BukkitRunnable {

	@Override
	public void run() {
		if(Main.timeLeftToStart > 0){
			Main.timeLeftToStart--;
			Main.log.info(Integer.toString(Main.timeLeftToStart));
		}else{
			Main.instance.startGame();
		}
		
	}

}
