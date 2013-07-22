package me.sleightofmind.hungergames.kits;

import me.sleightofmind.hungergames.Main;

import org.bukkit.Material;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

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
			Egg egg = (Egg) evt.getDamager();
			if(!(egg.getShooter() instanceof Player)) return;
			Player shooter = (Player) egg.getShooter();
			if(Main.playerkits.get(shooter.getName()) instanceof Kit_Summoner){
				LivingEntity e = (LivingEntity) evt.getEntity();
				if((e instanceof Player)) return;
				e.remove();
				Entity entity = evt.getEntity();
			    EntityType entitytype = entity.getType();
			    short entitytypeid = entitytype.getTypeId();
				ItemStack eggitem = new ItemStack(Material.MONSTER_EGG, 1, entitytypeid);

				shooter.getInventory().addItem(eggitem);
			}
			
		}
	}
}