package com.gmail.sharpcastle33.util;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.sharpcastle33.CivEnchant;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;


public class RageEffect extends BukkitRunnable {

  int level;
  int duration;
  Player player;
  Entity target;

  
public RageEffect(Player player, Entity target){

  level = 2;
  duration = 20 * CONSTANTS.I_RAGE_INITIAL_DURATION_SECONDS;
  this.player = player;
  this.target = target;
  

  this.runTaskTimer(CivEnchant.plugin,0,0);

}

@Override
  public void run(){
  
    duration--;
    //Bukkit.getLogger().info("Rage Effect Duration: " + duration);
 //  Bukkit.getLogger().info("Rage Players Size: " + CivEnchant.cdManager.ragePlayers.size());
   // Bukkit.getLogger().info("Rage Effect Size: " + CivEnchant.cdManager.rageEffects.size());
  //  Bukkit.getLogger().info("Rage Index " + CivEnchant.cdManager.ragePlayers.indexOf(player));
    if(duration <= 0 || target.isDead() || player.isDead()){
       // player.sendMessage("rage expiring");
        int playerIndex = CivEnchant.cdManager.ragePlayers.indexOf(player);
        
	CivEnchant.cdManager.ragePlayers.remove(playerIndex);
	CivEnchant.cdManager.rageEffects.remove(playerIndex);
        this.cancel();
        
     }
  }
  
  public int getLevel(){
    return level;
  }
  
  public void incrementLevel(){
      level++;
      duration = 20 * CONSTANTS.I_RAGE_HIT_DURATION_SECONDS; 
  }
  
  public boolean isTarget(Entity entity){
      
      return entity.equals(target);
      
  }
  
  public Entity getTarget(){
      return target;
  }

}
