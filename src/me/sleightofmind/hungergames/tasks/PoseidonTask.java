package me.sleightofmind.hungergames.tasks;

import me.sleightofmind.hungergames.Main;
import me.sleightofmind.hungergames.kits.Kit_Poseidon;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class PoseidonTask extends BukkitRunnable{

	@Override
	public void run() {
		for (String name : Main.playerkits.keySet()) {
			if (Main.playerkits.get(name) instanceof Kit_Poseidon) {
				Player p  = Bukkit.getPlayer(name);
				Material type = p.getLocation().getBlock().getType();
				if (type == Material.STATIONARY_WATER || type == Material.WATER) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 120, 0), true);
					p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 120, 1), true);
					p.setRemainingAir(p.getMaximumAir());
				}
			}
		}		
	}

}
