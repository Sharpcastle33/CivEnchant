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

import com.gmail.sharpcastle33.util.ExperienceManager;

import org.bukkit.ChatColor;

public class EmeraldInfusionListener implements Listener {

    @EventHandler
    public void infuseEmerald(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player p = event.getPlayer();
            //p.sendMessage("click");

            if (p.getInventory().getItemInMainHand() != null && p.getInventory().getItemInMainHand().getType() == Material.EMERALD && p.getInventory().getItemInMainHand().hasItemMeta() == false) {
                p.sendMessage("click withemmy");
                if (p.getLevel() >= 1) {
                    if (p.getInventory().getItemInMainHand().getAmount() > 1) {
                        p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
                    } else {
                        p.getInventory().setItemInMainHand(null);
                    }

                    ItemStack stack = new ItemStack(Material.EMERALD, 1);
                    ItemMeta meta = Bukkit.getItemFactory().getItemMeta(Material.EMERALD);
                    meta.setDisplayName(ChatColor.YELLOW + "Infused Emerald");
                    stack.setItemMeta(meta);

                    p.getWorld().dropItem(p.getLocation(), stack);

                    //calculate xp loss
                    int l = p.getLevel();
                    float f = p.getExp();

                    if (l < 5) {
                        p.setLevel(l - 1);
                    } else if (l < 10) {
                        if (f > 0.16f) {
                            p.setExp(f - 0.16f);
                        } else {
                            p.setLevel(l - 1);
                            p.setExp(1 + f - 0.16f);
                        }
                    } else if (l < 15) {
                        if (f > 0.14f) {
                            p.setExp(f - 0.14f);
                        } else {
                            p.setLevel(l - 1);
                            p.setExp(1 + f - 0.14f);
                        }
                    } else if (l < 25) {
                        if (f > 0.12f) {
                            p.setExp(f - 0.12f);
                        } else {
                            p.setLevel(l - 1);
                            p.setExp(1 + f - 0.12f);
                        }
                    } else {
                        if (f > 0.1f) {
                            p.setExp(f - 0.1f);
                        } else {
                            p.setLevel(l - 1);
                            p.setExp(1 + f - 0.1f);
                        }
                    }

                } else {
                    p.sendMessage(ChatColor.RED + "You do not have enough experience to create an infused emerald.");
                }
            }
        }

    }

}
