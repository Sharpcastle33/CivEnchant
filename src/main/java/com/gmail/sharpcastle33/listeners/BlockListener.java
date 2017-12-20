package com.gmail.sharpcastle33.listeners;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.gmail.sharpcastle33.enchantments.CustomEnchantment;
import com.gmail.sharpcastle33.enchantments.CustomEnchantmentManager;

import net.md_5.bungee.api.ChatColor;
import vg.civcraft.mc.citadel.Citadel;
import vg.civcraft.mc.citadel.reinforcement.Reinforcement;

public class BlockListener implements Listener{
	
	final static String TIMBER_REINFORCEMENT = ChatColor.RED + "Timber enchantment is disabled for reinfrocements.";
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		
		Player player = event.getPlayer();
		Block block = event.getBlock();
		ItemStack mainHand = event.getPlayer().getInventory().getItemInMainHand();
		
		if (mainHand.hasItemMeta()) {
			
			Map<CustomEnchantment, Integer> enchants = CustomEnchantmentManager.getCustomEnchantments(mainHand);
			
			if(enchants.containsKey(CustomEnchantment.IRON_AFFINITY)){
				
				//do stuff
				
			}
			
			if (enchants.containsKey(CustomEnchantment.GOLD_AFFINITY)) {
				
				//do stuff
				
			}
			
			if (block.getType() == Material.LOG || block.getType() == Material.LOG_2) {
				
				if (enchants.containsKey(CustomEnchantment.TIMBER)) {
					
					double x = block.getX();
					double y = block.getY();
					double z = block.getZ();
					
					for (double i = y; i < (y + (enchants.get(CustomEnchantment.TIMBER) * 3)); i++) {
						
						Location location = new Location(block.getWorld(), x, i, z);
						Reinforcement reinfrocement = Citadel.getCitadelDatabase().getReinforcement(location);
						
						if (!(reinfrocement == null)) {
							
							player.sendMessage(TIMBER_REINFORCEMENT);
							
							break;
							
						}
						
						else {
						
							if (location.getBlock().getType() == Material.LOG || location.getBlock().getType() == Material.LOG_2) {
							
								location.getBlock().breakNaturally();
							
								if (enchants.containsKey(CustomEnchantment.UNBREAKING)) {
								
									double random = Math.random();
									double unbreakingLvl = enchants.get(CustomEnchantment.UNBREAKING);
								
									if (random < (1 / unbreakingLvl)) {
									
										mainHand.setDurability((short) (mainHand.getDurability() + 1));
									
									}
								}
							
								else {
								
									mainHand.setDurability((short) (mainHand.getDurability() + 1));
								
								}
							}
							
							else {
								break;
							}
						}
						
					}
					
				}
				
			}
			
			if (enchants.containsKey(CustomEnchantment.AUTO_SMELT)) {
				
				if (event.getBlock().getType().equals(Material.GOLD_ORE)) {
					
					event.setDropItems(false);
					event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.GOLD_INGOT));
					event.getBlock().getWorld().spawn(event.getBlock().getLocation(), ExperienceOrb.class).setExperience(2 * enchants.get(CustomEnchantment.AUTO_SMELT));
					
				}
				
				if (event.getBlock().getType().equals(Material.IRON_ORE)) {
					
					event.setDropItems(false);
					event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.IRON_INGOT));
					event.getBlock().getWorld().spawn(event.getBlock().getLocation(), ExperienceOrb.class).setExperience(1 * enchants.get(CustomEnchantment.AUTO_SMELT));
					
				}
				
			}
			
		}
		
	}

}
