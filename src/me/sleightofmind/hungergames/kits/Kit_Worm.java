package me.sleightofmind.hungergames.kits;

import me.sleightofmind.hungergames.Config;
import me.sleightofmind.hungergames.Main;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

public class Kit_Worm extends Kit implements Listener {
	private int currentBlockCount = 0;
	
	public Kit_Worm() {
		name = "Worm";
		items = new ItemStack[0];	
	}
	
	@Override
	public void registerListeners() {
		Main.instance.getServer().getPluginManager().registerEvents(this, Main.instance);
	}
	
	@EventHandler
	public void onWormFall(EntityDamageEvent e){
		if(e.getEntity().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.DIRT && e.getCause() == DamageCause.FALL && e.getEntity() instanceof Player && Main.getKit(((Player) e.getEntity())) instanceof Kit_Worm){
			int damagereduction = Config.c.getInt("KitSettings.Worm.FallDamageReduction");
			double damage = e.getDamage() - damagereduction;
			if(damage < 0) damage = 0;
			e.setDamage(damage);
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e){
		if (e.getBlock().getType() != Material.DIRT) return;
		Kit k = Main.getKit(e.getPlayer());
		if(k instanceof Kit_Worm){
			Kit_Worm wk = (Kit_Worm) k;
			wk.currentBlockCount++;
			if(e.getPlayer().getFoodLevel() < 20){
				if(wk.currentBlockCount >= Config.wormBlocksPerFoodUnit){
					e.getPlayer().setFoodLevel(e.getPlayer().getFoodLevel() + 1);
					wk.currentBlockCount = 0;
					return;
				}
			}else{
				if(wk.currentBlockCount >= Config.wormBlocksPerHealth && e.getPlayer().getHealth() != e.getPlayer().getMaxHealth()){
					e.getPlayer().setHealth(e.getPlayer().getHealth() + 1);
					wk.currentBlockCount = 0;
					return;
				}
			}
		}
	}
	
	@EventHandler
	public void breakDirt(BlockDamageEvent evt){
		if(evt.getBlock().getType() == Material.DIRT && Main.getKit(evt.getPlayer()) instanceof Kit_Worm){
			evt.setInstaBreak(true);
		}
	}
}
