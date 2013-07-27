package me.sleightofmind.hungergames.listeners;

import me.sleightofmind.hungergames.Config;
import me.sleightofmind.hungergames.Debug;
import me.sleightofmind.hungergames.Main;
import me.sleightofmind.hungergames.kits.DefaultKit;
import me.sleightofmind.hungergames.tasks.GameCountdownTask;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;


public class PlayerJoinListener implements Listener{
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent evt) {
		Player player = evt.getPlayer();
		
		if(Main.inProgress && !(player.isOp() || player.hasPermission("HungerGames.CanJoinGameInProgress"))) {
			player.kickPlayer(ChatColor.GOLD + "You are not allowed to join a game that is in progress!");
			return;
		}
		
		if (player.isDead() && Main.inProgress && !(player.isOp() || player.hasPermission("HungerGames.CanRespawnWhileGameInProgress"))) {
			player.kickPlayer(ChatColor.GOLD + "You are not allowed to respawn while a game is in progress!");		
		}
		
		if (!Main.playerkits.containsKey(player.getName())){
			Main.playerkits.put(player.getName(), new DefaultKit());
			Debug.debug("Adding player " + player.getName() + " to default kit.");
		}
		
		
		if(!Main.inProgress && Main.instance.getServer().getOnlinePlayers().length == Config.minPlayersToStart && !Main.inProgress){
			Main.gameStartTask = Main.instance.getServer().getScheduler().runTaskTimer(Main.instance, new GameCountdownTask(), 20, 20);
		}
		
		if (!Main.inProgress && Main.instance.getServer().getOnlinePlayers().length == Config.playersToQuickStart && !Main.inProgress && Main.timeLeftToStart > Config.quickStartCountdownTime) {
			Main.timeLeftToStart = Config.quickStartCountdownTime;
		}
		
		if (!Main.inProgress){
			World w = Main.hgworld;
			player.teleport(w.getHighestBlockAt(w.getSpawnLocation()).getLocation());
			player.setHealth(player.getMaxHealth());
			player.setFoodLevel(20);
			player.getInventory().clear();
			for (PotionEffect effect : player.getActivePotionEffects()) {
				player.removePotionEffect(effect.getType());
			}
		}
		
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent evt){
		Player p = evt.getPlayer();
		//reset display name
		p.setDisplayName(p.getName());
		
		/* Note that the following if statement only checks to see if the online players
		 * is less than OR EQUAL TO the MinimumPlayersToStart. This is because when the player
		 * tries to disconnect, the online player count isn't updated until AFTER this method.
		 */
		if(Main.instance.getServer().getOnlinePlayers().length <= Config.minPlayersToStart && Main.gameStartTask != null && !Main.inProgress){
			Main.instance.getServer().getScheduler().cancelTask(Main.gameStartTask.getTaskId());
			Main.gameStartTask = null;
		}
		Main.playerkits.remove(evt.getPlayer().getName());
		
		if (Main.inProgress && !(p.isOp() || p.hasPermission("HungerGames.CanJoinGameInProgress")) && Main.playerkits.containsKey(p.getName())) {
			p.setHealth(0);
			Main.playerkits.remove(evt.getPlayer().getName());
		}
		
		if(Main.instance.getServer().getOnlinePlayers().length == 2 && Main.inProgress){
			Player winner = null;
			if(Main.instance.getServer().getOnlinePlayers()[0].getName().equals(p.getName())){
				winner = Main.instance.getServer().getOnlinePlayers()[1];
			}else{
				winner = Main.instance.getServer().getOnlinePlayers()[0];
			}
			Debug.debug("Registering victory for " + winner.getName());
			Main.registerVictory(winner);
		}
	}
}
