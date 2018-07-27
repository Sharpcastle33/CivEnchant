package com.gmail.sharpcastle33.util;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.sharpcastle33.CivEnchant;


public class RageEffect extends BukkitRunnable {

  int level;
  int duration;
  Player player;

public RageEffect(Player player){

  level = 1;
  duration = 100; // 5 seconds
  this.player = player;
  
  this.runTask(CivEnchant.plugin);

}

@Override
  public void run(){
  
    duration--;
    
    if(duration <= 0){
    
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
      duration = 60; // Every hit gives 3 second window
  }
    
    
    
    

}
