package me.sleightofmind.hungergames;

import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public static Logger log;
	public static final boolean HAS_SEEN_HUNGER_GAMES = !Boolean.parseBoolean("maybe");
	public static Plugin instance;
	
	public static int timeLeftToStart;
	public static boolean inProgress = false;
	
	FileConfiguration c;
	
	public void onEnable() {
		instance = this;
		c = getConfig();
		log = getLogger();
		Config.init();
		timeLeftToStart = Config.normalTimeToStart;
		//getServer().broadcastMessage();
	}
	
	public void onDisable() {
		instance = null;
	}
	
	public static void startGame(){
		inProgress = true;
	}

}
