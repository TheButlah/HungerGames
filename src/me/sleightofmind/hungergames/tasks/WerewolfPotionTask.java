package me.sleightofmind.hungergames.tasks;

import me.sleightofmind.hungergames.Main;
import me.sleightofmind.hungergames.kits.Kit_Werewolf;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class WerewolfPotionTask extends BukkitRunnable{

	@Override
	public void run() {
		for (String name : Main.playerkits.keySet()) {
			if (Main.playerkits.get(name) instanceof Kit_Werewolf) {
				Player p = Bukkit.getPlayer(name);
				long time = p.getWorld().getTime();
				//if its day
				if (time > 0 && time < 12300) {
					p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 11*20, 0), true);
					return;
				} 
				p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 11*20, 0), true);
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 11*20, 0), true);
			}
		}
		
	}

}
