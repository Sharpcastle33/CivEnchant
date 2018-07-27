package com.gmail.sharpcastle33.util;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.gmail.sharpcastle33.CivEnchant;
import com.gmail.sharpcastle33.enchantments.CustomEnchantment;

import net.md_5.bungee.api.ChatColor;

public class Util {
	
	private static Material[] swords = {Material.DIAMOND_SWORD, Material.IRON_SWORD, Material.GOLD_SWORD, Material.STONE_SWORD, Material.WOOD_SWORD};
  
	private static Material[] armors = {Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS, Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS, Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS, Material.GOLD_HELMET, Material.GOLD_CHESTPLATE, Material.GOLD_LEGGINGS, Material.GOLD_BOOTS, Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS};
	
	private static Material[] pickaxes = {Material.DIAMOND_PICKAXE, Material.IRON_PICKAXE, Material.GOLD_PICKAXE, Material.STONE_PICKAXE, Material.WOOD_PICKAXE};
	
	private static Material[] axes = {Material.DIAMOND_AXE, Material.IRON_AXE, Material.GOLD_AXE, Material.STONE_AXE, Material.WOOD_AXE};

	private static Material[] hoes = {Material.DIAMOND_HOE, Material.IRON_HOE, Material.GOLD_HOE, Material.STONE_HOE, Material.WOOD_HOE};

	private static Material[] spades = {Material.DIAMOND_SPADE, Material.IRON_SPADE, Material.GOLD_SPADE, Material.STONE_SPADE, Material.WOOD_SPADE};

	static CivEnchant plugin = CivEnchant.plugin;
	

	
	public static ItemStack generateItem(String s, int amt) {
		ItemStack stack = null;
		if(s.equalsIgnoreCase("EMERALD_FRAGMENT")) {
			stack = new ItemStack(Material.IRON_NUGGET, amt);
			ItemMeta meta = stack.getItemMeta();
			meta.setDisplayName(ChatColor.YELLOW + "Emerald Fragment");
			stack.setItemMeta(meta);
		}
		return stack;
	}
	

	public static void reducePotionDuration(Player p, PotionEffectType effect, int duration){

		// I'm 85% sure this will never be true
		if(p.getActivePotionEffects().contains(effect)){
		
			for(PotionEffect reducedEffect : p.getActivePotionEffects()){
				
				if(reducedEffect.getType() == effect){
				
					PotionEffect newPotion = new PotionEffect(effect, reducedEffect.getDuration() - duration, reducedEffect.getAmplifier());
					p.removePotionEffect(reducedEffect.getType());
					p.addPotionEffect(newPotion);
					
				}
				
				
			}
			
		}
		
	}
	
	
	public static boolean replacePotionEffect(Player p, PotionEffect effect) {
		
		if(p.getActivePotionEffects().contains(effect)) {
			

			PotionEffect previousEffect = effect;

			
			for(PotionEffect playersEffect : p.getActivePotionEffects()){ // Look at player's effects
				if(playersEffect.getType() == effect.getType()){	// If what we want to give exists for them
				
					if(playersEffect.getAmplifier() < effect.getAmplifier()){ // Is the one we want to give more powerful?
						p.removePotionEffect(playersEffect.getType()); // If so, remove & replace
						p.addPotionEffect(effect);
						ScheduledPotionReplace replace = new ScheduledPotionReplace(p, playersEffect, effect.getDuration());
						replace.runTask(CivEnchant.plugin);
					}
					
					
				}
				
				
			}
			
			
			
			
			return true;
		}else {
			p.addPotionEffect(effect);
			return false;
		}
		
	}
	
	public static boolean isDurable(ItemStack stack){
		if(isTool(stack) || isArmor(stack) || isSword(stack) || isTool(stack) || isBow(stack)){
			return true;
		}else return false;
	}
	
	public static boolean isBow(ItemStack stack){
		if(stack.getType() == Material.BOW){
			return true;
		}
		return false;
	}
	
	public static boolean isTool(ItemStack stack){
		if(isPickaxe(stack) || isAxe(stack) || isHoe(stack) || isShovel(stack)){
			return true;
		}else return false;
	}
	
	public static boolean isSword(ItemStack stack){
		for(Material m : swords){
			if(m == stack.getType()){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isPickaxe(ItemStack stack){
		for(Material m : pickaxes){
			if(m == stack.getType()){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isAxe(ItemStack stack) {
		for(Material m : axes) {
			if(m == stack.getType()) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isHoe(ItemStack stack) {
		for(Material m : hoes) {
			if(m == stack.getType()) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isShovel(ItemStack stack) {
		for(Material m : spades) {
			if(m == stack.getType()) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isArmor(ItemStack stack){
		for(Material m : armors){
			if(m == stack.getType()){
				return true;
			}
		}
		return false;
	}

  public static boolean decrementOffhand(Player p){
    if(p.getInventory().getItemInOffHand() != null){
      ItemStack stack = p.getInventory().getItemInOffHand();
      stack.setAmount(stack.getAmount()-1);
      p.getInventory().setItemInOffHand(stack);
    }
    
    if(p.getInventory().getItemInOffHand() != null){
      return true;
    }else return false;
  }
  
  public static boolean chance (int attempts, int bound){
	if(attempts >= bound) {
		return true;
	}
    Random rand = new Random(); 
    if(rand.nextInt(bound) <= attempts-1){
      return true;
    }else return false;
  }
  
  public static String getLevelFromPrefixTag(ItemMeta meta, String s){
	  if(meta.hasLore()){
		  java.util.List<String> lore = meta.getLore();
		  for(int i = 0; i < lore.size(); i++){
			  String line = lore.get(i);
			  if(line.startsWith(s)){
				  line = line.substring(s.length());
				  //Bukkit.getLogger().info(ChatColor.RED + line);
				  return line;
			  }
	  	  }
	  }
	  return "";
  }
  
  public static boolean containsLoreTag(ItemMeta meta, String s){
	  if(meta.hasLore()){
		  java.util.List<String> lore = meta.getLore();
		  if(lore.contains(s)){
			  return true;
		  }
	  }
	  return false;
  }
  
  public static boolean contiansPrefixTag(ItemMeta meta, String s){
	  if(meta.hasLore()){
		  java.util.List<String> lore = meta.getLore();
		  for(String line : lore){
			  if(line.startsWith(s)){
				  return true;
			  }
		  }
	  }
	  return false;
  }
  
  public static void addLoreTag(ItemMeta meta, String s){
	if(meta.hasLore()){
		  java.util.List<String> lore = meta.getLore();
		  lore.add(s);
		  meta.setLore(lore);
	}else{
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(s);
		meta.setLore(lore);
	}
	  	
  }
  
  public static void replaceLoreTag(ItemMeta meta, String old, String replace){
	  if(meta.hasLore()){
		  java.util.List<String> lore = meta.getLore();
		  if(lore.indexOf(old) == -1) { 
			  Bukkit.getLogger().info("failed replaceLoreTag, item does not contain: " + old);
			  return;
		  }
		  lore.set(lore.indexOf(old), replace);
		  meta.setLore(lore);
	  }
  }
  
  public static String getRomanNumeral(int i){
	  switch(i){
	  case 1:
		  return "I";
	  case 2:
		  return "II";
	  case 3:
		  return "III";
	  case 4:
		  return "IV";
	  case 5:
		  return "V";
	  case 6:
		  return "VI";
	  case 7:
		  return "VII";
	  case 8:
		  return "VIII";
	  case 9:
		  return "IX";
	  case 10:
		  return "X";
	  case 11:
		  return "XI";
	  default:
		  return "0";			
	  }
  }
  
  public static int fromNumeral(String numeral){
	switch(numeral){
	  case "I":
		  return 1;
	  case "II":
		  return 2;
	  case "III":
		  return 3;
	  case "IV":
		  return 4;
	  case "V":
		  return 5;
	  case "VI":
		  return 6;
	  case "VII":
		  return 7;
	  case "VIII":
		  return 8;
	  case "IX":
		  return 9;
	  case "X":
		  return 10;
	  case "XI":
		  return 11;
	  default:
		  return 0;			
	 }
  }
  
  public static void printMap(Map<CustomEnchantment, Integer> map){
	  Bukkit.getLogger().info("CustomEnchantment map contains: ");
	  for(CustomEnchantment e : map.keySet()){
		  Bukkit.getLogger().info(e.getName() + " " + map.get(e));
	  }
  }

}
