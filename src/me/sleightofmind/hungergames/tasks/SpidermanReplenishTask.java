package me.sleightofmind.hungergames.tasks;

import me.sleightofmind.hungergames.Main;
import me.sleightofmind.hungergames.kits.Kit_Spiderman;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class SpidermanReplenishTask extends BukkitRunnable {

	//private String player;
	
	/*public ScoutReplenishTask(String player){
		this.player = player;
	}*/
	@Override
	public void run() {
		for(Player p : Bukkit.getOnlinePlayers()){
			ItemStack webs = new ItemStack(Material.SNOW_BALL, 6);
			ItemMeta wmeta = webs.getItemMeta();
			wmeta.setDisplayName("Web");
			webs.setItemMeta(wmeta);
			
			if(Main.getKit(p) instanceof Kit_Spiderman){
				if(p.getInventory().firstEmpty() != -1){
					p.getInventory().addItem(webs);
				}else{
					p.sendMessage(ChatColor.YELLOW + "Your inventory was full, so your webs were dropped on the ground.");
					p.getLocation().getWorld().dropItem(p.getLocation(), webs);
				}
			}
		}	
	}
	
}
