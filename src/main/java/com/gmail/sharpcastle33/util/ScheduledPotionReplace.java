package com.gmail.sharpcastle33.util;


import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;


public class ScheduledPotionReplace extends BukkitRunnable {

  Player player;  // The player whose potion effect must be returned
  PotionEffect effect;  // The potion effect
  int duration; // How long the replacing potion will last. Once that duration is over, bring back the original effect
  
  
  public ScheduledPotionReplace(Player player, PotionEffect effect, int duration){
  
      this.player = player;
      this.effect = effect;
      this.duration = duration;  

  } 

      @Override
      public void run() {

        if(duration > 0){
        
          Bukkit.getLogger().info("Potion Replace in...: " + effect.getType());
          duration--;
          
        } else {
          Bukkit.getLogger().info("Putting old potion effect in: " + effect.getType());
          player.addPotionEffect(effect);
          this.cancel();
        }


      }




}
