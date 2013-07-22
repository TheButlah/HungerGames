package me.sleightofmind.hungergames.kits;

import me.sleightofmind.hungergames.Main;

import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class DefaultKit extends Kit implements Listener{
	
	public DefaultKit() {
		name = "Default";
		items = new ItemStack[] {};
	}
	
	@Override
	public void registerListeners() {
		Main.instance.getServer().getPluginManager().registerEvents(this, Main.instance);
	}
}
