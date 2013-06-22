package me.sleightofmind.hungergames.feasts;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class FeastUtils {

	public static void clearCyl(Location loc, int r, int h, Material baseMat) {
		int radius = r;
		int radiusSquared = radius * radius;
		World w = loc.getWorld();
		int cx = loc.getBlockX();
		int cy = loc.getBlockY();
		int cz = loc.getBlockZ();
		 
		for(int x = -radius; x <= radius; x++) {
			for(int y = -1; y <= h; y++){
			    for(int z = -radius; z <= radius; z++) {
			        if( Math.abs(x*x) + Math.abs(z*z) < radiusSquared) {
			        	Block b = w.getBlockAt(cx + x, y + cy, cz + z);
			        	if(y != -1 && b.getType() != Material.CHEST && b.getTypeId() != 0 && !b.getLocation().equals(loc)){
			        		b.setTypeId(0);
			        		//System.out.println(b.getX() + ", " + b.getY() + ", " + b.getZ());
			        	}
			        	else if(y==-1) b.setType(baseMat);
			        }
			    }
			}
		}
	}

}
