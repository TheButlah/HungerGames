package me.sleightofmind.hungergames.tasks;

import me.sleightofmind.hungergames.kits.Kit_Assassin;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AssassinDechargeTask extends BukkitRunnable{

	private Kit_Assassin kit;
	private Player player;
	
	public AssassinDechargeTask(Kit_Assassin k, Player p) {
		kit = k;
		player = p;
	}
	
	@Override
	public void run() {
		kit.decreaseCharge(player);		
	}

}
