package me.sleightofmind.hungergames.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AssassinCompassTask extends BukkitRunnable {

	private Player target;
	private Player assassin;
	
	public AssassinCompassTask(Player assassin, Player target) {
		this.target = target;
		this.assassin = assassin;
	}
	
	@Override
	public void run() {
		assassin.setCompassTarget(target.getLocation());
	}

}
