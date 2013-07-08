package me.sleightofmind.hungergames.tasks;

import me.sleightofmind.hungergames.kits.Kit_Flash;

import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

public class FlashCooldownTask extends BukkitRunnable{

	private Player p;
	
	public FlashCooldownTask(Player p) {
		this.p = p;
	}
	
	@Override
	public void run() {
		PlayerInventory pinv = p.getInventory();
		pinv.remove(Kit_Flash.inactiveTorch);
		pinv.addItem(Kit_Flash.activeTorch);
	}

}
