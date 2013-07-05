package me.sleightofmind.hungergames.tasks;

import me.sleightofmind.hungergames.Main;

import org.bukkit.scheduler.BukkitRunnable;

public class VictoryTask extends BukkitRunnable{

	@Override
	public void run() {
		Main.endGame();
	}

}
