package me.sleightofmind.hungergames.feasts;


import java.util.Random;

import me.sleightofmind.hungergames.Config;
import me.sleightofmind.hungergames.Main;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;

public class FeastGen {

	private static World world = Main.instance.getServer().getWorlds().get(0);
	
	public static void generateFeast(){
		Main.instance.getServer().getScheduler().cancelTask(Main.feastGenTask.getTaskId());
		
		
		Block et = selectLoc(world);
		et.setType(Material.ENCHANTMENT_TABLE);
		
		for(int x = -2; x <= 2; x++){
			for(int z = -2; z <= 2; z++){
				int worldx = (x + et.getX());
				int worldz = (z + et.getZ());
				if((Math.abs(x) == Math.abs(z) && x != 0) ||(Math.abs(x) == 2 && z == 0) || (Math.abs(z) == 2 && x == 0)){
					Location loc = new Location(et.getWorld(), worldx, highest(world, worldx, worldz), worldz);
					world.loadChunk(loc.getChunk());
					generateFeastChest(loc);
				}
			
				
			}
		}
		
		Main.instance.getServer().broadcastMessage(ChatColor.GOLD +"A Feast has been spawned at " + et.getX() + ", " + et.getY() + ", " + et.getZ());
	}
	
	private static int highest(World w, int x, int z){
		int[] invalidblocks = {0, 17, 18, 37, 38, 40, 39, 32, 27, 28, 30, 31, 6, 8, 9};
		int highest = 0;
		if(w.getBiome(x, z) != Biome.JUNGLE){
			for(int y = 0; y < 256; y++){
				int typeid = w.getBlockAt(x, y, z).getTypeId();
				boolean valid = true;
				for(int i : invalidblocks){
					if(i == typeid) valid = false;
				}
				if(valid) highest = y;
			}
			return highest + 1;
		} else{
			int airaboveleaves = 0;
			for(int y = 70; y < 256; y++){
				int typeid = w.getBlockAt(x, y, z).getTypeId();
				if(typeid == 0) airaboveleaves++;
				if(typeid != 17 && typeid != 18){
					highest = y;
					airaboveleaves = 0;
				}
				if(airaboveleaves == 2) return highest - 2;
			}
			return highest;
		}
	}
	
	public static Block selectLoc(World world){
		Random rand = new Random();
		int cx = rand.nextInt(128)-64;
		int cz = rand.nextInt(128)-64;
		world.loadChunk(cx, cz, true);
		Chunk c = world.getChunkAt(cx, cz);
		int randx = rand.nextInt(15);
		int randz = rand.nextInt(15);
		Block temp = c.getBlock(randx, 0, randz);
		Block b = c.getWorld().getBlockAt(temp.getX(), highest(world, temp.getX(), temp.getZ()), temp.getZ());
		return b;
	}
	
	public static void generateFeastChest(Location loc){
		Block b = world.getBlockAt(loc);
		b.setType(Material.CHEST);
		((Chest)b.getState()).getInventory().setContents(Config.getNewFeastChest());
	}

}
