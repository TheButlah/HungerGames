package me.sleightofmind.hungergames.kits;

import me.sleightofmind.hungergames.Config;
import me.sleightofmind.hungergames.Main;
import me.sleightofmind.hungergames.tasks.FlashCooldownTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Kit_Flash extends Kit implements Listener{

	public static ItemStack activeTorch = new ItemStack(Material.REDSTONE_TORCH_ON);
	public static ItemStack inactiveTorch = new ItemStack(Material.REDSTONE_TORCH_OFF);
	
	public Kit_Flash() {
		name = "Flash";
		
		ItemMeta ameta = activeTorch.getItemMeta();
		ameta.setDisplayName(ChatColor.GREEN + "Flash Ready!");
		activeTorch.setItemMeta(ameta);
		
		ItemMeta imeta = inactiveTorch.getItemMeta();
		imeta.setDisplayName(ChatColor.RED + "Flash Recharging...");
		inactiveTorch.setItemMeta(imeta);
		
		items = new ItemStack[] {activeTorch};
	}
	
	@Override
	public void registerListeners() {
		Main.instance.getServer().getPluginManager().registerEvents(this, Main.instance);
	}
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent evt) {
		Action a = evt.getAction();
		if ((a == Action.RIGHT_CLICK_AIR || a == Action.RIGHT_CLICK_BLOCK) && evt.hasItem()) {
			Player p = evt.getPlayer();
			if (evt.getItem().equals(activeTorch)) {
				if (!(Main.playerkits.get(p.getName()) instanceof Kit_Flash)) return;
				Block target = p.getTargetBlock(null, Config.flashMaxRange);
				if (target.getType() == Material.AIR) {
					evt.setCancelled(true);
					return;
				}
				p.teleport(target.getLocation());
				p.setItemInHand(inactiveTorch);
				Bukkit.getScheduler().runTaskLater(Main.instance, new FlashCooldownTask(p), Config.flashCooldownRate * 20);
				evt.setCancelled(true);
			} else if (evt.getItem().equals(inactiveTorch)) {
				if (!(Main.playerkits.get(p.getName()) instanceof Kit_Flash)) return;
				p.sendMessage(ChatColor.GREEN + "This action is on cooldown!");
				evt.setCancelled(true);
			}
		}
	}
}
