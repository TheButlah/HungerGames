package me.sleightofmind.hungergames.kits;

import me.sleightofmind.hungergames.Main;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class Kit_Viking extends Kit implements Listener{
	
	public Kit_Viking() {
		name = "Viking";
		items = new ItemStack[0];
	}
	
	@Override
	public void registerListeners() {
		Main.instance.getServer().getPluginManager().registerEvents(this, Main.instance);
	}
	
	@EventHandler
	public void onAttack(EntityDamageByEntityEvent evt) {
		if (evt.isCancelled()) {
			return;
		}
		if (!(evt.getDamager() instanceof Player)) return;
		Player p = (Player) evt.getDamager();
		ItemStack item = p.getItemInHand();
		if (item == null) {
			p.sendMessage("item is null");
			return;
		}
		Material type = item.getType();
		if (!(type == Material.WOOD_AXE || type == Material.STONE_AXE || type == Material.IRON_AXE || type == Material.DIAMOND_AXE)) return;
		if (!(Main.playerkits.get(p.getName()) instanceof Kit_Viking)) return;
		item.setDurability((short) (item.getDurability() - 1));
		evt.setDamage(evt.getDamage() + 2); //swords do 1 half heart more than axes, and we need it to do half heart more than swords
	}

}
