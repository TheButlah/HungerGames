package me.sleightofmind.hungergames.kits;

import me.sleightofmind.hungergames.Main;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class Kit_Tank extends Kit implements Listener{
	
	public Kit_Tank() {
		name = "Tank";
		items = new ItemStack[] {};
	}
	
	@Override
	public void registerListeners() {
		Main.instance.getServer().getPluginManager().registerEvents(this, Main.instance);
	}
	
	@EventHandler
	public void onKill(PlayerDeathEvent evt) {
		Player p = evt.getEntity();
		Player killer = p.getKiller();
		if (killer != null && Main.playerkits.get(killer.getName()) instanceof Kit_Tank) {
			p.getWorld().createExplosion(p.getLocation(), 4f);
		}
	}
	
	@EventHandler
	public void onDamageByExplosion(EntityDamageEvent evt) {
		if (!(evt.getEntity() instanceof Player)) return;
		if (evt.getCause() != DamageCause.BLOCK_EXPLOSION) return;
		Player p = (Player) evt.getEntity();
		if (Main.playerkits.get(p.getName()) instanceof Kit_Tank) evt.setCancelled(true);
	}
}
