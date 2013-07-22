package me.sleightofmind.hungergames.kits;

import me.sleightofmind.hungergames.Main;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Kit_Scout extends Kit implements Listener {
	public Kit_Scout() {
		name = "Scout";
		ItemStack is = new ItemStack(Material.POTION, 2);
		is.setDurability((short) 16386);
		items = new ItemStack[] {is};
	}
	
	@Override
	public void registerListeners() {
		Main.instance.getServer().getPluginManager().registerEvents(this, Main.instance);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onFall(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player){
			Player p = (Player) e.getEntity();
			if(e.getCause() == DamageCause.FALL && Main.getKit(p) instanceof Kit_Scout){
				for(PotionEffect pe : p.getActivePotionEffects()){
					if(pe.getType() == PotionEffectType.SPEED){
						e.setCancelled(true);
						return;
					}
				}
			}
			
		}
	}
	
}
