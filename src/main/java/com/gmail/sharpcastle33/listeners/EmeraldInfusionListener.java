package com.gmail.sharpcastle33.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class EmeraldInfusionListener implements Listener{
	
	@EventHandler
	public void infuseEmerald(PlayerInteractEvent event) {
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player p = event.getPlayer();
			//p.sendMessage("click");

			if(p.getInventory().getItemInMainHand() != null && p.getInventory().getItemInMainHand().getType() == Material.EMERALD && p.getInventory().getItemInMainHand().hasItemMeta() == false) {
				p.sendMessage("click withemmy");
				if(p.getTotalExperience() >= 7) {
					if(p.getInventory().getItemInMainHand().getAmount() > 1) {
						p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-1);
					}else { p.getInventory().setItemInMainHand(null); }
					
					ItemStack stack = new ItemStack(Material.EMERALD, 1);
					ItemMeta meta = Bukkit.getItemFactory().getItemMeta(Material.EMERALD);
					meta.setDisplayName(ChatColor.YELLOW + "Infused Emerald");
					stack.setItemMeta(meta);
					
					p.getWorld().dropItem(p.getLocation(), stack);
					p.giveExp(-7);
					p.sendMessage("success");
				}else { p.sendMessage("not enough xp, " + p.getTotalExperience()); }
			}
		}
			
	}

}
