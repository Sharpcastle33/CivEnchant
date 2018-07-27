package com.gmail.sharpcastle33.enchantments;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.sharpcastle33.util.Util;

import net.md_5.bungee.api.ChatColor;

public class CustomEnchantmentManager {
  
	/**
	 * Removes all custom and vanilla enchantments fron an item.
	 * @param stack
	 * @return
	 */
  public static ItemStack removeCustomEnchantments(ItemStack stack){
	stack = removeVanillaEnchantments(stack);
	stack = removeLoreEnchantments(stack);
    return stack;
  }
  
  /**
   * Removes all vanilla enchants from an item.
   * @param stack
   * @return
   */
  private static ItemStack removeVanillaEnchantments(ItemStack stack){
	  ItemMeta meta = stack.getItemMeta();
	  
	  for(Enchantment e : meta.getEnchants().keySet()){
		  if(!e.equals(Enchantment.DAMAGE_ALL) && !e.equals(Enchantment.ARROW_DAMAGE) && !e.equals(Enchantment.PROTECTION_ENVIRONMENTAL) && !e.equals(Enchantment.DIG_SPEED))
		  meta.removeEnchant(e);
	  }
	  stack.setItemMeta(meta);
	  return stack;
  }
  
  /**
   * Removes all custom enchantments from an item.
   * @param stack
   * @return
   */
  private static ItemStack removeLoreEnchantments(ItemStack stack){
	  Map<CustomEnchantment, Integer> enchants = getLoreTagEnchantments(stack);
	  ItemMeta meta = stack.getItemMeta();
	  if(meta.hasLore()){
		  List<String> lore = meta.getLore();
		  
		  Bukkit.getLogger().info(ChatColor.RED + "Item has enchantments:");
		  Util.printMap(enchants);
		  
		  for(CustomEnchantment ench : enchants.keySet()){
			  
			  if(ench.equals(CustomEnchantment.INFUSION))
				  continue;
			  
			  String level = Util.getRomanNumeral(enchants.get(ench));
			  Bukkit.getLogger().info("toremove: " + ChatColor.GRAY + ench.getName() + " " + level);
			  lore.remove(ChatColor.GRAY + ench.getName() + " " + level);
		  }
		  meta.setLore(lore);
		  
		  for(String line : lore){
			  Bukkit.getLogger().info(line);
		  }
	  }
	  stack.setItemMeta(meta);
	  return stack;
  }
  /**
   * Manages custom enchantment addition.
   * @param stack ItemStack to add enchantment to.
   * @param ench CustomEnchantment to add.
   * @param level Level of the enchantment.
   * @return ItemStack with enchantment.
   */
  public static ItemStack addCustomEnchantment(ItemStack stack, CustomEnchantment ench, int level){
	  
	  ItemMeta meta = stack.getItemMeta();
	  
	  switch(ench){
	  case AQUA_AFFINITY:
		  meta.addEnchant(Enchantment.OXYGEN, level, true);
		  break;
	  case BANE_OF_ARTHROPODS:
		  meta.addEnchant(Enchantment.DAMAGE_ARTHROPODS, level, true);
		  break;
	  case BLAST_PROTECTION:
		  meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, level, true);
		  break;
	  case DEPTH_STRIDER:
		  meta.addEnchant(Enchantment.DEPTH_STRIDER, level, true);
		  break;
	  case FEATHER_FALLING:
		  meta.addEnchant(Enchantment.PROTECTION_FALL, level, true);
		  break;
	  case FIRE_ASPECT:
		  meta.addEnchant(Enchantment.FIRE_ASPECT, level, true);
		  break;
	  case FIRE_PROTECTION:
		  meta.addEnchant(Enchantment.PROTECTION_FIRE, level, true);
		  break;
	  case FLAME:
		  meta.addEnchant(Enchantment.ARROW_FIRE, level, true);
		  break;
	  case FORTUNE:
		  meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, level, true);
		  break;
	  case FROST_WALKER:
		  meta.addEnchant(Enchantment.FROST_WALKER, level, true);
		  break;
	  case INFINITY:
		  meta.addEnchant(Enchantment.ARROW_INFINITE, level, true);
		  break;
	  case KNOCKBACK:
		  meta.addEnchant(Enchantment.KNOCKBACK, level, true);
		  break;
	  case LOOTING:
		  meta.addEnchant(Enchantment.LOOT_BONUS_MOBS, level, true);
		  break;
	  case LUCK_OF_THE_SEA:
		  meta.addEnchant(Enchantment.LUCK, level, true);
		  break;
	  case LURE:
		  meta.addEnchant(Enchantment.LURE, level, true);
		  break;
	  case PROJECTILE_PROTECTION:
		  meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, level, true);
		  break;
	  case PUNCH:
		  meta.addEnchant(Enchantment.ARROW_KNOCKBACK, level, true);
		  break;
	  case RESPIRATION:
		  meta.addEnchant(Enchantment.OXYGEN, level, true);
		  break;
	  case SILK_TOUCH:
		  meta.addEnchant(Enchantment.SILK_TOUCH, level, true);
		  break;
	  case SMITE:
		  meta.addEnchant(Enchantment.DAMAGE_UNDEAD, level, true);
		  break;
	  case SWEEPING_EDGE:
		  meta.addEnchant(Enchantment.SWEEPING_EDGE, level, true);
		  break;
	  case THORNS:
		  meta.addEnchant(Enchantment.THORNS, level, true);
		  break;
	  case UNBREAKING:
		  meta.addEnchant(Enchantment.DURABILITY, level, true);
		  break;
	  //CUSTOM ENCHANTMENTS
	  case AUTO_SMELT:
		  addLoreTagEnchantment(meta, CustomEnchantment.AUTO_SMELT,level);
		  break;
	  case FAR_SHOT:
		  addLoreTagEnchantment(meta, CustomEnchantment.FAR_SHOT,level);
		  break;
	  case POINT_BLANK:
		  addLoreTagEnchantment(meta, CustomEnchantment.POINT_BLANK,level);
		  break;
	  case RAGE:
		  addLoreTagEnchantment(meta, CustomEnchantment.RAGE,level);
		  break;
	  case LIFESTEAL:
		  addLoreTagEnchantment(meta,CustomEnchantment.LIFESTEAL,level);
		  break;
	  case VIGOR:
		  addLoreTagEnchantment(meta,CustomEnchantment.VIGOR,level);
		  break;
	  case VITALITY:
		  addLoreTagEnchantment(meta,CustomEnchantment.VITALITY,level);
		  break;
	  case IRON_AFFINITY:
		  addLoreTagEnchantment(meta,CustomEnchantment.IRON_AFFINITY,level);
		  break;
	  case GOLD_AFFINITY:
		  addLoreTagEnchantment(meta,CustomEnchantment.GOLD_AFFINITY,level);
		  break;
	  case ENDURANCE:
		  addLoreTagEnchantment(meta,CustomEnchantment.ENDURANCE,level);
		  break;
	  default:
		  addLoreTagEnchantment(meta,ench,level);
		  break;
		  		  
	  }
	stack.setItemMeta(meta);
    return stack;
  }
  
  /**
   * Manages enchantment weight tables for all items.
   * @param stack
   * @return
   */
  public static Map<CustomEnchantment, Integer> getEnchantmentTable(ItemStack stack){
	Map<CustomEnchantment, Integer> ret = new HashMap<CustomEnchantment, Integer>();
	
	if(Util.isDurable(stack)){
		ret.put(CustomEnchantment.UNBREAKING, 1);
	}
	
	if(Util.isSword(stack)){
		ret.put(CustomEnchantment.FIRE_ASPECT, 1);
		ret.put(CustomEnchantment.SMITE, 1);
		ret.put(CustomEnchantment.BANE_OF_ARTHROPODS, 1);
		ret.put(CustomEnchantment.KNOCKBACK, 1);
		ret.put(CustomEnchantment.LOOTING, 1);
		ret.put(CustomEnchantment.SWEEPING_EDGE, 1);
		
		ret.put(CustomEnchantment.LIFESTEAL, 1);
		ret.put(CustomEnchantment.RAGE, 1);
		ret.put(CustomEnchantment.CORROSIVE, 1);
		ret.put(CustomEnchantment.HELLFIRE, 1);
		ret.put(CustomEnchantment.SOUL_TAKER, 1);
		ret.put(CustomEnchantment.LIGHTBANE, 1);
	}
	
	if(Util.isArmor(stack)){
		
		ret.put(CustomEnchantment.FIRE_PROTECTION, 1);
		ret.put(CustomEnchantment.PROJECTILE_PROTECTION, 1);
		ret.put(CustomEnchantment.BLAST_PROTECTION, 1);
		ret.put(CustomEnchantment.THORNS, 1);
		ret.put(CustomEnchantment.UNBREAKING, 1);
		
		ret.put(CustomEnchantment.ENDURANCE, 1);
		ret.put(CustomEnchantment.VIGOR, 1);
		ret.put(CustomEnchantment.VITALITY, 1);
		ret.put(CustomEnchantment.EVASIVE, 1);
		
	}
	
	if(Util.isTool(stack)){
		ret.put(CustomEnchantment.FORTUNE, 1);
		ret.put(CustomEnchantment.SILK_TOUCH, 1);
		ret.put(CustomEnchantment.MUTANDIS, 1);
		ret.put(CustomEnchantment.NATURES_BOUNTY, 1);
	}
	
	if(Util.isPickaxe(stack)){
		ret.put(CustomEnchantment.GOLD_AFFINITY, 1);
		ret.put(CustomEnchantment.IRON_AFFINITY, 1);
		ret.put(CustomEnchantment.AUTO_SMELT, 1);
		ret.put(CustomEnchantment.DEMOLISHING, 1);
		ret.put(CustomEnchantment.CRYSTAL_ATTUNEMENT, 1);
		ret.put(CustomEnchantment.EMERALD_RESONANCE, 1);
		ret.put(CustomEnchantment.STONEMASON, 1);
		ret.put(CustomEnchantment.PROFICIENT, 1);
		
	}
	
	if(Util.isAxe(stack)){
		ret.put(CustomEnchantment.TIMBER, 1);
		ret.put(CustomEnchantment.WOODSMAN, 1);
		ret.put(CustomEnchantment.CARPENTRY, 1);
		ret.put(CustomEnchantment.APPLESEED, 1);
	}
	
	if(Util.isBow(stack)){
		ret.put(CustomEnchantment.PUNCH, 1);
		ret.put(CustomEnchantment.FLAME, 1);
		
		ret.put(CustomEnchantment.FAR_SHOT, 1);
		ret.put(CustomEnchantment.POINT_BLANK, 1);
		ret.put(CustomEnchantment.TRUE_SHOT, 1);
		ret.put(CustomEnchantment.HUNTERS_BLESSING, 1);
		ret.put(CustomEnchantment.HUNTERS_MARK, 1);
	}
	
	if(Util.isHoe(stack)) {
		
		ret.put(CustomEnchantment.GREEN_THUMB, 1);
		
	}
	
    return ret;
  }
  
  /**
   * Returns the max level of the enchantment specified.
   * @param e
   * @return
   */
  public static int getMaxLevel(CustomEnchantment e){
    return e.getMaxLevel();
  }
  
  /**
   * Returns all enchantments on an item.
   * @param stack
   * @return
   */
  public static Map<CustomEnchantment, Integer> getCustomEnchantments(ItemStack stack){
	Map<CustomEnchantment, Integer> ret = new HashMap<CustomEnchantment, Integer>();
	ret.putAll(getVanillaEnchantments(stack));
	ret.putAll(getLoreTagEnchantments(stack));
    return ret;
  }
  
  /**
   * Returns a list of CustomEnchantments containing all vanilla enchantments on an item.
   * @param stack
   * @return
   */
  private static Map<CustomEnchantment, Integer> getVanillaEnchantments(ItemStack stack){
	  Map<CustomEnchantment, Integer> ret = new HashMap<CustomEnchantment, Integer>();
	  if(stack.hasItemMeta()){
		  ItemMeta meta = stack.getItemMeta();
		  Map<Enchantment, Integer> enchants = meta.getEnchants();
		  for(Enchantment e : enchants.keySet()){
			 // Bukkit.getLogger().info("Item has enchant: " + toCustomEnchantment(e).getName());
			  ret.put(toCustomEnchantment(e), enchants.get(e));
		  }
	  }
	  return ret;
  }
  
  /**
   * Converts vanilla Enchantment types to CustomEnchantment type.
   * @param Enchantment e
   * @return CustomEnchantment
   */
  
  private static CustomEnchantment toCustomEnchantment(Enchantment e){

	  if(e.equals(Enchantment.WATER_WORKER))
		  return CustomEnchantment.AQUA_AFFINITY;
	  if(e.equals(Enchantment.DAMAGE_ARTHROPODS))
		  return CustomEnchantment.BANE_OF_ARTHROPODS;
	  if(e.equals(Enchantment.DEPTH_STRIDER))
		  return CustomEnchantment.DEPTH_STRIDER;
	  if(e.equals(Enchantment.PROTECTION_FALL))
		  return CustomEnchantment.FEATHER_FALLING;
	  if(e.equals(Enchantment.PROTECTION_EXPLOSIONS))
		  return CustomEnchantment.BLAST_PROTECTION;
	  if(e.equals(Enchantment.FIRE_ASPECT))
		  return CustomEnchantment.FIRE_ASPECT;
	  if(e.equals(Enchantment.PROTECTION_FIRE))
		  return CustomEnchantment.FIRE_PROTECTION;
	  if(e.equals(Enchantment.ARROW_FIRE))
		  return CustomEnchantment.FLAME;
	  if(e.equals(Enchantment.LOOT_BONUS_BLOCKS))
		  return CustomEnchantment.FORTUNE;
	  if(e.equals(Enchantment.FROST_WALKER))
	      return CustomEnchantment.FROST_WALKER;
	  if(e.equals(Enchantment.ARROW_INFINITE))
		  return CustomEnchantment.INFINITY;
	  if(e.equals(Enchantment.KNOCKBACK))
		  return CustomEnchantment.KNOCKBACK;
	  if(e.equals(Enchantment.LOOT_BONUS_MOBS))
		  return CustomEnchantment.LOOTING;
	  if(e.equals(Enchantment.LUCK))
		  return CustomEnchantment.LUCK_OF_THE_SEA;
	  if(e.equals(Enchantment.LURE))
		  return CustomEnchantment.LURE;
	  if(e.equals(Enchantment.PROTECTION_PROJECTILE))
		  return CustomEnchantment.PROJECTILE_PROTECTION;
	  if(e.equals(Enchantment.ARROW_KNOCKBACK))
	  	  return CustomEnchantment.PUNCH;
	  if(e.equals(Enchantment.OXYGEN))
		  return CustomEnchantment.RESPIRATION;
	  if(e.equals(Enchantment.SILK_TOUCH))
		  return CustomEnchantment.SILK_TOUCH;
	  if(e.equals(Enchantment.DAMAGE_UNDEAD))
		  return CustomEnchantment.SMITE;
	  if(e.equals(Enchantment.SWEEPING_EDGE))
		  return CustomEnchantment.SWEEPING_EDGE;
	  if(e.equals(Enchantment.THORNS))
		  return CustomEnchantment.THORNS;
	  if(e.equals(Enchantment.DURABILITY))
		  return CustomEnchantment.UNBREAKING;
	  if(e.equals(Enchantment.DIG_SPEED) || e.equals(Enchantment.DAMAGE_ALL) || e.equals(Enchantment.ARROW_DAMAGE) || e.equals(Enchantment.PROTECTION_ENVIRONMENTAL))
		  return CustomEnchantment.INFUSION;
	  return CustomEnchantment.NO_ENCHANTMENT;
	}
  
  /**
   * Returns a map of custom enchantments and their values.
   * @param stack
   * @return
   */
  public static Map<CustomEnchantment, Integer> getLoreTagEnchantments(ItemStack stack){
	 
	  Map<CustomEnchantment, Integer> ret = new HashMap<CustomEnchantment, Integer>();
	  
	  ItemMeta meta = stack.getItemMeta();
	  List<String> lore = meta.getLore();
	  
	  //fix this set to remove vanilla enchants
	  EnumSet<CustomEnchantment> enchantments = EnumSet.allOf( CustomEnchantment.class);
	 // Bukkit.getLogger().info(enchantments.toString());
	  for(CustomEnchantment ench : enchantments){
		  if(Util.contiansPrefixTag(meta, ChatColor.GRAY + ench.getName())){
			  
			  String temp = Util.getLevelFromPrefixTag(meta, ChatColor.GRAY + ench.getName() + " ");
			  ret.put(ench, Util.fromNumeral(temp));
		  }
	  }
	    
	  return ret;
  }
  
  /**
   * Generic method to add a lore tag enchantment.
   * @param meta
   * @param ench
   * @param level
   * @return
   */
  
  public static ItemMeta addLoreTagEnchantment(ItemMeta meta, CustomEnchantment ench, int level){
	  if(meta.hasLore()){
		  List<String> lore = meta.getLore();
		  lore.add(ChatColor.GRAY + ench.getName() + " " + Util.getRomanNumeral(level));
		  meta.setLore(lore);
	  }else{
		  ArrayList<String> lore = new ArrayList<String>();
		  lore.add(ChatColor.GRAY + ench.getName() + " " + Util.getRomanNumeral(level));
		  meta.setLore(lore);
	  }
	  return meta;
  }
}
