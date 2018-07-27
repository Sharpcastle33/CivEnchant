package com.gmail.sharpcastle33.repair;

import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.sharpcastle33.enchantments.CustomEnchantment;

public class ItemRepair {

	private static final float POWDER_STRENGTH = 0.1f;
	private static final float MAX_REPAIR_CHANCE = 0.98f;
	private static final int REPAIR_EXP_COST = 10;
	private static Random random;

	public ItemStack damagedItem;
	public List<ItemStack> stablizationPowder;
	public Player player;

	public ItemRepair(Player player, ItemStack dmgItem, List<ItemStack> powder) {
		this.damagedItem = dmgItem;
		this.player = player;
		this.stablizationPowder = powder;

		random = new Random();
	}

	public ItemStack repair() {
		player.sendMessage("Repairing!");
		Integer playerXP = player.getLevel();
		ItemStack repairedItem = damagedItem;
		Integer infusionLevel = 1;
		List<String> lore = repairedItem.getItemMeta().getLore();
		
		// Check if player has enough XP
		if(playerXP < REPAIR_EXP_COST) {
			player.sendMessage("Not enough XP");
			return repairedItem;
		}
		
		player.sendMessage("Enough XP");

		Integer numPowder = 0;
		for (ItemStack stabPowder : stablizationPowder) {
			numPowder += stabPowder.getAmount();
		}

		// Get infusion level from lore text
		if (repairedItem.hasItemMeta()) {
			if (repairedItem.getItemMeta().hasLore()) {
				for (String loreString : lore) {
					if (loreString.contains(CustomEnchantment.INFUSION.getName())) {
						// Assumes the infusion lore text looks like "Infusion: 4" or Infusion 4
						infusionLevel = Integer.parseInt(loreString.substring(loreString.length() - 1));
					}
				}
			}
		}

		player.sendMessage("Determining chance");
		
		// Determine repair chance
		// REPAIR ALGO /////////////////////////////
		float infLvlMod = 0.5f / infusionLevel;
		float podwerMod = ((numPowder * POWDER_STRENGTH) / ((numPowder * POWDER_STRENGTH) + 1f)) * 0.5f;
		float repairChance = infLvlMod + podwerMod;

		repairChance = repairChance > MAX_REPAIR_CHANCE ? MAX_REPAIR_CHANCE : repairChance;
		///////////////////////////////////////////
		
		float bar = random.nextFloat();
		player.sendMessage(repairChance + ", " + bar);

		if (repairChance > bar) {
			repairedItem.setDurability((short)0);
			player.sendMessage(ChatColor.GREEN + "Repair successful!");
		} else {
			player.sendMessage(ChatColor.DARK_RED + "Repair failed!");
			for (String loreString : lore) {
				if (loreString.contains(CustomEnchantment.INFUSION.getName())) {
					// Assumes the infusion lore text looks like "Infusion: 4" or Infusion 4
					infusionLevel = Integer.parseInt(loreString.substring(loreString.length() - 1));
					infusionLevel -= 1;
					loreString = "Infusion " + infusionLevel;
				}
			}
			
			repairedItem.getItemMeta().setLore(lore);
		}
		
		player.setLevel(playerXP - REPAIR_EXP_COST);

		return repairedItem;
	}
	
	
	private ItemStack removeRandomEnchant(ItemStack item) {
		return item;
	}

}
