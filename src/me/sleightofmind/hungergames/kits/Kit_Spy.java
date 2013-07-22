package me.sleightofmind.hungergames.kits;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Kit_Spy extends Kit{
	
	public Kit_Spy() {
		name = "Spy";
		items = new ItemStack[0];
	}
	
	public static void message(Player player, Player other, double distancesquared, Kit kitother) {
		player.sendMessage(ChatColor.AQUA + other.getName() + ChatColor.GREEN + "[" + kitother.getName() + "]" + ChatColor.RESET + " is " + ChatColor.BOLD + Integer.toString((int) Math.sqrt(distancesquared)) + ChatColor.GREEN + " blocks from you!");
	}

}
