package me.sleightofmind.hungergames.kits;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class Kit_Archer extends Kit implements Listener {
	
	public Kit_Archer() {
		name = "Archer";
		items = new ItemStack[] {new ItemStack(Material.BOW), new ItemStack(Material.ARROW, 64)};
	}
	/*
	@Override
	public void registerListeners() {
		Main.instance.getServer().getPluginManager().registerEvents(this, Main.instance);
	}*/
}
