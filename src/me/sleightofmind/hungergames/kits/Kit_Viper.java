package me.sleightofmind.hungergames.kits;

import me.sleightofmind.hungergames.Config;
import me.sleightofmind.hungergames.Main;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Kit_Viper extends Kit implements Listener{

	public Kit_Viper() {
		name = "Viper";
		items = new ItemStack[0];
	}
	
	@Override
	public void registerListeners() {
		Main.instance.getServer().getPluginManager().registerEvents(this, Main.instance);
	}
	
	@EventHandler
	public void onAttack(EntityDamageByEntityEvent evt) {
		if (evt.getDamager() instanceof Player && evt.getEntity() instanceof Player) {
			Player damager = (Player) evt.getDamager();
			Player hurt = (Player) evt.getEntity();
			if (Main.playerkits.get(damager.getName()) instanceof Kit_Viper) {
				if (Config.r.nextInt(3) == 0) hurt.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 900, 1));
			}
		}
	}
}
