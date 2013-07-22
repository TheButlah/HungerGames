package me.sleightofmind.hungergames.kits;

import me.sleightofmind.hungergames.Config;
import me.sleightofmind.hungergames.Main;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Kit_Snail extends Kit implements Listener {
	
	public Kit_Snail() {
		name = "Snail";
		items = new ItemStack[0];
	}
	
	@Override
	public void registerListeners() {
		Main.instance.getServer().getPluginManager().registerEvents(this, Main.instance);
	}	
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent e){
		if (e.isCancelled()) {
			return;
		}
		if(e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity){
			if(Main.getKit((Player) e.getDamager()) instanceof Kit_Snail){
				LivingEntity lent = (LivingEntity) e.getEntity();
				PotionEffect pe = new PotionEffect(PotionEffectType.SLOW, Config.snailSlowDurationTicks, 1);
				lent.addPotionEffect(pe);
			}
		}
	}
}
