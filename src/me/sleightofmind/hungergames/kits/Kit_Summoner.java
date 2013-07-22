package me.sleightofmind.hungergames.kits;

import me.sleightofmind.hungergames.Main;

import org.bukkit.Material;
import org.bukkit.entity.Egg;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.SpawnEgg;

public class Kit_Summoner extends Kit implements Listener {
	public Kit_Summoner() {
		name = "Summoner";
		items = new ItemStack[]{new ItemStack(Material.EGG, 5)};
	}
	
	@Override
	public void registerListeners() {
		Main.instance.getServer().getPluginManager().registerEvents(this, Main.instance);
	}
	
	@EventHandler
	public void onEgg(EntityDamageByEntityEvent evt) {
		if(evt.getEntity() instanceof LivingEntity && evt.getDamager() instanceof Egg){
			Egg s = (Egg) evt.getDamager();
			if(!(s.getShooter() instanceof Player)) return;
			Player p = (Player) s.getShooter();
			if(Main.playerkits.get(p.getName()) instanceof Kit_Summoner){
				LivingEntity e = (LivingEntity) evt.getEntity();
				if((e instanceof Player)) return;
				e.remove();
				SpawnEgg se = new SpawnEgg(e.getType());
				ItemStack sei = new ItemStack(Material.MONSTER_EGG);
				sei.setData(se);
				p.getInventory().addItem(sei);
			}
			
		}
	}
}
