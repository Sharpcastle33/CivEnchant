package com.gmail.sharpcastle33.listeners;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

import com.gmail.sharpcastle33.enchantments.CustomEnchantment;
import com.gmail.sharpcastle33.enchantments.CustomEnchantmentManager;
import com.gmail.sharpcastle33.util.Util;

public class DamageListener implements Listener{
	
	@EventHandler
	public void calculateDamage(EntityDamageByEntityEvent event){
		Entity offense = event.getDamager();
		Entity defense = event.getEntity();
		
		double dmgFlat = 0;
		double dmgMod = 0;
		double dmgMulti = 0;
		
		//ATTACKING PLAYER
		if(offense instanceof Player){
			Player attacker = (Player) offense;
			
			if(attacker.getInventory().getItemInMainHand() != null){
				ItemStack weapon = attacker.getInventory().getItemInMainHand();
				if(weapon.hasItemMeta()){
					Map<CustomEnchantment, Integer> enchants = CustomEnchantmentManager.getCustomEnchantments(weapon);
					
					if(enchants.containsKey(CustomEnchantment.LIFESTEAL)){
						if(Util.chance(enchants.get(CustomEnchantment.LIFESTEAL), 33)){
							attacker.setHealth(Math.min(attacker.getHealth()+1, attacker.getMaxHealth()));
							Bukkit.getLogger().info("healed " + attacker.getName());
						}
					}
				}
			}
		}
		
		//DEFENDING PLAYER
		if(defense instanceof Player){
			Player defender = (Player) defense;
			ItemStack[] armor = defender.getInventory().getArmorContents();
			int evadeChance = 0;
			for(ItemStack stack : armor){
				if(stack != null && stack.hasItemMeta()){
					Map<CustomEnchantment, Integer> enchants = CustomEnchantmentManager.getCustomEnchantments(stack);
					
					if(enchants.containsKey(CustomEnchantment.EVASIVE)){
						
					}
					
					if(enchants.containsKey(CustomEnchantment.ENDURANCE)){
						//TODO
						defender.sendMessage("endured hit");
						dmgFlat-=(enchants.get(CustomEnchantment.ENDURANCE)*0.15);
					}
					
					if(enchants.containsKey(CustomEnchantment.VIGOR)){
						
					}
				}
			}
			
			ItemStack stack = defender.getInventory().getItemInOffHand();
			
			if(stack != null && stack.getType() == Material.SHIELD){
				dmgFlat-=1;
			}
		}
		
		//PLAYER VS PLAYER
		if(offense instanceof Player && defense instanceof Player){
			Player attacker = (Player) offense;
			Player defender = (Player) defense;
		}
		
		double finalDamage = (event.getDamage() + dmgFlat) * (1 + dmgMod) * (1 + dmgMulti);
	}
	
	@EventHandler
	public void onArrowShoot(EntityShootBowEvent event) {
		
		if (event.getEntity() instanceof Player) {
			
			Player player = (Player) event.getEntity();
			ItemStack bow = event.getBow();
			
			if (bow.hasItemMeta()) {
				
				Map<CustomEnchantment, Integer> enchants = CustomEnchantmentManager.getCustomEnchantments(bow);
				
				if(enchants.containsKey(CustomEnchantment.FAR_SHOT)){
					
					//TODO
					
				}
				
				if(enchants.containsKey(CustomEnchantment.POINT_BLANK)) {
					
					//TODO
					
				}
			}
		}
	}

}
