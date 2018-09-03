package com.gmail.sharpcastle33;

import java.util.ArrayList;

import org.bukkit.plugin.java.JavaPlugin;

import com.codingforcookies.armorequip.ArmorListener;
import com.gmail.sharpcastle33.debugtools.GiveCivEnchantCommand;
import com.gmail.sharpcastle33.debugtools.ListCivEnchantsCommand;
import com.gmail.sharpcastle33.durability.DurabilityListener;
import com.gmail.sharpcastle33.enchantments.CustomEnchantmentManager;
import com.gmail.sharpcastle33.listeners.AnvilListener;
import com.gmail.sharpcastle33.listeners.ArmorEquipListener;
import com.gmail.sharpcastle33.listeners.BlockListener;
import com.gmail.sharpcastle33.listeners.CraftingOrbListener;
import com.gmail.sharpcastle33.listeners.DamageListener;
import com.gmail.sharpcastle33.listeners.EmeraldInfusionListener;
import com.gmail.sharpcastle33.listeners.EnhancementListener;
import com.gmail.sharpcastle33.listeners.SetBonusListener;
import com.gmail.sharpcastle33.util.CooldownManager;

public class CivEnchant extends JavaPlugin{
  
  public static CivEnchant plugin;
  private static EnhancementListener enhancementListener;
  private static CraftingOrbListener craftingOrbListener;
  private static DamageListener damageListener;
  private static BlockListener blockListener;
  private static ArmorEquipListener armorEquipListener;
  private static AnvilListener anvilListener;
  private static DurabilityListener durabilityListener;
  private static SetBonusListener setBonusListener;
  private static ArmorListener armorListener;
  private static EmeraldInfusionListener emeraldListener;
  
  
  public static CustomEnchantmentManager manager;
  public static CooldownManager cdManager;
  
  public static ArrayList<String> adamantSet;
  public static ArrayList<String> soulSet;
  
  
  private ArrayList blockedMaterials;
  
  public void onEnable(){
    plugin = this;
    manager = new CustomEnchantmentManager();
    cdManager = new CooldownManager();
    
    blockedMaterials = new ArrayList<String>();
	adamantSet = new ArrayList<String>();
	soulSet = new ArrayList<String>();
    
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
    
    durabilityListener = new DurabilityListener();
    this.getServer().getPluginManager().registerEvents(durabilityListener, plugin);
    
    armorListener = new ArmorListener(blockedMaterials);
    this.getServer().getPluginManager().registerEvents(armorListener, this);
    
    setBonusListener = new SetBonusListener();
    this.getServer().getPluginManager().registerEvents(setBonusListener, plugin);
    
    emeraldListener = new EmeraldInfusionListener();
    this.getServer().getPluginManager().registerEvents(emeraldListener, plugin);
    
    // DEBUGTOOLS
    
    getCommand("givecivenchant").setExecutor(new GiveCivEnchantCommand());
    getCommand("listcivenchants").setExecutor(new ListCivEnchantsCommand());
  }
  
  public void onDisable(){
    
  }

}
