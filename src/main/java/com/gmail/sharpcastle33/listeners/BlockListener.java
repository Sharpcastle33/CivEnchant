package com.gmail.sharpcastle33.listeners;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.gmail.sharpcastle33.enchantments.CustomEnchantment;
import com.gmail.sharpcastle33.enchantments.CustomEnchantmentManager;

public class BlockListener implements Listener{
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		
		Player player = event.getPlayer();
		ItemStack mainHand = event.getPlayer().getInventory().getItemInMainHand();
		
		if (mainHand.hasItemMeta()) {
			
			Map<CustomEnchantment, Integer> enchants = CustomEnchantmentManager.getCustomEnchantments(mainHand);
			
			if(enchants.containsKey(CustomEnchantment.IRON_AFFINITY)){
				
				//do stuff
				
			}
			
			if (enchants.containsKey(CustomEnchantment.GOLD_AFFINITY)) {
				
				//do stuff
				
			}
			
			if (enchants.containsKey(CustomEnchantment.AUTO_SMELT)) {
				
				if (event.getBlock().getType().equals(Material.GOLD_ORE)) {
					
					event.setDropItems(false);
					event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.GOLD_INGOT));
					
				}
				
				if (event.getBlock().getType().equals(Material.IRON_ORE)) {
					
					event.setDropItems(false);
					event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.IRON_INGOT));
					
				}
				
			}
			
		}
		
	}

}
