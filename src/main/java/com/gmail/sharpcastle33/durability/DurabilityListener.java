package com.gmail.sharpcastle33.durability;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

public class DurabilityListener extends DurabilityManager implements Listener {

    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent event) {
        //Bukkit.getServer().getLogger().info("hey hi hello dura");
        if (!(event.isCancelled())) {
            ItemStack item = event.getItem();
            String lore = getDurabilityLore(item);
            //Bukkit.getServer().getLogger().info("Lore: " + lore);			
            if (lore != null) {
                List<Integer> dur = getDurability(lore);
                int cur = dur.get(0);
                int max = dur.get(1);
                setNewDurability(item, cur - event.getDamage(), max);
            }
        }
    }
}
