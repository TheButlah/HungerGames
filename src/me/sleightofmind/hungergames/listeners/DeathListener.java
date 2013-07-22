package me.sleightofmind.hungergames.listeners;

import me.sleightofmind.hungergames.Main;
import me.sleightofmind.hungergames.tasks.PlayerKickTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener{
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent evt){
		Bukkit.getScheduler().runTask(Main.instance, new PlayerKickTask(evt.getEntity()));
		for(Player p : Bukkit.getOnlinePlayers()){
			p.sendMessage(ChatColor.AQUA.toString() + (Bukkit.getOnlinePlayers().length - 1) + " Players remaining.");
		}
	}
}
