package me.sleightofmind.hungergames.kits;

import me.sleightofmind.hungergames.Main;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class Kit_Lumberjack extends Kit implements Listener {
	public Kit_Lumberjack() {
		name = "Snail";
		items = new ItemStack[0];
	}
	
	@Override
	public void registerListeners() {
		Main.instance.getServer().getPluginManager().registerEvents(this, Main.instance);
	}
	
	
	
	@EventHandler
	public void onLumberjack(BlockBreakEvent e){
		if(e.getBlock().getType() == Material.LOG && Main.getKit(e.getPlayer()) instanceof Kit_Lumberjack){
			breakLogs(e.getBlock());
		}
	}
	
	public void breakLogs(Block b){
		b.breakNaturally();
		Block[] abs = {b.getRelative(BlockFace.UP), b.getRelative(BlockFace.DOWN), b.getRelative(BlockFace.NORTH), b.getRelative(BlockFace.SOUTH),
				b.getRelative(BlockFace.EAST), b.getRelative(BlockFace.WEST), b.getRelative(BlockFace.NORTH_WEST),
				b.getRelative(BlockFace.SOUTH_WEST), b.getRelative(BlockFace.NORTH_EAST), b.getRelative(BlockFace.SOUTH_EAST),
				b.getRelative(BlockFace.NORTH_WEST), b.getRelative(BlockFace.NORTH_WEST), b.getRelative(BlockFace.NORTH_WEST),
				b.getRelative(BlockFace.NORTH_WEST),
				b.getRelative(BlockFace.NORTH).getRelative(BlockFace.UP), b.getRelative(BlockFace.SOUTH).getRelative(BlockFace.UP),
				b.getRelative(BlockFace.EAST).getRelative(BlockFace.UP), b.getRelative(BlockFace.WEST).getRelative(BlockFace.UP), b.getRelative(BlockFace.NORTH_WEST).getRelative(BlockFace.UP),
				b.getRelative(BlockFace.SOUTH_WEST).getRelative(BlockFace.UP), b.getRelative(BlockFace.NORTH_EAST).getRelative(BlockFace.UP), b.getRelative(BlockFace.SOUTH_EAST).getRelative(BlockFace.UP),
				b.getRelative(BlockFace.NORTH_WEST).getRelative(BlockFace.UP), b.getRelative(BlockFace.NORTH_WEST).getRelative(BlockFace.UP), b.getRelative(BlockFace.NORTH_WEST).getRelative(BlockFace.UP),
				b.getRelative(BlockFace.NORTH_WEST).getRelative(BlockFace.UP)};
		for(Block cblock : abs){
			if(cblock.getType() == Material.LOG){
				breakLogs(cblock);
			}
		}
		
	}
}
