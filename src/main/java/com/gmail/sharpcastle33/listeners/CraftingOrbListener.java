package com.gmail.sharpcastle33.listeners;

import java.util.Map;
import java.util.Random;

import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import com.gmail.sharpcastle33.enchantments.CustomEnchantment;
import com.gmail.sharpcastle33.enchantments.CustomEnchantmentManager;
import com.gmail.sharpcastle33.util.Util;

import net.md_5.bungee.api.ChatColor;

public class CraftingOrbListener implements Listener {
  
  final static boolean REQUIRE_SNEAKING = true;
 
  final static String ARMOR_ENHANCEMENT = ChatColor.YELLOW + "Enhancement Orb (Armor)";
  final static String TOOL_ENHANCEMENT = ChatColor.YELLOW + "Enhancement Orb (Tool)";
  final static String WEAPON_ENHANCEMENT = ChatColor.YELLOW + "Enhancement Orb (Weapon)";
  
  final static String ENCHANTMENT_ORB = ChatColor.YELLOW + "Orb of Enchantment";
  final static String CELESTIAL_ORB = ChatColor.YELLOW + "Celestial Orb";
  final static String DREAM_ORB = ChatColor.YELLOW + "Orb of Dream";
  final static String AWAKENED_ORB = ChatColor.YELLOW + "Awakened Orb";
  final static String DISCORD_ORB = ChatColor.YELLOW + "Orb of Discord";
  final static String SCOUR_ORB = ChatColor.YELLOW + "Orb of Scouring";
  
 // final static Material[] armor = {Material.DIAMOND_CHESTPLATE, Material.DIAMOND_HELMET, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS, Material.CHAINMAIL_BOOTS, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_HELMET, Material.CHAINMAIL_LEGGINGS};
  //final static Material[] weps = {Material.DIAMOND_SWORD, Material.DIAMOND_AXE, Material.BOW};
 // final static Material[] tools = {Material.DIAMOND_AXE, Material.DIAMOND_PICKAXE, Material.DIAMOND_SPADE, Material.DIAMOND_HOE};
  
  //final static ArrayList<Material> ARMOR_BASES = new ArrayList<Material>();
  //final static ArrayList<Material> WEAPON_BASES = new ArrayList<Material>();
  //final static ArrayList<Material> TOOL_BASES = new ArrayList<Material>();
  
  final static String ERROR_ENCHANTED = ChatColor.RED + "You cannot use an " + ENCHANTMENT_ORB + ChatColor.RED + " on an already enchanted item.";
  final static String ENCHANT_SUCCESS = ChatColor.BLUE + "Enchantment Success!";
  final static String ERROR_MAXIMUM_ENCHANTMENTS = ChatColor.RED + "An item cannot have more enchantments than its enhancement level";
  final static String SCOUR_SUCCESS = ChatColor.BLUE + "Enchantments removed from item.";
  final static String ERROR_NO_ENCHANTMENTS = ChatColor.RED + "You may only use a " + SCOUR_ORB + ChatColor.RED + " on an enchanted item.";
  final static String DISCORD_FAILURE = ChatColor.RED + "You may only use a " + DISCORD_ORB + ChatColor.RED + " on an enchanted item.";

  public CraftingOrbListener(){
   
  }
  


  //TODO use util methods
  
  /**
   * Checks if an item is enchantable.
   * 
   * @param stack
   * @return boolean
   */
  public boolean isEnchantable(ItemStack stack){
    if(Util.isArmor(stack) || Util.isSword(stack) || Util.isTool(stack) || Util.isBow(stack)){
    	
      return true;
    }else return false;
  }
  
  /**
   * 
   * @param stack
   * @return the amount of enchantments on the item.
   */
  
  public int countEnchantments(ItemStack stack){
    int temp = 0;
    
    if(stack.hasItemMeta()){
     Map<Enchantment,Integer> enchants = stack.getEnchantments();
     if(enchants.containsKey(Enchantment.ARROW_DAMAGE)) { temp--; }
     if(enchants.containsKey(Enchantment.DAMAGE_ALL)) { temp--; }
     if(enchants.containsKey(Enchantment.PROTECTION_ENVIRONMENTAL)) { temp--; }
     if(enchants.containsKey(Enchantment.DIG_SPEED)) { temp--; }
     temp += enchants.size(); 
    }
        
    return temp + countCustomEnchantments(stack);
  }
  
  /**
   * Private method for counting custom enchantments on an item. You should almost always only use countEnchantments.
   * @param stack
   * @return
   */
  
  private int countCustomEnchantments(ItemStack stack){
	Map<CustomEnchantment, Integer> enchs = CustomEnchantmentManager.getLoreTagEnchantments(stack);
    
	enchs.remove(CustomEnchantment.INFUSION);
	
	return enchs.size();
  }
  
  /**
   * Uses an enchantment orb  on the item in the player's hand. 
   * @param p
   * @param stack
   */
  
  public void useEnchantmentOrb(Player p, ItemStack stack){
	
	CustomEnchantment roll = rollEnchantment(stack);
    stack = CustomEnchantmentManager.addCustomEnchantment(stack, roll, rollEnchantmentValue(roll,stack)); 
  
    roll = rollEnchantment(stack);
    stack = CustomEnchantmentManager.addCustomEnchantment(stack, roll, rollEnchantmentValue(roll,stack)); 

    p.getInventory().setItemInMainHand(stack);
    
    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
    p.sendMessage(ENCHANT_SUCCESS);
    Util.decrementOffhand(p);
  }
  
  /**
   * Uses a celestial orb  on the item in the player's hand. 
   * @param p
   * @param stack
   */
  
  public void useCelestialOrb(Player p, ItemStack stack){
  
	CustomEnchantment roll = rollEnchantment(stack);
	stack = CustomEnchantmentManager.addCustomEnchantment(stack, roll, rollEnchantmentValue(roll,stack)); 
	  
    p.getInventory().setItemInMainHand(stack);

    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
    p.sendMessage(ENCHANT_SUCCESS);
    Util.decrementOffhand(p);
  }
  
  /**
   * Rolls an enchantment value for applicable enchant and stack.
   * @param e
   * @param stack
   * @return
   */
  
  public int rollEnchantmentValue(CustomEnchantment e, ItemStack stack){
	  Random rand = new Random();
	  return rand.nextInt(e.getMaxLevel())+1;
  }
  
  /**
   * Rolls an enchantment for the item.
   * @param stack
   * @return
   */
  
  //TODO this method should use weighted values from the Map in order to roll enchantments. As of now it chooses at random.
  
  public CustomEnchantment rollEnchantment(ItemStack stack){
   Map<CustomEnchantment, Integer> table = CustomEnchantmentManager.getEnchantmentTable(stack);
    
   Map<CustomEnchantment, Integer> currentEnchantments = CustomEnchantmentManager.getCustomEnchantments(stack);
    
   //remove existing enchantments from table
   for(CustomEnchantment e : currentEnchantments.keySet()){
     table.remove(e);
   }
   
   
   //pick enchantment from table
   Random rand = new Random();
   int choice = rand.nextInt(table.size()-1);
   CustomEnchantment[] rolls = new CustomEnchantment[table.size()];
   table.keySet().toArray(rolls);
   if(rolls.length > 0){
	   return rolls[choice];
   }
   return CustomEnchantment.NO_ENCHANTMENT;
  }
  
  /**
   * Uses a scour orb on an item in the player's main hand.
   * @param p
   * @param stack
   */
  
  public void useScourOrb(Player p, ItemStack stack){
    stack = CustomEnchantmentManager.removeCustomEnchantments(stack);
    p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 1f, 1f);
    p.sendMessage(SCOUR_SUCCESS);
    p.getInventory().setItemInMainHand(stack);
    Util.decrementOffhand(p);
    
  }
  
  /**
   * Uses a discord orb on the item in the player's main hand.
   * @param p
   * @param stack
   */
  
  public void useDiscordOrb(Player p, ItemStack stack){
	
	  Map<CustomEnchantment, Integer> enchants = CustomEnchantmentManager.getCustomEnchantments(stack);
	  
	  //Randomise
	  if(Util.chance(50, 100)){
		  
		  Map<CustomEnchantment, Integer> original = enchants;
		  
		  CustomEnchantmentManager.removeCustomEnchantments(stack);
		  
		  if (original.isEmpty()) {
			  return;
		  }
		  else {
			  
			  for (Map.Entry<CustomEnchantment, Integer> e : original.entrySet()) {
				  
				  if (!(e.getKey() == CustomEnchantment.INFUSION)) {
					  
					  Random rand = new Random();
					  int level = rand.nextInt((e.getKey().getMaxLevel()));
					  
					  if (level == 0) {
						  level = 1;
					  }
					  
					  stack = CustomEnchantmentManager.addCustomEnchantment(stack, e.getKey(), level);
					  
				  }
				  
				  else {
					  
					  stack = CustomEnchantmentManager.addCustomEnchantment(stack, e.getKey(), e.getValue());
				  }
			  }
		  } p.playSound(p.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1f, 1f);
	  }
	  
	  //Remove 1 enchant
	  else {
		  
		  Map<CustomEnchantment, Integer> original = enchants;
		  Random rand = new Random();
		  int choice = rand.nextInt((enchants.keySet().size()));
		  
		  CustomEnchantmentManager.removeCustomEnchantments(stack);
		  
		  if (original.isEmpty()) {
			  return;
		  }
		  else {
			  
			  int counter = 0;
			  
			  if (choice == 0) {
				  choice = 1;
			  }
			  
			  for (Map.Entry<CustomEnchantment, Integer> e : original.entrySet()) {
				  
				  counter = counter + 1;
				  
				  if (!(choice == counter)) {
					  
					  stack = CustomEnchantmentManager.addCustomEnchantment(stack, e.getKey(), e.getValue());
					  
				  }
				  
				  else {
					  
					  if (e.getKey() == CustomEnchantment.INFUSION) {
						   
						  counter = counter - 1;
					  }
				  }
			  }
		  }
		  p.playSound(p.getLocation(), Sound.BLOCK_GLASS_BREAK, 1f, 1f);
	  }
	  Util.decrementOffhand(p);
	  p.sendMessage(ENCHANT_SUCCESS);
  }
  
  /**
   * Event handler for all Orb Crafting
   * @param event
   */
  
  @EventHandler 
  public void craftingOrb(PlayerInteractEvent event){
    if(event.getHand() == EquipmentSlot.HAND && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)){
      Player p = event.getPlayer();
      if(p.isSneaking() == REQUIRE_SNEAKING || REQUIRE_SNEAKING == false){
        ItemStack main = p.getInventory().getItemInMainHand();
        ItemStack off = p.getInventory().getItemInOffHand();
        
        if(main == null || off == null || !isEnchantable(main)) { return; }
        
        //ENCHANTMENT ORB
        if(off.hasItemMeta() && off.getItemMeta().getDisplayName().contains(ENCHANTMENT_ORB)){
        	event.setCancelled(true);

       	  if(countEnchantments(main) == 0){
            if(EnhancementListener.getEnhancementLevel(main) >= 2){
              useEnchantmentOrb(p,main);
            }else{
              p.sendMessage(ERROR_MAXIMUM_ENCHANTMENTS);
              p.playSound(p.getLocation(), Sound.BLOCK_NOTE_SNARE, 1, 1);
            }
          }else{
            p.sendMessage(ERROR_ENCHANTED);
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_SNARE, 1, 1);
            
          }
        }
        
        //CELESTIAL ORB
        if(off.hasItemMeta() && off.getItemMeta().getDisplayName().contains(CELESTIAL_ORB)){
           event.setCancelled(true);

          if(countEnchantments(main) < EnhancementListener.getEnhancementLevel(main)){
            useCelestialOrb(p,main);
          }else{
            p.sendMessage(ERROR_MAXIMUM_ENCHANTMENTS);
            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_SNARE, 1, 1);
          }
          
        }
        
        //DISCORD ORB
        if(off.hasItemMeta() && off.getItemMeta().getDisplayName().contains(DISCORD_ORB)){
        	event.setCancelled(true);
        	if(countEnchantments(main) > 0){
            	useDiscordOrb(p,main);
        	}else{
        		p.sendMessage(DISCORD_FAILURE);
        	}
        }
   
        //DREAM ORB
        if(off.hasItemMeta() && off.getItemMeta().getDisplayName().contains(DREAM_ORB)){
        	event.setCancelled(true);
        	p.sendMessage(ChatColor.RED + "You aren't quite sure how to use this Orb.");
        }
        
        //AWAKENED ORB
        if(off.hasItemMeta() && off.getItemMeta().getDisplayName().contains(AWAKENED_ORB)){
        	event.setCancelled(true);
        	p.sendMessage(ChatColor.RED + "This orb is not yet enabled. How did you get one?");
        }
        
        //SCOUR ORB
        if(off.hasItemMeta() && off.getItemMeta().getDisplayName().contains(SCOUR_ORB)){
          event.setCancelled(true);
          if(countEnchantments(main) > 0){
        	  useScourOrb(p,main);
          }else{
        	  p.sendMessage(ERROR_NO_ENCHANTMENTS);
              p.playSound(p.getLocation(), Sound.BLOCK_NOTE_SNARE, 1, 1);
          }
        }
      }
    }
  }
}
