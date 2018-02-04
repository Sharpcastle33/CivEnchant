package com.gmail.sharpcastle33.util;

import com.gmail.sharpcastle33.CivEnchant;
import java.util.ArrayList;
import org.bukkit.entity.Player;
import com.gmail.sharpcastle33.CivEnchant;
import com.gmail.sharpcastle33.enchantments.CustomEnchantment;


public class CooldownManager {

  public ArrayList<Player> secondWind;
  public ArrayList<Player> lastStand;
  public ArrayList<Player> adrenaline;
  CivEnchant plugin;


 public CooldownManager(){

      secondWind = new ArrayList<Player>();
      lastStand = new ArrayList<Player>();
      adrenaline = new ArrayList<Player>();
      plugin = CivEnchant.plugin;
 
 }

  public void add(Player player, CustomEnchantment ench, int duration){
  
      EnchantmentCooldown cd = new EnchantmentCooldown(player, duration, secondWind);

      switch(ench){
      
        case SECOND_WIND:
        
        	cd.setList(secondWind);
          secondWind.add(player);
       
           
        break;
        
        case LAST_STAND:
        
        	cd.setList(lastStand);
            lastStand.add(player);
            
        break;
        
        case ADRENALINE:  
        
        	cd.setList(adrenaline);
            adrenaline.add(player);
            
        break;
      
      
      
      
      }
      
      cd.runTask(plugin);
  
  
  
  
  }


}
