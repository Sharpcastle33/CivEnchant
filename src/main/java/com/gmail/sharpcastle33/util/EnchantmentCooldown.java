package com.gmail.sharpcastle33.util;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EnchantmentCooldown extends BukkitRunnable {

  Player player;
  int duration;
  ArrayList<Player> listOfPlayers;
  
  public EnchantmentCooldown(Player player, int duration, ArrayList<Player> listOfPlayers){
      
    this.player = player;
    this.duration = (duration * 20) * 3; //(Duration of potion) * 3 = Cooldown
    this.listOfPlayers = listOfPlayers;
    

  } 

  @Override
  public void run(){
  
    
    duration--;
    
    if(duration <= 0){
      
      listOfPlayers.remove(player);
      this.cancel();
    }
  
  
  }
  
  public void setList(ArrayList<Player> list){
	  listOfPlayers = list;
  }
  
  public void setPlayer(Player p){
	  this.player = p;
  }
  
  public void setDuration(int d){
	  this.duration = d;
  }
  
  




}
