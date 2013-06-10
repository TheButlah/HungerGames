package me.sleightofmind.hungergames;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class LobbyCancelListener implements Listener{
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent evt){
		if(!Main.inProgress || Main.invincible){
			evt.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent evt) {
		if (!Main.inProgress) evt.setCancelled(true);
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent evt) {
		if (!Main.inProgress) evt.setCancelled(true);
	}
}
