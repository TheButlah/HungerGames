package me.sleightofmind.hungergames.kits;

import me.sleightofmind.hungergames.Config;
import me.sleightofmind.hungergames.Main;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.SmallFireball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Kit_Pyro extends Kit implements Listener {
	public Kit_Pyro() {
		name = "Pyro";
		items = new ItemStack[] {new ItemStack(Material.FLINT_AND_STEEL), new ItemStack(Material.FIREBALL, 5)};
	}
	
	@Override
	public void registerListeners() {
		Main.instance.getServer().getPluginManager().registerEvents(this, Main.instance);
	}
	
	@EventHandler
	public void onFireDMG(EntityDamageEvent evt) {
		if(evt.getEntity() instanceof Player){
			Player p = (Player) evt.getEntity();
			if(Main.playerkits.get(p.getName()) instanceof Kit_Pyro){
				DamageCause dc = evt.getCause();
				if(dc == DamageCause.FIRE || dc == DamageCause.FIRE_TICK || dc == DamageCause.LIGHTNING){ //I *think* this is all we need...
					evt.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onFireCharge(PlayerInteractEvent e){
		Action a = e.getAction();
		if(a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK){
			if(e.getPlayer().getItemInHand().getType() == Material.FIREBALL){
				e.getPlayer().launchProjectile(SmallFireball.class);
				/*World w = e.getPlayer().getLocation().getWorld();
				SmallFireball f = (SmallFireball) w.spawnEntity(e.getPlayer().getLocation(), EntityType.SMALL_FIREBALL);
				Location ploc = e.getPlayer().getLocation();
				Location tloc = e.getPlayer().getTargetBlock(null, 10).getLocation();
				f.setVelocity(new Vector(tloc.getX() - ploc.getX(), tloc.getY() - ploc.getY(), tloc.getZ() - ploc.getZ()));*/
				
			}
		}
	}
	
	@EventHandler
	public void onChargeHit(ProjectileHitEvent e){
		if(e.getEntity() instanceof SmallFireball){
			Location impactloc = e.getEntity().getLocation();
			for(Entity ent : impactloc.getWorld().getEntities()){
				if(ent instanceof LivingEntity && ent.getLocation().distance(impactloc) < Config.c.getInt("HungerGames.kits.PyroFireRadius")){
					LivingEntity lent = (LivingEntity) e.getEntity();
					lent.setFireTicks(Config.c.getInt("KitSettings.Pyro.PyroFireDuration"));
					e.getEntity().remove();
				}
			}
		}
	}
}
