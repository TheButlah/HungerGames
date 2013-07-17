package me.sleightofmind.hungergames;

import java.io.File;
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
import me.sleightofmind.hungergames.tasks.FireworkDisplayTask;
import me.sleightofmind.hungergames.tasks.ForceFieldTask;
import me.sleightofmind.hungergames.tasks.InvincibilityTask;
import me.sleightofmind.hungergames.tasks.KitInformTask;
import me.sleightofmind.hungergames.tasks.PoseidonTask;
import me.sleightofmind.hungergames.tasks.SpidermanWebWalkTask;
import me.sleightofmind.hungergames.tasks.SpyCompassTask;
import me.sleightofmind.hungergames.tasks.VictoryTask;
import me.sleightofmind.hungergames.tasks.WerewolfPotionTask;
import me.sleightofmind.hungergames.worldgen.LoadListener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.World.Environment;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
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
	public static int invincibilityTimeLeft;
	public static boolean inProgress = false;
	
	public static boolean invinciblePeriod = true;
	private static BukkitTask invincibletask = null;
	
	public static World hgworld;
	
	
	FileConfiguration c;
	
	public void onEnable() {
		saveDefaultConfig();
		instance = this;
		c = getConfig();
		log = getLogger();
		Config.init();
		timeLeftToStart = Config.initialCountdownTime;
		PluginManager pm = getServer().getPluginManager();
		
		if (Config.resetMap) {
			File worldfile = new File(Bukkit.getWorldContainer(), "HungerGames");
			deleteFolder(worldfile);
		}		
		
		Bukkit.getScheduler().runTask(Main.instance, new BukkitRunnable() {

			@Override
			public void run() {
				Main.hgworld = Bukkit.createWorld(WorldCreator.name("HungerGames").environment(Environment.NORMAL));
			}
			
		});
		
		
		//Set up non-kit related listeners
		pm.registerEvents(new LobbyCancelListener(), this);
		pm.registerEvents(new PlayerJoinListener(), this);
		pm.registerEvents(new LoadListener(), this);
		pm.registerEvents(new SoupListener(), this);
		pm.registerEvents(new CompassListener(), this);
		pm.registerEvents(new FeastBlockListener(), this);
		pm.registerEvents(new DeathListener(), this);
		
		//Load kits into defaultkits array
		defaultkits.add(new Kit_Assassin());
		defaultkits.add(new Kit_Thor());
		defaultkits.add(new Kit_Cultivator());
		defaultkits.add(new Kit_Viper());
		defaultkits.add(new Kit_Suprise());
		defaultkits.add(new Kit_Barbarian());
		defaultkits.add(new Kit_Tank());
		defaultkits.add(new Kit_Werewolf());
		defaultkits.add(new Kit_Spiderman());
		defaultkits.add(new Kit_Urgal());
		defaultkits.add(new Kit_Flash());
		defaultkits.add(new Kit_Poseidon());
		defaultkits.add(new Kit_Miner());
		defaultkits.add(new Kit_Beastmaster());
		defaultkits.add(new Kit_Viking());
		defaultkits.add(new Kit_Spy());
		
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
		
		//set up soup recipies
		ItemStack cj = new ItemStack(Material.MUSHROOM_SOUP);
		ItemMeta cjmeta = cj.getItemMeta();
		cjmeta.setDisplayName("Cacti Juice");
		cj.setItemMeta(cjmeta);
		
		ShapelessRecipe cactusSoupRecipe = new ShapelessRecipe(cj);
		cactusSoupRecipe.addIngredient(Material.CACTUS);
		cactusSoupRecipe.addIngredient(Material.CACTUS);
		cactusSoupRecipe.addIngredient(Material.BOWL);
		
		ItemStack cm = new ItemStack(Material.MUSHROOM_SOUP);
		ItemMeta cmmeta = cm.getItemMeta();
		cmmeta.setDisplayName("Chocolate Milk");
		cm.setItemMeta(cmmeta);
		
		ShapelessRecipe cocoaSoupRecipe = new ShapelessRecipe(cm);
		cocoaSoupRecipe.addIngredient(Material.COCOA);
	    cocoaSoupRecipe.addIngredient(Material.BOWL);
		
		getServer().addRecipe(cactusSoupRecipe);
		getServer().addRecipe(cocoaSoupRecipe);
	}
	
	public void onDisable() {
		instance = null;
		Bukkit.getScheduler().cancelAllTasks();
		Bukkit.shutdown();
	}
	
	private void deleteFolder(File file) {
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				if (f.isDirectory()) {
					deleteFolder(f);
				} else {
					f.delete();
				}				
			}
		}
	}
	
	public static void startGame(){
		timeLeftToStart = Config.initialCountdownTime;
		inProgress = true;
		Main.instance.getServer().getScheduler().cancelTask(gameStartTask.getTaskId());
		
		
		for (int i = 0; i<10; i++) Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(Config.gameStartMessage);
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(Config.invincibilityStartMessage);
		
		for (Player p : instance.getServer().getOnlinePlayers()) {
			p.teleport(Main.hgworld.getHighestBlockAt(Main.hgworld.getSpawnLocation()).getLocation());
			
			
			if(getKit(p) != null){
				
				getKit(p).init(p);
				
			}else{
				p.setDisplayName(p.getName() + "(None)");
				p.sendMessage(Config.noKitMessage);
			}
		}
				
		//Activate invincibility countdown
		invincibilityTimeLeft = Config.invincibilityDuration;
		invincibletask = Bukkit.getScheduler().runTaskTimer(instance, new InvincibilityTask(), 20, 20);
		
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
		
		Main.playerkits.clear();
		Main.defaultkits.clear();
		
		Bukkit.shutdown();
	}
	
	public static Kit getKit(Player p){
		return playerkits.get(p.getName());
	}
	
	public static void registerVictory(Player p){
		p.sendMessage(Config.victoryMessage);
		p.setHealth(20);
		
		Bukkit.getScheduler().runTaskTimer(Main.instance, new FireworkDisplayTask(p.getLocation()), 5, 10);
		
		Debug.debug("Sent victory message to " + p.getName());
		Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(instance, new VictoryTask(), Config.victoryCelebrationDuration*20);
	}

	private static void setupTasks(){
		//note the seemingly arbitrary initial offsets to the tasks.
		//This is to evenly spread the load so that all the tasks don't get run at the same time.
		Bukkit.getScheduler().runTaskTimer(Main.instance, new ForceFieldTask(), 1, 2*20);
		Bukkit.getScheduler().runTaskTimer(Main.instance, new AssassinCompassTask(), 1, 2*20);
		Bukkit.getScheduler().runTaskTimer(Main.instance, new KitInformTask(), 20*5, 5*20);
		Bukkit.getScheduler().runTaskTimer(Main.instance, new WerewolfPotionTask(), 20*10, 10*20);
		Bukkit.getScheduler().runTaskTimer(Main.instance, new SpidermanWebWalkTask(), 30, 20);
		Bukkit.getScheduler().runTaskTimer(Main.instance, new PoseidonTask(), 25, 5*20);
		Bukkit.getScheduler().runTaskTimer(Main.instance, new SpyCompassTask(), 35, 2*20);
	}
	
	public static void cancelInvincibilityTask() {
		if (invincibletask == null) return;
		Bukkit.getScheduler().cancelTask(invincibletask.getTaskId());
	}
	
}
