package me.sleightofmind.hungergames.kits;

import me.sleightofmind.hungergames.Main;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class Kit_Beastmaster extends Kit implements Listener{
	
	public Kit_Beastmaster() {
		name = "Beastmaster";
		items = new ItemStack[] {new ItemStack(Material.BONE, 4), new ItemStack(Material.MONSTER_EGG, 3, (short) 95)}; //4 bones and 3 wolf eggs
	}
	
	@Override
	public void registerListeners() {
		Main.instance.getServer().getPluginManager().registerEvents(this, Main.instance);
	}
	
	@EventHandler
	public void onWolfClick(PlayerInteractEntityEvent evt) {
		if (evt.getRightClicked() != null && !evt.getRightClicked().isDead()) {
			LivingEntity ent = (LivingEntity) evt.getRightClicked();
			if (ent.getType() == EntityType.WOLF) {
				Wolf w = (Wolf) ent;
				if (w.isTamed()) return;
				Player p = evt.getPlayer();
				ItemStack item = p.getItemInHand();
				if (item == null || item.getType() != Material.BONE) return;
				if (!(Main.playerkits.get(p.getName()) instanceof Kit_Beastmaster)) return;
				w.setOwner(p);
				if (item.getAmount() <= 1) {
					item.setType(Material.AIR);
				} else {
					item.setAmount(item.getAmount() - 1);
				}
			}
		}
	}

}
