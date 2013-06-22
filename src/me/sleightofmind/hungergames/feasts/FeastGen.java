package me.sleightofmind.hungergames.feasts;


import java.util.Arrays;
import java.util.List;
import java.util.Random;

import me.sleightofmind.hungergames.Config;
import me.sleightofmind.hungergames.Main;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;

public class FeastGen {

	static World world = Main.instance.getServer().getWorld(Config.hgWorld);
	static int floatamount = Config.feastFloatDistance;
	public static Block FeastLoc;
	
	public static void generateFeast(){
		//Main.instance.getServer().getScheduler().cancelTask(Main.feastGenTask.getTaskId());
		
		
		Block initloc = selectLoc(world);
		initloc.setType(Material.ENCHANTMENT_TABLE);
		
		FeastUtils.clearCyl(initloc.getLocation(), 10, 25, Material.GRASS);
		
		Block et = world.getHighestBlockAt(initloc.getLocation());
		FeastLoc = et;
		et.setType(Material.ENCHANTMENT_TABLE);
		
		for(int x = -2; x <= 2; x++){
			for(int z = -2; z <= 2; z++){
				int worldx = (x + et.getX());
				int worldz = (z + et.getZ());
				if((Math.abs(x) == Math.abs(z) && x != 0) || (Math.abs(x) == 2 && z == 0) || (Math.abs(z) == 2 && x == 0)){
					//Location loc = new Location(et.getWorld(), worldx, highest(world, worldx, worldz), worldz);
					
					
					//loc = new Location(et.getWorld(), worldx, highest(world, worldx, worldz), worldz);
					
					generateFeastChest(new Location(et.getLocation().getWorld(), worldx, et.getY(), worldz));
				}
			}
		}
		
		System.out.println("Spawned feast at " + et.getX() + ", " + et.getY() + ", " + et.getZ());
	}
	
	public static int highest(World w, int x, int z){
		List<Integer> invalidblocks = Arrays.asList(0, 17, 18, 37, 38, 40, 39, 32, 27, 28, 30, 31, 6);
		int highest = world.getMaxHeight();
		for(int i = highest; i > 0; i--){
			if(!invalidblocks.contains(world.getBlockTypeIdAt(x, i, z)))return i;
		}
		return 0;
	}
	
	
	
	public static Block selectLoc(World world){
		Random rand = new Random();
		int finx = world.getSpawnLocation().getBlockX() + (rand.nextInt(Config.forcefieldSideLength * 2) - Config.forcefieldSideLength);
		int finz = world.getSpawnLocation().getBlockZ() + (rand.nextInt(Config.forcefieldSideLength * 2) - Config.forcefieldSideLength);
		Block b = world.getBlockAt(finx, highest(world, finx, finz) + floatamount, finz);
		return b;
	}
	
	public static void generateFeastChest(Location loc){
		Block b = world.getBlockAt(loc);
		b.setType(Material.CHEST);
		((Chest)b.getState()).getInventory().setContents(Config.getNewFeastChest());
		System.out.println("Feast chest generated at " + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ());
		world.getPlayers().get(0).teleport(loc);
	}
	
	//config.getNewFeastChest();

}
