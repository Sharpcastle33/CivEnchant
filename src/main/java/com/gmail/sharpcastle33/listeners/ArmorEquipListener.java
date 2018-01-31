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


public class ArmorEquipListener implements Listener{


    @EventHandler
    public void onArmorEquip(ArmorEquipEvent event) {
  
        ItemStack newArmor;
        Player player = event.getPlayer();
      
        if(event.getNewArmorPiece() != null && event.getNewArmorPiece().getType() != material.AIR){ // Equip
        
          newArmor = e.getNewArmorPiece();
          
		  if (newArmor.hasItemMeta()) {

				       enchants = CustomEnchantmentManager.getCustomEnchantments(newArmor);

					if (enchants.containsKey(CustomEnchantment.VIGOR)) {
						
						
						player.setMaxHealth(player.getMaxHealth() + (enchants.get(CustomEnchantment.VIGOR)*0.25));


					}

					if (enchants.containsKey(CustomEnchantment.VITALITY)) {

						

					}
		   }
          
        
        } // Equip end
	    
	    
	if(event.getOldArmorPiece() != null && event.getOldArmorPiece().getType() != Material.AIR) { // unequip
		
		oldArmor = event.getOldArmorPiece();
	
		enchants = CustomEnchantmentManager.getCustomEnchantments(oldArmor);

		if (enchants.containsKey(CustomEnchantment.VIGOR)) {

			player.setMaxHealth(player.getMaxHealth() - (enchants.get(CustomEnchantment.VIGOR)*0.25));


		}

		if (enchants.containsKey(CustomEnchantment.VITALITY)) {



		}
		
	}
        


    }
  
  
  
  }
