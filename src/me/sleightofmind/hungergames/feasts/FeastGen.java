package me.sleightofmind.hungergames.feasts;


import java.util.Arrays;
import java.util.List;
import java.util.Random;

import me.sleightofmind.hungergames.Config;
import me.sleightofmind.hungergames.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;

public class FeastGen {

	static int floatamount = Config.feastFloatDistance;
	public static Block FeastLoc;
	public static int radius = 10;
	
	public static void generateFeast(){
		FeastUtils.clearCyl(FeastLoc.getLocation(), radius, 25, Material.GRASS);
		
		FeastLoc.setType(Material.ENCHANTMENT_TABLE);
		
		for(int x = -2; x <= 2; x++){
			for(int z = -2; z <= 2; z++){
				int worldx = (x + FeastLoc.getX());
				int worldz = (z + FeastLoc.getZ());
				if((Math.abs(x) == Math.abs(z) && x != 0) || (Math.abs(x) == 2 && z == 0) || (Math.abs(z) == 2 && x == 0)){
					generateFeastChest(new Location(FeastLoc.getLocation().getWorld(), worldx, FeastLoc.getY(), worldz));
				}
			}
		}
		
		Bukkit.broadcastMessage(ChatColor.GOLD + "Spawned feast at " + FeastLoc.getX() + ", " + FeastLoc.getY() + ", " + FeastLoc.getZ() + "!");
	}
	
	public static int highest(World w, int x, int z){
		List<Integer> invalidblocks = Arrays.asList(0, 17, 18, 37, 38, 40, 39, 32, 27, 28, 30, 31, 6);
		int highest = Main.hgworld.getMaxHeight();
		for(int i = highest; i > 0; i--){
			if(!invalidblocks.contains(Main.hgworld.getBlockTypeIdAt(x, i, z)))return i;
		}
		return 0;
	}
	
	
	
	public static Block selectLoc(World world){
		Random rand = new Random();
		int finx = world.getSpawnLocation().getBlockX() + (rand.nextInt(Config.forcefieldSideLength * 2) - Config.forcefieldSideLength);
		int finz = world.getSpawnLocation().getBlockZ() + (rand.nextInt(Config.forcefieldSideLength * 2) - Config.forcefieldSideLength);
		Block b = world.getBlockAt(finx, world.getHighestBlockYAt(finx, finz) + floatamount, finz);
		return b;
	}
	
	public static void generateFeastChest(Location loc){
		Block b = Main.hgworld.getBlockAt(loc);
		b.setType(Material.CHEST);
		((Chest)b.getState()).getInventory().setContents(Config.getNewFeastChest());
		//System.out.println("Feast chest generated at " + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ());
		//world.getPlayers().get(0).teleport(loc);
	}
	
	//config.getNewFeastChest();

}
