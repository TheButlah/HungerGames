package me.sleightofmind.hungergames.listeners;

import me.sleightofmind.hungergames.feasts.FeastGen;
import me.sleightofmind.hungergames.feasts.MiniFeast;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class FeastBlockListener implements Listener{
	int mdz, mdx, dz, dx;

	@EventHandler
	public void onBlockBreak(BlockBreakEvent evt) {
		if(MiniFeast.getLocation() != null){
			mdx = evt.getBlock().getLocation().getBlockX() - MiniFeast.getLocation().getBlockX();
			mdz = evt.getBlock().getLocation().getBlockZ() - MiniFeast.getLocation().getBlockZ();
		}
		if(FeastGen.FeastLoc != null){
			dx = evt.getBlock().getLocation().getBlockX() - FeastGen.FeastLoc.getLocation().getBlockX();
			dz = evt.getBlock().getLocation().getBlockZ() - FeastGen.FeastLoc.getLocation().getBlockZ();
		}
		if(FeastGen.FeastLoc != null && (evt.getBlock().getZ() < FeastGen.FeastLoc.getLocation().getBlockZ() + FeastGen.radius && evt.getBlock().getZ() > FeastGen.FeastLoc.getLocation().getBlockZ() - FeastGen.radius) && (evt.getBlock().getX() < FeastGen.FeastLoc.getLocation().getBlockX() + FeastGen.radius && evt.getBlock().getX() > FeastGen.FeastLoc.getLocation().getBlockX() - FeastGen.radius) && (dx*dx) + (dz*dz) < FeastGen.radius * FeastGen.radius){
			evt.setCancelled(true);
		}
		if(MiniFeast.getLocation() != null && (evt.getBlock().getZ() < MiniFeast.getLocation().getBlockZ() + MiniFeast.radius && evt.getBlock().getZ() > MiniFeast.getLocation().getBlockZ() - MiniFeast.radius) && (evt.getBlock().getX() < MiniFeast.getLocation().getBlockX() + MiniFeast.radius && evt.getBlock().getX() > MiniFeast.getLocation().getBlockX() - MiniFeast.radius) && (mdx*mdx) + (mdz*mdz) < MiniFeast.radius * MiniFeast.radius){
			evt.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent evt) {
		if(MiniFeast.getLocation() != null){
			mdx = evt.getBlock().getLocation().getBlockX() - MiniFeast.getLocation().getBlockX();
			mdz = evt.getBlock().getLocation().getBlockZ() - MiniFeast.getLocation().getBlockZ();
		}
		if(FeastGen.FeastLoc != null){
			dx = evt.getBlock().getLocation().getBlockX() - FeastGen.FeastLoc.getLocation().getBlockX();
			dz = evt.getBlock().getLocation().getBlockZ() - FeastGen.FeastLoc.getLocation().getBlockZ();
		}
		if(FeastGen.FeastLoc != null && (evt.getBlock().getZ() < FeastGen.FeastLoc.getLocation().getBlockZ() + FeastGen.radius && evt.getBlock().getZ() > FeastGen.FeastLoc.getLocation().getBlockZ() - FeastGen.radius) && (evt.getBlock().getX() < FeastGen.FeastLoc.getLocation().getBlockX() + FeastGen.radius && evt.getBlock().getX() > FeastGen.FeastLoc.getLocation().getBlockX() - FeastGen.radius) && (dx*dx) + (dz*dz) < FeastGen.radius * FeastGen.radius){
			evt.setCancelled(true);
		}
		if(MiniFeast.getLocation() != null && (evt.getBlock().getZ() < MiniFeast.getLocation().getBlockZ() + MiniFeast.radius && evt.getBlock().getZ() > MiniFeast.getLocation().getBlockZ() - MiniFeast.radius) && (evt.getBlock().getX() < MiniFeast.getLocation().getBlockX() + MiniFeast.radius && evt.getBlock().getX() > MiniFeast.getLocation().getBlockX() - MiniFeast.radius) && (mdx*mdx) + (mdz*mdz) < MiniFeast.radius * MiniFeast.radius){
			evt.setCancelled(true);
		}
	}

}