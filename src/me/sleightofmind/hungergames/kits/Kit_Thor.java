package me.sleightofmind.hungergames.kits;

import me.sleightofmind.hungergames.Config;
import me.sleightofmind.hungergames.Main;
import me.sleightofmind.hungergames.tasks.ThorCooldownTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Kit_Thor extends Kit implements Listener{

	public boolean onCooldown = false;
	
	public Kit_Thor() {
		name = "Thor";
		items = new ItemStack[] {new ItemStack(Material.WOOD_AXE)};
	}
	
	@Override
	public void registerListeners() {
		Main.instance.getServer().getPluginManager().registerEvents(this, Main.instance);
	}
	
	@EventHandler
	public void onPlayerClick(PlayerInteractEvent evt) {
		if (evt.getAction() == Action.LEFT_CLICK_AIR || evt.getAction() == Action.LEFT_CLICK_BLOCK || evt.getAction() == Action.PHYSICAL) {
			return;
		}
		if (evt.hasItem() && evt.getItem().getType() == Material.WOOD_AXE) {
			Kit k = Main.playerkits.get(evt.getPlayer().getName());
			if (!(k instanceof Kit_Thor)) return;
			Kit_Thor kit = (Kit_Thor) k;
			if (kit.onCooldown) {
				evt.getPlayer().sendMessage(ChatColor.GREEN + "This ability is on cooldown!");
				return;				
			} else {
				Block b = evt.getPlayer().getTargetBlock(null, 7);
				if (b.getType() == Material.AIR) return;
				kit.onCooldown = true;
				Bukkit.getScheduler().runTaskLater(Main.instance, new ThorCooldownTask(kit), Config.thorCooldownRate);
				b.setType(Material.NETHERRACK);
				b.getRelative(BlockFace.UP).setType(Material.FIRE);
				Location loc = b.getWorld().getHighestBlockAt(b.getLocation()).getLocation();
				b.getWorld().strikeLightning(loc);
			}
		}
	}
}
