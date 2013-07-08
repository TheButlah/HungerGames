package me.sleightofmind.hungergames.kits;

import me.sleightofmind.hungergames.Config;
import me.sleightofmind.hungergames.Main;
import me.sleightofmind.hungergames.tasks.SpidermanCooldownTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Kit_Spiderman extends Kit implements Listener{

	public boolean onCooldown = false;
	
	public Kit_Spiderman() {
		name = "Spiderman";
		items = new ItemStack[] {new ItemStack(Material.SNOW_BALL, 6)};
	}
	
	@Override
	public void registerListeners() {
		Main.instance.getServer().getPluginManager().registerEvents(this, Main.instance);
	}
	
	@EventHandler
	public void onSnowBallHit(ProjectileHitEvent evt) {
		Projectile p = evt.getEntity();
		if (p.getType() != EntityType.SNOWBALL) return;
		if (!(p.getShooter() instanceof Player)) return;
		Player shooter = (Player) p.getShooter();
		if (!(Main.playerkits.get(shooter.getName()) instanceof Kit_Spiderman)) return;
		Block b = p.getLocation().getBlock();
		b.setType(Material.WEB);
		b.getRelative(BlockFace.NORTH).setType(Material.WEB);
		b.getRelative(BlockFace.SOUTH).setType(Material.WEB);
		b.getRelative(BlockFace.EAST).setType(Material.WEB);
		b.getRelative(BlockFace.WEST).setType(Material.WEB);
	}
	
	@EventHandler
	public void onSnowBallThrow(PlayerInteractEvent evt) {
		Action a = evt.getAction();
		if (!(a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK)) return;
		ItemStack item = evt.getItem();
		if (item == null || item.getType() != Material.SNOW_BALL) return;
		Player shooter = evt.getPlayer();
		Kit k = Main.playerkits.get(shooter.getName());
		if (!(k instanceof Kit_Spiderman)) return;
		Kit_Spiderman kit = (Kit_Spiderman) k;
		if (kit.onCooldown) {
			shooter.sendMessage(ChatColor.GREEN + "This action is on cooldown!");
			evt.setCancelled(true);
			return;
		} else {
			kit.onCooldown = true;
			Bukkit.getScheduler().runTaskLater(Main.instance, new SpidermanCooldownTask(kit), Config.spidermanCooldownRate);
		}
	}
	
}
