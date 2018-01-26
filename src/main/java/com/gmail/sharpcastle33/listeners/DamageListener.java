package com.gmail.sharpcastle33.listeners;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;

import com.gmail.sharpcastle33.CivEnchant;
import com.gmail.sharpcastle33.enchantments.CustomEnchantment;
import com.gmail.sharpcastle33.enchantments.CustomEnchantmentManager;
import com.gmail.sharpcastle33.util.Util;

public class DamageListener implements Listener{
	
	private CivEnchant plugin = CivEnchant.plugin;
	
	@EventHandler
	public void calculateDamage(EntityDamageByEntityEvent event){
		Entity offense = event.getDamager();
		Entity defense = event.getEntity();
			
		double dmgFlat = 0;
		double dmgMod = 0;
		double dmgMulti = 0;
		int evadeChance = 0;
		int endureChance = 0;
		int trueShot = 0;
		
		
		
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
			
			
			
			for(ItemStack stack : armor){
				if(stack != null && stack.hasItemMeta()){
					Map<CustomEnchantment, Integer> enchants = CustomEnchantmentManager.getCustomEnchantments(stack);
					
					if(enchants.containsKey(CustomEnchantment.EVASIVE)){
						
						// Accumulate levels of evasion for each armor piece
						// (Maximum of 12 evasion (lvl 3 ench on 4 pieces of armor))
						
						evadeChance += enchants.get(CustomEnchantment.EVASIVE);	
						
						
					}
					
					if(enchants.containsKey(CustomEnchantment.ENDURANCE)){
						//Similar logic as evasion, but chance to mitigate dmg and not avoid entirely
						endureChance += enchants.get(CustomEnchantment.ENDURANCE);
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
	
	
	//PLAYER SHOT BY ARROW

		if (offense instanceof Arrow) {
				
				Arrow arrow = (Arrow) offense;
				
				if (arrow.getShooter() instanceof Player) {
					
					Player shooter = (Player) arrow.getShooter();
					
					double shooterX = shooter.getLocation().getX();
					double shooterY = shooter.getLocation().getY();
					double shooterZ = shooter.getLocation().getZ();
					double defenseX = defense.getLocation().getX();
					double defenseY = defense.getLocation().getY();
					double defenseZ = defense.getLocation().getZ();
					
					//Distance between
					double xDistance = Math.abs(shooterX - defenseX);
					double yDistance = Math.abs(shooterY - defenseY);
					double zDistance = Math.abs(shooterZ - defenseZ);
					double diagDistance = Math.sqrt((xDistance * xDistance) + (zDistance * zDistance));
					
					double finalDistance = Math.sqrt((diagDistance * diagDistance) + (yDistance * yDistance));
					
					
					if(arrow.getName().contains("trueshot")){
						trueshot = 1;	
					}
					
					
					if(arrow.getName().contains("farshot1")) {
						
						if (finalDistance > 90) {
							dmgFlat = dmgFlat + 4;
						}
						else if (finalDistance > 60 && finalDistance < 90) {
							dmgFlat = dmgFlat + 3;
						}
						
						else if (finalDistance > 50 && finalDistance < 60){
							dmgFlat = dmgFlat + 1.5;
						}
					}
					
					if(arrow.getName().contains("farshot2")) {
						
						if (finalDistance > 90) {
							dmgFlat = dmgFlat + 6;
						}
						else if (finalDistance > 60 && finalDistance < 90) {
							dmgFlat = dmgFlat + 4;
						}
						
						else if (finalDistance > 50 && finalDistance < 60){
							dmgFlat = dmgFlat + 2;
						}
					}
					
					if(arrow.getName().contains("pointblank1")) {
						
						if (finalDistance > 10) {
						}
						else if (finalDistance > 7 && finalDistance < 10) {
							dmgFlat = dmgFlat + 1;
						}
						
						else if (finalDistance > 3 && finalDistance < 7){
							dmgFlat = dmgFlat + 2;
						}
						else {
							dmgFlat = dmgFlat + 3;
						}
					}
					
					if(arrow.getName().contains("pointblank2")) {
						
						if (finalDistance > 15) {
						}
						else if (finalDistance > 11 && finalDistance < 15) {
							dmgFlat = dmgFlat + 2;
						}
						
						else if (finalDistance > 5 && finalDistance < 11){
							dmgFlat = dmgFlat +3 ;
						}
						else {
							dmgFlat = dmgFlat + 4;
						}
					}
				}
			}
			
		
			//Calculate chance to evade
			Random ran = new Random();
			int roll = ran.nextInt(99) + 1; // Roll between 1-100 ## CHANGE THIS TO CHANGE PROBABILITY OF EVADE
			int evade = 1; // 1 is no evade, 0 is successful evade (for calculating finalDamage below)
			int enduredDamage = 0;
		
			if(roll <= evadeChance){
				//successful evasion
				evade = 0 + trueShot;
				defense.sendMessage("You evaded their attack!");
				defense.spawnParticle(Particle.VILLAGER_HAPPY, defense.getLocation.getX(), defense.getLocation.getY(), defense.getLocation.getZ(), 2)
					//spawnParticle​(Particle particle, double x, double y, double z, int count)
			}
			
			if(roll <= endureChance*3){	// I use roll from evade, but can be easily changed
				// Successful endure (about 1/3 chance @ max lvl)
					
				enduredDamage = endureChance / 2; // Max dmg endured is 6
				defense.sendMessage("You endured a hit!");
				
			}
		
		
		
			double finalDamage = ((event.getDamage() + dmgFlat) * (1 + dmgMod) * (1 + dmgMulti) * evade) + trueShot - enduredDamage;
			event.setDamage(finalDamage);
		}
		
		@EventHandler
		public void onArrowShoot(EntityShootBowEvent event) {
			
			if (event.getEntity() instanceof Player) {
				
				ItemStack bow = event.getBow();
				Entity arrow = event.getProjectile();
				
				if (event.getForce() > 0.8) {
				
					if (bow.hasItemMeta()) {
						
						Map<CustomEnchantment, Integer> enchants = CustomEnchantmentManager.getCustomEnchantments(bow);
					
						if(enchants.containsKey(CustomEnchantment.FAR_SHOT)){
							//Might want to try doing this with metadata instead for future compatibility.
							arrow.setCustomName(arrow.getName() + "farshot" + enchants.get(CustomEnchantment.FAR_SHOT));
						
						}
					
						if(enchants.containsKey(CustomEnchantment.POINT_BLANK)) {
						
							arrow.setCustomName(arrow.getName() + "pointblank" + enchants.get(CustomEnchantment.POINT_BLANK));
						
					}
				}
			}
		}

	}
	
	
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event){
		
		Player killed = event.getEntity();
		Entity killer = event.getEntity().getKiller();
		
		if(killer instanceof Player){
			
			if(killer.getInventory().getItemInMainHand() != null){
				ItemStack weapon = killer.getInventory().getItemInMainHand();
				
				if(weapon.hasItemMeta()){
					Map<CustomEnchantment, Integer> enchants = CustomEnchantmentManager.getCustomEnchantments(weapon);
					
					if(enchants.containsKey(CustomEnchantment.SOUL_TAKER)){
					
						if(killed.getBedSpawnLocation != NULL){
							
							killed.sendMessage(killer.getName() + "'s weapon destroyed your bed!");
							killed.getBedSpawnLocation().getBlock().breakNaturally();
							
						}
						
					}
				}
			}
		}
		
	}
}
