package me.sleightofmind.hungergames.listeners;

import me.sleightofmind.hungergames.Config;
import me.sleightofmind.hungergames.Main;
import me.sleightofmind.hungergames.tasks.GameCountdownTask;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class PlayerJoinListener implements Listener{
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent evt) {
		Main.playerkits.put(evt.getPlayer().getName(), null);
		
		if(Main.instance.getServer().getOnlinePlayers().length == Config.minPlayersToStart && !Main.inProgress){
			Main.gameStartTask = Main.instance.getServer().getScheduler().runTaskTimer(Main.instance, new GameCountdownTask(), 20, 20);
		}
		if (Main.instance.getServer().getOnlinePlayers().length == Config.playersToQuickStart && !Main.inProgress && Main.timeLeftToStart > Config.quickStartCountdownTime) {
			Main.timeLeftToStart = Config.quickStartCountdownTime;
		}
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent evt){
		/* Note that the following if statement only checks to see if the online players
		 * is less than OR EQUAL TO the MinimumPlayersToStart. This is because when the player
		 * tries to disconnect, the online player count isn't updated until AFTER this method.
		 */
		if(Main.instance.getServer().getOnlinePlayers().length <= Config.minPlayersToStart && Main.gameStartTask != null && !Main.inProgress){
			Main.instance.getServer().getScheduler().cancelTask(Main.gameStartTask.getTaskId());
			Main.gameStartTask = null;
		}
		Main.playerkits.remove(evt.getPlayer().getName());
	}
}
