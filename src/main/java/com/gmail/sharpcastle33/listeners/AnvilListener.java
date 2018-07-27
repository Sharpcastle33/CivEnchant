package com.gmail.sharpcastle33.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.sharpcastle33.repair.ItemRepair;

public class AnvilListener implements Listener {

	public static final String REPAIR_INVENTORY_NAME = "Item Repair";
	public static final String REPAIR_ITEM_NAME = "Repair";
	public static final String HELP_ITEM_NAME = "Help";
	public static final String STABLIZATION_POWDER_DISPLAY_NAME = "Stabilization Powder";

	@EventHandler
	public void onAnvilOpen(PlayerInteractEvent event) {

		// Check if interaction block is an anvil
		if (event.getClickedBlock().getType() != Material.ANVIL) {
			return;
		}

		Player player = event.getPlayer();
		ItemStack powder = new ItemStack(Material.SUGAR, 64);
		ItemMeta meta = Bukkit.getItemFactory().getItemMeta(Material.SUGAR);
		meta.setDisplayName(STABLIZATION_POWDER_DISPLAY_NAME);
		powder.setItemMeta(meta);
		player.getInventory().addItem(powder);
		

		ItemStack pick = new ItemStack(Material.DIAMOND_PICKAXE, 1);
		meta = Bukkit.getItemFactory().getItemMeta(Material.DIAMOND_PICKAXE);
		List<String> lore = new ArrayList<String>();
		lore.add("Infusion 1");
		meta.setLore(lore);
		pick.setItemMeta(meta);
		player.getInventory().addItem(pick);

		// Open GUI

		if (player instanceof Player) {
			
			event.setCancelled(true);

			Inventory repair = Bukkit.getServer().createInventory(player, 9, REPAIR_INVENTORY_NAME);

			ItemStack repairItem = new ItemStack(Material.ANVIL);
			ItemMeta repairItemMeta = repairItem.hasItemMeta() ? repairItem.getItemMeta()
					: Bukkit.getItemFactory().getItemMeta(repairItem.getType());

			repairItemMeta.setDisplayName(REPAIR_ITEM_NAME);
			repairItem.setItemMeta(repairItemMeta);

			ItemStack help = new ItemStack(Material.PAPER);
			ItemMeta helpMeta = help.hasItemMeta() ? help.getItemMeta()
					: Bukkit.getItemFactory().getItemMeta(Material.PAPER);
			
			helpMeta.setDisplayName(HELP_ITEM_NAME);
			
			ArrayList<String> helpLore = new ArrayList<>();
			helpLore.add(ChatColor.GREEN + "Place item and stablization powder to the right");
			helpLore.add(ChatColor.GREEN + "Click 'Repair'");

			helpMeta.setLore(helpLore);

			help.setItemMeta(helpMeta);

			repair.setItem(0, repairItem);
			repair.setItem(1, help);

			player.sendMessage("You've clicked on an anvil, it should open a new gui.");
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
		
		// Handle repair item clicked
		if(clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasDisplayName() && clickedItem.getItemMeta().getDisplayName().equals(REPAIR_ITEM_NAME)) {
			// Cancel event
			event.setCancelled(true);
			
			// Get all the powder in the gui
			List<ItemStack> powder = new ArrayList<ItemStack>();
			for (int i = 0; i < event.getInventory().getSize(); i++) {
				ItemStack item = event.getInventory().getItem(i);
				if(item == null) {
					continue;
				}
				ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
				if(meta.hasDisplayName() && meta.getDisplayName().equals(STABLIZATION_POWDER_DISPLAY_NAME)) {
					powder.add(item);
				}
				
			}
			
			// Init repairer
			ItemRepair repair = new ItemRepair((Player)event.getWhoClicked(), event.getInventory().getItem(2), powder);
			ItemStack anviledItem = repair.repair();
			
			for (int i = 2; i < event.getInventory().getSize(); i++) {
				event.getInventory().setItem(i, null);
			}
			
			if(anviledItem != null) {
				event.getInventory().setItem(2, anviledItem);
			}
		}
		
		// Handle help item clicked
		if(clickedItem.hasItemMeta() && clickedItem.getItemMeta().hasDisplayName() && clickedItem.getItemMeta().getDisplayName().equals(HELP_ITEM_NAME)) {
			event.setCancelled(true);
		}
		
		
		
	}
}

























