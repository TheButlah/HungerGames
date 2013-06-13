package me.sleightofmind.hungergames.feasts;


import java.util.Random;

import me.sleightofmind.hungergames.Config;
import me.sleightofmind.hungergames.Main;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;

public class FeastGen {

	static World world = Main.instance.getServer().getWorlds().get(0);
	
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
					
					generateFeastChest(loc);
					System.out.println(loc.getBlockY());
				}
				/*else if(Math.abs(x) == 2 && z == 0){
					generateFeastChest(new Location(et.getWorld(), (x + et.getX()), et.getWorld().getHighestBlockYAt(x, z) + 1, (z + et.getZ())));
					System.out.println(et.getWorld().getHighestBlockYAt(x, z));
				}
				else if(Math.abs(z) == 2 && x == 0){
					generateFeastChest(new Location(et.getWorld(), (x + et.getX()), et.getWorld().getHighestBlockYAt(x, z) + 1, (z + et.getZ())));
					System.out.println(et.getWorld().getHighestBlockYAt(x, z));
				}*/
				
			}
		}
		
		System.out.println("Spawned at " + et.getX() + ", " + et.getY() + ", " + et.getZ());
		telePlayer(Main.instance.getServer().getPlayer("negasora"), et.getLocation());
		
	}
	
	private static void telePlayer(Player p, Location loc){
		p.teleport(loc);
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
		}
		else{
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
	
	/*private static void clearCyl(Location loc, int r) {
        int cx = loc.getBlockX();
        int cy = loc.getBlockY();
        int cz = loc.getBlockZ();
        World w = loc.getWorld();
        int rSquared = r * r;
        for(int y = 0; y < 50; y++){
	        for (int x = cx - r; x <= cx +r; x++) {
	                for (int z = cz - r; z <= cz +r; z++) {
	                        if ((cx - x) * (cx - x) + (cz - z) * (cz - z) <= rSquared) {
	                                w.getBlockAt(cx + x, y + cy, z + cz).setType(Material.AIR);
	                                System.out.println("Cleared " + (cx + x) + ", " + (cy + y) + ", " + (cz + z));
	                        }
	                }
	        }
        }
        System.out.println("Cleared cyl w center " + loc.toString());
	}*/
	
	public static Block selectLoc(World world){
		Random rand = new Random();
		Chunk[] chunks = world.getLoadedChunks();
		Chunk c = chunks[rand.nextInt(chunks.length)];
		int randx = rand.nextInt(16);
		int randz = rand.nextInt(16);
		Block temp = c.getBlock(randx, 0, randz);
		Block b = c.getWorld().getBlockAt(temp.getX(), highest(world, temp.getX(), temp.getZ()), temp.getZ());
		return b;
	}
	
	public static void generateFeastChest(Location loc){
		Block b = world.getBlockAt(loc);
		b.setType(Material.CHEST);
		((Chest)b).getInventory().setContents(Config.getNewFeastChest());
		System.out.println("Chest generated at " + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ());
	}
	
	//config.getNewFeastChest();

}
