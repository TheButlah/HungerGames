package me.sleightofmind.hungergames.kits;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public abstract class Kit{

	protected ItemStack[] items;
	
	protected String name;
	
	public String getName(){
		return name;
	}
	
	public ItemStack[] getItems(){
		return items;
	}
	
	public void init(Player player) {
		player.getInventory().clear();
		player.getInventory().setContents(items);
	}

}
