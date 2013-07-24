package me.sleightofmind.hungergames.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class Kit_Archer extends Kit implements Listener {
	
	public Kit_Archer() {
		name = "Archer";
		
		ItemStack bow = new ItemStack(Material.BOW);
		bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 1);
		
		items = new ItemStack[] {bow, new ItemStack(Material.ARROW, 20)};
	}
}
