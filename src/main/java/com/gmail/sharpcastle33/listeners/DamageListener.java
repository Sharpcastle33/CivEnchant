package com.gmail.sharpcastle33.listeners;


import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
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
import com.gmail.sharpcastle33.util.RageEffect;
import com.gmail.sharpcastle33.util.Util;
import org.bukkit.Bukkit;

public class DamageListener implements Listener {

	private CivEnchant plugin = CivEnchant.plugin;
	private Random rand = new Random();
	
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
                                                attacker.sendMessage("Lifesteal chance");
						if (Util.chance(enchants.get(CustomEnchantment.LIFESTEAL), CONSTANTS.I_LIFESTEAL_CHANCE_BOUND)) {
                                                    
                                                    attacker.sendMessage("Lifesteal hit!");
							attacker.setHealth(Math.min(attacker.getHealth() + 1, attacker.getMaxHealth()));
                                                        
						}
					}
					
					if(enchants.containsKey(CustomEnchantment.CORROSIVE)){
					  if(defense instanceof LivingEntity){
                        LivingEntity target = (LivingEntity) defense;
                        if(defense.getCustomName() != null){
                          String name = defense.getCustomName();
                          if(name.startsWith(ChatColor.YELLOW + "Clockwork") ||
                              name.startsWith(ChatColor.YELLOW + "Steamwork") ||
                              name.startsWith(ChatColor.YELLOW + "Unhinged")){
                            int lvl = enchants.get(CustomEnchantment.CORROSIVE);
                            dmgFlat += 0.5*lvl;
                            
                          }
                        }
					  }
					}
					
                    if(enchants.containsKey(CustomEnchantment.PLAGUEBANE)){
                      if(defense instanceof LivingEntity){
                        LivingEntity target = (LivingEntity) defense;
                        if(defense.getCustomName() != null){
                          String name = defense.getCustomName();
                          if(name.startsWith(ChatColor.YELLOW + "Plague") ||
                              name.startsWith(ChatColor.YELLOW + "Plagued")){
                            int lvl = enchants.get(CustomEnchantment.PLAGUEBANE);
                            dmgFlat += 0.5*lvl;
                            
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
						Block  b = attacker.getLocation().getBlock();
						if(b.getType() == Material.WATER || b.getType() == Material.STATIONARY_WATER) {
		                     int lvl = enchants.get(CustomEnchantment.AQUATIC_COMBATANT);
						  dmgFlat += 0.5*lvl;
							
							if(Util.chance(20, 100)) {
								attacker.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 200, 1));
							
								Location location = attacker.getLocation();
								
				                for (int degree = 0; degree < 360; degree+=10) {
				                    double radians = Math.toRadians(degree);
				                    double x = Math.cos(radians);
				                    double z = Math.sin(radians);
				                    location.add(x, 0, z);
				                    location.getWorld().playEffect(location, Effect.WATERDRIP, 1);
				                    location.subtract(x, 0, z);
				                }
							}
						}
					}
					
					if (enchants.containsKey(CustomEnchantment.BERSERKING)) {
						int lvl = enchants.get(CustomEnchantment.BERSERKING);
						if(attacker.getHealth() <= 6) {
							if(Util.chance(20, 100)){
								dmgFlat += 3;
								
								Location location = event.getEntity().getLocation();
								
				                for (int degree = 0; degree < 360; degree+=10) {
				                    double radians = Math.toRadians(degree);
				                    double x = Math.cos(radians);
				                    double z = Math.sin(radians);
				                    location.add(x, 0, z);
				                    location.getWorld().playEffect(location, Effect.FLAME, 1);
				                    location.subtract(x, 0, z);
				                }
				                
				                
							}else dmgFlat += 0.5*lvl;
							
						}
					}

					if (enchants.containsKey(CustomEnchantment.RAGE)) {
						if (CivEnchant.cdManager.ragePlayers.contains(attacker)) {
                                                    boolean hasLostRage = false;
                                                    int playerIndex = CivEnchant.cdManager.ragePlayers.indexOf(attacker);
                                                        
                                                    //Chain must be on same target...
                                                    if(!CivEnchant.cdManager.rageEffects.get(playerIndex).isTarget(defense)){
                                                        
                                                        CivEnchant.cdManager.ragePlayers.remove(playerIndex);
                                                        CivEnchant.cdManager.rageEffects.get(playerIndex).cancel();
                                                        CivEnchant.cdManager.rageEffects.remove(playerIndex);
                                                        
                                                        attacker.sendMessage("Chain broken..");
                                                        
                                                        CivEnchant.cdManager.rageEffects.add(new RageEffect(attacker, defense)); // new effect
							CivEnchant.cdManager.ragePlayers.add(attacker);
                                                        
                                                    } else 
                                                    if (CivEnchant.cdManager.rageEffects.get(playerIndex).getLevel() < CONSTANTS.I_RAGE_CHAIN_MAX_COUNTER) {
                                                        
							CivEnchant.cdManager.rageEffects.get(playerIndex).incrementLevel();
                                                        attacker.sendMessage("Rage Level: " + CivEnchant.cdManager.rageEffects.get(playerIndex).getLevel());
                                                        
                                                    } else {
                                                        
                                                        attacker.sendMessage("Rage!!");
							dmgFlat += enchants.get(CustomEnchantment.RAGE);

							// PLAY NEATO PARTICLE EFFECT
							attacker.spawnParticle(Particle.FIREWORKS_SPARK, attacker.getLocation().getX(),
										attacker.getLocation().getY(), attacker.getLocation().getZ(), 5);
                                                        CivEnchant.cdManager.ragePlayers.remove(playerIndex);
                                                        CivEnchant.cdManager.rageEffects.get(playerIndex).cancel();
                                                        CivEnchant.cdManager.rageEffects.remove(playerIndex);
                                                    }
						} else { // if first hit

							CivEnchant.cdManager.rageEffects.add(new RageEffect(attacker, defense)); // new effect
							CivEnchant.cdManager.ragePlayers.add(attacker);
                                                        

						}
					}

				}
			}
                        
                        //Added damage against glowing enemies HUNTER's MARK
                        if(((LivingEntity)defense).hasPotionEffect(PotionEffectType.GLOWING)){
                            
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
                                                
                                                    dmgFlat -= (enchants.get(CustomEnchantment.ENDURANCE) * CONSTANTS.D_ENDURANCE_DAMAGE_REDUCTION);
                                                    if(dmgFlat < 0){
                                                        dmgFlat = 0; // Avoid attacks healing people
                                                    }
					}
					
	                if (enchants.containsKey(CustomEnchantment.SURVIVALIST)) {
	                  Biome b = defender.getLocation().getWorld().getBiome(defender.getLocation().getBlockX(), defender.getLocation().getBlockZ());
	                  if(survivalistBiomes.contains(b))
	                    dmgFlat -= (enchants.get(CustomEnchantment.SURVIVALIST) * 0.33);

	                }

					if (enchants.containsKey(CustomEnchantment.SECOND_WIND)) {
                                                
						if (defender.getHealth()-event.getDamage() < 4 && defender.getHealth() > 4) { // Does not account for ench dmg change
							if (!CivEnchant.cdManager.secondWind.contains(defender)) { // if SW is off CD
                                                                defender.sendMessage("Second Wind");
								CivEnchant.cdManager.add(defender, CustomEnchantment.SECOND_WIND, CONSTANTS.I_SECOND_WIND_COOLDOWN_DURATION_SECONDS);
								Util.replacePotionEffect(defender,
										new PotionEffect(PotionEffectType.REGENERATION, 20*CONSTANTS.I_SECOND_WIND_DURATION_SECONDS, 1));
								Util.replacePotionEffect(defender, 
                                                                                new PotionEffect(PotionEffectType.SPEED, 20*CONSTANTS.I_SECOND_WIND_DURATION_SECONDS, 1));

							}
						}
					}

					if (enchants.containsKey(CustomEnchantment.LAST_STAND)) {

                                                
						if (defender.getHealth()-event.getDamage() < 2 && defender.getHealth() > 2) { // Does not account for ench dmg change
                                                    
							if (!CivEnchant.cdManager.lastStand.contains(defender)) { // if SW is off CD
                                                                
								CivEnchant.cdManager.add(defender, CustomEnchantment.LAST_STAND, CONSTANTS.I_LAST_STAND_COOLDOWN_DURATION_SECONDS);
                                                                
								Util.replacePotionEffect(defender,
										new PotionEffect(PotionEffectType.REGENERATION, 20*CONSTANTS.I_LAST_STAND_DURATION_SECONDS, 1));
								
                                                                Util.replacePotionEffect(defender,
										new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20*CONSTANTS.I_LAST_STAND_DURATION_SECONDS, 1));
                                                                
							}
						}

					}
					
	                   if (enchants.containsKey(CustomEnchantment.DIVINE_INTERVENTION)) {

                         
	                        if (defender.getHealth()-event.getDamage() < 2 && defender.getHealth() > 2) { // Does not account for ench dmg change
	                                                    
	                            if (!CivEnchant.cdManager.divineIntervention.contains(defender)) { // if SW is off CD
	                               int lvl = enchants.get(CustomEnchantment.DIVINE_INTERVENTION);                                
	                                CivEnchant.cdManager.add(defender, CustomEnchantment.DIVINE_INTERVENTION, CONSTANTS.I_DIVINE_INTERVENTION_COOLDOWN_DURATION_SECONDS);
	                                                                
	                                Util.replacePotionEffect(defender,
	                                        new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*(2 + lvl), 4));
	                                
	                                                                Util.replacePotionEffect(defender,
	                                        new PotionEffect(PotionEffectType.REGENERATION, 20*5 + (20*2*lvl), 3));
	                                                                
	                            }
	                        }

	                    }

					if (enchants.containsKey(CustomEnchantment.ADRENALINE)) {

						if (defender.getHealth()-event.getDamage() < 4 && defender.getHealth() > 4) { // Does not account for ench dmg change
                                                        
							if (!CivEnchant.cdManager.adrenaline.contains(defender)) { // if adrenaline is off CD

								CivEnchant.cdManager.add(defender, CustomEnchantment.ADRENALINE, CONSTANTS.I_ADRENALINE_COOLDOWN_DURATION_SECONDS);
								Util.replacePotionEffect(defender, new PotionEffect(PotionEffectType.SPEED, 20*CONSTANTS.I_ADRENALINE_DURATION_SECONDS, 3));

							}
						}

					}

				}
			}

			ItemStack stack = defender.getInventory().getItemInOffHand();

			/*if (stack != null && stack.getType() == Material.SHIELD) {
				dmgFlat -= 1;
			}*/

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
									//defender.sendMessage(
											//attacker.getName() + "'s weapon has corroded your armor! WIP NO EFFECT");

									/*defender.spawnParticle(Particle.VILLAGER_ANGRY, defense.getLocation().getX(),
											defense.getLocation().getY(), defense.getLocation().getZ(), 2);
									*/
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

				double finalDistance = shooter.getLocation().distance(defense.getLocation());
                                //shooter.sendMessage("Distance of Shot: " + finalDistance);
				if (arrow.getName().contains("trueshot")) {
                                        //shooter.sendMessage("True Shot!");
					trueShot = 1;
				}

				if (arrow.getName().contains("farshot1")) {
					if (finalDistance > CONSTANTS.I_FAR_SHOT_MAX_DISTANCE_BLOCKS) {
						dmgFlat = dmgFlat + 4;
					} else if (finalDistance > CONSTANTS.I_FAR_SHOT_MED_DISTANCE_BLOCKS && finalDistance < CONSTANTS.I_FAR_SHOT_MAX_DISTANCE_BLOCKS) {
						dmgFlat = dmgFlat + 3;
					}

					else if (finalDistance > CONSTANTS.I_FAR_SHOT_MIN_DISTANCE_BLOCKS && finalDistance < CONSTANTS.I_FAR_SHOT_MED_DISTANCE_BLOCKS) {
						dmgFlat = dmgFlat + 1.5;
					}
				}

				if (arrow.getName().contains("farshot2")) {
					if (finalDistance > CONSTANTS.I_FAR_SHOT_MAX_DISTANCE_BLOCKS) {
						dmgFlat = dmgFlat + 6;
					} else if (finalDistance > CONSTANTS.I_FAR_SHOT_MED_DISTANCE_BLOCKS && finalDistance < CONSTANTS.I_FAR_SHOT_MAX_DISTANCE_BLOCKS) {
						dmgFlat = dmgFlat + 4;
					}

					else if (finalDistance > CONSTANTS.I_FAR_SHOT_MIN_DISTANCE_BLOCKS && finalDistance < CONSTANTS.I_FAR_SHOT_MED_DISTANCE_BLOCKS) {
						dmgFlat = dmgFlat + 2;
					}
				}

				if (arrow.getName().contains("pointblank1")) {
                                    
					if (finalDistance > CONSTANTS.I_POINT_BLANK_MED_DISTANCE_BLOCKS && finalDistance < CONSTANTS.I_POINT_BLANK_MAX_DISTANCE_BLOCKS) {
                                            
						dmgFlat = dmgFlat + 1;
					} else if (finalDistance > CONSTANTS.I_POINT_BLANK_MIN_DISTANCE_BLOCKS && finalDistance < CONSTANTS.I_POINT_BLANK_MED_DISTANCE_BLOCKS) {
                                            
						dmgFlat = dmgFlat + 2;
                                                
					} else if(finalDistance < CONSTANTS.I_POINT_BLANK_MIN_DISTANCE_BLOCKS)  {
                                            
						dmgFlat = dmgFlat + 3;
					}
				}

				if (arrow.getName().contains("pointblank2")) {
                                    
                                        if (finalDistance > CONSTANTS.I_POINT_BLANK_MED_DISTANCE_BLOCKS && finalDistance < CONSTANTS.I_POINT_BLANK_MAX_DISTANCE_BLOCKS) {
                                            
						dmgFlat = dmgFlat + 2;
					} else if (finalDistance > CONSTANTS.I_POINT_BLANK_MIN_DISTANCE_BLOCKS && finalDistance < CONSTANTS.I_POINT_BLANK_MED_DISTANCE_BLOCKS) {
                                            
						dmgFlat = dmgFlat + 3;
                                                
					} else if(finalDistance < CONSTANTS.I_POINT_BLANK_MIN_DISTANCE_BLOCKS)  {
                                            
						dmgFlat = dmgFlat + 4;
					}
				}
                                
                                if (arrow.getName().contains("huntersmark1")) {
					if (defense instanceof LivingEntity) {
						LivingEntity target = (LivingEntity) defense;

						target.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,
								20  + (20 * CONSTANTS.I_BASE_HUNTERS_MARK_SECONDS), // Duration
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
				// successful evasion
				evade = 0 + trueShot;
				if (trueShot == 0) {
					defender.sendMessage("You evaded their attack!");
					defender.spawnParticle(Particle.VILLAGER_HAPPY, defense.getLocation().getX(),
							defense.getLocation().getY(), defense.getLocation().getZ(), 4);
					// spawnParticle​(Particle particle, double x, double y, double z, int count)
				}

			}
		}
                                    
		double finalDamage = ((event.getDamage() + dmgFlat) * (1 + dmgMod) * (1 + dmgMulti) * evade) + trueShot
				- enduredDamage;
		event.setDamage(finalDamage);
                
                Bukkit.getLogger().info("Damage: " + finalDamage);
                
                
	}

	@EventHandler
	public void onArrowShoot(EntityShootBowEvent event) {
		if (event.getEntity() instanceof Player) {

			Player p = (Player) event.getEntity();
			ItemStack bow = event.getBow();
			Entity arrow = event.getProjectile();
                       // event.getEntity().sendMessage("Arrow Shot, Force: " + event.getForce());
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
		
					if (enchants.containsKey(CustomEnchantment.CRIPPLING)) {

						arrow.setCustomName(arrow.getName() + "crippling");

					}
					
					if(enchants.containsKey(CustomEnchantment.MULTISHOT)) {
						Arrow a1 = p.launchProjectile(Arrow.class);
						
						a1.setVelocity(a1.getVelocity().add(new Vector(0,2,0)));
						
						Arrow b1 = p.launchProjectile(Arrow.class);
						
						b1.setVelocity(a1.getVelocity().add(new Vector(0,-2,0)));


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
                                                        killer.sendMessage("Its Raining food!");
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
		}
                
                // If rage target dies..
                int i = 0;
                for(RageEffect entity : CivEnchant.cdManager.rageEffects){
                    if(entity.getTarget().equals(killed)){
                        Bukkit.getLogger().info("Target died, removing rage");
                        CivEnchant.cdManager.ragePlayers.remove(i);
                        CivEnchant.cdManager.rageEffects.get(i).cancel();;
                        CivEnchant.cdManager.rageEffects.remove(i);
                    }
                 
                    i++;
                }
                
              
                
	}

}
