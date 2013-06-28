package me.sleightofmind.hungergames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import me.sleightofmind.hungergames.commands.Kit_CommandExecutor;
import me.sleightofmind.hungergames.commands.Target_CommandExecutor;
import me.sleightofmind.hungergames.kits.Kit;
import me.sleightofmind.hungergames.kits.Kit_Assassin;
import me.sleightofmind.hungergames.kits.Kit_Cultivator;
import me.sleightofmind.hungergames.kits.Kit_Suprise;
import me.sleightofmind.hungergames.kits.Kit_Test;
import me.sleightofmind.hungergames.kits.Kit_Thor;
import me.sleightofmind.hungergames.kits.Kit_Viper;
import me.sleightofmind.hungergames.listeners.CompassListener;
import me.sleightofmind.hungergames.listeners.FeastBlockListener;
import me.sleightofmind.hungergames.listeners.LobbyCancelListener;
import me.sleightofmind.hungergames.listeners.PlayerJoinListener;
import me.sleightofmind.hungergames.listeners.SoupListener;
import me.sleightofmind.hungergames.tasks.AssassinCompassTask;
import me.sleightofmind.hungergames.tasks.FeastCountdownTask;
import me.sleightofmind.hungergames.tasks.ForceFieldTask;
import me.sleightofmind.hungergames.worldgen.LoadListener;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Main extends JavaPlugin {
	
	public static final boolean HAS_SEEN_HUNGER_GAMES = !Boolean.parseBoolean("maybe");
	
	public static Logger log;
	public static Main instance;
	public static HashMap<String, Kit> playerkits = new HashMap<String, Kit>();
	public static List<Kit> defaultkits = new ArrayList<Kit>();
	public static BukkitTask gameStartTask = null;
	public static BukkitTask feastGenTask = null;
	
	public static int timeLeftToStart;
	public static boolean inProgress = false;
	
	public static boolean invinciblePeriod = true;
	
	
	FileConfiguration c;
	
	public void onEnable() {
		saveDefaultConfig();
		instance = this;
		c = getConfig();
		log = getLogger();
		Config.init();
		timeLeftToStart = Config.initialCountdownTime;
		PluginManager pm = getServer().getPluginManager();
		
		
		//Set up non-kit related listeners
		pm.registerEvents(new LobbyCancelListener(), this);
		pm.registerEvents(new PlayerJoinListener(), this);
		pm.registerEvents(new LoadListener(), this);
		pm.registerEvents(new SoupListener(), this);
		pm.registerEvents(new CompassListener(), this);
		pm.registerEvents(new FeastBlockListener(), this);
		
		//Load kits into defaultkits array
		defaultkits.add(new Kit_Test());
		defaultkits.add(new Kit_Assassin());
		defaultkits.add(new Kit_Thor());
		defaultkits.add(new Kit_Cultivator());
		defaultkits.add(new Kit_Viper());
		defaultkits.add(new Kit_Suprise());
		Bukkit.broadcastMessage(Integer.toString(defaultkits.size()));
		
		//setup tasks
		this.getServer().getScheduler().scheduleSyncDelayedTask(this, new ForceFieldTask(), 20);
		Bukkit.getScheduler().runTaskTimer(this, new AssassinCompassTask(), 1, 40);
		
		//Set up Kit related Listeners
		for (Kit k : defaultkits) {
			k.registerListeners();
		}
		
		
		//Set up commands
		getCommand("kit").setExecutor(new Kit_CommandExecutor());
		getCommand("target").setExecutor(new Target_CommandExecutor());
		
		
	}
	
	public void onDisable() {
		instance = null;
	}
	
	public void startGame(){
		inProgress = true;
		Main.instance.getServer().getScheduler().cancelTask(gameStartTask.getTaskId());
		
		
		for (int i = 0; i<10; i++) getServer().broadcastMessage("");
		getServer().broadcastMessage(Config.gameStartMessage);
		getServer().broadcastMessage("");
		getServer().broadcastMessage(Config.invincibilityStartMessage);
		
		for (Player p : instance.getServer().getOnlinePlayers()) {
			World w = p.getWorld();
			p.teleport(w.getHighestBlockAt(w.getSpawnLocation()).getLocation());
			if(getKit(p) != null){
				getKit(p).init(p);
				
			}
		}
				
		//Activate invincibility countdown
		getServer().getScheduler().runTaskLater(this, new BukkitRunnable() {
			@Override
			public void run() { 
				Main.invinciblePeriod = false; 
				Main.instance.getServer().broadcastMessage(Config.invincibilityExpireMessage);
			}
		}, Config.invincibilityDuration * 20);
		
		feastGenTask = new FeastCountdownTask().runTaskTimer(Main.instance, 1200, 1200);
	}
	
	public void unloadMap(String mapname){
		if(getServer().unloadWorld(getServer().getWorld(mapname), false)){
			getServer().getLogger().info("Successfully unloaded " + mapname);
		}
		else{
			getServer().getLogger().severe("COULD NOT UNLOAD " + mapname);
		}
	}

	public void loadMap(String mapname){
		getServer().createWorld(new WorldCreator(mapname));
	}

	public void resetMap(String mapname){
		unloadMap(mapname);
		loadMap(mapname);
	}
	
	public static Kit getKit(Player p){
		return playerkits.get(p.getName());
	}

}
