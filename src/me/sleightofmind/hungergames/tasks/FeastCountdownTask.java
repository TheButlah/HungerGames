package me.sleightofmind.hungergames.tasks;

import me.sleightofmind.hungergames.Config;
import me.sleightofmind.hungergames.Debug;
import me.sleightofmind.hungergames.Main;
import me.sleightofmind.hungergames.feasts.FeastGen;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class FeastCountdownTask extends BukkitRunnable{

	
	private int feasttime = Config.minutesToFeast;
	
	public void run() {
		Debug.debug("Task run.");
		if(Main.instance.getServer().getWorld(Config.hgWorld) == null){
			Debug.debug("World is null lol");
		}
		if(FeastGen.FeastLoc == null) FeastGen.FeastLoc = FeastGen.selectLoc(Main.instance.getServer().getWorld(Config.hgWorld));
		
		feasttime--;
		if(feasttime <= 5){
			if(feasttime <= 0){
				for(Player p : Main.instance.getServer().getOnlinePlayers()){
					p.sendMessage(ChatColor.RED + "The Feast Has Begun!");
				}
				FeastGen.generateFeast();
				new MiniFeastCountdownTask().runTaskTimer(Main.instance, 1200, 1200);
				this.cancel();
			}
			
			for(Player p : Main.instance.getServer().getOnlinePlayers()){
				if(feasttime > 1)p.sendMessage(ChatColor.RED + "The feast will begin at (" + FeastGen.FeastLoc.getX() + ", " + FeastGen.FeastLoc.getY() + ", " + FeastGen.FeastLoc.getZ() + ") in " + feasttime + " minutes!");
				else if(feasttime == 1)p.sendMessage(ChatColor.RED + "The feast will begin at (" + FeastGen.FeastLoc.getX() + ", " + FeastGen.FeastLoc.getY() + ", " + FeastGen.FeastLoc.getZ() + ") in " + feasttime + " minute!");
			}
		}
	}
}
