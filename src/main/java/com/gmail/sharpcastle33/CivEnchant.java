package com.gmail.sharpcastle33;

import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.sharpcastle33.enchantments.CustomEnchantmentManager;
import com.gmail.sharpcastle33.listeners.AnvilListener;
import com.gmail.sharpcastle33.listeners.ArmorEquipListener;
import com.gmail.sharpcastle33.listeners.BlockListener;
import com.gmail.sharpcastle33.listeners.CraftingOrbListener;
import com.gmail.sharpcastle33.listeners.DamageListener;
import com.gmail.sharpcastle33.listeners.EnhancementListener;
import com.gmail.sharpcastle33.util.CooldownManager;

public class CivEnchant extends JavaPlugin{
  
  public static CivEnchant plugin;
  private static EnhancementListener enhancementListener;
  private static CraftingOrbListener craftingOrbListener;
  private static DamageListener damageListener;
  private static BlockListener blockListener;
  private static ArmorEquipListener armorEquipListener;
  private static AnvilListener anvilListener;
  
  public static CustomEnchantmentManager manager;
  public static CooldownManager cdManager;
  
  
  public void onEnable(){
    plugin = this;
    manager = new CustomEnchantmentManager();
    cdManager = new CooldownManager();
    
    enhancementListener = new EnhancementListener();
    this.getServer().getPluginManager().registerEvents(enhancementListener, plugin);
    
    damageListener = new DamageListener();
    this.getServer().getPluginManager().registerEvents(damageListener, plugin);
    
    craftingOrbListener = new CraftingOrbListener();
    this.getServer().getPluginManager().registerEvents(craftingOrbListener, plugin);
    
    blockListener = new BlockListener();
    this.getServer().getPluginManager().registerEvents(blockListener, plugin);
    
    armorEquipListener = new ArmorEquipListener();
    this.getServer().getPluginManager().registerEvents(armorEquipListener, plugin);
    
    anvilListener = new AnvilListener();
    this.getServer().getPluginManager().registerEvents(anvilListener, plugin);
    
  }
  
  public void onDisable(){
    
  }

}
