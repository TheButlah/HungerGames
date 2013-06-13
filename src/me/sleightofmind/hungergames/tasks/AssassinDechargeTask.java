package me.sleightofmind.hungergames.tasks;

import me.sleightofmind.hungergames.kits.Kit_Assassin;

import org.bukkit.scheduler.BukkitRunnable;

public class AssassinDechargeTask extends BukkitRunnable{

	private Kit_Assassin kit;
	
	public AssassinDechargeTask(Kit_Assassin k) {
		kit = k;
	}
	
	@Override
	public void run() {
		kit.decreaseCharge();		
	}

}
