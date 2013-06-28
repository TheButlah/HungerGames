package me.sleightofmind.hungergames.tasks;

import me.sleightofmind.hungergames.Config;
import me.sleightofmind.hungergames.Main;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class ForceFieldTask extends BukkitRunnable {

	@Override
	public void run() {
		World w = Main.instance.getServer().getWorld(Config.hgWorld);
		Location loc = w.getSpawnLocation();
		
		for(Player p : w.getPlayers()){
			if(Math.abs(p.getLocation().getZ() - loc.getZ()) > Config.forcefieldSideLength || Math.abs(p.getLocation().getX() - loc.getX()) > Config.forcefieldSideLength){
				p.damage(4);
				p.sendMessage(ChatColor.RED + "ALERT! YOU ARE IN THE FORCEFIELD! TURN BACK!");
				p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 3), true);
			} else if(Math.abs(p.getLocation().getZ() - loc.getZ()) > Config.forcefieldSideLength - 15 || Math.abs(p.getLocation().getX() - loc.getX()) > Config.forcefieldSideLength - 15) {
				p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 100, 0), true);
				p.sendMessage(ChatColor.RED + "You are nearing the forcefield!");
			}
		}
		
	}
	
}
