package me.sleightofmind.hungergames.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Kit{

	protected ItemStack[] items;
	protected String playername;	
	protected String name;
	
	public String getName(){
		return name;
	}
	
	public ItemStack[] getItems(){
		return items;
	}
	
	//only override this if you need to register listeners
	public void registerListeners() {
		
	}
	
	public void init(Player player) {
		player.getInventory().setContents(items);
		player.getInventory().addItem(new ItemStack(Material.COMPASS));
	}

}
