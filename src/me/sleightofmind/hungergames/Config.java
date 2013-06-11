package me.sleightofmind.hungergames;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {

	public static int minPlayersToStart, playersToQuickStart, initialCountdownTime, quickStartCountdownTime, invincibilityDuration;
	public static FileConfiguration c;
	
	public static void init() {
		c = Main.instance.getConfig();
		try{
			minPlayersToStart = c.getInt("Timer.MinimumPlayersToStart");
			playersToQuickStart = c.getInt("Timer.PlayersToQuickStart");
			initialCountdownTime = c.getInt("Timer.InitialCountdownTime");
			quickStartCountdownTime = c.getInt("Timer.QuickStartCountdownTime");
			invincibilityDuration = c.getInt("Timer.InvincibilityDuration");
		}
		catch(NumberFormatException e){
			Main.log.severe("One of the configuration options has an invalid value.");
			e.printStackTrace();
		}
	}
}
