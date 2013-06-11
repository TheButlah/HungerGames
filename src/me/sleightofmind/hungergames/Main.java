package me.sleightofmind.hungergames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import me.sleightofmind.hungergames.commands.Kit_CommandExecutor;
import me.sleightofmind.hungergames.kits.Kit;
import me.sleightofmind.hungergames.kits.Kit_Test;
import me.sleightofmind.hungergames.listeners.LobbyCancelListener;
import me.sleightofmind.hungergames.listeners.PlayerJoinListener;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class Main extends JavaPlugin {
	
	public static Logger log;
	public static final boolean HAS_SEEN_HUNGER_GAMES = !Boolean.parseBoolean("maybe");
	public static Main instance;
	
	public static HashMap<String, Kit> playerkits = new HashMap<String, Kit>();
	public static List<Kit> defaultkits = new ArrayList<Kit>();
	public static BukkitTask gameStartTask = null;
	
	public static int timeLeftToStart;
	public static boolean inProgress = false;
	
	public static boolean invinciblePeriod = true;
	
	
	FileConfiguration c;
	
	public void onEnable() {
		instance = this;
		c = getConfig();
		log = getLogger();
		Config.init();
		timeLeftToStart = Config.initialCountdownTime;
		PluginManager pm = getServer().getPluginManager();
		
		//Set up non-kit related listeners
		pm.registerEvents(new LobbyCancelListener(), this);
		pm.registerEvents(new PlayerJoinListener(), this);
		
		//Load kits into defaultkits array
		defaultkits.add(new Kit_Test());
		
		
		//Set up commands
		getCommand("kit").setExecutor(new Kit_CommandExecutor());
	}
	
	public void onDisable() {
		instance = null;
	}
	
	public void startGame(){
		inProgress = true;
		Main.instance.getServer().getScheduler().cancelTask(gameStartTask.getTaskId());
		for (Player p : instance.getServer().getOnlinePlayers()) {
			p.teleport(p.getWorld().getSpawnLocation().add(0, 2, 0));
			if(getKit(p) != null){
				getKit(p).init(p);
				
			}
		}
		
		
		getServer().broadcastMessage("");
		getServer().broadcastMessage("");
		getServer().broadcastMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "The Hunger Games have begun! May the odds be ever in your favour...");
		getServer().broadcastMessage("");
		getServer().broadcastMessage("");
	}
	
	public Kit getKit(Player p){
		return playerkits.get(p.getName());
	}
	
	public List<String> getPlayers(String kitname){
		List<String> result = new ArrayList<String>();
		for (String playername : playerkits.keySet()) {
			if (playerkits.get(playername).getName().equals(kitname)) result.add(playername);
		}
		return result;
	}

}
