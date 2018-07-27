package com.gmail.sharpcastle33.listeners;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.sharpcastle33.util.Util;

import net.md_5.bungee.api.ChatColor;

public class EnhancementListener implements Listener{
  
  final static boolean REQUIRE_SNEAKING = true;
  final static String ARMOR_ENHANCEMENT = ChatColor.YELLOW + "Enhancement Orb (Armor)";
  final static String TOOL_ENHANCEMENT = ChatColor.YELLOW + "Enhancement Orb (Tool)";
  final static String WEAPON_ENHANCEMENT = ChatColor.YELLOW + "Enhancement Orb (Weapon)";
  
  final static Material[] armor = {Material.DIAMOND_CHESTPLATE, Material.DIAMOND_HELMET, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS, Material.CHAINMAIL_BOOTS, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_HELMET, Material.CHAINMAIL_LEGGINGS};
  final static Material[] weps = {Material.DIAMOND_SWORD, Material.DIAMOND_AXE, Material.BOW};
  final static Material[] tools = {Material.DIAMOND_AXE, Material.DIAMOND_PICKAXE, Material.DIAMOND_SPADE, Material.DIAMOND_HOE};
  
  final static ArrayList<Material> ARMOR_BASES = new ArrayList<Material>();
  final static ArrayList<Material> WEAPON_BASES = new ArrayList<Material>();
  final static ArrayList<Material> TOOL_BASES = new ArrayList<Material>();
  
  final static String ENHANCEMENT_SUCCESS = ChatColor.BLUE + "Enhancement Success!";
  final static String ENHANCEMENT_FAILURE = ChatColor.RED + "Enhancement Failed.";
  
  final static String MAX_ENHANCEMENT_ERROR = ChatColor.RED + "Your item is at its current maximum enhancement level. It cannot be enhanced further (yet).";
  final static String ENHANCEMENT = ChatColor.GRAY + "Enhancement: +";
  
  public EnhancementListener(){
    for(Material m : armor){
      ARMOR_BASES.add(m);
    }
    
    for(Material m : weps){
      WEAPON_BASES.add(m);
    }
    
    for(Material m : tools){
      TOOL_BASES.add(m);
    }
    
  }
  
  @EventHandler
  public void enhance(PlayerInteractEvent event){
    if(event.getHand() == EquipmentSlot.HAND && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)){
      Player p = event.getPlayer();
      if(p.isSneaking() == REQUIRE_SNEAKING || REQUIRE_SNEAKING == false){
    	      	
        ItemStack main = p.getInventory().getItemInMainHand();
        ItemStack off = p.getInventory().getItemInOffHand();
        
        if(main == null || off == null) { return; }
        
        String type = enhancementType(main, off);
        if(type == ARMOR_ENHANCEMENT){
         event.setCancelled(true);
         if(getEnhancementLevel(main) >= 4){
           p.sendMessage(MAX_ENHANCEMENT_ERROR);
         }else{
           Util.decrementOffhand(p);
           if(Util.chance(1,3 + (3*getEnhancementLevel(main)))){
             p.sendMessage(ENHANCEMENT_SUCCESS);
             p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
             p.getInventory().setItemInMainHand(enhanceItem(main));
           }else{
        	   p.sendMessage(ENHANCEMENT_FAILURE);
               p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1);
           }
         }
        }
        
        if(type == TOOL_ENHANCEMENT){
          if(getEnhancementLevel(main) >= 5){
            p.sendMessage(MAX_ENHANCEMENT_ERROR);
          }else{
            Util.decrementOffhand(p);
            if(Util.chance(1,3 + (3*getEnhancementLevel(main)))){
              p.sendMessage(ENHANCEMENT_SUCCESS);
              p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
              p.getInventory().setItemInMainHand(enhanceItem(main));
            }else{
            	p.sendMessage(ENHANCEMENT_FAILURE);
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1);
            }
          }
        }
        
        if(type == WEAPON_ENHANCEMENT){
          if(getEnhancementLevel(main) >= 5){
            p.sendMessage(MAX_ENHANCEMENT_ERROR);
          }else{
            Util.decrementOffhand(p);
            if(Util.chance(1,3 + (3*getEnhancementLevel(main)))){
              p.sendMessage(ENHANCEMENT_SUCCESS);
              p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
              p.getInventory().setItemInMainHand(enhanceItem(main));
            }else{
            	p.sendMessage(ENHANCEMENT_FAILURE);
                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, 1, 1);
            }
          }
        }
        
      }
    }
  }
 
  
  public void upgradeEnchantment(ItemMeta meta, Enchantment ench){
		if(meta.hasEnchant(ench)){
            int lvl = meta.getEnchantLevel(ench) + 1;
            meta.removeEnchant(ench);
            meta.addEnchant(ench, lvl, true);
          }else{
            meta.addEnchant(ench, 1, true);
          }
  }
  

  
  public void enhanceTag(ItemStack stack, ItemMeta meta){
	if(getEnhancementLevel(stack) == 0){
  		Util.addLoreTag(meta,ENHANCEMENT + 1);
  	}else Util.replaceLoreTag(meta,ENHANCEMENT + getEnhancementLevel(stack), ENHANCEMENT + (getEnhancementLevel(stack)+1));	
  }
  
  public ItemStack enhanceItem(ItemStack stack){
    if(stack != null){
      ItemMeta meta = stack.getItemMeta();
      
      if(ARMOR_BASES.contains(stack.getType())){   	
    	enhanceTag(stack,meta);
        upgradeEnchantment(meta,Enchantment.PROTECTION_ENVIRONMENTAL);    
      }
      
      if(WEAPON_BASES.contains(stack.getType())){
        if(stack.getType() == Material.BOW){	
        	enhanceTag(stack,meta);
        	upgradeEnchantment(meta,Enchantment.ARROW_DAMAGE);
        }
        
        if(stack.getType() == Material.DIAMOND_SWORD){
        	enhanceTag(stack,meta);
        	upgradeEnchantment(meta,Enchantment.DAMAGE_ALL);
        }
        
       /* if(stack.getType() == Material.DIAMOND_AXE){
        	enhanceTag(stack,meta);
        	upgradeEnchantment(meta,Enchantment.DAMAGE_ALL);
        }*/
      }
      
      
      if(TOOL_BASES.contains(stack.getType())){  
    	enhanceTag(stack,meta);
      	upgradeEnchantment(meta,Enchantment.DIG_SPEED);
      }
      
      stack.setItemMeta(meta);
    }
    return stack;  
  }
  
  
  
  
  public static int getEnhancementLevel(ItemStack stack){
  
    if(stack != null){
      if(stack.hasItemMeta()){
        ItemMeta meta = stack.getItemMeta();
        if(meta.hasEnchant(Enchantment.DAMAGE_ALL)){
          return meta.getEnchantLevel(Enchantment.DAMAGE_ALL);
        }
        if(meta.hasEnchant(Enchantment.ARROW_DAMAGE)){
          return meta.getEnchantLevel(Enchantment.ARROW_DAMAGE);
        }
        if(meta.hasEnchant(Enchantment.DIG_SPEED)){
          return meta.getEnchantLevel(Enchantment.DIG_SPEED);
        }
        if(meta.hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)){
          return meta.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
        }
        return 0;
      }
      return 0;
    }
    return -1;
  }
  
  public String enhancementType(ItemStack main, ItemStack off){
	
    if(off != null && off.getItemMeta() != null && main != null){
      
      String offName = off.getItemMeta().getDisplayName();
      
      
      if(offName.contains(ARMOR_ENHANCEMENT)){
        if(ARMOR_BASES.contains(main.getType())){
          return ARMOR_ENHANCEMENT;
        }
      }
      if(offName.contains(TOOL_ENHANCEMENT)){
        if(TOOL_BASES.contains(main.getType())){
          return TOOL_ENHANCEMENT;
        }
      }
      if(offName.contains(WEAPON_ENHANCEMENT)){
        if(WEAPON_BASES.contains(main.getType())){
          return WEAPON_ENHANCEMENT;
        }
      }
    }
    return "";
  }

}
