package me.sleightofmind.hungergames.commands;

import me.sleightofmind.hungergames.Main;
import me.sleightofmind.hungergames.kits.Kit;
import me.sleightofmind.hungergames.kits.Kit_Assassin;
import me.sleightofmind.hungergames.tasks.AssassinCompassTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Target_CommandExecutor implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player) || args.length != 1) return false;
		Player player = (Player) sender;
		Kit k = Main.playerkits.get(player.getName());
		if (!(k instanceof Kit_Assassin)) return true;
		if (args[0].equals(player.getName())) {
			player.sendMessage(ChatColor.GREEN + "You cannot target yourself!");
			return true;
		}
		Player target = Bukkit.getPlayer(args[0]);
		if (target == null) {
			player.sendMessage(ChatColor.GREEN + "There is no player by that name!");
			return true;
		}
		AssassinCompassTask.targets.put(player.getName(), target.getName());
		return true;	
	}

}
