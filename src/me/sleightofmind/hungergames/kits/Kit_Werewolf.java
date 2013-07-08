package me.sleightofmind.hungergames.kits;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Kit_Werewolf extends Kit{
	
	//Need to stop wolves from attacking werewolf when setTarget stops being borked
	public Kit_Werewolf() {
		name = "Werewolf";
		items = new ItemStack[] {new ItemStack(Material.WATCH)};
	}
	
}
