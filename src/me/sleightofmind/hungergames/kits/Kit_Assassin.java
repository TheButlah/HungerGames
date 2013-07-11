package me.sleightofmind.hungergames.kits;

import me.sleightofmind.hungergames.Config;
import me.sleightofmind.hungergames.Main;
import me.sleightofmind.hungergames.tasks.AssassinChargeTask;
import me.sleightofmind.hungergames.tasks.AssassinCompassTask;
import me.sleightofmind.hungergames.tasks.AssassinDechargeTask;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

public class Kit_Assassin extends Kit implements Listener{
	
	private int chargelevel = 0;
	private static final int maxchargelevel = 5;
	private BukkitTask chargeTask;
	private BukkitTask dechargeTask;
	
	public Kit_Assassin() {
		name = "Assassin";
		items = new ItemStack[0];
	}
	
	@Override
	public void registerListeners() {
		Main.instance.getServer().getPluginManager().registerEvents(this, Main.instance);
	}
	
	@EventHandler
	public void onPlayerCrouch(PlayerToggleSneakEvent evt) {
		Player player = evt.getPlayer();
		Kit k = Main.playerkits.get(player.getName());
		if (!(k instanceof Kit_Assassin)) return;
		Kit_Assassin kit = (Kit_Assassin) k;
		
		//if player is ENTERING sneak mode
		if (!player.isSneaking()) {
			if (kit.dechargeTask != null) Main.instance.getServer().getScheduler().cancelTask(kit.dechargeTask.getTaskId());
			if(kit.chargelevel < maxchargelevel) {
				kit.chargeTask = Main.instance.getServer().getScheduler().runTaskTimer(Main.instance, new AssassinChargeTask(kit, player), Config.assassinChargeRate, Config.assassinChargeRate);
			}
		}
		
		//if player is LEAVING sneak mode
		if (player.isSneaking()) {
			if (kit.chargeTask != null) Main.instance.getServer().getScheduler().cancelTask(kit.chargeTask.getTaskId());
			if (kit.chargelevel > 0) {
				kit.dechargeTask = Main.instance.getServer().getScheduler().runTaskTimer(Main.instance, new AssassinDechargeTask(kit, player), Config.assassinDechargeRate, Config.assassinDechargeRate);
			}
		}
	}
	
	public void increaseCharge(Player p) {
		if (chargelevel < maxchargelevel) {
			chargelevel++;
		} 
		if (chargelevel >= maxchargelevel) {
			Main.instance.getServer().getScheduler().cancelTask(chargeTask.getTaskId());
		}
		p.sendMessage(ChatColor.GREEN + Integer.toString(chargelevel*20) + "% Attack Power");
	}
	
	public void decreaseCharge(Player p) {
		if (chargelevel > 0) {
			chargelevel--;
		}
		if (chargelevel <= 0) {
			Main.instance.getServer().getScheduler().cancelTask(dechargeTask.getTaskId());
		}
		p.sendMessage(ChatColor.GREEN + Integer.toString(chargelevel*20) + "% Attack Power");
	}
	
	@EventHandler
	public void onPlayerAttack(EntityDamageByEntityEvent evt) {
		Entity ent = evt.getDamager();
		if (!(ent instanceof Player)) return;
		Player damager = (Player) ent;
		
		Kit k = Main.playerkits.get(damager.getName());
		if (!(k instanceof Kit_Assassin)) return;
		Kit_Assassin kit = (Kit_Assassin) k;
		
		evt.setDamage((int) (evt.getDamage()*(1+kit.chargelevel/5.0)));
	}
	
	@EventHandler
	public void onCompassClick(PlayerInteractEvent evt) {
		Player player = evt.getPlayer();
		if (!(Main.playerkits.get(player.getName()) instanceof Kit_Assassin)) return;
		ItemStack item = evt.getItem();
		if (evt.hasItem() && item.getType().equals(Material.COMPASS)) {
			Player[] players = Main.instance.getServer().getOnlinePlayers();
			if (players.length > 1) {
				Player target = players[(int)(Math.random()*players.length)];
				while(target.getName().equals(player.getName())) {
					target = players[(int)(Math.random()*players.length)];
				}
				player.sendMessage(ChatColor.GREEN + "You have selected " + target.getName() + "!");
				AssassinCompassTask.targets.put(player.getName(), target.getName());
			} else {
				player.setCompassTarget(Main.hgworld.getSpawnLocation());
			}
		}
	}
	
}
