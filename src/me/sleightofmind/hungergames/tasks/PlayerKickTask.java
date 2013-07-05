package me.sleightofmind.hungergames.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerKickTask extends BukkitRunnable{

	private Player p;
	public PlayerKickTask(Player p) {
		this.p = p;
	}
	
	@Override
	public void run() {
		p.kickPlayer("You were killed and have been kicked from the game.");
	}

}
