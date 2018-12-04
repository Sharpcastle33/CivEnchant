package com.gmail.sharpcastle33.listeners;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AnvilListener implements Listener {

	public static final String REPAIR_INVENTORY_NAME = "Item Repair (DO NOT USE, WORK IN PROGRESS)";
	public static final String REPAIR_ITEM_NAME = ChatColor.BLUE + "Repair";
	public static final String HELP_ITEM_NAME = ChatColor.BLUE + "Information";
	public static final String STABLIZATION_POWDER_DISPLAY_NAME = ChatColor.YELLOW + "Tinker's Talc";

	//returns a repair chance from 0-100.
	public int calculateRepairChance(ItemStack stack) {
		if(!stack.hasItemMeta()) {
			return 100;
		}
		
		if(stack.getItemMeta().hasLore() == false) {
			return 100;
		}
		
		int infusionLevel = EnhancementListener.getEnhancementLevel(stack);
				
		if(stack.getType() == Material.DIAMOND_SWORD) {
			return(100-(10*infusionLevel));
		}
		
		if(stack.getType() == Material.BOW) {
			if(infusionLevel <= 2) {
				return 100;
			}else {
				return(100-(10*(infusionLevel-2)));
			}
		}
		
		if(stack.getType() == Material.DIAMOND_HELMET || stack.getType() == Material.DIAMOND_CHESTPLATE || stack.getType() == Material.DIAMOND_LEGGINGS || stack.getType() == Material.DIAMOND_BOOTS) {
			return(100-(10*infusionLevel));
		}
		
		return(100-(5*infusionLevel));
	}
	
	public String getCustomMaterial(ItemStack stack) {
		return "";
	}
	
	@EventHandler
	public void onAnvilClose(InventoryCloseEvent event) {
		Inventory inv = event.getInventory();
		
		if(!inv.getName().equals(REPAIR_INVENTORY_NAME)) {
			return;
		}
		
		Location loc = inv.getViewers().get(0).getLocation();
		
		ItemStack primary = inv.getItem(1);
		
		if(primary != null) {
			loc.getWorld().dropItemNaturally(loc, primary);
		}
		
		ItemStack secondary = inv.getItem(5);
		
		if(secondary != null) {
			loc.getWorld().dropItemNaturally(loc, secondary);
		}
		
		ItemStack third = inv.getItem(6);
		
		if(third != null) {
			loc.getWorld().dropItemNaturally(loc, third);
		}
		
		ItemStack fourth = inv.getItem(7);
		
		if(fourth != null) {
			loc.getWorld().dropItemNaturally(loc, fourth);
		}
		
		
	}
	
	@EventHandler
	public void onAnvilOpen(PlayerInteractEvent event) {

		// Check if interaction block is an anvil
		
		if(event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		
		if(event.getClickedBlock().getType() == Material.ENCHANTMENT_TABLE) {
			event.setCancelled(true);
		}
		
		if (event.getClickedBlock().getType() != Material.ANVIL) {
			return;
		}

		Player player = event.getPlayer();
		ItemStack powder = new ItemStack(Material.GLOWSTONE_DUST, 64);
		ItemMeta meta = Bukkit.getItemFactory().getItemMeta(Material.SUGAR);
		meta.setDisplayName(STABLIZATION_POWDER_DISPLAY_NAME);
		powder.setItemMeta(meta);
		//player.getInventory().addItem(powder);
		

		// Open GUI

		if (player instanceof Player) {
			
			event.setCancelled(true);

			Inventory repair = Bukkit.getServer().createInventory(player, 9, REPAIR_INVENTORY_NAME);

			ItemStack repairItem = new ItemStack(Material.ANVIL);
			ItemMeta repairItemMeta = repairItem.hasItemMeta() ? repairItem.getItemMeta()
					: Bukkit.getItemFactory().getItemMeta(repairItem.getType());
			
			ItemStack bars = new ItemStack(Material.IRON_FENCE);
			ItemMeta barsMeta = bars.hasItemMeta() ? bars.getItemMeta()
					: Bukkit.getItemFactory().getItemMeta(bars.getType());
			barsMeta.setDisplayName(ChatColor.RED + "");
			bars.setItemMeta(barsMeta);
			
			ItemStack repchance = new ItemStack(Material.PAPER);
			ItemMeta repchancemeta = repchance.hasItemMeta() ? repchance.getItemMeta()
					: Bukkit.getItemFactory().getItemMeta(repchance.getType());
			repchancemeta.setDisplayName(ChatColor.BLUE + "Repair Chance");

			ArrayList<String> repLore = new ArrayList<>();
			repLore.add(ChatColor.GREEN + "Click to Recalculate");
			repchancemeta.setLore(repLore);
			
			repchance.setItemMeta(repchancemeta);

			repairItemMeta.setDisplayName(REPAIR_ITEM_NAME);
			repairItem.setItemMeta(repairItemMeta);

			ItemStack help = new ItemStack(Material.PAPER);
			ItemMeta helpMeta = help.hasItemMeta() ? help.getItemMeta()
					: Bukkit.getItemFactory().getItemMeta(Material.PAPER);
			
			helpMeta.setDisplayName(HELP_ITEM_NAME);
			
			ArrayList<String> helpLore = new ArrayList<>();
			helpLore.add(ChatColor.GREEN + "Place item and Tinker's Talc to the right");
			helpLore.add(ChatColor.GREEN + "Click 'Repair'");

			helpMeta.setLore(helpLore);

			help.setItemMeta(helpMeta);

			repair.setItem(0, repairItem);
			repair.setItem(2, bars);
			repair.setItem(3, repchance);
			repair.setItem(4, bars);
			repair.setItem(8, help);

			//player.sendMessage("You've clicked on an anvil, it should open a new gui.");
			player.openInventory(repair);
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if(!event.getInventory().getName().equals(REPAIR_INVENTORY_NAME)) {
			return;
		}
		
		if(!(event.getWhoClicked() instanceof Player)) {
			return;
		}
		
		ItemStack clickedItem = event.getCurrentItem();
		
		//Handle damaged item added to gui
		if(clickedItem != null && clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasDisplayName() && clickedItem.getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Repair Chance")) {
			
			event.setCancelled(true);
			ItemStack stack = event.getInventory().getItem(1);
			
			if(stack == null) {return;}
				int chance = calculateRepairChance(stack);
				if(event.getInventory().getItem(3).hasItemMeta()) {
					ItemMeta chanceMeta = event.getInventory().getItem(3).getItemMeta();
					ArrayList<String> repLore = new ArrayList<>();
					repLore.add(ChatColor.GREEN + "Click to Recalculate");
					repLore.add(ChatColor.GREEN + "" + chance + "%");
					chanceMeta.setLore(repLore);
					event.getInventory().getItem(3).setItemMeta(chanceMeta);
				}
		}
		
		
		
		
		// Handle repair item clicked
		if(clickedItem != null && clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasDisplayName() && clickedItem.getItemMeta().getDisplayName().equals(REPAIR_ITEM_NAME)) {
			// Cancel event
			event.setCancelled(true);
			
			// Get all the powder in the gui
			int talc = 0;
			for (int i = 5; i < event.getInventory().getSize(); i++) {
				ItemStack item = event.getInventory().getItem(i);
				if(item == null) {
					continue;
				}
				ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
				if(meta.hasDisplayName() && meta.getDisplayName().equals(STABLIZATION_POWDER_DISPLAY_NAME)) {
					talc += item.getAmount();
				}
				
			}
			
			// Remove talc
			
			for (int i = 5; i < 8; i++) {
				event.getInventory().setItem(i, null);
			}
			
		}
		
		// Handle help item clicked
		if(clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasDisplayName()) {
			if(clickedItem.getItemMeta().getDisplayName().equals(HELP_ITEM_NAME)
					|| clickedItem.getItemMeta().getDisplayName().equals(ChatColor.RED + "")
					|| clickedItem.getItemMeta().getDisplayName().equals(ChatColor.RED + ""))
			event.setCancelled(true);
		}
		
		
		
	}
}

























