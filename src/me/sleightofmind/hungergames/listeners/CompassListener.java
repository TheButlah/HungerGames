package me.sleightofmind.hungergames.listeners;

import me.sleightofmind.hungergames.Config;
import me.sleightofmind.hungergames.Main;
import me.sleightofmind.hungergames.kits.Kit;
import me.sleightofmind.hungergames.kits.Kit_Assassin;
import me.sleightofmind.hungergames.kits.Kit_Spy;

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
		int mindistsq = Integer.MAX_VALUE;
		int distsq = -1;
		Player target = null;
		
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK && Main.instance.getServer().getOnlinePlayers().length > 1 && e.hasItem() && e.getItem().getType().equals(Material.COMPASS)){
			Kit k = Main.playerkits.get(player.getName());
			if (k instanceof Kit_Assassin) return;
			for(Player p : Main.instance.getServer().getOnlinePlayers()){
				if(!p.equals(player)){
					int dx = px - p.getLocation().getBlockX();
					int dy = py - p.getLocation().getBlockY();
					int dz = pz - p.getLocation().getBlockZ();
					
					distsq = (dx*dx) + (dy*dy) + (dz*dz);
					if(mindistsq > distsq){
						mindistsq = distsq;
						target = p;
					}
				}
			}
			player.setCompassTarget(target.getLocation());
			
			if (k instanceof Kit_Spy) {
				Kit_Spy.message(player, target, distsq, Main.playerkits.get(target.getName()));
				return;
			}
			
			player.sendMessage(Config.compassTrackMessage.replaceAll("<playername>", target.getDisplayName()));
		}
	}
}
