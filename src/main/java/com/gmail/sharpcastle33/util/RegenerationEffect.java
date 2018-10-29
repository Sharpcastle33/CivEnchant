

package com.gmail.sharpcastle33.util;

import java.util.ArrayList;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Player;
import com.gmail.sharpcastle33.CivEnchant;
import org.bukkit.Bukkit;


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
  this.runTaskTimer(CivEnchant.plugin,0,0);
  

}

@Override
  public void run(){
  
    ticks++;
    
    if(regenLevel == 0){
        Bukkit.getLogger().info("Removing Regen Effect..");
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
    
    
    if(ticks % (20 * CONSTANTS.I_REGEN_INTERVAL_SECONDS) == 0){ // procs every 15 seconds
    
        if(player.getHealth() < player.getMaxHealth() && player.getHealth() + regenAmount <= player.getMaxHealth()){
            player.setHealth(player.getHealth() + regenAmount);
        }
      
        
    }

  }
  
  
  public void removeLevels(int amount){
      Bukkit.getLogger().info(regenLevel + ": Removing Levels: " + amount);
      regenLevel -= amount;
  }
  
  public void addLevels(int amount){
      Bukkit.getLogger().info(regenLevel + ": Adding Levels: " + amount);
      regenLevel += amount;
  }
  
  public void setLevel(double amount){
      regenLevel = amount;
  }
  
  public double getLevel(){
      return regenLevel;
  }
  

}
