package me.sleightofmind.hungergames.worldgen;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

public class LoadListener implements Listener {
	@EventHandler
	public void worldInit(WorldInitEvent e){
		if(e.getWorld().getName().equals("HungerGames")){
			e.getWorld().getPopulators().add(new DiamondUnpopulator());
		}
		
	}
	
	
}
