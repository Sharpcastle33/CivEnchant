package com.gmail.sharpcastle33.curse;

import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import net.md_5.bungee.api.ChatColor;

public class CurseUtil {
  
  private static String DEAL_NAME = ChatColor.YELLOW + "Cursed Deal";
  
  private static String DEAL_LORE = ChatColor.BLUE + "Activate to trade Cursed Coins for the specified item. Expires after 24 hours.";
  
  public ItemStack createDealBook(int coins, ItemStack reward){
    ItemStack book = new ItemStack(Material.BOOK);
    ItemMeta bookMeta = book.getItemMeta();
    
    bookMeta.setDisplayName(DEAL_NAME);
    
    ArrayList<String> lore = new ArrayList<String>();
    
    //TODO
    
  }
  
  public boolean parseDate(ItemStack book){
    ItemMeta m = book.getItemMeta();
    String date = m.getLore().get(1);
    
    double old = 0;
    double cur = System.currentTimeMillis();
    
    if(cur-old > 1000*60*60*24){
      return false;
    }else return true;
  }
  
  public int parseCoins (ItemStack book){
    int ret = 0;
    if(book.hasItemMeta()){
      
    }
  }
  
  public ItemStack parseItem(ItemStack book){
    ItemStack ret = new ItemStack(Material.PAPER);
    
  }

}
