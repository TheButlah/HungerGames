package me.sleightofmind.hungergames.kits;

import me.sleightofmind.hungergames.Main;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;

public class Kit_Test extends Kit implements Listener{
	
	public Kit_Test(){
		name = "TestKit";
		items = new ItemStack[] {new ItemStack(Material.COBBLESTONE)};
		Main.instance.getServer().getPluginManager().registerEvents(this, Main.instance);
	}
	
	@EventHandler
	public void BlockDamageListener(BlockDamageEvent e){
		if (Main.playerkits.get(e.getPlayer().getName()) instanceof Kit_Test) {
			Location loc = e.getBlock().getLocation();
			loc.getWorld().spawnEntity(loc, EntityType.PRIMED_TNT);
		} else {
			
		}
	}

}
