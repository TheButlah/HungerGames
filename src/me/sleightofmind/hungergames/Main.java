package me.sleightofmind.hungergames;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
import me.sleightofmind.hungergames.tasks.InvincibilityTask;
import me.sleightofmind.hungergames.tasks.KitInformTask;
import me.sleightofmind.hungergames.tasks.VictoryTask;
import me.sleightofmind.hungergames.worldgen.LoadListener;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
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
		defaultkits.add(new Kit_Assassin());
		defaultkits.add(new Kit_Thor());
		defaultkits.add(new Kit_Cultivator());
		defaultkits.add(new Kit_Viper());
		defaultkits.add(new Kit_Suprise());
		defaultkits.add(new Kit_Barbarian());
		
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
		
		if (Config.resetMapOnStartup) Bukkit.getScheduler().runTaskLater(this, new Runnable(){

			@Override
			public void run() {
				resetMap(Config.hgWorld);
			}
			
		},1);
		
	}
	
	public void onDisable() {
		instance = null;
		Bukkit.getScheduler().cancelAllTasks();
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
			World w = p.getWorld();
			p.teleport(w.getHighestBlockAt(w.getSpawnLocation()).getLocation());
			
			
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
		if (Config.resetMapOnGameEnd) Main.resetMap(Config.hgWorld);
		Main.playerkits.clear();
		setupTasks();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void resetMap(String mapname){
		//test
		World w = Main.instance.getServer().getWorld(mapname);
		
		long newSeed = Config.r.nextLong();
		Debug.debug("Seed is " + newSeed);
		
		for(Chunk c : w.getLoadedChunks()){
			w.unloadChunk(c);
		}
		
		try {
			Debug.debug("Beginning reseed!");
			String name = Main.instance.getServer().getClass().getPackage().getName();
			String version = name.substring(name.lastIndexOf('.') + 1);
			//String version = "v" + numbers[0] + "_" + numbers[1] + "_R" + numbers[2];
			//version = "v1_5_R3";
			Debug.debug("Version is " + version);
			Class worldDataClass = Class.forName("net.minecraft.server." + version + ".WorldData");
			Class craftWorldClass = Class.forName("org.bukkit.craftbukkit." + version + ".CraftWorld");
			Class worldServerClass = Class.forName("net.minecraft.server." + version + ".WorldServer");
			setSeed(w, newSeed, version, craftWorldClass, worldServerClass, worldDataClass);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		double totalchunks = Math.pow((((Config.forcefieldSideLength / 16)*2) + 1), 2);
		double donechunks = 0;
		int chunks = 0;
		Debug.debug("Final seed:  " + w.getSeed());
		
		for(int x = w.getSpawnLocation().getChunk().getX() - (Config.forcefieldSideLength / 16) - 1 ; x < w.getSpawnLocation().getChunk().getX() + (Config.forcefieldSideLength / 16) + 1; x++){
			for(int z = w.getSpawnLocation().getChunk().getZ() - (Config.forcefieldSideLength / 16) - 1 ; z < w.getSpawnLocation().getChunk().getZ() + (Config.forcefieldSideLength / 16) + 1; z++){
				w.regenerateChunk(x, z);
				chunks++;
				donechunks++;
				if(chunks == 100){
					chunks = 0;
					System.out.println(donechunks + " chunks generated, " + Double.toString((donechunks/totalchunks)*100).substring(0, 4) + "% complete.");
				}
			}
			
		}
		Debug.debug("Final seed:  " + w.getSeed());
	}
	
	public static <CW,WS,WD> void setSeed(World world, long seed, String version, Class<CW> craftworldclass, Class<WS> worldServerClass, Class<WD> worlddataclass){
		
		@SuppressWarnings("unchecked")
		CW craftworld = (CW) world;
		try {
			Method handleMeth = craftworld.getClass().getMethod("getHandle");
			@SuppressWarnings("unchecked")
			WS handle = (WS) handleMeth.invoke(craftworld, new Object[0]);
			Method worldDataMeth = handle.getClass().getMethod("getWorldData");
			@SuppressWarnings("unchecked")
			WD data = (WD) worldDataMeth.invoke(handle, new Object[0]);
			
			Field f = data.getClass().getDeclaredField("seed");
			f.setAccessible(true);
			f.setLong(data, seed);
			f.setAccessible(false);
			 
			world.save();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			//net.minecraft.server.v1_5_R3.WorldData data = ((org.bukkit.craftbukkit.v1_5_R3.CraftWorld)w).getHandle().getWorldData();
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	public static Kit getKit(Player p){
		return playerkits.get(p.getName());
	}
	
	public static void registerVictory(Player p){
		p.sendMessage(Config.victoryMessage);
		p.setHealth(20);
		Firework f = p.getWorld().spawn(p.getLocation(), Firework.class);
		FireworkMeta fmeta = f.getFireworkMeta();
		
		Builder effect1 = FireworkEffect.builder();
		effect1.with(Type.CREEPER);
		effect1.withColor(Color.GREEN);
		effect1.flicker(true);
		fmeta.addEffect(effect1.build());
		
		fmeta.setPower(1);
		f.setFireworkMeta(fmeta);
		Debug.debug("Sent victory message to " + p.getName());
		Main.instance.getServer().getScheduler().scheduleSyncDelayedTask(instance, new VictoryTask(), 200);
	}

	private static void setupTasks(){
		Bukkit.getScheduler().runTaskTimer(Main.instance, new ForceFieldTask(), 1, 40);
		Bukkit.getScheduler().runTaskTimer(Main.instance, new AssassinCompassTask(), 1, 40);
		Bukkit.getScheduler().runTaskTimer(Main.instance, new KitInformTask(), 100, 100);
	}
	
	public static void cancelInvincibilityTask() {
		if (invincibletask == null) return;
		Bukkit.getScheduler().cancelTask(invincibletask.getTaskId());
	}
	
}
