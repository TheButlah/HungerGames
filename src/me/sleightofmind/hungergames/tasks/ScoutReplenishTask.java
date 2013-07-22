package me.sleightofmind.hungergames.tasks;

import me.sleightofmind.hungergames.Main;
import me.sleightofmind.hungergames.kits.Kit_Scout;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ScoutReplenishTask extends BukkitRunnable {

	//private String player;
	
	/*public ScoutReplenishTask(String player){
		this.player = player;
	}*/
	@Override
	public void run() {
		for(Player p : Bukkit.getOnlinePlayers()){
			ItemStack speed = new ItemStack(Material.POTION, 2);
			speed.setDurability((short) 16386);
			
			if(Main.getKit(p) instanceof Kit_Scout){
				if(p.getInventory().firstEmpty() != -1){
					p.getInventory().addItem(speed);
				}else{
					p.sendMessage(ChatColor.YELLOW + "Your inventory was full, so your speed potions were dropped on the ground.");
					p.getLocation().getWorld().dropItem(p.getLocation(), speed);
				}
			}
		}	
	}
	
}
