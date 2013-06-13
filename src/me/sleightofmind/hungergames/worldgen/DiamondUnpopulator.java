package me.sleightofmind.hungergames.worldgen;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

public class DiamondUnpopulator extends BlockPopulator{
	
	public static boolean runonce = true;
	public static int stonecount = 0;
	public static int orecount = 0;
	
	@Override
	public void populate(World w, Random r, Chunk c) {
		int x,y,z;
		
		
		
		for (x = 0; x < 16; x++){
			for (z = 0; z < 16; z++){
				for (y = 0; y < 33; y++) {
					
					
					if(c.getBlock(x, y, z).getType() == Material.DIAMOND_ORE){
						c.getBlock(x, y, z).setType(Material.IRON_ORE);
						orecount++;
					}
					else if(c.getBlock(x, y, z).getType() == Material.EMERALD_ORE){
						c.getBlock(x, y, z).setType(Material.IRON_ORE);
						orecount++;
					}
					else if(c.getBlock(x, y, z).getType() == Material.LAPIS_ORE){
						c.getBlock(x, y, z).setType(Material.IRON_ORE);
						orecount++;
					}
					else{
						stonecount++;
					}
					
					
				}
			}
		}
		runonce = false;
	}

}
