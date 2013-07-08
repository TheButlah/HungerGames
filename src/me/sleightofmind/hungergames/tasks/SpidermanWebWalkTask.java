package me.sleightofmind.hungergames.tasks;

import me.sleightofmind.hungergames.Main;
import me.sleightofmind.hungergames.kits.Kit_Spiderman;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class SpidermanWebWalkTask extends BukkitRunnable{

	@Override
	public void run() {
		for (String name : Main.playerkits.keySet()) {
			if (Main.playerkits.get(name) instanceof Kit_Spiderman) {
				Player p = Bukkit.getPlayer(name);
				if (p.getLocation().getBlock().getType() == Material.WEB || p.getEyeLocation().getBlock().getType() == Material.WEB) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 40, 1), true);
				}
			}
		}
		
	}

}
