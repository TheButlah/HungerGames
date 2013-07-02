package me.sleightofmind.hungergames;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import me.sleightofmind.hungergames.commands.Kit_CommandExecutor;
import me.sleightofmind.hungergames.commands.Target_CommandExecutor;
import me.sleightofmind.hungergames.kits.*;
import me.sleightofmind.hungergames.listeners.CompassListener;
import me.sleightofmind.hungergames.listeners.DeathListener;
import me.sleightofmind.hungergames.listeners.FeastBlockListener;
import me.sleightofmind.hungergames.listeners.LobbyCancelListener;
import me.sleightofmind.hungergames.listeners.PlayerJoinListener;
import me.sleightofmind.hungergames.listeners.SoupListener;
import me.sleightofmind.hungergames.tasks.AssassinCompassTask;
import me.sleightofmind.hungergames.tasks.FeastCountdownTask;
import me.sleightofmind.hungergames.tasks.ForceFieldTask;
import me.sleightofmind.hungergames.tasks.VictoryTask;
import me.sleightofmind.hungergames.worldgen.LoadListener;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
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
	public static BukkitTask miniFeastGenTask = null;
	
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
		pm.registerEvents(new DeathListener(), this);
		//Load kits into defaultkits array
		defaultkits.add(new Kit_Test());
		defaultkits.add(new Kit_Assassin());
		defaultkits.add(new Kit_Thor());
		defaultkits.add(new Kit_Cultivator());
		defaultkits.add(new Kit_Viper());
		defaultkits.add(new Kit_Suprise());
		
		//setup tasks
		setupTasks();
		
		//Set up Kit related Listeners
		for (Kit k : defaultkits) {
			k.registerListeners();
		}
		
		
		//Set up commands
		getCommand("kit").setExecutor(new Kit_CommandExecutor());
		getCommand("target").setExecutor(new Target_CommandExecutor());
		//
		
		ShapedRecipe grinderRecipe = new ShapedRecipe(new ItemStack(Material.MUSHROOM_SOUP)).shape("bib", "iri", "bib").setIngredient('b', Material.CLAY_BRICK).
		setIngredient('i', Material.IRON_INGOT).setIngredient('r', Material.REDSTONE);
		getServer().addRecipe(grinderRecipe);
		
	}
	
	public void onDisable() {
		instance = null;
		Bukkit.getScheduler().cancelAllTasks();
	}
	
	public void startGame(){
		this.timeLeftToStart = Config.initialCountdownTime;
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
	
	public static void endGame(){
		Bukkit.getScheduler().cancelTasks(instance);
		
		feastGenTask = null;
		miniFeastGenTask = null;
		invinciblePeriod = false;
		
		inProgress = false;
		
		for(Player p : Bukkit.getOnlinePlayers()){
			p.kickPlayer("Server being restarted to reset the map for the next game!");
		}
		Main.resetMap(Config.hgWorld);
		Main.playerkits.clear();
		setupTasks();
	}
	
	public static void unloadMap(String mapname){
		if(instance.getServer().unloadWorld(instance.getServer().getWorld(mapname), false)){
			instance.getServer().getLogger().info("Successfully unloaded " + mapname);
		}
		else{
			instance.getServer().getLogger().severe("COULD NOT UNLOAD " + mapname);
		}
	}

	public static void loadMap(String mapname){
		instance.getServer().createWorld(new WorldCreator(mapname));
	}

	public static void resetMap(String mapname){
		//test
		World w = Main.instance.getServer().getWorld(mapname);
		
		for(Chunk c : w.getLoadedChunks()){
			w.regenerateChunk(c.getX(), c.getZ());
		}
		/*unloadMap(mapname);
		loadMap(mapname);*/
	}
	
	
	
	
	public static Kit getKit(Player p){
		return playerkits.get(p.getName());
	}
	
	public static void registerVictory(Player p){
		p.sendMessage(Config.victoryMessage);
		Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(instance, new VictoryTask(), 200);
	}

	private static void setupTasks(){
		Bukkit.getScheduler().runTaskTimer(Main.instance, new ForceFieldTask(), 1, 40);
		Bukkit.getScheduler().runTaskTimer(Main.instance, new AssassinCompassTask(), 1, 40);
	}
	
}
