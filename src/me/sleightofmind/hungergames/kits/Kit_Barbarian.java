package me.sleightofmind.hungergames.kits;


import java.util.ArrayList;

import me.sleightofmind.hungergames.Config;
import me.sleightofmind.hungergames.Main;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Kit_Barbarian extends Kit implements Listener{

	private static final String swordname = Config.barbarianSwordName + ": 0 kills";
	private final ArrayList<String> swordlore = new ArrayList<String>();
	
	public Kit_Barbarian() {
		name = "Barbarian";
		
		swordlore.add("");
		swordlore.add(ChatColor.DARK_PURPLE.toString()+ChatColor.ITALIC+"This sord will level up with you.");
		swordlore.add("You can follow it's progress by looking at the");
		swordlore.add("durability. Once the durability is full it will");
		swordlore.add("upgrade, becoming a more powerful sword.");
		
		ItemStack sword = new ItemStack(Material.WOOD_SWORD);
		sword.addUnsafeEnchantment(Enchantment.DURABILITY, 999);
		ItemMeta meta = sword.getItemMeta();
		meta.setDisplayName(swordname);
		meta.setLore(swordlore);
		sword.setItemMeta(meta);
		items = new ItemStack[] {sword};
	}
	
	@EventHandler
	public void onKill(PlayerInteractEvent evt) {
		//Player dead = evt.getEntity();
		//Player killer = dead.getKiller();
		Player killer = evt.getPlayer();
		if (evt.getAction() != Action.LEFT_CLICK_AIR) return;
		if (killer == null) return;
		Kit k = Main.playerkits.get(killer.getName());
		if (!(k instanceof Kit_Barbarian)) return;
		ItemStack item = killer.getItemInHand();
		if (item == null) return;
		ItemMeta meta = item.getItemMeta();
		if (meta == null) return;
		String name = meta.getDisplayName();
		if (name == null) return;
		if (!(name.substring(0, swordname.indexOf(':') + 2)).equals(swordname.substring(0,swordname.indexOf(':') + 2)) && meta.getLore().equals(swordlore)) return;
		addkill(item);
		killer.setItemInHand(item);
	}
	
	/**
	 * precondition: Item is the correct Barbarian sword
	 * @param item
	 */
	private static void addkill(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		String name = meta.getDisplayName();
		int kills = Integer.parseInt(name.substring(swordname.indexOf(':')+2, name.length()-6));
		//note that the kills are 1 less than you would intuitively think because when they kill someone,
		//the name isnt updated until after this check
		if (kills == Config.barbarianKillsToRankUp-1) { //default 2
			item.setType(Material.STONE_SWORD);
		} else if (kills == 2*Config.barbarianKillsToRankUp) { //default 5
			item.setType(Material.IRON_SWORD);
		} else if (kills == 3*Config.barbarianKillsToRankUp + 2) { //default 9
			item.setType(Material.DIAMOND_SWORD);
		} else if (kills == 4*Config.barbarianKillsToRankUp + 5) { //default 14
			meta.addEnchant(Enchantment.DAMAGE_ALL, 2, false);
		} else if (kills == 5*Config.barbarianKillsToRankUp + 9) { //default 20
			meta.addEnchant(Enchantment.FIRE_ASPECT, 2, false);
		}
		kills++;
		String newname = name.substring(0,swordname.indexOf(':')+2)+kills+name.substring(name.length()-6);
		meta.setDisplayName(newname);
		item.setItemMeta(meta);
	}

	@Override
	public void registerListeners() {
		Main.instance.getServer().getPluginManager().registerEvents(this, Main.instance);
	}
}
