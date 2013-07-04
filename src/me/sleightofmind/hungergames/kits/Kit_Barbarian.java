package me.sleightofmind.hungergames.kits;


import java.util.ArrayList;

import me.sleightofmind.hungergames.Main;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Kit_Barbarian extends Kit implements Listener{

	private static final String swordname = ChatColor.RED + "Tyrfing";
	private final ArrayList<String> swordlore = new ArrayList<String>();
	
	public Kit_Barbarian() {
		name = "Barbarian";
		
		swordlore.add("");
		swordlore.add(ChatColor.DARK_PURPLE.toString()+ChatColor.ITALIC+"Tyrfing will level up with you.");
		swordlore.add("You can follow it's progress by looking at the");
		swordlore.add("durability. Once the durability is full Tyrfing will");
		swordlore.add("upgrade, becoming a more powerful sword.");
		
		ItemStack tyrfing = new ItemStack(Material.WOOD_SWORD);
		tyrfing.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		ItemMeta meta = tyrfing.getItemMeta();
		meta.setDisplayName(swordname);
		meta.setLore(swordlore);
		tyrfing.setItemMeta(meta);
		items = new ItemStack[] {tyrfing};
	}
	
	@EventHandler
	public void onKill(PlayerDeathEvent evt) {
		Player dead = evt.getEntity();
		Player killer = dead.getKiller();
		if (killer == null) return;
		Kit k = Main.playerkits.get(killer.getName());
		if (!(k instanceof Kit_Barbarian)) return;
		ItemStack item = killer.getItemInHand();
		if (item == null) return;
		ItemMeta meta = item.getItemMeta();
		if (meta == null) return;
		String name = meta.getDisplayName();
		if (name == null) return;
		if (!(name.equals(swordname) && meta.getLore().equals(swordlore))) return;
		addkill(item);
	}
	
	private static void addkill(ItemStack item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerListeners() {
		Main.instance.getServer().getPluginManager().registerEvents(this, Main.instance);
	}
}
