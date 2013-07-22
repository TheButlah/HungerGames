package me.sleightofmind.hungergames.kits;

import me.sleightofmind.hungergames.Main;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class Kit_Switcher extends Kit implements Listener {
	public Kit_Switcher() {
		name = "Switcher";
		items = new ItemStack[] {new ItemStack(Material.SNOW_BALL, 5)};
	}
	
	@Override
	public void registerListeners() {
		Main.instance.getServer().getPluginManager().registerEvents(this, Main.instance);
	}
	
	@EventHandler
	public void onSnowball(EntityDamageByEntityEvent evt) {
		if(evt.getEntity() instanceof LivingEntity && evt.getDamager() instanceof Snowball){
			Snowball s = (Snowball) evt.getDamager();
			if(!(s.getShooter() instanceof Player)) return;
			Player p = (Player) s.getShooter();
			if(Main.playerkits.get(p.getName()) instanceof Kit_Switcher){
				Location tloc = evt.getEntity().getLocation();
				Location sloc = p.getLocation();
				
				p.teleport(tloc);
				
				evt.getEntity().teleport(sloc);
			}
			
		}
	}
}
