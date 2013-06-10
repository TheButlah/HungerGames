package me.sleightofmind.hungergames;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {

	public static int minPlayersToStart, playersToQuickStart, normalTimeToStart, quickTimeToStart;
	public static FileConfiguration c;
	
	public static void init() {
		c = Main.instance.getConfig();
		try{
			minPlayersToStart = c.getInt("MinimumPlayersToStart");
			playersToQuickStart = c.getInt("PlayersToQuickStart");
			normalTimeToStart = c.getInt("normalTimeToStart");
			quickTimeToStart = c.getInt("QuickTimeToStart");
		}
		catch(NumberFormatException e){
			Main.log.severe("One of the configuration options has an invalid value.");
		}
	}
}
