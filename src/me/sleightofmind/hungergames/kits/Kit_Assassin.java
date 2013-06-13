package me.sleightofmind.hungergames.kits;

import me.sleightofmind.hungergames.Config;
import me.sleightofmind.hungergames.Main;
import me.sleightofmind.hungergames.tasks.AssassinChargeTask;
import me.sleightofmind.hungergames.tasks.AssassinDechargeTask;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

public class Kit_Assassin extends Kit implements Listener{
	
	private int chargelevel = 0;
	private final int maxchargelevel = 5;
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
		//if player is ENTERING sneak mode
		if (!player.isSneaking()) {
			if (dechargeTask != null) Main.instance.getServer().getScheduler().cancelTask(dechargeTask.getTaskId());
			if(chargelevel < maxchargelevel) {
				chargeTask = Main.instance.getServer().getScheduler().runTaskTimer(Main.instance, new AssassinChargeTask(this), Config.assassinChargeRate, Config.assassinChargeRate);
			}
		}
		
		//if player is LEAVING sneak mode
		if (player.isSneaking()) {
			if (chargeTask != null) Main.instance.getServer().getScheduler().cancelTask(chargeTask.getTaskId());
			if (chargelevel > 0) {
				dechargeTask = Main.instance.getServer().getScheduler().runTaskTimer(Main.instance, new AssassinDechargeTask(this), Config.assassinDechargeRate, Config.assassinDechargeRate);
			}
		}
	}
	
	public void increaseCharge() {
		if (chargelevel < maxchargelevel) {
			chargelevel++;
		} 
		if (chargelevel >= maxchargelevel) {
			Main.instance.getServer().getScheduler().cancelTask(chargeTask.getTaskId());
		}
		System.out.println(chargelevel +" increaseCharge");
	}
	
	public void decreaseCharge() {
		if (chargelevel > 0) {
			chargelevel--;
		}
		if (chargelevel <= 0) {
			Main.instance.getServer().getScheduler().cancelTask(dechargeTask.getTaskId());
		}
		System.out.println(chargelevel+ " decreaseCharge");
	}
	
}
