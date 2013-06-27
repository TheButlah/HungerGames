package me.sleightofmind.hungergames.kits;

import me.sleightofmind.hungergames.Main;

import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class Kit_Cultivator extends Kit implements Listener{

	public Kit_Cultivator() {
		name = "Cultivator";
		items = new ItemStack[0];
	}
	
	@Override
	public void registerListeners() {
		Main.instance.getServer().getPluginManager().registerEvents(this, Main.instance);
	}
	
	@EventHandler
	public void onPlant(BlockPlaceEvent evt) {
		if (Main.playerkits.get(evt.getPlayer().getName()) instanceof Kit_Cultivator) {
			Block b = evt.getBlockPlaced();
			if (b.getType() == Material.CROPS) {
				b.setData((byte) 7); //sets the crop to fully grown
			} else if (b.getType() == Material.SAPLING) {
				b.setType(Material.AIR); //this is needed otherwise it wont work
				switch (b.getData()) {
				case 0:
					b.getWorld().generateTree(b.getLocation(), TreeType.TREE);
					break;
				case 1:
					b.getWorld().generateTree(b.getLocation(), TreeType.REDWOOD);
					break;
				case 2:
					b.getWorld().generateTree(b.getLocation(), TreeType.BIRCH);
					break;
				case 3:
					b.getWorld().generateTree(b.getLocation(), TreeType.SMALL_JUNGLE);
					break;
				}
			}
		}
	}
}
