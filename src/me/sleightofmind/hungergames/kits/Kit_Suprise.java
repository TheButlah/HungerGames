package me.sleightofmind.hungergames.kits;

import me.sleightofmind.hungergames.Config;
import me.sleightofmind.hungergames.Debug;
import me.sleightofmind.hungergames.Main;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Kit_Suprise extends Kit{

	public Kit_Suprise() {
		name = "Suprise";
		items = new ItemStack[0];
	}
	
	@Override
	public void init(Player player) {
		Kit newkit;
		int numkits = Main.defaultkits.size();
		do {
			newkit = Main.defaultkits.get(Config.r.nextInt(numkits));
			Debug.debug("Attempting to select the " + newkit.name + " kit");
		} while (newkit instanceof Kit_Suprise);
		try {
			Kit instantiatedkit = newkit.getClass().newInstance();
			Main.playerkits.put(player.getName(), instantiatedkit);
			Debug.debug("put the " + instantiatedkit.name + " kit in the playerkits array!");
			player.sendMessage(ChatColor.GREEN + "You have drawn the " + instantiatedkit.name + " kit!");
			instantiatedkit.init(player);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
