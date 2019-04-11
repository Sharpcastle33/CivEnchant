package com.gmail.sharpcastle33.curse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.gmail.sharpcastle33.enchantments.CustomEnchantment;
import com.gmail.sharpcastle33.enchantments.CustomEnchantmentManager;
import net.md_5.bungee.api.ChatColor;

public class CurseUtil {
  
  private static String DEAL_NAME = ChatColor.YELLOW + "Cursed Deal"; 
  private static String DEAL_LORE = ChatColor.BLUE + "Activate to trade Cursed Coins for the specified item. Expires after 24 hours.";
  private static String DEAL_DATE_LINE = ChatColor.RED + "Active until "; 
  private static String DEAL_DATE_CREATED = ChatColor.RED + "Created on ";
  
  private static String BREAK_LINE = ChatColor.GRAY + "------------";
  
  public ItemStack createDealBook(int coins, ItemStack reward){
    ItemStack book = new ItemStack(Material.BOOK);
    ItemMeta bookMeta = book.getItemMeta();
    
    //Set the name and description
    bookMeta.setDisplayName(DEAL_NAME);
    
    ArrayList<String> lore = new ArrayList<String>();
    lore.add(DEAL_LORE);
      
    //Write the creation date and expiration date
    double cur = System.currentTimeMillis();
    SimpleDateFormat form = 
    new SimpleDateFormat ("HH:mm:ss zzz, MM/dd/yy");

    lore.add(DEAL_DATE_CREATED + form.format(cur));
    cur+= 24*60*60*1000;
    lore.add(DEAL_DATE_LINE + form.format(cur));
    
    lore.add(BREAK_LINE);
    
    //Write the price
    lore.add(ChatColor.GOLD + "Price: " + ChatColor.BLUE + coins + ChatColor.GOLD + "x Cursed Coins");   
    lore.add(ChatColor.GRAY + "");
    //Write the reward
    lore.addAll(writeItemToLore(reward));
    //Return the book
    bookMeta.setLore(lore);
    book.setItemMeta(bookMeta);
    return book;
    //TODO
    
  }
  
  public boolean checkDate(ItemStack book){
    ItemMeta m = book.getItemMeta();
    String date = m.getLore().get(2);
    
    double old = 0;
    double cur = System.currentTimeMillis();
    
    if(cur-old > 1000*60*60*24){
      return false;
    }else return true;
  }
  
  public int parseCoins (ItemStack book){
    int ret = 9999;
    if(book.hasItemMeta()){
      List<String> lore = book.getItemMeta().getLore();
      
      //Price is always the 5th line
      String parse = lore.get(4);
      if(parse.startsWith(ChatColor.GOLD + "Price: " + ChatColor.BLUE)){
         String[] split = parse.split(": " + ChatColor.BLUE);
         String[] split2 = split[1].split(ChatColor.GOLD + "x");
         
         //This is the coins required
         String coins = split2[0];
         ret = Integer.parseInt(coins);
         return ret;
      }
      
    }
    return ret;
  }
  
  public ArrayList<String> writeItemToLore(ItemStack i){
    ArrayList<String> ret = new ArrayList<String>();
    Material m = i.getType();
    //Line 0 = item material
    ret.add(m.toString());
    
    //Line 1 = item name
    ret.add(i.getItemMeta().getDisplayName());
    
    //Line 2-n = ENCHANTMENT_NAME + " " + LEVEL
    Map<CustomEnchantment,Integer> ench = CustomEnchantmentManager.getCustomEnchantments(i);
    for(CustomEnchantment e : ench.keySet()){
      ret.add(e.getName() + " " + ench.get(e));
    }
    return ret;
  }
  
  public ItemStack parseItem(ItemStack book){
    ItemStack ret = new ItemStack(Material.PAPER);
    
    //Get all lore from the book
    if(book.hasItemMeta() && book.getItemMeta().hasLore()){
      List<String> lore = book.getItemMeta().getLore();
      ArrayList<String> item = new ArrayList<String>();
      
      //Item will be a list of strings that we will parse into an item.
      for(int i = 6; i < lore.size(); i++){
        item.add(lore.get(i));
      }
      
      //Set its type and get a list to put lore into
      ret.setType(Material.valueOf(item.get(0)));
      ItemMeta retMeta = ret.getItemMeta();
      List<String> retLore = retMeta.getLore();
      
      //Set its name
      retMeta.setDisplayName(item.get(1));
      
      //Parse all vanilla AND custom enchantments
      Map<CustomEnchantment, Integer> ench = CustomEnchantmentManager.getCustomEnchantments(book);
      
      for(CustomEnchantment e : ench.keySet()) {
        CustomEnchantmentManager.addCustomEnchantment(ret, e, ench.get(e));
      }
      //Do I have to worry about custom material? Not sure.
      
      retMeta.setLore(retLore);
      ret.setItemMeta(retMeta);
    }
    
    return ret;    
  }

}
