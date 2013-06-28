package me.sleightofmind.hungergames.kits;

import me.sleightofmind.hungergames.Main;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class Kit_Hades extends Kit implements Listener {

	 
	public Kit_Hades() {
		name = "Hades";
		items = new ItemStack[] {new ItemStack(Material.IRON_INGOT)};
	}
	
	@Override
	public void registerListeners() {
		Main.instance.getServer().getPluginManager().registerEvents(this, Main.instance);
	}
	
	@EventHandler
	public void onClick(PlayerInteractEntityEvent evt) {
		Player p = evt.getPlayer();
		if (Main.playerkits.get(p.getName()) instanceof Kit_Hades) {
			ItemStack item = p.getItemInHand();
			if (item.getType() == Material.IRON_INGOT) {
				if (item.getAmount() == 1) p.setItemInHand(new ItemStack(Material.AIR));
				item.setAmount(item.getAmount() - 1);
			}
			Entity ent = evt.getRightClicked();
			if (ent instanceof Zombie) {
				Zombie mob = (Zombie) ent;
				mob.setCanPickupItems(true);
				mob.setCustomName(p.getName() + "'s Minion");
				mob.setRemoveWhenFarAway(false);
			}
		}
	}
}
