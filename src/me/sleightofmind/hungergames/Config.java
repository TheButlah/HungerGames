package me.sleightofmind.hungergames;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Config {

	public static String hgWorld;
	
	public static int minPlayersToStart, playersToQuickStart, initialCountdownTime, quickStartCountdownTime, invincibilityDuration, minutesToFeast, forcefieldSideLength;
	public static String compassTrackMessage;
	
	public static int feastFloatDistance, miniFeastFloatDistance;
	
	
	public static String gameStartMessage, invincibilityExpireMessage, invincibilityStartMessage, noKitMessage, kitSelectMessage, victoryMessage;
	
	
	
	public static int assassinChargeRate, assassinDechargeRate;
	public static double assassinDamageModifier;
	
	public static int barbarianKillsToRankUp;
	public static String barbarianSwordName;
	
	
	public static int thorCooldownRate;
	
	
	public static FileConfiguration c;
	public static Random r;
	
	
	public static void init() {
		r = new Random();
		c = Main.instance.getConfig();
		try{
			hgWorld = c.getString("Settings.HungerGamesWorld");
			
			minPlayersToStart = c.getInt("Timer.MinimumPlayersToStart");
			playersToQuickStart = c.getInt("Timer.PlayersToQuickStart");
			initialCountdownTime = c.getInt("Timer.InitialCountdownTime");
			quickStartCountdownTime = c.getInt("Timer.QuickStartCountdownTime");
			invincibilityDuration = c.getInt("Timer.InvincibilityDuration");
			minutesToFeast = c.getInt("Timer.MinutesToFeast");
			forcefieldSideLength = c.getInt("Settings.ForcefieldSideLength");
			
			feastFloatDistance = c.getInt("Setting.FeastFloatingDistance");
			miniFeastFloatDistance = c.getInt("Setting.MiniFeastFloatingDistance");
			
			invincibilityStartMessage = c.getString("Settings.InvincibilityStartMessage").replaceAll("&", "§");
			gameStartMessage = c.getString("Settings.GameStartMessage").replaceAll("&", "§");
			invincibilityExpireMessage = c.getString("Settings.InvincibilityExpireMessage").replaceAll("&", "§");
			noKitMessage = c.getString("Settings.NoKitMessage").replaceAll("&", "§");
			kitSelectMessage = c.getString("Settings.KitSelectMessage").replaceAll("&", "§");
			victoryMessage = c.getString("Settings.VictoryMessage").replaceAll("&", "§");
			compassTrackMessage = c.getString("Settings.CompassTrackMessage").replaceAll("&", "§");
			
			assassinChargeRate = c.getInt("KitSettings.Assassin.AssassinChargeRate");
			assassinDechargeRate = c.getInt("KitSettings.Assassin.AssassinDechargeRate");
			assassinDamageModifier = c.getDouble("KitSettings.Assassin.AssassinDamageModifier");
			
			barbarianKillsToRankUp = c.getInt("KitSettings.Barbarian.KillsToRankUp");
			barbarianSwordName = c.getString("KitSettings.Barbarian.SwordName").replaceAll("&", "§");
			
			thorCooldownRate = c.getInt("KitSettings.Thor.ThorCooldownRate");
			
		}
		catch(NumberFormatException e){
			Main.log.severe("One of the configuration options has an invalid value.");
			e.printStackTrace();
		}
	}
	
	public static ItemStack[] getNewFeastChest(){
		ArrayList<ItemStack> result = new ArrayList<ItemStack>();
		for(String path : c.getConfigurationSection("FeastItems").getKeys(false)){
			Debug.debug("Looking for item at path: " + path);
			int chosenpercent = r.nextInt(100) + 1;
			if(c.getInt("FeastItems." + path + ".percentchance") <= chosenpercent){
				Debug.debug("Item's percentage was low enough.");
				int id = c.getInt("FeastItems." + path + ".itemid"); 
				Debug.debug("ID is " + id);
				Integer data = -1;
				if(c.isInt("FeastItems." + path + ".itemdata")){
					data = c.getInt("FeastItems." + path + ".itemdata"); 
				}
				
				Integer minquant = 1;
				if(c.isInt("FeastItems." + path + ".minquantity")){
					minquant= c.getInt("FeastItems." + path + ".minquantity");
				}
				
				Integer maxquant = 1;
				if(c.isInt("FeastItems." + path + ".maxquantity")){
					maxquant= c.getInt("FeastItems." + path + ".maxquantity");
				}
				Debug.debug("Max number is " + maxquant + " min is " + minquant);
				int quant = r.nextInt(maxquant - minquant + 1) + minquant;
				ItemStack currentitem = new ItemStack(id, quant);
				if(data != -1){
					currentitem.setDurability(Short.parseShort(data.toString())); 
				}
				
				if(c.isSet("FeastItems." + path + ".enchantments")){
					Debug.debug("Getting enchants from " + "FeastItems." + path + ".enchantments" + "which is a string list equal to " +
							c.getStringList("FeastItems." + path + ".enchantments").toString());
					for(String ench : c.getStringList("FeastItems." + path + ".enchantments")){
						Debug.debug("Sending " + ench + " to parser.");
						currentitem = addParsedEnchant(currentitem, ench);
					}
				}
				if(c.isString("FeastItems." + path + ".itemname")){
					ItemMeta im = currentitem.getItemMeta();
					im.setDisplayName(c.getString("FeastItems." + path + ".itemname"));
					currentitem.setItemMeta(im);
				}
				Debug.debug("Adding item to results: " + currentitem.toString());
				result.add(currentitem);
				
			}
			
		}
		result = cullToChestSize(result);
		ItemStack[] realresult = new ItemStack[result.size()];
		realresult = result.toArray(realresult);
		return realresult;
	}
	
	public static ItemStack[] getNewMiniFeastChest(){
		ArrayList<ItemStack> result = new ArrayList<ItemStack>();
		for(String path : c.getConfigurationSection("MiniFeastItems").getKeys(false)){
			Debug.debug("Looking for item at path: " + path);
			int chosenpercent = r.nextInt(100) + 1;
			if(c.getInt("MiniFeastItems." + path + ".percentchance") <= chosenpercent){
				Debug.debug("Item's percentage was low enough.");
				int id = c.getInt("MiniFeastItems." + path + ".itemid"); 
				Debug.debug("ID is " + id);
				Integer data = -1;
				if(c.isInt("MiniFeastItems." + path + ".itemdata")){
					data = c.getInt("MiniFeastItems." + path + ".itemdata"); 
				}
				
				Integer minquant = 1;
				if(c.isInt("MiniFeastItems." + path + ".minquantity")){
					minquant= c.getInt("MiniFeastItems." + path + ".minquantity");
				}
				
				Integer maxquant = 1;
				if(c.isInt("MiniFeastItems." + path + ".maxquantity")){
					maxquant= c.getInt("MiniFeastItems." + path + ".maxquantity");
				}
				Debug.debug("Max number is " + maxquant + " min is " + minquant);
				int quant = r.nextInt(maxquant - minquant + 1) + minquant;
				ItemStack currentitem = new ItemStack(id, quant);
				if(data != -1){
					currentitem.setDurability(Short.parseShort(data.toString())); 
				}
				
				if(c.isSet("MiniFeastItems." + path + ".enchantments")){
					Debug.debug("Getting enchants from " + "MiniFeastItems." + path + ".enchantments" + "which is a string list equal to " +
							c.getStringList("MiniFeastItems." + path + ".enchantments").toString());
					for(String ench : c.getStringList("MiniFeastItems." + path + ".enchantments")){
						Debug.debug("Sending " + ench + " to parser.");
						currentitem = addParsedEnchant(currentitem, ench);
					}
				}
				if(c.isString("MiniFeastItems." + path + ".itemname")){
					ItemMeta im = currentitem.getItemMeta();
					im.setDisplayName(c.getString("MiniFeastItems." + path + ".itemname"));
					currentitem.setItemMeta(im);
				}
				Debug.debug("Adding item to results: " + currentitem.toString());
				result.add(currentitem);
				
			}
			
		}
		result = cullToChestSize(result);
		ItemStack[] realresult = new ItemStack[result.size()];
		realresult = result.toArray(realresult);
		return realresult;
	}
	
	public static ItemStack addParsedEnchant(ItemStack target, String ench){
		String[] enchs = ench.split("\\|");
		Debug.debug("Attempting to parse enchantment from: " + ench);
		Enchantment enchant = Enchantment.getById(Integer.parseInt(enchs[0]));
		target.addUnsafeEnchantment(enchant, Integer.parseInt(enchs[1]));
		
		return target;
	}
	
	public static ArrayList<ItemStack> cullToChestSize(ArrayList<ItemStack> items){
		while(items.size() > 27){
			int random = Config.r.nextInt(items.size());
			items.remove(random);
		}
		return items;
	}
	
}
