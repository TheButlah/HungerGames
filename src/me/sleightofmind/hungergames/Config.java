package me.sleightofmind.hungergames;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Config {
	
	public static List<String> freeKits;
	
	public static int minPlayersToStart, playersToQuickStart, initialCountdownTime, 
	quickStartCountdownTime, invincibilityDuration, minutesToFeast, victoryCelebrationDuration, 
	fireworkDisplayDistance, forcefieldSideLength;
	
	public static double feastBoundaryRatio;
	
	public static String compassTrackMessage;
	
	public static int feastFloatDistance, miniFeastFloatDistance;
	
	public static String gameStartMessage, invincibilityExpireMessage, invincibilityStartMessage, noKitMessage, kitSelectMessage, victoryMessage, kitInformMessage, cannotChangeInProgessMessage, 
	doNotHavePermissionMessage, playerLeftToStartMessage, timeLeftToStartMessage, noKitInfoMessage;
	
	public static String worldname;
	
	public static boolean resetMap;
	
	public static int assassinChargeRate, assassinDechargeRate;
	public static double assassinDamageModifier;
	
	public static int barbarianKillsToRankUp;
	public static String barbarianSwordName;
	
	
	public static int thorCooldownRate;
	
	public static int spidermanCooldownRate;
	
	public static int flashMaxRange, flashCooldownRate;
	
	public static int minerPotionDuration;
	
	public static int snailSlowDurationTicks;
	
	public static int spidermanRegenRate;
	
	public static int wormBlocksPerFoodUnit, wormBlocksPerHealth;
	
	public static int pyroFireRadius, pyroFireDuration;
	
	public static FileConfiguration c;
	public static Random r;


	public static final String sc = String.valueOf(new Character((char) 167)); //the section character
	
	
	public static void init() {
		
		r = new Random();
		c = Main.instance.getConfig();
		try{
			Debug.debug = Config.c.getBoolean("Settings.DebugMessages");
			
			freeKits = c.getStringList("Settings.FreeKits");
			Debug.debug(freeKits.toString());
			
			Config.worldname = c.getString("Settings.Worldname");
			
			minPlayersToStart = c.getInt("Timer.MinimumPlayersToStart");
			playersToQuickStart = c.getInt("Timer.PlayersToQuickStart");
			initialCountdownTime = c.getInt("Timer.InitialCountdownTime");
			quickStartCountdownTime = c.getInt("Timer.QuickStartCountdownTime");
			invincibilityDuration = c.getInt("Timer.InvincibilityDuration");
			minutesToFeast = c.getInt("Timer.MinutesToFeast");
			victoryCelebrationDuration = c.getInt("Timer.VictoryCelebrationDuration");
			
			forcefieldSideLength = c.getInt("Settings.ForcefieldSideLength");
			fireworkDisplayDistance = c.getInt("Settings.FireworkDisplayDistance");
			
			feastBoundaryRatio = c.getDouble("Settings.FeastBoundaryRatio");
			feastFloatDistance = c.getInt("Setting.FeastFloatingDistance");
			miniFeastFloatDistance = c.getInt("Setting.MiniFeastFloatingDistance");
			
			invincibilityStartMessage = c.getString("Settings.InvincibilityStartMessage").replaceAll("&", sc);
			gameStartMessage = c.getString("Settings.GameStartMessage").replaceAll("&", sc);
			invincibilityExpireMessage = c.getString("Settings.InvincibilityExpireMessage").replaceAll("&", sc);
			noKitMessage = c.getString("Settings.NoKitMessage").replaceAll("&", sc);
			kitSelectMessage = c.getString("Settings.KitSelectMessage").replaceAll("&", sc);
			victoryMessage = c.getString("Settings.VictoryMessage").replaceAll("&", sc);
			compassTrackMessage = c.getString("Settings.CompassTrackMessage").replaceAll("&", sc);
			
			kitInformMessage = c.getString("Settings.KitInformMessage").replaceAll("&", sc);
			cannotChangeInProgessMessage = c.getString("Settings.CannotChangeInProgessMessage").replaceAll("&", sc);
			doNotHavePermissionMessage = c.getString("Settings.DoNotHavePermissionMessage").replaceAll("&", sc);
			
			Config.timeLeftToStartMessage = c.getString("Settings.TimeLeftToStartMessage").replaceAll("&", sc);
			Config.playerLeftToStartMessage = c.getString("Settings.PlayersLeftToStartMessage").replaceAll("&", sc);
			Config.noKitInfoMessage = c.getString("Settings.NoKitInfoMessage").replaceAll("&", sc);
			
			resetMap = c.getBoolean("Settings.ResetMap");
			
			assassinChargeRate = c.getInt("KitSettings.Assassin.AssassinChargeRate");
			assassinDechargeRate = c.getInt("KitSettings.Assassin.AssassinDechargeRate");
			assassinDamageModifier = c.getDouble("KitSettings.Assassin.AssassinDamageModifier");
			
			barbarianKillsToRankUp = c.getInt("KitSettings.Barbarian.KillsToRankUp");
			barbarianSwordName = c.getString("KitSettings.Barbarian.SwordName").replaceAll("&", sc);
			
			thorCooldownRate = c.getInt("KitSettings.Thor.ThorCooldownRate");
			
			spidermanCooldownRate = c.getInt("KitSettings.Spiderman.SpidermanCooldownRate");
			spidermanRegenRate = c.getInt("KitSettings.Spiderman.SpidermanRegenRate");
			
			flashMaxRange = c.getInt("KitSettings.Flash.FlashMaxRange");
			flashCooldownRate = c.getInt("KitSettings.Flash.FlashCooldownRate");
			
			minerPotionDuration = c.getInt("KitSettings.Miner.MinerPotionDuration");
			
			snailSlowDurationTicks = c.getInt("KitSettings.Snail.SnailSlowDurationTicks");
			
			wormBlocksPerFoodUnit = c.getInt("KitSettings.Worm.WormBlocksPerFoodUnit");
			wormBlocksPerHealth = c.getInt("KitSettings.Worm.WormBlocksPerHealth");
			
			pyroFireRadius = Config.c.getInt("KitSettings.Pyro.PyroFireRadius");
			pyroFireDuration = Config.c.getInt("KitSettings.Pyro.PyroFireDuration");
			
			
		} catch(NumberFormatException e) {
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
		for(String path : c.getConfigurationSection("MiniFeastItems").getKeys(false)) {
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
	
	public static boolean canUseKit(Player p, String kitname){
		for(String k : freeKits){
			Debug.debug("Checking equality of " + k + " and " + kitname);
			if(k.equalsIgnoreCase(kitname)){
				return true;
			}
		}
		if(p.hasPermission("HungerGames.kits." + kitname) || p.isOp()){
			return true;
		}
		return false;
	}
}
