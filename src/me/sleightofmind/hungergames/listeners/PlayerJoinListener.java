package me.sleightofmind.hungergames.listeners;

import me.sleightofmind.hungergames.Config;
import me.sleightofmind.hungergames.GameStartTask;
import me.sleightofmind.hungergames.Main;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;


public class PlayerJoinListener implements Listener{
	
	public static BukkitTask gameStartTask = null;
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent evt) {
		if(Main.instance.getServer().getOnlinePlayers().length == Config.minPlayersToStart && !Main.inProgress){
			gameStartTask = Main.instance.getServer().getScheduler().runTaskTimer(Main.instance, new GameStartTask(), 20, 20);
		}
		if (Main.instance.getServer().getOnlinePlayers().length == Config.playersToQuickStart && !Main.inProgress && Main.timeLeftToStart > Config.quickTimeToStart) {
			Main.timeLeftToStart = Config.quickTimeToStart;
		}
	}
	
	public void onPlayerLeave(PlayerQuitEvent evt){
		if(Main.instance.getServer().getOnlinePlayers().length < Config.minPlayersToStart && gameStartTask != null && !Main.inProgress){
			gameStartTask.cancel();
			gameStartTask = null;
		}
	}
}
