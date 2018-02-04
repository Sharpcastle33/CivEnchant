package com.gmail.sharpcastle33.util;

import com.gmail.sharpcastle33.CivEnchant;
import java.util.ArrayList;
import org.bukkit.entity.Player;
import com.gmail.sharpcastle33.CivEnchant;
import com.gmail.sharpcastle33.enchantments.CustomEnchantment;


public class CooldownManager {

  ArrayList<Player> secondWind;
  ArrayList<Player> lastStand;
  ArrayList<Player> adrenaline;
  ArrayList<RegenerationEffect> vitality;
  CivEnchant plugin;


 public CooldownManager(){

      secondWind = new ArrayList<Player>();
      lastStand = new ArrayList<Player>();
      adrenaline = new ArrayList<Player>();
      vitality = new ArrayList<RegenerationEffect>();
      plugin = CivEnchant.plugin;
 
 }
  public void addRegen(Player player, int regenAmount){
   
    vitality.add(new RegenerationEffect(player, regenAmount, vitality));
    
  }
  
  

  public void add(Player player, CustomEnchantment ench, int duration){
  
      switch(ench){
      
        case CustomEnchantment.SECOND_WIND:
        
          secondWind.add(player);
          EnchantCooldown cd = new EnchantCooldown(player, duration, secondWind);
          cd.runTask(plugin);
           
        break;
        
        case CustomEnchantment.LAST_STAND:
        
            lastStand.add(player);
            EnchantCooldown cd = new EnchantCooldown(player, duration, lastStand);
            cd.runTask(plugin);
            
        break;
        
        case CustomEnchantment.ADRENALINE:  
        
            adrenaline.add(player);
            EnchantCooldown cd = new EnchantCooldown(player, duration, adrenaline);
            cd.runTask(plugin);
            
        break;
      
      
      
      
      }
  
  
  
  
  }


}
