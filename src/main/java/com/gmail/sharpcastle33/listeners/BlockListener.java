package com.gmail.sharpcastle33.listeners;

import java.util.Map;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.gmail.sharpcastle33.enchantments.CustomEnchantment;
import com.gmail.sharpcastle33.enchantments.CustomEnchantmentManager;
import com.gmail.sharpcastle33.util.Util;

import net.md_5.bungee.api.ChatColor;
import vg.civcraft.mc.citadel.Citadel;
import vg.civcraft.mc.citadel.reinforcement.Reinforcement;

public class BlockListener implements Listener{
	
	final static String TIMBER_REINFORCEMENT = ChatColor.RED + "Timber enchantment is disabled for reinforcements.";
	final static Material[] crystals = {Material.DIAMOND_ORE, Material.EMERALD_ORE, Material.QUARTZ_ORE};
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		
		Player player = event.getPlayer();
		Block block = event.getBlock();
		ItemStack mainHand = event.getPlayer().getInventory().getItemInMainHand();
		
		boolean demolished = false;
		boolean smelt = false;
		
		if (mainHand.hasItemMeta()) {
			
			Map<CustomEnchantment, Integer> enchants = CustomEnchantmentManager.getCustomEnchantments(mainHand);
			
			//PICKAXE
			if(Util.isPickaxe(mainHand)) {
				
				if (enchants.containsKey(CustomEnchantment.AUTO_SMELT)) {
					
					smelt = true;
					
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

				if(enchants.containsKey(CustomEnchantment.DEMOLISHING)) {
					demolished = true;
					if(block.getType() == Material.STONE) {
						event.setDropItems(false);
						if(Util.chance(25*enchants.get(CustomEnchantment.DEMOLISHING), 100)) {
							event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.GRAVEL));				
						}
					}
				}
				
				if(enchants.containsKey(CustomEnchantment.CRYSTAL_ATTUNEMENT)) {
					for(Material m : crystals) {
						if(block.getType() == m) {
							Util.replacePotionEffect(player, new PotionEffect(PotionEffectType.FAST_DIGGING, 0, 2*enchants.get(CustomEnchantment.CRYSTAL_ATTUNEMENT)));
						}
					}
				}
				
				if(enchants.containsKey(CustomEnchantment.STONEMASON)) {
					if(!(demolished)) {
						if(block.getType() == Material.STONE) {
							byte data = block.getData();
							
							if(data == 0) {
								if(Util.chance(5*enchants.get(CustomEnchantment.STONEMASON), 100)){
									event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.COBBLESTONE));				
								}
							}else{
								if(Util.chance(20*enchants.get(CustomEnchantment.STONEMASON), 100)){
									event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.STONE,data));				
								}
							}
						}
					}
				}
				
				if(enchants.containsKey(CustomEnchantment.IRON_AFFINITY)){
					//How do we want to deal with players placing ore?
					if(block.getType() == Material.IRON_ORE) {
						
					}
		
				}
				
				if (enchants.containsKey(CustomEnchantment.GOLD_AFFINITY)) {
					if(block.getType() == Material.GOLD_ORE) {
						
					}
				}
			}
			
			//AXE
			if(Util.isAxe(mainHand)) {
				
				if(enchants.containsKey(CustomEnchantment.CARPENTRY)) {
					//LOGS
					if (block.getType() == Material.LOG) {
						//STICK DROP
						if(Util.chance(5*enchants.get(CustomEnchantment.CARPENTRY), 100)) {
							Random rand = new Random();
							int amt = rand.nextInt(enchants.get(CustomEnchantment.CARPENTRY))+1;		
							event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.STICK,amt));				
						}
						//FENCE DROP
						if(Util.chance(2*enchants.get(CustomEnchantment.CARPENTRY), 100)) {
							Byte data = block.getData();
							Random rand = new Random();
							int amt = rand.nextInt(enchants.get(CustomEnchantment.CARPENTRY)/2)+1;		
							event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.FENCE,amt,data));				
						}
						
						
					//LOG_2s	
					}else if (block.getType() == Material.LOG_2) {
						//STICK DROP
						if(Util.chance(5*enchants.get(CustomEnchantment.CARPENTRY), 100)) {
							Random rand = new Random();
							int amt = rand.nextInt(enchants.get(CustomEnchantment.CARPENTRY))+1;		
							event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.STICK,amt));				
						}
						//FENCE DROP
						if(Util.chance(2*enchants.get(CustomEnchantment.CARPENTRY), 100)) {
							Byte data = block.getData();
							Random rand = new Random();
							int amt = rand.nextInt(enchants.get(CustomEnchantment.CARPENTRY)/2)+1;		
							event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.FENCE,amt,data));				
						}
					}
					
					
				}
				
				if (enchants.containsKey(CustomEnchantment.TIMBER)) {
					if (block.getType() == Material.LOG || block.getType() == Material.LOG_2) {
						
						
						
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
			}
			
			if(Util.isShovel(mainHand)) {
				
			}
			
			if(Util.isHoe(mainHand)) {
				
			}
			
			
			
			

			
		}
		
	}

}
