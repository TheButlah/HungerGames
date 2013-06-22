package me.sleightofmind.hungergames.tasks;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AssassinCompassTask extends BukkitRunnable {

	public static Map<String, String> targets = new HashMap<String, String>();
	
	@Override
	public void run() {
		for (String assassin : targets.keySet()) {
			Player a = Bukkit.getPlayer(assassin);
			Player t = Bukkit.getPlayer(targets.get(assassin));
			
			if (a == null) {
				targets.remove(assassin);
				return;
			}
			if (t == null) {
				a.sendMessage(ChatColor.GREEN + "Your target has died! Select another one!");
				targets.remove(assassin);
				return;
			}
			a.setCompassTarget(t.getLocation());
		}
	}

}
