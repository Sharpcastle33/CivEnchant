package com.gmail.sharpcastle33;

import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.sharpcastle33.enchantments.CustomEnchantmentManager;
import com.gmail.sharpcastle33.listeners.CraftingOrbListener;
import com.gmail.sharpcastle33.listeners.DamageListener;
import com.gmail.sharpcastle33.listeners.EnhancementListener;

public class CivEnchant extends JavaPlugin{
  
  public static CivEnchant plugin;
  private static EnhancementListener enhancementListener;
  private static CraftingOrbListener craftingOrbListener;
  private static DamageListener damageListener;
  public static CustomEnchantmentManager manager;
  
  public void onEnable(){
    plugin = this;
    manager = new CustomEnchantmentManager();
    
    enhancementListener = new EnhancementListener();
    this.getServer().getPluginManager().registerEvents(enhancementListener, plugin);
    
    damageListener = new DamageListener();
    this.getServer().getPluginManager().registerEvents(damageListener, plugin);
    
    craftingOrbListener = new CraftingOrbListener();
    this.getServer().getPluginManager().registerEvents(craftingOrbListener, plugin);
  }
  
  public void onDisable(){
    
  }

}
