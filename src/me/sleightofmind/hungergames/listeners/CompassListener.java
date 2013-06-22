package me.sleightofmind.hungergames.listeners;

import me.sleightofmind.hungergames.Main;
import me.sleightofmind.hungergames.kits.Kit_Assassin;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class CompassListener implements Listener {
	
	@EventHandler
	public void onPlayerRightClick(PlayerInteractEvent e){
		Player player = e.getPlayer();
		int px = player.getLocation().getBlockX();
		int py = player.getLocation().getBlockY();
		int pz = player.getLocation().getBlockZ();
		float mindistsq = Float.MAX_VALUE;
		Player target = null;
		
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK && Main.instance.getServer().getOnlinePlayers().length > 1 && e.hasItem() && e.getItem().getType().equals(Material.COMPASS) && !(Main.playerkits.get(player.getName()) instanceof Kit_Assassin)){
			for(Player p : Main.instance.getServer().getOnlinePlayers()){
				if(!p.equals(player)){
					int dx = px - p.getLocation().getBlockX();
					int dy = py - p.getLocation().getBlockY();
					int dz = pz - p.getLocation().getBlockZ();
					
					int distsq = (dx*dx) + (dy*dy) + (dz*dz);
					if(mindistsq > distsq){
						mindistsq = distsq;
						target = p;
					}
				}
			}
			player.setCompassTarget(target.getLocation());
		}
	}
}
