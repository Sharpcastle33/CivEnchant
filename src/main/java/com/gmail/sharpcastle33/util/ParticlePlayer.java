
package com.gmail.sharpcastle33.util;

import com.gmail.sharpcastle33.CivEnchant;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class ParticlePlayer {
     
    public static void playEvadeEffect(Player player){
        
        player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getEyeLocation().add(1,0,0), 0, 0, 0, 0, 1);
        player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getEyeLocation().add(0,0,1), 0, 0, 0, 0, 1);
        player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getEyeLocation().subtract(1,0,0), 0, 0, 0, 0, 1);
        player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getEyeLocation().subtract(0,0,1), 0, 0, 0, 0, 1);
                
    }
    
    public static void playRageEffect(LivingEntity player){
               
        
                Location location1 = player.getEyeLocation();
       
                int particles = 20;
                float radius = 0.5f;
               
                for (int i = 0; i < particles; i++){
            
                    double angle, x, z;
                    angle = 2 * Math.PI * i / particles;
                    x = Math.cos(angle) * radius;
                    z = Math.sin(angle) * radius;
                    location1.add(x, 0, z);
                                    
                    player.getWorld().spawnParticle(Particle.FLAME, location1, 0, 0, 0, 0, 1);
                    
                    location1.subtract(x, 0, z);
                    
                }
        
    }
    
    public static void playAdrenalineEffect(LivingEntity player){
               
        
        new BukkitRunnable(){
            
            Vector behindPlayer = player.getLocation().getDirection().normalize().multiply(-1);
            Location loc = player.getEyeLocation();
            int duration = 20 * CONSTANTS.I_ADRENALINE_DURATION_SECONDS;
            
            
            
            @Override
            public void run(){
                duration--;
                
                if(duration <= 0){
                    this.cancel();
                }
                
                player.getWorld().spawnParticle(Particle.TOTEM, loc.add(behindPlayer).subtract(0,.1,0), 0, 0, 0, 0, 1);
                player.getWorld().spawnParticle(Particle.TOTEM, loc.add(behindPlayer).subtract(0,.2,0), 0, 0, 0, 0, 1);
                player.getWorld().spawnParticle(Particle.TOTEM, loc.add(behindPlayer).subtract(0,.3,0), 0, 0, 0, 0, 1);
                
                loc = player.getEyeLocation();
                behindPlayer = loc.getDirection().normalize().multiply(-1);
                
            }
        }.runTaskTimer(CivEnchant.plugin, 0, 0);
        
    }
       
    
    public static void playLastStandEffect(LivingEntity player){
               
       
                Location location1 = player.getEyeLocation();
                Location location2 = player.getEyeLocation();
                Location location3 = player.getEyeLocation();
                int particles = 50;
                float radius = 0.7f;
               
                for (int i = 0; i < particles; i++){
            
                    double angle, x, z;
                    angle = 2 * Math.PI * i / particles;
                    x = Math.cos(angle) * radius;
                    z = Math.sin(angle) * radius;
                    location1.add(x, 0, z);
                    location2.add(x, -0.66, z);
                    location3.add(x, -1.33, z);
                                    
                    player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, location1, 0, 0, 0, 0, 1);
                    player.getWorld().spawnParticle(Particle.DRAGON_BREATH, location2, 0, 0, 0, 0, 1);
                    player.getWorld().spawnParticle(Particle.CRIT_MAGIC, location3, 0, 0, 0, 0, 1);
                    
                    location1.subtract(x, 0, z);
                    location2.subtract(x, -0.66, z);
                    location3.subtract(x, -1.33, z);
                    
                }
                
    }
    
    public static void playDIEffect(LivingEntity player){
               
                    
            
            new BukkitRunnable() {
            
                int particles = 50;
                float radius = 0.7f;
                double height = 0;  
                
                
                @Override
                public void run(){
                    
                    Location location1 = player.getEyeLocation().add(0,10-height,0);
                    
                    height += 0.4;
                    
                    if(location1.getY() < player.getEyeHeight()){
                        this.cancel();
                    }
                
                    for (int i = 0; i < particles; i++){

                        double angle, x, z;
                        angle = 2 * Math.PI * i / particles;
                        x = Math.cos(angle) * radius;
                        z = Math.sin(angle) * radius;
                        location1.add(x, 0, z);

                        player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, location1, 0, 0, 0, 0, 1);

                        location1.subtract(x, 0, z);

                    }
                    
                }
            }.runTaskTimer(CivEnchant.plugin, 0, 0);
    }

    
    
    
    
    
}
