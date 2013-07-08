package me.sleightofmind.hungergames.tasks;

import java.util.Random;

import me.sleightofmind.hungergames.Config;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class FireworkDisplayTask extends BukkitRunnable{

	private Location loc;
	public FireworkDisplayTask(Location loc) {
		this.loc = loc;
	}
	
	@Override
	public void run() {
		Random rand = new Random();
		int rx = rand.nextInt(Config.fireworkDisplayDistance*2) - Config.fireworkDisplayDistance;
		int rz = rand.nextInt(Config.fireworkDisplayDistance*2) - Config.fireworkDisplayDistance;
		
		int colorR = rand.nextInt(256);
		int colorB = rand.nextInt(256);
		int colorG = rand.nextInt(256);
		
		int rindex = rand.nextInt(Type.values().length);
	
		Location newloc = loc.clone().add(rx, 0, rz);
		Firework f = loc.getWorld().spawn(newloc, Firework.class);
		FireworkMeta fmeta = f.getFireworkMeta();

		Builder effect = FireworkEffect.builder();
		effect.with(Type.values()[rindex]);
		effect.withColor(Color.fromRGB(colorR, colorG, colorB));
		effect.flicker(rand.nextBoolean());
		effect.trail(rand.nextBoolean());
		fmeta.addEffect(effect.build());
		f.setFireworkMeta(fmeta);
		
	}

}
