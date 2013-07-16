package me.sleightofmind.hungergames.tasks;

import me.sleightofmind.hungergames.Main;
import me.sleightofmind.hungergames.kits.Kit;
import me.sleightofmind.hungergames.kits.Kit_Spy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SpyCompassTask extends BukkitRunnable{

	@Override
	public void run() {
		for (String name : Main.playerkits.keySet()) {
			if (Main.playerkits.get(name) instanceof Kit_Spy) {
				Player p = Bukkit.getPlayer(name);
				for (Entity ent : p.getNearbyEntities(50, 50, 50)) {
					if (!(ent instanceof Player)) return;
					Player other = (Player) ent;
					double distsqu = other.getLocation().distanceSquared(p.getLocation());
					if (distsqu <= 2500) {
						Kit k = Main.playerkits.get(other.getName());
						message(p, other, distsqu, k);
					}
				}
			}
		}
		
	}
	
	private void message(Player player, Player other, double distancesquared, Kit kit) {
		player.sendMessage(ChatColor.AQUA + other.getDisplayName() + ChatColor.GREEN + "[" + kit.getName() + "]" + ChatColor.RESET + " is " + ChatColor.BOLD + Integer.toString((int) Math.sqrt(distancesquared)) + ChatColor.GREEN + " blocks from you!");
	}

}
