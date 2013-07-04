package me.sleightofmind.hungergames.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener{
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent evt){
		evt.getEntity().kickPlayer("You were killed and have been kicked from the game.");
		for(Player p : Bukkit.getOnlinePlayers()){
			p.sendMessage(ChatColor.AQUA + ""+ Bukkit.getOnlinePlayers().length + " Players remaining.");
		}
	}
}
