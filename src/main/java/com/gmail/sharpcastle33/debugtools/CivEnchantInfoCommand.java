package com.gmail.sharpcastle33.debugtools;

import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import com.gmail.sharpcastle33.enchantments.CustomEnchantment;
import com.gmail.sharpcastle33.enchantments.CustomEnchantmentManager;

public class CivEnchantInfoCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(args.length == 0) { // when getting info on the enchantments in an item
			if(!(sender instanceof Player)) return true;
			Player player = (Player) sender;
			
			ItemStack item = player.getInventory().getItemInMainHand();
			
			Map<CustomEnchantment,Integer> itemEnch = CustomEnchantmentManager.getCustomEnchantments(player.getInventory().getItemInMainHand());
			
			for(CustomEnchantment ce: CustomEnchantmentManager.getCustomEnchantments(item).keySet()) {
				player.sendMessage(ChatColor.DARK_AQUA + ce.getName() +": " + ChatColor.AQUA + info(ce) + ChatColor.DARK_AQUA + " Max Level: " + ce.getMaxLevel());
			} // for
			
			player.sendMessage(ChatColor.GRAY + "The following enchantments can potentially be applied to this item using either a Celestial Orb or an Orb of Enchantment:");
			
			for(CustomEnchantment ce : CustomEnchantmentManager.getEnchantmentTable(player.getInventory().getItemInMainHand()).keySet()) {
              
			  if(!(itemEnch.containsKey(ce)))
			  player.sendMessage(ChatColor.BLUE + ce.getName() + " Max Level: " + ce.getMaxLevel());
			}
			
			return true;
		} else if(args.length == 1) { // when getting info on a specific enchantment by enum
			if(!sender.isOp()) return false; // ensures info by enum is secret functionality
			
			CustomEnchantment customEnchant;
			try {
				customEnchant = CustomEnchantment.valueOf(args[0]);
			} catch(IllegalArgumentException e) {
				sender.sendMessage(ChatColor.RED + "Invalid Enchantment (try /lce to list all enchants)");
				return true;
			} // try/catch

			sender.sendMessage(ChatColor.DARK_AQUA + customEnchant.getName() +": " + ChatColor.AQUA + info(customEnchant)  + ChatColor.DARK_AQUA + " Max Level: " + customEnchant.getMaxLevel());
			
			return true;
		} // if/else if
		
		return false;
	} // onCommand
	
	String info(CustomEnchantment customEnchant) {
		switch(customEnchant) {
			case INFUSION: 
				return "Increases the number of enchantments one can add to the item and increases the power of the item's infusion effect";
			case ADRENALINE:
				return "Provides a brief speed boost upon reaching low health";
			case APPLESEED:
				return "Chance to drop apples from logs";
			case AQUA_AFFINITY:
				return "Increases underwater mining rate";
			case AUTO_SMELT:
				return "Automatically smelts ore drops";
			case BANE_OF_ARTHROPODS:
				return "Increases damage to arthropods";
			case BLAST_PROTECTION:
				return  "Reduces explosion damage";
			case BRICKLAYER:
				return "Chance to drop bricks/terracotta from clay";
			case CARPENTRY:
				return "Chance to drop fences/sticks";
			case CORROSIVE:
				return "Deals increased damage to Clockwork and Steamwork mobs";
			case CRYSTAL_ATTUNEMENT:
				return "Gives a short burst of haste after mining any crystal blocks (diamond ore, emerald ore, quartz ore)";
			case DEMOLISHING:
				return "Cobblestone no longer drops from stone breaks--Chance to drop gravel from stone based on level";
			case DEPTH_STRIDER:
				return "Increases underwater movement speed";
			case DIVINE_INTERVENTION:
				return "Unobtainable"; // ------------------------------------ UNDEFINED ------------------------------------------------------
			case EMERALD_RESONANCE:
				return "Passively increased EXP gains from mining emerald ore--Chance for emeralds to shatter, dropping only "
						+ "1-2 emerald fragments and massively increased EXP drops";
			case ENDURANCE:
				return "0.15 flat damage reduction per level, stacks with endurance on all armor pieces";
			case EXECUTIONER:
				return "0.33 flat damage increase per level, against targets with <20% hp";
			case EVASIVE:
				return "Small chance to dodge incoming attacks, nullifying their damage (1% per level per piece)";
			case FAR_SHOT:
				return "Increased damage at long range based on level and range interval--Bow must be fully charged";
			case FEATHER_FALLING:
				return "Reduces fall damage";
			case FIRE_ASPECT:
				return "Sets target on fire";
			case FIRE_PROTECTION:
				return "Reduces fire damage";
			case FLAME:
				return "Arrows set target on fire";
			case FORTUNE:
				return "Increased block drops";
			case FROST_WALKER:
				return "Walk on water";
			case GOLD_AFFINITY:
				return "Drops multiple gold ore fragments instead of gold ore when broken";
			case GILLS:
				return "Increases oxygen level when breaking blocks";
			case GLASSBLOWER:
				return "Drops glass and glass related-items from sand";
			case GEMCUTTER:
				return "Drops extra items from emerald and diamond ores.";
			case GREEN_THUMB:
				return "Disabled"; // ------------------------------------- DISABLED -------------------------------------------------------
			case HEADHUNTER:
				return "Chance to drop heads on kill";
			case HELLFIRE:
				return "Increased damage to burning targets; chance to reduce the duration of the defender�s fire resistance effects";
			case HELLSTONE:
				return "Chance to drop Netherrack from stone";
			case HUNTERS_BLESSING:
				return "Increased food drops from slain mobs";
			case HUNTERS_MARK:
				return "Marks players to take increased damage from melee attacks.";
			case INFINITY:
				return "Shooting consumes no arrows";
			case IRON_AFFINITY:
				return "Drops multiple iron ore fragments instead of iron ore when broken";
			case KNOCKBACK:
				return "Increases knockback";
			case LAST_STAND:
				return "Undefined"; // ------------------------------------ UNDEFINED ------------------------------------------------------
			case LIFESTEAL:
				return "Small chance to steal 1/2 heart on hitting an enemy";
			case LIGHTBANE:
				return "Increased damage dealt to fire/light themed mobs";
			case LOOTING:
				return "Increases mob loot";
			case LUCK_OF_THE_SEA:
				return "Increases fishing luck";
			case LURE:
				return "Increases fishing rate";
			case MARKSMAN:
				return "Increased bow damage per level";
			case MUTANDIS:
				return "Chance for alternative block types to drop, e.g. diorite from stone, clay from gravel, sandstone from sand";
			case NATURES_BOUNTY:
				return "Very small chance for bonus drops to occur, e.g. bonus logs from logs, ores from stone, clay from sand/gravel";
			case NO_ENCHANTMENT:
				return "null";
			case PROSPERITY:
				  return "Small chance for bonus drops from gold and diamond ore.";
			case POINT_BLANK:
				return "Increased damage at short range based on level and range interval--Bow must be fully charged";
			case PROFICIENT:
				return "Occasionally drops small amounts of exp from mining stone";
			case PROJECTILE_PROTECTION:
				return "Reduces projectile damage";
			case PUNCH:
				return "Increases arrow knockback";
			case RAGE:
				return "Every 5th uninterrupted hit deals 25% increased damage";
			case RESPIRATION:
				return "Extends underwater breathing time";
			case SECOND_WIND:
				return "Undefined"; // ------------------------------------ UNDEFINED ------------------------------------------------------
			case SIFTING:
				return "Chance for bonus drops when breaking gravel (e.g. clay)";
			case SILK_TOUCH:
				return "Mined blocks drop themselves";
			case SMITE:
				return "Increases damage to undead";
			case SOUL_TAKER:
				return "Removes slain players bed spawn locations";
			case STONEMASON:
				return "Bonus cobblestone drops, and bonus drops of stone variants";
			case SWEEPING_EDGE:
				return "Increases sweeping attack damage";
			case THORNS:
				return "Damages attackers";
			case TIMBER:
				return "Chops down logs above the block broken (2 additional logs per level); does not work with reinforced blocks";
			case TRUE_SHOT:
				return "Small flat damage increase to all targets; very high chance to counter the effects of Evasive";
			case TRUE_STRIKE:
			  return "+0.3 damage per level to players.";
			case BLOOD_RITE:
			  return "Massive heal on killing a player, and gain a short duration health boost effect.";
			case BLOODLUST:
			  return "Chance to gain a short burst of strength on killing a mob. A larger burst of strength is given on killing a player.";
			case UMBRAL:
				return "10% more coal drops and 3-5% chance to drop Nightmare fuel from coal";
			case UNBREAKING:
				return "Increases effective durability";
			case VANGUARD:
				return "Disabled"; // ------------------------------------- DISABLED -------------------------------------------------------
			case VIGOR:
				return "0.33 max health increase per level, up to 3 additional hearts";
			case VITALITY:
				return "Small chance to trigger a short regeneration effect when hit. Stacks on all pieces";
			case WOODSMAN:
				return "Chance to drop additional wooden planks";
			default:
				return "Undefined";
		}
	} // info

} // class
