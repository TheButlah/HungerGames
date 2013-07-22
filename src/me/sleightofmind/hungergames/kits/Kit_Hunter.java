package me.sleightofmind.hungergames.kits;

import me.sleightofmind.hungergames.Debug;
import me.sleightofmind.hungergames.Main;

import org.bukkit.Material;
import org.bukkit.entity.Pig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class Kit_Hunter extends Kit implements Listener {
	public Kit_Hunter() {
		name = "Hunter";
		items = new ItemStack[0];
	}
	
	@Override
	public void registerListeners() {
		Main.instance.getServer().getPluginManager().registerEvents(this, Main.instance);
	}
	
	
	
	@EventHandler
	public void onPigKill(EntityDeathEvent e){
		if(e.getEntity() instanceof Pig){
			Debug.debug("Pig killed!");
			if(Main.getKit(e.getEntity().getKiller()) instanceof Kit_Hunter){
				Debug.debug("Pig killed by Hunter!");
				e.getDrops().clear();
				e.getDrops().add(new ItemStack(Material.PORK, 2));
			}
		}
	}
}
