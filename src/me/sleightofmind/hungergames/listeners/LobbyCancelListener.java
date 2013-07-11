package me.sleightofmind.hungergames.listeners;

import me.sleightofmind.hungergames.Main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class LobbyCancelListener implements Listener{
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent evt){
		if(!Main.inProgress || Main.invinciblePeriod){
			evt.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onHunger(FoodLevelChangeEvent e){
		if(!Main.inProgress){
			e.setCancelled(true);
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
	
	@EventHandler
	public void onBlockBreak(EntityExplodeEvent evt) {
		if (!Main.inProgress) evt.setCancelled(true);
	}
}
