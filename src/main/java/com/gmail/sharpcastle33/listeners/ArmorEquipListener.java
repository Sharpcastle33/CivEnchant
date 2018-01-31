package com.gmail.sharpcastle33.listeners;

import java.util.Map;
import java.util.Random;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.gmail.sharpcastle33.enchantments.CustomEnchantment;
import com.gmail.sharpcastle33.enchantments.CustomEnchantmentManager;
import com.gmail.sharpcastle33.util.Util;


public class BlockListener implements Listener{


    @EventHandler
    public void onArmorEquip(ArmorEquipEvent event) {
  
        ItemStack newArmor;
        
      
        if(event.getNewArmorPiece() != null && event.getNewArmorPiece().getType() != material.AIR){
        
          newArmor = e.getNewArmorPiece();
          
          if (newArmor.hasItemMeta()) {
			
			       enchants = CustomEnchantmentManager.getCustomEnchantments(newArmor);
			
                if (enchants.containsKey(CustomEnchantment.VIGOR)) {
                
                
                      
                
                }
           }
          
        
        } //
        


    }


    @EventHandler
    public void onArmorUnequip(ArmorEquipEvent event) {




    }
  
  
  
  }
