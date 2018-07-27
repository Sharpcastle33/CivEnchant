

package com.gmail.sharpcastle33.util;

import java.util.ArrayList;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Player;
import com.gmail.sharpcastle33.CivEnchant;


public class RegenerationEffect extends BukkitRunnable {


Player player;
double regenLevel;
ArrayList<RegenerationEffect> regenList;
ArrayList<Player> playerList;
int ticks;
double regenAmount = 1;

public RegenerationEffect(Player player, double initialRegenLevel, ArrayList<RegenerationEffect> regenList, ArrayList<Player> playerList){

  this.player = player; // The player who is receiving regen
  this.regenLevel = initialRegenLevel; // When this object is created, the initial amount of regen the player recieves
  this.regenList = regenList; // List of players who are receiving regen (used to take player off if regenLevel = 0);
  this.playerList = playerList;
  this.ticks = 0; // Time passed
  this.runTask(CivEnchant.plugin);
  

}

@Override
  public void run(){
  
    ticks++;
    
    if(regenLevel == 0){
        regenList.remove(this);
        playerList.remove(player);
        this.cancel();
    }
    
    
    if(regenLevel < 4){
      regenAmount = 1; // .5 Heart
    } else
    if(regenLevel < 8){
      regenAmount = 2; // 1 Heart
    } else
    if(regenLevel < 12){
      regenAmount = 3; // 1.5 Hearts
    } else
    if(regenLevel == 12){
      regenAmount = 4; // 2 Hearts
    }
    
    
    if(ticks % 300 == 0){ // procs every 15 seconds
    
        if(player.getMaxHealth() != player.getHealth()){
            player.setHealth(player.getHealth() + regenAmount);
        }
        
    }

  }
  
  
  public void removeLevels(int amount){
      regenLevel -= amount;
  }
  
  public void addLevels(int amount){
      regenLevel += amount;
  }
  

}
