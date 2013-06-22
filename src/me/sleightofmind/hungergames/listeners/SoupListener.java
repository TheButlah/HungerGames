package me.sleightofmind.hungergames.listeners;

import me.sleightofmind.hungergames.Debug;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SoupListener implements Listener {
	
	@EventHandler
	public void onSoupDrink(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(p.getItemInHand().getType() == Material.MUSHROOM_SOUP && (e.getAction() != Action.LEFT_CLICK_AIR && e.getAction() != Action.LEFT_CLICK_BLOCK) ){
			
			
			
			if(p.getHealth() < p.getMaxHealth()){
				if(p.getHealth() + 8 > 20){
					p.setHealth(20);
				}else{
					p.setHealth(p.getHealth() + 8);
				}
			}else if(p.getFoodLevel() < 20){
				if(p.getFoodLevel() + 8 > 20){
					p.setFoodLevel(20);
				}else{
					p.setFoodLevel(p.getFoodLevel() + 8);
				}
			}else{
				return;
			}
			
			
			ItemStack handitem = e.getPlayer().getItemInHand();
			int currentnumber = handitem.getAmount();
			handitem.setAmount(currentnumber - 1);
			if(currentnumber - 1 == 0){
				e.getItem().setType(Material.BOWL);
				Debug.debug("Attempting to remove final soup by setting type");
				return;
			}
			
			
			Debug.debug("Setting number of items to " + (currentnumber - 1) + " from the previous " + currentnumber);
			
			
			ItemStack[] inv = p.getInventory().getContents();
			for(int i = 0; i < inv.length; i++){
				if(inv[i] != null && inv[i].getType() == Material.BOWL && inv[i].getAmount() < 64){
					inv[i].setAmount(inv[i].getAmount() + 1);
					break;
				}
				else if(i == inv.length - 1){
					int empty = p.getInventory().firstEmpty();
					inv[empty] = new ItemStack(Material.BOWL);
				}
			}
			p.getInventory().setContents(inv);
			
		}
	}
}
