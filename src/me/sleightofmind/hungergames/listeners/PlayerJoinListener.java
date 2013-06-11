package me.sleightofmind.hungergames.listeners;

import me.sleightofmind.hungergames.Config;
import me.sleightofmind.hungergames.Main;
import me.sleightofmind.hungergames.tasks.GameStartTask;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;


public class PlayerJoinListener implements Listener{
	
	public static BukkitTask gameStartTask = null;
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent evt) {
		Player p = evt.getPlayer();
		p.sendMessage(Integer.toString(p.getInventory().getContents().length));
		Main.playerkits.put(evt.getPlayer().getName(), null);
		
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
		Main.playerkits.remove(evt.getPlayer().getName());
	}
}
