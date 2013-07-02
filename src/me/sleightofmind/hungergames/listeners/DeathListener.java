package me.sleightofmind.hungergames.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener{
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent evt){
		evt.getEntity().kickPlayer("You were killed and have been kicked from the game.");
	}
}
