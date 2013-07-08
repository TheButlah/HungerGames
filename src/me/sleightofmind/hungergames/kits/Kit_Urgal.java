package me.sleightofmind.hungergames.kits;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class Kit_Urgal extends Kit{

	public Kit_Urgal() {
		name = "Urgal";
		
		ItemStack potion = new ItemStack(Material.POTION, 3);
		
		Potion strength = new Potion(PotionType.STRENGTH, 1);
		strength.setSplash(true);
		strength.apply(potion);
		
		items = new ItemStack[] {potion};
	}
}
