package com.gmail.sharpcastle33.curse;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class CursedDealListener implements Listener{
  
  @EventHandler
  public void triggerDeal(PlayerInteractEvent event) {
    if(event.getHand() == EquipmentSlot.HAND && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)){
      Player p = event.getPlayer();
      ItemStack main = p.getInventory().getItemInMainHand();
      ItemStack off = p.getInventory().getItemInOffHand();
        
      if(main == null) { return; }
        
      //CURSED DEAL
      if(main.hasItemMeta() && main.getType() == Material.BOOK && main.getItemMeta().getDisplayName().contains(CurseUtil.DEAL_NAME)){
        event.setCancelled(true);
        
        ItemStack deal = p.getInventory().getItemInMainHand();
        
        int coins = CurseUtil.parseCoins(deal);
        
      }
    }
  }
  
}


