package com.gmail.sharpcastle33.listeners;

import java.util.Map;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
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

import com.gmail.sharpcastle33.CivEnchant;
import com.gmail.sharpcastle33.enchantments.CustomEnchantment;
import com.gmail.sharpcastle33.enchantments.CustomEnchantmentManager;
import com.gmail.sharpcastle33.util.RageEffect;
import com.gmail.sharpcastle33.util.Util;

public class DamageListener implements Listener {

	private CivEnchant plugin = CivEnchant.plugin;
	private Random rand = new Random();

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
						if (Util.chance(enchants.get(CustomEnchantment.LIFESTEAL), 33)) {
							attacker.setHealth(Math.min(attacker.getHealth() + 1, attacker.getMaxHealth()));
							// Bukkit.getLogger().info("healed " + attacker.getName());
						}
					}

					if (enchants.containsKey(CustomEnchantment.HUNTERS_MARK)) {
						if (defense instanceof LivingEntity) {
							LivingEntity target = (LivingEntity) defense;

							target.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
									10 * enchants.get(CustomEnchantment.HUNTERS_MARK), // Duration
									1)); // Amplifier
						}
					}

					if (enchants.containsKey(CustomEnchantment.RAGE)) {
						if (CivEnchant.cdManager.ragePlayers.contains(attacker)) {
							int playerIndex = CivEnchant.cdManager.ragePlayers.indexOf(attacker);

							if (CivEnchant.cdManager.rageEffects.get(playerIndex).getLevel() < 5) {

								CivEnchant.cdManager.rageEffects.get(playerIndex).incrementLevel();

							} else {

								dmgFlat += enchants.get(CustomEnchantment.RAGE);

								// PLAY NEATO PARTICLE EFFECT
								attacker.spawnParticle(Particle.FIREWORKS_SPARK, attacker.getLocation().getX(),
										attacker.getLocation().getY(), attacker.getLocation().getZ(), 5);

							}
						} else { // if first hit

							CivEnchant.cdManager.rageEffects.add(new RageEffect(attacker)); // new effect
							CivEnchant.cdManager.ragePlayers.add(attacker);

						}
					}

				}
			}

		}

		// DEFENDING PLAYER
		if (defense instanceof Player) {
			Player defender = (Player) defense;
			ItemStack[] armor = defender.getInventory().getArmorContents();

			if (CivEnchant.cdManager.ragePlayers.contains(defender)) {
				int playerIndex = CivEnchant.cdManager.ragePlayers.indexOf(defender);

				CivEnchant.cdManager.ragePlayers.remove(playerIndex);
				CivEnchant.cdManager.rageEffects.remove(playerIndex);
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

						dmgFlat -= (enchants.get(CustomEnchantment.ENDURANCE) * 0.15);

					}

					// Vigor Moved to ArmorEquipListener (Still needs to be done as of 1/30)

					if (enchants.containsKey(CustomEnchantment.SECOND_WIND)) {

						if (defender.getHealth() < 4) { // Does not account for ench dmg change
							if (!CivEnchant.cdManager.secondWind.contains(defender)) { // if SW is off CD

								CivEnchant.cdManager.add(defender, CustomEnchantment.SECOND_WIND, 10);
								Util.replacePotionEffect(defender,
										new PotionEffect(PotionEffectType.REGENERATION, 10, 1));
								Util.replacePotionEffect(defender, new PotionEffect(PotionEffectType.SPEED, 10, 1));

							}
						}
					}

					if (enchants.containsKey(CustomEnchantment.LAST_STAND)) {

						if (defender.getHealth() < 2) { // Does not account for ench dmg change

							if (!CivEnchant.cdManager.lastStand.contains(defender)) { // if SW is off CD

								CivEnchant.cdManager.add(defender, CustomEnchantment.LAST_STAND, 10);
								Util.replacePotionEffect(defender,
										new PotionEffect(PotionEffectType.REGENERATION, 10, 1));
								Util.replacePotionEffect(defender,
										new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 10, 1));

							}
						}

					}

					if (enchants.containsKey(CustomEnchantment.ADRENALINE)) {

						if (defender.getHealth() < 4) { // Does not account for ench dmg change

							if (!CivEnchant.cdManager.adrenaline.contains(defender)) { // if SW is off CD

								CivEnchant.cdManager.add(defender, CustomEnchantment.ADRENALINE, 10);
								Util.replacePotionEffect(defender, new PotionEffect(PotionEffectType.SPEED, 10, 3));

							}
						}

					}

				}
			}

			ItemStack stack = defender.getInventory().getItemInOffHand();

			if (stack != null && stack.getType() == Material.SHIELD) {
				dmgFlat -= 1;
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
									defender.sendMessage(
											attacker.getName() + "'s weapon has corroded your armor! WIP NO EFFECT");

									defender.spawnParticle(Particle.VILLAGER_ANGRY, defense.getLocation().getX(),
											defense.getLocation().getY(), defense.getLocation().getZ(), 2);

								}
							}
						}
					} // Corrosive end
				}
			}

		}

		// PLAYER SHOT BY ARROW

		if (offense instanceof Arrow) {

			Arrow arrow = (Arrow) offense;

			if (arrow.getShooter() instanceof Player) {

				Player shooter = (Player) arrow.getShooter();

				double shooterX = shooter.getLocation().getX();
				double shooterY = shooter.getLocation().getY();
				double shooterZ = shooter.getLocation().getZ();
				double defenseX = defense.getLocation().getX();
				double defenseY = defense.getLocation().getY();
				double defenseZ = defense.getLocation().getZ();

				// Distance between
				double xDistance = Math.abs(shooterX - defenseX);
				double yDistance = Math.abs(shooterY - defenseY);
				double zDistance = Math.abs(shooterZ - defenseZ);
				double diagDistance = Math.sqrt((xDistance * xDistance) + (zDistance * zDistance));

				double finalDistance = Math.sqrt((diagDistance * diagDistance) + (yDistance * yDistance));

				if (arrow.getName().contains("trueshot")) {
					trueShot = 1;
				}

				if (arrow.getName().contains("farshot1")) {

					if (finalDistance > 90) {
						dmgFlat = dmgFlat + 4;
					} else if (finalDistance > 60 && finalDistance < 90) {
						dmgFlat = dmgFlat + 3;
					}

					else if (finalDistance > 50 && finalDistance < 60) {
						dmgFlat = dmgFlat + 1.5;
					}
				}

				if (arrow.getName().contains("farshot2")) {

					if (finalDistance > 90) {
						dmgFlat = dmgFlat + 6;
					} else if (finalDistance > 60 && finalDistance < 90) {
						dmgFlat = dmgFlat + 4;
					}

					else if (finalDistance > 50 && finalDistance < 60) {
						dmgFlat = dmgFlat + 2;
					}
				}

				if (arrow.getName().contains("pointblank1")) {

					if (finalDistance > 10) {
					} else if (finalDistance > 7 && finalDistance < 10) {
						dmgFlat = dmgFlat + 1;
					}

					else if (finalDistance > 3 && finalDistance < 7) {
						dmgFlat = dmgFlat + 2;
					} else {
						dmgFlat = dmgFlat + 3;
					}
				}

				if (arrow.getName().contains("pointblank2")) {

					if (finalDistance > 15) {
					} else if (finalDistance > 11 && finalDistance < 15) {
						dmgFlat = dmgFlat + 2;
					}

					else if (finalDistance > 5 && finalDistance < 11) {
						dmgFlat = dmgFlat + 3;
					} else {
						dmgFlat = dmgFlat + 4;
					}
				}
			}
		}

		// Calculate chance to evade

		int roll = rand.nextInt(99) + 1; // Roll between 1-100 ## CHANGE THIS TO CHANGE PROBABILITY OF EVADE
		int evade = 1; // 1 is no evade, 0 is successful evade (for calculating finalDamage below)
		int enduredDamage = 0;

		if (defense instanceof Player) {

			Player defender = (Player) defense;

			if (roll <= evadeChance) {
				// successful evasion
				evade = 0 + trueShot;
				if (trueShot == 0) {
					defender.sendMessage("You evaded their attack!");
					defender.spawnParticle(Particle.VILLAGER_HAPPY, defense.getLocation().getX(),
							defense.getLocation().getY(), defense.getLocation().getZ(), 2);
					// spawnParticle​(Particle particle, double x, double y, double z, int count)
				}

			}
		}

		double finalDamage = ((event.getDamage() + dmgFlat) * (1 + dmgMod) * (1 + dmgMulti) * evade) + trueShot
				- enduredDamage;
		event.setDamage(finalDamage);
	}

	@EventHandler
	public void onArrowShoot(EntityShootBowEvent event) {

		if (event.getEntity() instanceof Player) {

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
						int roll = rand.nextInt(60) + 1 / enchants.get(CustomEnchantment.HUNTERS_BLESSING);
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
						int roll = ran.nextInt(99) + 1;
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
		}
	}

}
