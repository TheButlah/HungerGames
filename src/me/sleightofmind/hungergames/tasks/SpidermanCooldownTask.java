package me.sleightofmind.hungergames.tasks;

import me.sleightofmind.hungergames.kits.Kit_Spiderman;
import org.bukkit.scheduler.BukkitRunnable;;

public class SpidermanCooldownTask extends BukkitRunnable {

	Kit_Spiderman kit;
	
	public SpidermanCooldownTask(Kit_Spiderman kit) {
		this.kit = kit;
	}
	
	@Override
	public void run() {
		kit.onCooldown = false;
	}

}
