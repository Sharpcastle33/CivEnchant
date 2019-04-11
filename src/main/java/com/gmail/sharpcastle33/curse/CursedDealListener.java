package com.gmail.sharpcastle33.curse;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import net.md_5.bungee.api.ChatColor;

public class CursedDealListener implements Listener{
  
  public static String ERROR_NOT_ENOUGH_COINS = ChatColor.RED + "You do not have enough Cursed Coins to purchase this item!";
  public static String DEAL_SUCCESS = "DEAL_SUCCESS";
  @EventHandler
  public void triggerDeal(PlayerInteractEvent event) {
    if(event.getHand() == EquipmentSlot.HAND && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)){
      Player p = event.getPlayer();
      ItemStack main = p.getInventory().getItemInMainHand();
        
      if(main == null) { return; }
        
      //CURSED DEAL
      if(main.hasItemMeta() && main.getType() == Material.BOOK && main.getItemMeta().getDisplayName().contains(CurseUtil.DEAL_NAME)){
        event.setCancelled(true);
        
        ItemStack deal = p.getInventory().getItemInMainHand();
        
        int coins = CurseUtil.parseCoins(deal);
        
        //if the player has enough coins
        if(CurseUtil.hasEnoughCoins(p, coins)) {
          
          //Complete transaction
          CurseUtil.removeCoins(p, coins);
          p.getInventory().setItemInMainHand(CurseUtil.parseItem(deal));
          p.sendMessage(DEAL_SUCCESS);
          p.getWorld().playSound(p.getLocation(), Sound.AMBIENT_CAVE, 1, 1);

          
        }else {
          p.sendMessage(ERROR_NOT_ENOUGH_COINS);
        }
        
      }
    }
  }
  
}


