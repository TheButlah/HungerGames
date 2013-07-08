package me.sleightofmind.hungergames.kits;

import me.sleightofmind.hungergames.Config;
import me.sleightofmind.hungergames.Main;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Kit_Miner extends Kit implements Listener{
	
	public Kit_Miner() {
		name = "Miner";
		items = new ItemStack[] {new ItemStack(Material.STONE_PICKAXE)};
	}
	
	@Override
	public void registerListeners() {
		Main.instance.getServer().getPluginManager().registerEvents(this, Main.instance);
	}
	
	@EventHandler
	public void onAppleEat(PlayerInteractEvent evt) {
		Action a = evt.getAction();
		if ((a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) && evt.hasItem()) {
			ItemStack item = evt.getItem();
			if (!(item.getType() == Material.APPLE)) return;
			Player p = evt.getPlayer();
			if (p.getFoodLevel() < 10 || p.hasPotionEffect(PotionEffectType.FAST_DIGGING)) return;
			if (!(Main.playerkits.get(p.getName()) instanceof Kit_Miner)) return;
			p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Config.minerPotionDuration*20, 14), true);
			if (item.getAmount() > 1) {
				item.setAmount(item.getAmount() - 1);
			} else {
				p.setItemInHand(new ItemStack(Material.AIR));
			}
			evt.setCancelled(true);
		}
	}
}
