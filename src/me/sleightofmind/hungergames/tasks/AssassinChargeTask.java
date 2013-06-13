package me.sleightofmind.hungergames.tasks;

import me.sleightofmind.hungergames.kits.Kit_Assassin;

import org.bukkit.scheduler.BukkitRunnable;

public class AssassinChargeTask extends BukkitRunnable{

	private Kit_Assassin kit;
	
	public AssassinChargeTask(Kit_Assassin k) {
		kit = k;
	}
	@Override
	public void run() {
		kit.increaseCharge();		
	}

}
