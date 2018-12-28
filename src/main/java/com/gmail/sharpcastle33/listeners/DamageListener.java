package com.gmail.sharpcastle33.listeners;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import com.gmail.sharpcastle33.CivEnchant;
import com.gmail.sharpcastle33.enchantments.CustomEnchantment;
import com.gmail.sharpcastle33.enchantments.CustomEnchantmentManager;
import com.gmail.sharpcastle33.util.CONSTANTS;
import com.gmail.sharpcastle33.util.ParticlePlayer;
import com.gmail.sharpcastle33.util.RageEffect;
import com.gmail.sharpcastle33.util.Util;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Horse;

public class DamageListener implements Listener {

    final private CivEnchant plugin = CivEnchant.plugin;
    final private Random rand = new Random();

    ArrayList<Biome> survivalistBiomes;

    @EventHandler
    public void calculateDamage(EntityDamageByEntityEvent event) {
        Entity offense = event.getDamager();
        Entity defense = event.getEntity();
        double dmgFlat = 0;
        double dmgMod = 0;
        double dmgMulti = 0;
        int evadeChance = 0;
        int trueShot = 0;

        // ATTACKING PLAYER
        if (offense instanceof Player) {
            Player attacker = (Player) offense;

            if (attacker.getInventory().getItemInMainHand() != null) {
                ItemStack weapon = attacker.getInventory().getItemInMainHand();
                if (weapon.hasItemMeta()) {
                    Map<CustomEnchantment, Integer> enchants = CustomEnchantmentManager.getCustomEnchantments(weapon);

                    if (enchants.containsKey(CustomEnchantment.LIFESTEAL)) {
                        if (Util.chance(enchants.get(CustomEnchantment.LIFESTEAL), CONSTANTS.I_LIFESTEAL_CHANCE_BOUND)) {

                            attacker.setHealth(Math.min(attacker.getHealth() + CONSTANTS.D_LIFESTEAL_STOLEN_HEALTH, attacker.getMaxHealth()));

                        }
                    }
                    if (enchants.containsKey(CustomEnchantment.KNIGHT)) {
                        if (attacker.isInsideVehicle() && attacker.getVehicle() instanceof Horse) {
                            dmgFlat += enchants.get(CustomEnchantment.KNIGHT);
                        }
                    }

                    if (enchants.containsKey(CustomEnchantment.CORROSIVE)) {
                        if (defense instanceof LivingEntity) {
                            LivingEntity target = (LivingEntity) defense;
                            if (defense.getCustomName() != null) {
                                String name = defense.getCustomName();
                                if (name.startsWith(ChatColor.YELLOW + "Clockwork")
                                        || name.startsWith(ChatColor.YELLOW + "Steamwork")
                                        || name.startsWith(ChatColor.YELLOW + "Unhinged")) {
                                    int lvl = enchants.get(CustomEnchantment.CORROSIVE);
                                    dmgFlat += CONSTANTS.D_CORROSIVE_DAMAGE_MULTIPLIER * lvl;

                                }
                            }
                        }
                    }

                    if (enchants.containsKey(CustomEnchantment.PLAGUEBANE)) {
                        if (defense instanceof LivingEntity) {
                            LivingEntity target = (LivingEntity) defense;
                            if (defense.getCustomName() != null) {
                                String name = defense.getCustomName();
                                if (name.startsWith(ChatColor.YELLOW + "Plague")
                                        || name.startsWith(ChatColor.YELLOW + "Plagued")) {

                                    dmgFlat += CONSTANTS.D_PLAGUEBANE_DAMAGE_MULTIPLIER * enchants.get(CustomEnchantment.PLAGUEBANE);

                                }
                            }
                        }
                    }

                    if (enchants.containsKey(CustomEnchantment.HUNTERS_MARK)) {
                        if (defense instanceof LivingEntity) {
                            LivingEntity target = (LivingEntity) defense;

                            target.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
                                    20 * enchants.get(CustomEnchantment.HUNTERS_MARK), // Duration
                                    1)); // Amplifier

                        }
                    }

                    if (enchants.containsKey(CustomEnchantment.AQUATIC_COMBATANT)) {
                        Block b = attacker.getLocation().getBlock();
                        if (b.getType() == Material.WATER || b.getType() == Material.STATIONARY_WATER) {

                            dmgFlat += CONSTANTS.D_AQUATIC_COMBATANT_DAMAGE_MULTIPLIER * enchants.get(CustomEnchantment.AQUATIC_COMBATANT);

                            if (Util.chance(CONSTANTS.I_AQUATIC_COMBATANT_LOWER_BOUND, CONSTANTS.I_AQUATIC_COMBATANT_UPPER_BOUND)) {
                                attacker.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 20 * CONSTANTS.I_AQUATIC_COMBATANT_DURATION_SECONDS, 1));

                                ParticlePlayer.playAquaticCombatantEffect(attacker);

                            }
                        }
                    }

                    if (enchants.containsKey(CustomEnchantment.BERSERKING)) {

                        if (attacker.getHealth() <= 6) {
                            if (Util.chance(CONSTANTS.I_BERSERKING_LOWER_BOUND, CONSTANTS.I_BERSERKING_UPPER_BOUND)) {
                                dmgFlat += CONSTANTS.D_BERSERKING_DAMAGE;
                                ParticlePlayer.playBerserkingEffect(attacker);

                            } else {
                                dmgFlat += CONSTANTS.D_BERKSERING_DAMAGE_MULTIPLER * enchants.get(CustomEnchantment.BERSERKING);
                            }

                        }
                    }

                    if (enchants.containsKey(CustomEnchantment.RAGE)) {
                        if (CivEnchant.cdManager.ragePlayers.contains(attacker)) {
                            int playerIndex = CivEnchant.cdManager.ragePlayers.indexOf(attacker);

                            //Chain must be on same target...
                            if (!CivEnchant.cdManager.rageEffects.get(playerIndex).isTarget(defense)) {

                                removeRage(playerIndex);
                                // new effect on new target
                                addRage(attacker, defense);

                            } else if (CivEnchant.cdManager.rageEffects.get(playerIndex).getLevel() < CONSTANTS.I_RAGE_CHAIN_MAX_COUNTER) {

                                incrementRage(playerIndex);

                            } else {

                                dmgFlat += enchants.get(CustomEnchantment.RAGE);
                                ParticlePlayer.playRageEffect(attacker);

                                removeRage(playerIndex);
                            }
                        } else { // if first hit

                            addRage(attacker, defense);

                        }
                    }

                    if (enchants.containsKey(CustomEnchantment.HELLFIRE)) {
                        // if on fire...
                        if (defense.getFireTicks() != 0) {
                            dmgFlat += enchants.get(CustomEnchantment.HELLFIRE);
                            if (defense instanceof Player) {
                                if (((Player) defense).hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)) {
                                    Util.reducePotionDuration((Player) defense, PotionEffectType.FIRE_RESISTANCE, enchants.get(CustomEnchantment.HELLFIRE));
                                }
                            }

                        }

                    }

                }
            }
            //Horse Armor Enchants
            if (offense.getVehicle() != null && offense.getVehicle() instanceof Horse) {
                Horse mount = (Horse) offense.getVehicle();

                if (mount.getInventory().getArmor() != null) {
                    ItemStack horseArmor = mount.getInventory().getArmor();
                    Map<CustomEnchantment, Integer> enchants = CustomEnchantmentManager.getCustomEnchantments(horseArmor);

                    if (enchants.containsKey(CustomEnchantment.CHARGE) && ((Player) offense).isSprinting()) {
                        // speed is random value between 0.1125 and 0.3375
                        double speed = mount.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue();
                        dmgFlat += ((int) (speed * 10)) + enchants.get(CustomEnchantment.CHARGE);

                    }

                }

            }
            if (defense instanceof Horse) {
                Horse mount = (Horse) defense;
                if (!mount.getPassengers().isEmpty()) {
                    ItemStack horseArmor = mount.getInventory().getArmor();
                    Map<CustomEnchantment, Integer> enchants = CustomEnchantmentManager.getCustomEnchantments(horseArmor);

                    if (enchants.containsKey(CustomEnchantment.WARHORSE)) {
                        dmgFlat -= (enchants.get(CustomEnchantment.WARHORSE) * CONSTANTS.D_WARHORSE_DAMAGE_REDUCTION);
                    }
                }

            }

            //Added damage against glowing enemies HUNTER's MARK
            if (((LivingEntity) defense).hasPotionEffect(PotionEffectType.GLOWING)) {

                dmgMod += CONSTANTS.D_HUNTERS_MARK_DAMAGE;

            }

        }

        // DEFENDING PLAYER
        if (defense instanceof Player) {
            Player defender = (Player) defense;
            ItemStack[] armor = defender.getInventory().getArmorContents();

            // Break rage combo chain
            if (CivEnchant.cdManager.ragePlayers.contains(defender)) {
                int playerIndex = CivEnchant.cdManager.ragePlayers.indexOf(defender);

                removeRage(playerIndex);
            }

            for (ItemStack stack : armor) {
                if (stack != null && stack.hasItemMeta()) {
                    Map<CustomEnchantment, Integer> enchants = CustomEnchantmentManager.getCustomEnchantments(stack);

                    if (enchants.containsKey(CustomEnchantment.EVASIVE)) {

                        // Accumulate levels of evasion for each armor piece
                        // (Maximum of 12 evasion (lvl 3 ench on 4 pieces of armor))
                        evadeChance += enchants.get(CustomEnchantment.EVASIVE);

                    }

                    if (enchants.containsKey(CustomEnchantment.ENDURANCE)) {

                        dmgFlat -= (enchants.get(CustomEnchantment.ENDURANCE) * CONSTANTS.D_ENDURANCE_DAMAGE_REDUCTION);
                        if (dmgFlat < 0) {
                            dmgFlat = 0; // Avoid attacks healing people
                        }
                    }

                    if (enchants.containsKey(CustomEnchantment.SURVIVALIST)) {
                        Biome b = defender.getLocation().getWorld().getBiome(defender.getLocation().getBlockX(), defender.getLocation().getBlockZ());
                        if (survivalistBiomes.contains(b)) {
                            dmgFlat -= (enchants.get(CustomEnchantment.SURVIVALIST) * CONSTANTS.D_SURVIVALIST_DAMAGE_REDUCTION_MULTIPLIER);
                        }

                    }

                    if (enchants.containsKey(CustomEnchantment.SECOND_WIND)) {

                        if (defender.getHealth() - event.getDamage() < 4 && defender.getHealth() > 4) { // Does not account for ench dmg change
                            if (!CivEnchant.cdManager.secondWind.contains(defender)) { // if SW is off CD
                                defender.sendMessage("Second Wind");
                                CivEnchant.cdManager.add(defender, CustomEnchantment.SECOND_WIND, CONSTANTS.I_SECOND_WIND_COOLDOWN_DURATION_SECONDS);
                                Util.replacePotionEffect(defender,
                                        new PotionEffect(PotionEffectType.REGENERATION, 20 * CONSTANTS.I_SECOND_WIND_DURATION_SECONDS, 1));
                                Util.replacePotionEffect(defender,
                                        new PotionEffect(PotionEffectType.SPEED, 20 * CONSTANTS.I_SECOND_WIND_DURATION_SECONDS, 1));

                            }
                        }
                    }

                    if (enchants.containsKey(CustomEnchantment.LAST_STAND)) {

                        if (defender.getHealth() - event.getDamage() < 2 && defender.getHealth() > 2) { // Does not account for ench dmg change

                            if (!CivEnchant.cdManager.lastStand.contains(defender)) { // if SW is off CD

                                CivEnchant.cdManager.add(defender, CustomEnchantment.LAST_STAND, CONSTANTS.I_LAST_STAND_COOLDOWN_DURATION_SECONDS);

                                Util.replacePotionEffect(defender,
                                        new PotionEffect(PotionEffectType.REGENERATION, 20 * CONSTANTS.I_LAST_STAND_DURATION_SECONDS, 1));

                                Util.replacePotionEffect(defender,
                                        new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * CONSTANTS.I_LAST_STAND_DURATION_SECONDS, 1));

                                ParticlePlayer.playLastStandEffect(defender);
                            }
                        }

                    }

                    if (enchants.containsKey(CustomEnchantment.DIVINE_INTERVENTION)) {

                        if (defender.getHealth() - event.getDamage() < 2 && defender.getHealth() > 2) { // Does not account for ench dmg change

                            if (!CivEnchant.cdManager.divineIntervention.contains(defender)) { // if SW is off CD
                                int lvl = enchants.get(CustomEnchantment.DIVINE_INTERVENTION);
                                CivEnchant.cdManager.add(defender, CustomEnchantment.DIVINE_INTERVENTION, CONSTANTS.I_DIVINE_INTERVENTION_COOLDOWN_DURATION_SECONDS);

                                Util.replacePotionEffect(defender,
                                        new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * (2 + lvl), 4));

                                Util.replacePotionEffect(defender,
                                        new PotionEffect(PotionEffectType.REGENERATION, 20 * 5 + (20 * 2 * lvl), 3));

                                ParticlePlayer.playDIEffect(defender);
                            }
                        }

                    }

                    if (enchants.containsKey(CustomEnchantment.ADRENALINE)) {

                        if (defender.getHealth() - event.getDamage() < 4 && defender.getHealth() > 4) { // Does not account for ench dmg change

                            if (!CivEnchant.cdManager.adrenaline.contains(defender)) { // if adrenaline is off CD

                                CivEnchant.cdManager.add(defender, CustomEnchantment.ADRENALINE, CONSTANTS.I_ADRENALINE_COOLDOWN_DURATION_SECONDS);
                                Util.replacePotionEffect(defender, new PotionEffect(PotionEffectType.SPEED, 20 * CONSTANTS.I_ADRENALINE_DURATION_SECONDS, 3));
                                ParticlePlayer.playAdrenalineEffect(defender);
                            }
                        }

                    }

                }
            }

        }

        // PLAYER VS PLAYER
        if (offense instanceof Player && defense instanceof Player) {
            Player attacker = (Player) offense;
            Player defender = (Player) defense;

            if (attacker.getInventory().getItemInMainHand() != null) {
                ItemStack weapon = attacker.getInventory().getItemInMainHand();
                if (weapon.hasItemMeta()) {
                    Map<CustomEnchantment, Integer> enchants = CustomEnchantmentManager.getCustomEnchantments(weapon);

                    // CORROSIVE
                    if (enchants.containsKey(CustomEnchantment.CORROSIVE)) {
                        /*   TODO
                        int roll = rand.nextInt(100) + 1;

                        if (roll < 5) { // Max lvl 1/20 chance of corrosive hit
                            ItemStack[] armor = defender.getInventory().getArmorContents();

                            for (ItemStack stack : armor) {
                                if (stack != null && stack.hasItemMeta()) {
                                    // TODO fix corrode
                                    // double corrode = enchants.get(CustomEnchantment.CORROSIVE) * 0.03; // Each
                                    // lvl corrodes 3%
                                    // stack.setDurability(stack.getDurability() - (stack.getDurability() *
                                    // corrode));
                                    //defender.sendMessage(
                                    //attacker.getName() + "'s weapon has corroded your armor! WIP NO EFFECT");

                                    defender.spawnParticle(Particle.VILLAGER_ANGRY, defense.getLocation().getX(),
											defense.getLocation().getY(), defense.getLocation().getZ(), 2);
                                     
                                }
                            }
                        }
                         */
                    } // Corrosive end
                }
            }

        }

        // PLAYER SHOT BY ARROW
        if (offense instanceof Arrow) {

            Arrow arrow = (Arrow) offense;

            if (arrow.getShooter() instanceof Player) {

                Player shooter = (Player) arrow.getShooter();

                double finalDistance = shooter.getLocation().distance(defense.getLocation());
                if (arrow.getName().contains("trueshot")) {
                    trueShot = 1;
                }

                if (arrow.getName().contains("farshot1")) {
                    if (finalDistance > CONSTANTS.I_FAR_SHOT_MAX_DISTANCE_BLOCKS) {
                        dmgFlat = dmgFlat + 4;
                    } else if (finalDistance > CONSTANTS.I_FAR_SHOT_MED_DISTANCE_BLOCKS && finalDistance < CONSTANTS.I_FAR_SHOT_MAX_DISTANCE_BLOCKS) {
                        dmgFlat = dmgFlat + 3;
                    } else if (finalDistance > CONSTANTS.I_FAR_SHOT_MIN_DISTANCE_BLOCKS && finalDistance < CONSTANTS.I_FAR_SHOT_MED_DISTANCE_BLOCKS) {
                        dmgFlat = dmgFlat + 1.5;
                    }
                }

                if (arrow.getName().contains("farshot2")) {
                    if (finalDistance > CONSTANTS.I_FAR_SHOT_MAX_DISTANCE_BLOCKS) {
                        dmgFlat = dmgFlat + 6;
                    } else if (finalDistance > CONSTANTS.I_FAR_SHOT_MED_DISTANCE_BLOCKS && finalDistance < CONSTANTS.I_FAR_SHOT_MAX_DISTANCE_BLOCKS) {
                        dmgFlat = dmgFlat + 4;
                    } else if (finalDistance > CONSTANTS.I_FAR_SHOT_MIN_DISTANCE_BLOCKS && finalDistance < CONSTANTS.I_FAR_SHOT_MED_DISTANCE_BLOCKS) {
                        dmgFlat = dmgFlat + 2;
                    }
                }

                if (arrow.getName().contains("pointblank1")) {

                    if (finalDistance > CONSTANTS.I_POINT_BLANK_MED_DISTANCE_BLOCKS && finalDistance < CONSTANTS.I_POINT_BLANK_MAX_DISTANCE_BLOCKS) {

                        dmgFlat = dmgFlat + 1;
                    } else if (finalDistance > CONSTANTS.I_POINT_BLANK_MIN_DISTANCE_BLOCKS && finalDistance < CONSTANTS.I_POINT_BLANK_MED_DISTANCE_BLOCKS) {

                        dmgFlat = dmgFlat + 2;

                    } else if (finalDistance < CONSTANTS.I_POINT_BLANK_MIN_DISTANCE_BLOCKS) {

                        dmgFlat = dmgFlat + 3;
                    }
                }

                if (arrow.getName().contains("pointblank2")) {

                    if (finalDistance > CONSTANTS.I_POINT_BLANK_MED_DISTANCE_BLOCKS && finalDistance < CONSTANTS.I_POINT_BLANK_MAX_DISTANCE_BLOCKS) {

                        dmgFlat = dmgFlat + 2;
                    } else if (finalDistance > CONSTANTS.I_POINT_BLANK_MIN_DISTANCE_BLOCKS && finalDistance < CONSTANTS.I_POINT_BLANK_MED_DISTANCE_BLOCKS) {

                        dmgFlat = dmgFlat + 3;

                    } else if (finalDistance < CONSTANTS.I_POINT_BLANK_MIN_DISTANCE_BLOCKS) {

                        dmgFlat = dmgFlat + 4;
                    }
                }

                if (arrow.getName().contains("huntersmark1")) {
                    if (defense instanceof LivingEntity) {
                        LivingEntity target = (LivingEntity) defense;

                        target.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
                                20 + (20 * CONSTANTS.I_BASE_HUNTERS_MARK_SECONDS), // Duration
                                1)); // Amplifier
                    }
                }
                if (arrow.getName().contains("huntersmark2")) {
                    if (defense instanceof LivingEntity) {
                        LivingEntity target = (LivingEntity) defense;

                        target.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
                                20 * 2 + (20 * CONSTANTS.I_BASE_HUNTERS_MARK_SECONDS), // Duration
                                1)); // Amplifier
                    }
                }
                if (arrow.getName().contains("huntersmark3")) {
                    if (defense instanceof LivingEntity) {
                        LivingEntity target = (LivingEntity) defense;

                        target.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
                                20 * 3 + (20 * CONSTANTS.I_BASE_HUNTERS_MARK_SECONDS), // Duration
                                1)); // Amplifier
                    }
                }
            }
        }

        // Calculate chance to evade
        int roll = rand.nextInt(CONSTANTS.I_EVASION_CHANCE_BOUND) + 1; // Roll between 1-100 ## CHANGE THIS TO CHANGE PROBABILITY OF EVADE
        int evade = 1; // 1 is no evade, 0 is successful evade (for calculating finalDamage below)
        int enduredDamage = 0;

        if (defense instanceof Player) {

            Player defender = (Player) defense;

            if (roll <= evadeChance) {
                // successful evasion, but if trueshot = 1, evasion is stopped
                evade = 0 + trueShot;
                if (trueShot == 0) {

                    ParticlePlayer.playEvadeEffect(defender);

                }

            }
        }

        double finalDamage = ((event.getDamage() + dmgFlat) * (1 + dmgMod) * (1 + dmgMulti)) * evade + trueShot
                - enduredDamage;
        event.setDamage(finalDamage);

    }

    @EventHandler
    public void onArrowShoot(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player) {

            Player p = (Player) event.getEntity();
            ItemStack bow = event.getBow();
            Entity arrow = event.getProjectile();

            if (event.getForce() > 0.8) {

                if (bow.hasItemMeta()) {

                    Map<CustomEnchantment, Integer> enchants = CustomEnchantmentManager.getCustomEnchantments(bow);

                    if (enchants.containsKey(CustomEnchantment.FAR_SHOT)) {
                        // Might want to try doing this with metadata instead for future compatibility.
                        arrow.setCustomName(arrow.getName() + "farshot" + enchants.get(CustomEnchantment.FAR_SHOT));

                    }

                    if (enchants.containsKey(CustomEnchantment.POINT_BLANK)) {

                        arrow.setCustomName(
                                arrow.getName() + "pointblank" + enchants.get(CustomEnchantment.POINT_BLANK));

                    }

                    if (enchants.containsKey(CustomEnchantment.TRUE_SHOT)) {

                        arrow.setCustomName(arrow.getName() + "trueshot");

                    }

                    if (enchants.containsKey(CustomEnchantment.HUNTERS_MARK)) {
                        // Might want to try doing this with metadata instead for future compatibility.
                        arrow.setCustomName(arrow.getName() + "huntersmark" + enchants.get(CustomEnchantment.HUNTERS_MARK));

                    }

                    if (enchants.containsKey(CustomEnchantment.CRIPPLING)) {

                        arrow.setCustomName(arrow.getName() + "crippling");

                    }

                    if (enchants.containsKey(CustomEnchantment.MULTISHOT)) {
                        Arrow a1 = p.launchProjectile(Arrow.class);

                        a1.setVelocity(a1.getVelocity().add(new Vector(0, 2, 0)));

                        Arrow b1 = p.launchProjectile(Arrow.class);

                        b1.setVelocity(a1.getVelocity().add(new Vector(0, -2, 0)));

                    }
                }
            }
            //Horse Armor Enchants
            if (p.getVehicle() != null && p.getVehicle() instanceof Horse) {
                Horse mount = (Horse) p.getVehicle();

                if (mount.getInventory().getArmor() != null) {
                    ItemStack horseArmor = mount.getInventory().getArmor();
                    Map<CustomEnchantment, Integer> enchants = CustomEnchantmentManager.getCustomEnchantments(horseArmor);

                    if (enchants.containsKey(CustomEnchantment.HORSE_ARCHERY)) {
                        switch (enchants.get(CustomEnchantment.HORSE_ARCHERY)) {

                            case 1:
                                if (event.getForce() > 0.7) {
                                    event.getProjectile().setVelocity(p.getLocation().getDirection().multiply(3.0D));
                                }
                                break;
                            case 2:
                                if (event.getForce() > 0.5) {
                                    event.getProjectile().setVelocity(p.getLocation().getDirection().multiply(3.0D));
                                }
                                break;
                            case 3:
                                if (event.getForce() > 0.3 && event.getForce() < 0.7) {
                                    event.getProjectile().setVelocity(p.getLocation().getDirection().multiply(3.0D));
                                } else if (event.getForce() > 0.7) {
                                    event.getProjectile().setVelocity(p.getLocation().getDirection().multiply(3.5D));
                                }
                                break;
                            default:
                        }

                    }

                }

            }

        }

    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        // SOUL TAKER
        Player killed = event.getEntity();
        Entity entityKiller = event.getEntity().getKiller();

        if (entityKiller instanceof Player) {

            Player killer = (Player) entityKiller;

            if (killer.getInventory().getItemInMainHand() != null) {
                ItemStack weapon = killer.getInventory().getItemInMainHand();

                if (weapon.hasItemMeta()) {
                    Map<CustomEnchantment, Integer> enchants = CustomEnchantmentManager.getCustomEnchantments(weapon);

                    if (enchants.containsKey(CustomEnchantment.SOUL_TAKER)) {

                        if (killed.getBedSpawnLocation() != null) {

                            killed.sendMessage("You have forgotten your bed!");
                            killed.setBedSpawnLocation(null);

                        }

                    }
                }
            }
        }

    }

    @EventHandler
    public void onLivingEntityDeath(EntityDeathEvent event) {

        LivingEntity killed = event.getEntity();
        Entity entityKiller = event.getEntity().getKiller();
        ItemStack headDrop = null;

        // HUNTERS BLESSING
        if (killed instanceof Animals && entityKiller instanceof Player) {

            Player killer = (Player) entityKiller;

            if (killer.getInventory().getItemInMainHand() != null) {
                ItemStack weapon = killer.getInventory().getItemInMainHand();

                if (weapon.hasItemMeta()) {
                    Map<CustomEnchantment, Integer> enchants = CustomEnchantmentManager.getCustomEnchantments(weapon);

                    if (enchants.containsKey(CustomEnchantment.HUNTERS_BLESSING)) {
                        int roll = rand.nextInt(CONSTANTS.I_HUNTERS_BLESSING_CHANCE_BOUND) + 1 / enchants.get(CustomEnchantment.HUNTERS_BLESSING);
                        if (roll == 10) { // 1/60 chance on lvl 1, 1/30 on lvl 2, 1/15 on lvl 3
                            for (ItemStack drop : event.getDrops()) {

                                switch (drop.getType()) {
                                    case PORK:
                                        event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(),
                                                new ItemStack(Material.PORK,
                                                        enchants.get(CustomEnchantment.HUNTERS_BLESSING)));
                                        break;
                                    case RAW_BEEF:
                                        event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(),
                                                new ItemStack(Material.RAW_BEEF,
                                                        enchants.get(CustomEnchantment.HUNTERS_BLESSING)));
                                        break;
                                    case MUTTON:
                                        event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(),
                                                new ItemStack(Material.MUTTON,
                                                        enchants.get(CustomEnchantment.HUNTERS_BLESSING)));
                                        break;
                                    default:
                                        break;

                                }

                            }

                        }

                    }
                }
            }

        }

        // HEADHUNTER
        if (entityKiller instanceof Player) {

            Player killer = (Player) entityKiller;

            if (killer.getInventory().getItemInMainHand() != null) {
                ItemStack weapon = killer.getInventory().getItemInMainHand();

                if (weapon.hasItemMeta()) {
                    Map<CustomEnchantment, Integer> enchants = CustomEnchantmentManager.getCustomEnchantments(weapon);

                    if (enchants.containsKey(CustomEnchantment.HEADHUNTER)) {

                        Random ran = new Random();
                        int roll = ran.nextInt(CONSTANTS.I_HEADHUNTER_CHANCE_BOUND) + 1;
                        int chance = enchants.get(CustomEnchantment.HEADHUNTER) * 3;

                        if (roll <= chance) { // each lvl increments chance by 3%, so max lvl has 9% chance of drop

                            switch (killed.getType()) {
                                case SKELETON:
                                    headDrop = new ItemStack(Material.SKULL_ITEM, 1, (short) 0);
                                    break;
                                case PLAYER:

                                    headDrop = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);

                                    // Make head appear to be killed's head
                                    SkullMeta sm = (SkullMeta) headDrop.getItemMeta();

                                    sm.setOwner(killed.getName()); // COULD BE PROBLEMATIC, SKULLMETA IS FUCKY

                                    headDrop.setItemMeta(sm);

                                    // Make head's name appear to be killed's head
                                    ItemMeta itemMeta = headDrop.getItemMeta();
                                    itemMeta.setDisplayName(ChatColor.RED + killed.getName() + "s head");
                                    headDrop.setItemMeta(itemMeta);

                                    // The above method might be buggy
                                    // People online were having a hard time aswell, it seems
                                    break;
                                case ZOMBIE:
                                    headDrop = new ItemStack(Material.SKULL_ITEM, 1, (short) 2);
                                    break;
                                case CREEPER:
                                    headDrop = new ItemStack(Material.SKULL_ITEM, 1, (short) 4);
                                    break;
                                default:
                                    break;
                            }

                            if (headDrop != null) {
                                event.getDrops().add(headDrop);
                            }
                        }
                    }

                }
            }

            if (killed instanceof Horse) {
                Horse mount = (Horse) killed;
                if (!mount.getPassengers().isEmpty()) {
                    ItemStack horseArmor = mount.getInventory().getArmor();
                    Map<CustomEnchantment, Integer> enchants = CustomEnchantmentManager.getCustomEnchantments(horseArmor);

                    if (enchants.containsKey(CustomEnchantment.VALIANT)) {
                        switch (enchants.get(CustomEnchantment.VALIANT)) {
                            case 3:
                                Util.replacePotionEffect((Player) mount.getPassengers().get(0),
                                        new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * CONSTANTS.I_VALIANT_DURATION_SECONDS, 3));

                            case 2:
                                Util.replacePotionEffect((Player) mount.getPassengers().get(0),
                                        new PotionEffect(PotionEffectType.REGENERATION, 20 * CONSTANTS.I_VALIANT_DURATION_SECONDS, 3));

                            case 1:
                                Util.replacePotionEffect((Player) mount.getPassengers().get(0),
                                        new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * CONSTANTS.I_VALIANT_DURATION_SECONDS, 3));
                                break;
                        }

                    }

                }

            }
        }

    }

    public void addRage(Player attacker, Entity defender) {
        CivEnchant.cdManager.rageEffects.add(new RageEffect(attacker, defender));
        CivEnchant.cdManager.ragePlayers.add(attacker);

    }

    public void removeRage(int playerIndex) {
        CivEnchant.cdManager.ragePlayers.remove(playerIndex);
        CivEnchant.cdManager.rageEffects.get(playerIndex).cancel();
        CivEnchant.cdManager.rageEffects.remove(playerIndex);
    }

    public void incrementRage(int playerIndex) {
        CivEnchant.cdManager.rageEffects.get(playerIndex).incrementLevel();
    }

}
