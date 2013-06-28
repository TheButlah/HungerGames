package me.sleightofmind.hungergames.kits;

import java.util.ArrayList;
import java.util.List;
import me.sleightofmind.hungergames.Main;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class Kit_Hades extends Kit implements Listener {
	/*
	 * NOTE: DO NOT USE THIS KIT YET! IT IS NOT COMPLETED AND IS WAITING FOR THE CREATURE.SETTARGET() METHOD TO BE FIXED IN BUKKIT!
	 * The below code works as intended, although the entire kit is incomplete as the borked code has been removed
	 * 
	 * Also: Please ensure that there are at least ten adult zombies to make a minyan...
	 */
	private List<Creature> mobs;
	
	public Kit_Hades() {
		name = "Hades";
		items = new ItemStack[] {new ItemStack(Material.IRON_INGOT)};
		mobs = new ArrayList<Creature>();
	}
	
	@Override
	public void registerListeners() {
		Main.instance.getServer().getPluginManager().registerEvents(this, Main.instance);
	}
	
	@EventHandler
	public void onClick(PlayerInteractEntityEvent evt) {
		Player p = evt.getPlayer();
		ItemStack item = p.getItemInHand();
		Entity temp = evt.getRightClicked();
		LivingEntity ent;
		Kit k = Main.playerkits.get(p.getName());
		
		if (item.getType() == Material.IRON_INGOT && temp instanceof LivingEntity && k instanceof Kit_Hades) {
			ent = (LivingEntity) temp;
		} else {
			return;
		}
		
		Kit_Hades kit = (Kit_Hades) k;
		
		//ensure that the mob hasn't already been tamed
		String name = ent.getCustomName();
		if (name != null && name.equalsIgnoreCase(ChatColor.RED + p.getName() + "'s" + ChatColor.RESET + " Minion") ) return;
		
		if (item.getAmount() == 1) {
			p.setItemInHand(new ItemStack(Material.AIR));
		} else {
			item.setAmount(item.getAmount() - 1);
		}
		
		if (ent instanceof Zombie) {
			Zombie mob = (Zombie) ent;
			mob.setCanPickupItems(true);
			mob.setCustomName(ChatColor.RED + p.getName() + "'s" + ChatColor.RESET + " Minion");
			mob.setRemoveWhenFarAway(false);
			mob.setTarget(null);
		} else if (ent instanceof Skeleton) {
			Skeleton mob = (Skeleton) ent;
			mob.setCanPickupItems(true);
			mob.setCustomName(ChatColor.RED + p.getName() + "'s" + ChatColor.RESET + " Minion");
			mob.setRemoveWhenFarAway(false);
			mob.setTarget(null);
		} else if (ent instanceof Creeper) {
			Creeper mob = (Creeper) ent;
			mob.setCustomName(ChatColor.RED + p.getName() + "'s" + ChatColor.RESET + " Minion");
			mob.setRemoveWhenFarAway(false);
			mob.setTarget(null);
		}  else {
			return;
		}
		kit.mobs.add((Creature) ent);
	}
	
	@EventHandler
	public void onMobDeath(EntityDeathEvent evt) {
		String mobname = evt.getEntity().getCustomName();
		if (mobname == null) return;
		String player = mobname.replaceFirst(ChatColor.RED.toString(), "").replaceFirst("'s" + ChatColor.RESET + " Minion", "");
		Kit k = Main.playerkits.get(player);
		if (!(k instanceof Kit_Hades)) return;
		Kit_Hades kit = (Kit_Hades) k;
		kit.mobs.remove(evt.getEntity());
	}
}
