package me.sleightofmind.hungergames.kits;
 
import me.sleightofmind.hungergames.Main;
 

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
 
public class Kit_Doctor extends Kit implements Listener {
	public Kit_Doctor() {
		name = "Doctor";
		items = new ItemStack[] {new ItemStack(Material.SHEARS)};
	}
       
	@Override
	public void registerListeners() {
		Main.instance.getServer().getPluginManager().registerEvents(this, Main.instance);
	}
       
	@EventHandler
	public void onRightCLick(PlayerInteractEntityEvent evt) {
		if(evt.getRightClicked() instanceof LivingEntity && Main.getKit(evt.getPlayer()) instanceof Kit_Doctor){
			LivingEntity le = (LivingEntity) evt.getRightClicked();
			for (PotionEffect effect : le.getActivePotionEffects()) {
				le.removePotionEffect(effect.getType());
			}
		}
	}
}