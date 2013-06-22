package me.sleightofmind.hungergames.feasts;

import java.util.Random;

import me.sleightofmind.hungergames.Config;
import me.sleightofmind.hungergames.Main;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;

public class MiniFeast {
	
	private static int floatamount = Config.miniFeastFloatDistance;
	public static int radius = 8;
	private static Location loc;
	
	private static Location chooseLoc(Location FeastLoc){
		Random rand = new Random();
		int xdiff = rand.nextInt(401) - 200;
		int zdiff = rand.nextInt(401) - 200;
		int finx = FeastLoc.getBlockX() + xdiff;
		int finz = FeastLoc.getBlockX() + zdiff;
		int finy = FeastLoc.getWorld().getHighestBlockYAt(finx, finz);
		return new Location(FeastLoc.getWorld(), finx, finy, finz);
	}
	
	private static String genRanges(Location loc){
		int lowerx, upperx, lowerz, upperz = 0;
		if(loc.getBlockX() >= 0){
			lowerx = (int)(((float)loc.getBlockX())/100f);
			upperx = lowerx + 1;
		}
		else{
			upperx = (int)(((float)loc.getBlockX())/100f);
			lowerx = upperx - 1;
		}
		
		if(loc.getBlockZ() >= 0){
			lowerz = (int)(((float)loc.getBlockZ())/100f);
			upperz = lowerz + 1;
		}
		else{
			upperz = (int)(((float)loc.getBlockZ())/100f);
			lowerz = upperz - 1;
		}
		
		return " X (" + lowerx*100 + ", " + upperx*100 + ") Z (" + lowerz*100 + ", " + upperz*100 + ")";
	}
	
	public static void gen(Location FeastLoc){
		Location temp = chooseLoc(FeastLoc);
		loc = new Location(temp.getWorld(), temp.getBlockX(), temp.getBlockY() + floatamount, temp.getBlockZ());
		FeastUtils.clearCyl(loc, radius, 15, Material.GLASS);
		loc.getBlock().setType(Material.ANVIL);
		Block ne = loc.getBlock().getRelative(BlockFace.NORTH_EAST);
		Block nw = loc.getBlock().getRelative(BlockFace.NORTH_WEST);
		Block se = loc.getBlock().getRelative(BlockFace.SOUTH_EAST);
		Block sw = loc.getBlock().getRelative(BlockFace.SOUTH_WEST);
		Block[] chests = {ne, nw, se, sw};
		for(Block b : chests){
			b.setType(Material.CHEST);
			Chest tempchest = (Chest) b.getState();
			tempchest.getInventory().setContents(Config.getNewMiniFeastChest());
		}
		//System.out.println(loc.getBlockX() + " " + loc.getBlockZ());
		for(Player p : Main.instance.getServer().getOnlinePlayers()){
			p.sendMessage(ChatColor.GREEN + "A Mini-Feast Has Been Generated in " + genRanges(loc));
		}
	}
	
	public static Location getLocation(){
		return loc;
	}

}
