package com.gmail.sharpcastle33.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.codingforcookies.armorequip.ArmorEquipEvent;
import com.gmail.sharpcastle33.CivEnchant;
import com.gmail.sharpcastle33.util.CONSTANTS;

public class SetBonusListener implements Listener {

    private static String donned = ChatColor.GREEN + "You have donned full ";
    private static String doffed = ChatColor.RED + "You have doffed full ";
    private static String crim = ChatColor.BLUE + "Crimson Steel";
    private static String soul = ChatColor.BLUE + "Soulsteel";

    public SetBonusListener() {

    }

    @EventHandler
    public void crimDamageReduction(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player p = (Player) event.getEntity();
            if (CivEnchant.adamantSet.contains(p.getName())) {
                event.setDamage(Math.max(event.getDamage() - CONSTANTS.CRIMSON_DAMAGE_REDUCTION, 0));
                //p.sendMessage("Damage reduced");
            }
        }
    }

    @EventHandler
    public void soulDamageIncrease(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Arrow) {
            Arrow arr = (Arrow) event.getDamager();
            if (arr.getShooter() instanceof Player) {
                Player p = (Player) arr.getShooter();
                if (CivEnchant.soulSet.contains(p.getName())) {
                    event.setDamage(event.getDamage() * CONSTANTS.D_SOUL_DAMAGE_INCREASE_MULTIPLIER);
                }
            }
        }
    }

    @EventHandler
    public void soulVelocityIncrease(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player) {
            Player p = (Player) event.getEntity();
            if (CivEnchant.soulSet.contains(p.getName())) {
                Entity arr = event.getProjectile();
                arr.setVelocity(arr.getVelocity().multiply(CONSTANTS.D_SOUL_VELOCITY_INCREASE_MULTIPLIER));
            }
        }
    }

    @EventHandler
    public void checkSet(ArmorEquipEvent event) {
        Player p = event.getPlayer();

        if (p == null || p.getName() == null) {
            return;
        }

        ItemStack[] set = p.getInventory().getArmorContents();

        ItemStack newItem = event.getNewArmorPiece();
        ItemStack oldItem = event.getOldArmorPiece();

        //Bukkit.getServer().getLogger().info("armorequip" + CivEnchant.adamantSet + "___" + CivEnchant.soulSet);
        //If they had a set, check if they removed it
        if (CivEnchant.adamantSet.contains(p.getName())) {
            if (newItem == null || !newItem.hasItemMeta() || !newItem.getItemMeta().hasLore()) {
                Bukkit.getServer().getLogger().info("Player: " + p.getName() + " has doffed their Crimson Steel.");
                p.sendMessage(doffed + crim);
                CivEnchant.adamantSet.remove(p.getName());
                return;
            }

            Boolean hasMaterial = false;
            for (String str : newItem.getItemMeta().getLore()) {
                if (str.contains("Material: Crimson Steel")) {
                    hasMaterial = true;
                }
            }

            if (hasMaterial == false) {
                Bukkit.getServer().getLogger().info("Player: " + p.getName() + " has doffed their Crimson Steel.");
                p.sendMessage(doffed + crim);
                CivEnchant.adamantSet.remove(p.getName());
            }
            return;
        }

        if (CivEnchant.soulSet.contains(p.getName())) {
            if (newItem == null || !newItem.hasItemMeta() || !newItem.getItemMeta().hasLore()) {
                CivEnchant.soulSet.remove(p.getName());
                p.sendMessage(doffed + soul);
                Bukkit.getServer().getLogger().info("Player: " + p.getName() + " has doffed their Soulsteel.");
                return;
            }

            Boolean hasMaterial = false;
            for (String str : newItem.getItemMeta().getLore()) {
                if (str.contains("Material: Soulsteel")) {
                    hasMaterial = true;
                }
            }

            if (hasMaterial == false) {
                CivEnchant.soulSet.remove(p.getName());
                p.sendMessage(doffed + soul);
                Bukkit.getServer().getLogger().info("Player: " + p.getName() + " has doffed their Soulsteel.");
            }
            return;
        }

        //If they didn't have a set, check if they donned one.
        if (newItem == null || !newItem.hasItemMeta() || !newItem.getItemMeta().hasLore()) {
            return;
        }

        String typeString = "";
        for (ItemStack s : set) {

            if (s == null || !s.hasItemMeta() || !s.getItemMeta().hasLore()) {
                continue;
            }

            ItemMeta meta = s.getItemMeta();
            if (meta.hasLore()) {
                for (String str : meta.getLore()) {
                    if (str.contains("Material: Crimson Steel")) {
                        typeString = typeString + "cs";
                    }

                    if (str.contains("Material: Soulsteel")) {
                        typeString = typeString + "ss";
                    }
                }
            }
        }

        if (oldItem != null && oldItem.hasItemMeta() && oldItem.getItemMeta().hasLore()) {
            ItemMeta meta = oldItem.getItemMeta();
            if (meta.hasLore()) {
                for (String str : meta.getLore()) {
                    if (str.contains("Material: Crimson Steel")) {
                        typeString.substring(0, typeString.length() - 2);
                    }

                    if (str.contains("Material: Soulsteel")) {
                        typeString.substring(0, typeString.length() - 2);
                    }
                }
            }
        }

        ItemMeta meta = newItem.getItemMeta();
        for (String str : meta.getLore()) {
            if (str.contains("Material: Crimson Steel")) {
                typeString = typeString + "cs";
            }

            if (str.contains("Material: Soulsteel")) {
                typeString = typeString + "ss";
            }
        }

        if (typeString.equals("cscscscs")) {
            CivEnchant.adamantSet.add(p.getName());
            Bukkit.getServer().getLogger().info("Player: " + p.getName() + " has donned full Crimson Steel.");
            p.sendMessage(donned + crim);
            return;
        }

        if (typeString.equals("ssssssss")) {
            p.sendMessage(donned + soul);
            CivEnchant.soulSet.add(p.getName());
            Bukkit.getServer().getLogger().info("Player: " + p.getName() + " has donned full Soulsteel.");
            return;
        }
    }

    /*//Bukkit.getServer().getLogger().info("armor equipped");
		//p.sendMessage("armor equipped");
		
		String type = "";
		for(ItemStack s : set) {
			
			if(oldItem != null && s != null && s.isSimilar(oldItem)) { s = newItem; }
			
			if(s != null && s.hasItemMeta()) {
				ItemMeta meta = s.getItemMeta();
				if(meta.hasLore()) {
					for(String str : meta.getLore()) {
						//Bukkit.getServer().getLogger().info("Lore line: " + str);
						if(str.contains("Material: Crimson Steel")) {
							type = type + "cs";
						}
						
						if(str.contains("Material: Soulsteel")) {
							type = type + "ss";
						}
					}
				}
			}
		}
		
		Bukkit.getServer().getLogger().info("Type: " + type);
		
		if(type.equals("cscscscs")) {
			CivEnchant.adamantSet.add(p.getName());
			Bukkit.getServer().getLogger().info("Player: " + p.getName() + " has donned full Crimson Steel.");
			return;
		}
		
		if(CivEnchant.adamantSet.contains(p.getName())) {
			CivEnchant.adamantSet.remove(p.getName());
			Bukkit.getServer().getLogger().info("Player: " + p.getName() + " has doffed their Crimson Steel.");
			return;
		}
			
		if(type.equals("ssssssss")) {
			CivEnchant.soulSet.add(p.getName());
			Bukkit.getServer().getLogger().info("Player: " + p.getName() + " has donned full Soulsteel.");
			return;
		}
		
		if(CivEnchant.soulSet.contains(p.getName())) {
			CivEnchant.soulSet.remove(p.getName());
			Bukkit.getServer().getLogger().info("Player: " + p.getName() + " has doffed their Soulsteel.");
			return;
		}*/
}
