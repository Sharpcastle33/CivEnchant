package com.gmail.sharpcastle33.listeners;

import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.codingforcookies.armorequip.ArmorEquipEvent;
import com.gmail.sharpcastle33.CivEnchant;
import com.gmail.sharpcastle33.enchantments.CustomEnchantment;
import com.gmail.sharpcastle33.enchantments.CustomEnchantmentManager;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

// NEED ARMOREQUIPEVENT DEPENDENCY AND IMPORT
public class ArmorEquipListener implements Listener {

    @EventHandler
    public void onArmorEquip(ArmorEquipEvent event) {
        ItemStack newArmor;
        ItemStack oldArmor;
        Player player = event.getPlayer();
        Map<CustomEnchantment, Integer> enchants;

        if (event.getNewArmorPiece() != null && event.getNewArmorPiece().getType() != Material.AIR) { // Equip

            newArmor = event.getNewArmorPiece();

            if (newArmor.hasItemMeta()) {

                enchants = CustomEnchantmentManager.getCustomEnchantments(newArmor);

                if (enchants.containsKey(CustomEnchantment.VIGOR)) {

                    player.setMaxHealth(player.getMaxHealth() + ((int) enchants.get(CustomEnchantment.VIGOR) * 0.25));

                }

                if (enchants.containsKey(CustomEnchantment.VITALITY)) {

                    if (!CivEnchant.cdManager.vitalityPlayers.contains(player)) {

                        CivEnchant.cdManager.addRegen(player, (int) enchants.get(CustomEnchantment.VITALITY));

                    } else {

                        int index = CivEnchant.cdManager.vitalityPlayers.indexOf(player);

                        CivEnchant.cdManager.vitalityEffects.get(index).addLevels((int) enchants.get(CustomEnchantment.VITALITY));

                    }

                }
            }

        } // Equip end
        else if (event.getOldArmorPiece() != null && event.getOldArmorPiece().getType() != Material.AIR) { // unequip

            oldArmor = event.getOldArmorPiece();

            enchants = CustomEnchantmentManager.getCustomEnchantments(oldArmor);

            if (enchants.containsKey(CustomEnchantment.VIGOR)) {

                player.setMaxHealth(player.getMaxHealth() - ((int) enchants.get(CustomEnchantment.VIGOR) * 0.25));

            }

            if (enchants.containsKey(CustomEnchantment.VITALITY)) {

                int index = CivEnchant.cdManager.vitalityPlayers.indexOf(player);

                CivEnchant.cdManager.vitalityEffects.get(index).removeLevels((int) enchants.get(CustomEnchantment.VITALITY));

            }

        }

    }

    // Add armor affects for joiners
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        for (ItemStack newArmor : player.getInventory().getArmorContents()) {

            if (newArmor != null && newArmor.hasItemMeta()) {

                Map<CustomEnchantment, Integer> enchants = CustomEnchantmentManager.getCustomEnchantments(newArmor);

                if (enchants.containsKey(CustomEnchantment.VITALITY)) {

                    if (!CivEnchant.cdManager.vitalityPlayers.contains(player)) {

                        CivEnchant.cdManager.addRegen(player, (int) enchants.get(CustomEnchantment.VITALITY));

                    } else {

                        int index = CivEnchant.cdManager.vitalityPlayers.indexOf(player);

                        CivEnchant.cdManager.vitalityEffects.get(index).addLevels((int) enchants.get(CustomEnchantment.VITALITY));

                    }

                }
            }

        }

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        for (ItemStack newArmor : player.getInventory().getArmorContents()) {

            if (newArmor != null && newArmor.hasItemMeta()) {
                Map<CustomEnchantment, Integer> enchants = CustomEnchantmentManager.getCustomEnchantments(newArmor);

                if (enchants.containsKey(CustomEnchantment.VITALITY)) {

                    int index = CivEnchant.cdManager.vitalityPlayers.indexOf(player);

                    CivEnchant.cdManager.vitalityEffects.get(index).setLevel(0);

                }
            }

        }

    }

}
