package me.sleightofmind.hungergames.tasks;

import me.sleightofmind.hungergames.kits.Kit_Thor;

import org.bukkit.scheduler.BukkitRunnable;;

public class ThorCooldownTask extends BukkitRunnable {

	Kit_Thor kit;
	
	public ThorCooldownTask(Kit_Thor kit) {
		this.kit = kit;
	}
	
	@Override
	public void run() {
		kit.onCooldown = false;
	}

}
