package com.gmail.sharpcastle33.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import com.gmail.sharpcastle33.durability.DurabilityManager;
import com.gmail.sharpcastle33.enchantments.CustomEnchantment;
import com.gmail.sharpcastle33.enchantments.CustomEnchantmentManager;
import com.gmail.sharpcastle33.util.Util;
import com.gmail.sharpcastle33.util.CONSTANTS;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.Action;



public class BlockListener implements Listener {

	final static Material[] crystals = { Material.DIAMOND_ORE, Material.EMERALD_ORE, Material.QUARTZ_ORE };
	
	final static Map<Material, Byte> stones = new HashMap<>();
	static List<Material> stonesList = new ArrayList<>();
	final static Map<Material, Byte> sands = new HashMap<>();
	static List<Material> sandsList = new ArrayList<>();
	final static Map<Material, Byte> logs = new HashMap<>();
	static List<Material> logsList = new ArrayList<>();
	final static Map<Material, Byte> woods = new HashMap<>();
	static List<Material> woodsList = new ArrayList<>();
	final static Map<Material, Byte> flowers = new HashMap<>();
	static List<Material> flowersList = new ArrayList<>();
	
	static {
		stones.put(Material.STONE, (byte) 0);
		stones.put(Material.STONE, (byte) 1);
		stones.put(Material.STONE, (byte) 2);
		stones.put(Material.STONE, (byte) 3);
		stones.put(Material.STONE, (byte) 4);
		stones.put(Material.STONE, (byte) 5);
		stones.put(Material.STONE, (byte) 6);
		stones.put(Material.COBBLESTONE, (byte) 0);
		stones.put(Material.DOUBLE_STONE_SLAB2, (byte) 8);
		stones.put(Material.SMOOTH_BRICK, (byte) 0);
		stones.put(Material.SMOOTH_BRICK, (byte) 1);
		stones.put(Material.SMOOTH_BRICK, (byte) 2);
		stones.put(Material.SMOOTH_BRICK, (byte) 3);
		stones.put(Material.SANDSTONE, (byte) 0);
		stones.put(Material.SANDSTONE, (byte) 1);
		stones.put(Material.SANDSTONE, (byte) 2);
		stones.put(Material.RED_SANDSTONE, (byte) 0);
		stones.put(Material.RED_SANDSTONE, (byte) 1);
		stones.put(Material.RED_SANDSTONE, (byte) 2);
		stonesList.addAll(stones.keySet());
		
		sands.put(Material.SAND, (byte) 0);
		sands.put(Material.SAND, (byte) 1);
		sands.put(Material.SOUL_SAND, (byte) 0);
		sands.put(Material.DIRT, (byte) 0);
		sands.put(Material.DIRT, (byte) 1);
		sands.put(Material.DIRT, (byte) 2);
		sands.put(Material.GRASS, (byte) 0);
		sands.put(Material.GRASS_PATH, (byte) 0);
		sands.put(Material.GRAVEL, (byte) 0);
		sandsList.addAll(sands.keySet());
		
		logs.put(Material.LOG, (byte) 0);
		logs.put(Material.LOG, (byte) 1);
		logs.put(Material.LOG, (byte) 2);
		logs.put(Material.LOG, (byte) 3);
		logs.put(Material.LOG_2, (byte) 0);
		logs.put(Material.LOG_2, (byte) 1);
		logsList.addAll(logs.keySet());
		
		woods.put(Material.WOOD, (byte) 0);
		woods.put(Material.WOOD, (byte) 1);
		woods.put(Material.WOOD, (byte) 2);
		woods.put(Material.WOOD, (byte) 3);
		woods.put(Material.WOOD, (byte) 4);
		woods.put(Material.WOOD, (byte) 5);
		woodsList.addAll(woods.keySet());
		
		flowers.put(Material.LONG_GRASS, (byte) 0);
		flowers.put(Material.LONG_GRASS, (byte) 1);
		flowers.put(Material.LONG_GRASS, (byte) 2);
		flowers.put(Material.DEAD_BUSH, (byte) 0);
		flowers.put(Material.YELLOW_FLOWER, (byte) 0);
		flowers.put(Material.RED_ROSE, (byte) 0);
		flowers.put(Material.RED_ROSE, (byte) 1);
		flowers.put(Material.RED_ROSE, (byte) 2);
		flowers.put(Material.RED_ROSE, (byte) 3);
		flowers.put(Material.RED_ROSE, (byte) 4);
		flowers.put(Material.RED_ROSE, (byte) 5);
		flowers.put(Material.RED_ROSE, (byte) 6);
		flowers.put(Material.RED_ROSE, (byte) 7);
		flowers.put(Material.RED_ROSE, (byte) 8);
		flowers.put(Material.BROWN_MUSHROOM, (byte) 0);
		flowers.put(Material.RED_MUSHROOM, (byte) 0);
		flowersList.addAll(flowers.keySet());
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {

		Player player = event.getPlayer();
		Block block = event.getBlock();
		ItemStack mainHand = event.getPlayer().getInventory().getItemInMainHand();
		Map<CustomEnchantment, Integer> enchants;

		boolean demolished = false;
		boolean smelt = false;

		if (mainHand.hasItemMeta()) {

			enchants = CustomEnchantmentManager.getCustomEnchantments(mainHand);
			
			// TOOL
			if(Util.isTool(mainHand)) {
				// MUTANDIS
				if(enchants.containsKey(CustomEnchantment.MUTANDIS) && Math.random() > 1.00 - (enchants.get(CustomEnchantment.MUTANDIS).intValue() * 0.25)) {
					if(stonesList.contains(block.getType())) {
						Material newMat = stonesList.get((int) (Math.random() * stonesList.size()));
						block.setType(newMat);
						block.setData(stones.get(newMat));
					} else if(sandsList.contains(block.getType())) {
						Material newMat = sandsList.get((int) (Math.random() * sandsList.size()));
						block.setType(newMat);
						block.setData(sands.get(newMat));
					} else if(logsList.contains(block.getType())) {
						Material newMat = logsList.get((int) (Math.random() * logsList.size()));
						block.setType(newMat);
						block.setData(logs.get(newMat));
					} else if(woodsList.contains(block.getType())) {
						Material newMat = woodsList.get((int) (Math.random() * woodsList.size()));
						block.setType(newMat);
						block.setData(woods.get(newMat));
					} else if(flowersList.contains(block.getType())) {
						Material newMat = flowersList.get((int) (Math.random() * flowersList.size()));
						block.setType(newMat);
						block.setData(flowers.get(newMat));
					} // if/else if/else if/else if/else if
				} // if
				
			} // if

			// PICKAXE
			if (Util.isPickaxe(mainHand)) {
				// AUTO SMELT
				if (enchants.containsKey(CustomEnchantment.AUTO_SMELT)) {

					smelt = true; 

					if (event.getBlock().getType().equals(Material.GOLD_ORE)) {

						event.setDropItems(false);
						event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(),
								new ItemStack(Material.GOLD_INGOT));
						event.getBlock().getWorld().spawn(event.getBlock().getLocation(), ExperienceOrb.class)
								.setExperience(2 * enchants.get(CustomEnchantment.AUTO_SMELT));

					}

					if (event.getBlock().getType().equals(Material.IRON_ORE)) {

						event.setDropItems(false);
						event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(),
								new ItemStack(Material.IRON_INGOT));
						event.getBlock().getWorld().spawn(event.getBlock().getLocation(), ExperienceOrb.class)
								.setExperience(1 * enchants.get(CustomEnchantment.AUTO_SMELT));

					}

				}
				//UMBRAL
				if(enchants.containsKey(CustomEnchantment.UMBRAL)){
				  if(block.getType() == Material.COAL_ORE){
				    if(Math.random() > (CONSTANTS.D_UMBRAL_CHANCE * enchants.get(CustomEnchantment.UMBRAL))){
                                        
                                        ItemStack fuel = new ItemStack(Material.COAL,1);
                                        ItemMeta meta = Bukkit.getItemFactory().getItemMeta(Material.COAL);
                                        fuel.setItemMeta(meta);
                                        block.getWorld().dropItemNaturally(block.getLocation(), fuel);
                          
				    }
				    
				    if(Math.random() > 0.1){
                                        ItemStack coal = new ItemStack(Material.COAL,1);
                                        block.getWorld().dropItemNaturally(block.getLocation(), coal);
				    }
				  }
				}
				// DEMOLISHING
				if (enchants.containsKey(CustomEnchantment.DEMOLISHING)) {
					demolished = true;
					if (block.getType() == Material.STONE) {
						event.setDropItems(false);
						if (Util.chance(25 * enchants.get(CustomEnchantment.DEMOLISHING), CONSTANTS.I_DEMOLISHING_CHANCE_BOUND)) {
							event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(),
									new ItemStack(Material.GRAVEL));
						}
					}
				}
				// CRYSTAL ATTUNEMENT
				if (enchants.containsKey(CustomEnchantment.CRYSTAL_ATTUNEMENT)) {
					for (Material m : crystals) {
						if (block.getType() == m) {
							Util.replacePotionEffect(player, new PotionEffect(PotionEffectType.FAST_DIGGING, 20*CONSTANTS.I_CRYSTAL_ATTUNEMENT_DURATION_SECONDS,
									2 * enchants.get(CustomEnchantment.CRYSTAL_ATTUNEMENT)));
						}
					}
				}
				// CRYSTAL RESTORATION
				if (enchants.containsKey(CustomEnchantment.CRYSTAL_RESTORATION)){
                  for (Material m : crystals) {
                    if (block.getType() == m) {
                     DurabilityManager.addDurability(mainHand, 10*enchants.get(CustomEnchantment.CRYSTAL_RESTORATION));
                    }
                  }
				}
				// EMERALD RESONANCE
				// is silk touch a problem? Don't think so.
				if (enchants.containsKey(CustomEnchantment.EMERALD_RESONANCE)) {
					if (block.getType() == Material.EMERALD_ORE) {
						int lvl = enchants.get(CustomEnchantment.EMERALD_RESONANCE);
						// dissonance
						if (Util.chance(20 + (10 * lvl), CONSTANTS.I_EMERALD_RESONANCE_BOUND)) {
							event.setDropItems(false);
							block.getLocation().getWorld().playSound(block.getLocation(), Sound.BLOCK_GLASS_BREAK, 1f,
									1f);

							Random rand = new Random();

							int amount = 0;

							if (lvl == 1) {
								amount += rand.nextInt(1);
							} else if (lvl == 2) {
								amount += rand.nextInt(2);
							} else {
								amount += rand.nextInt(1);

								if (Util.chance(50, 100)) {
									amount += 1;
								}

								if (Util.chance(5, 100)) {
									amount += 1;
								}
							}

							event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(),
									Util.generateItem("EMERALD_FRAGMENT", amount));
							int exp = event.getExpToDrop();
							event.setExpToDrop((int) (exp * (2.5 + (0.5 * lvl))));

						} else {
							// resonance

							int exp = event.getExpToDrop();

							if (!(enchants.containsKey(CustomEnchantment.SILK_TOUCH))) {
								event.setExpToDrop(((int) (exp * (1.1 + (0.15 * lvl)))));
								block.getLocation().getWorld().playSound(block.getLocation(), Sound.BLOCK_NOTE_CHIME,
										1f, 1f);

							}

						}
					}
				}

				// PROFICIENT
				if (enchants.containsKey(CustomEnchantment.PROFICIENT)) {
					if (block.getType() == Material.STONE) {
						if (Util.chance(enchants.get(CustomEnchantment.PROFICIENT), CONSTANTS.I_PROFICIENT_CHANCE_BOUND)) {
							event.setExpToDrop(CONSTANTS.I_PROFICIENT_EXP_AMOUNT);
						}
					}
				}
				// PROSPERITY
                if (enchants.containsKey(CustomEnchantment.PROSPERITY)) {
                  int lvl = enchants.get(CustomEnchantment.PROSPERITY); 
                 
                  if(!enchants.containsKey(CustomEnchantment.SILK_TOUCH)){
                    if(block.getType() == Material.GOLD_ORE){
                      if (Util.chance(10 + 15*lvl, 100)){
                        ItemStack drop = new ItemStack(Material.GOLD_NUGGET, 3);
                        block.getWorld().dropItemNaturally(block.getLocation(), drop);
                      }
                    }
                    
                    if(block.getType() == Material.DIAMOND_ORE){
                      if (Util.chance(15 + 5*lvl, 100)){
                        ItemStack drop = new ItemStack(Material.DIAMOND, 1);
                        block.getWorld().dropItemNaturally(block.getLocation(), drop);
             
                      }
                    }
                  }
                 
                  
                }
				// STONEMASON
				if (enchants.containsKey(CustomEnchantment.STONEMASON)) {
                                    if (!demolished && block.getType() == Material.STONE) {
						
					byte data = block.getData();

					if (data == 0) {
                                            if (Util.chance(5 * enchants.get(CustomEnchantment.STONEMASON), CONSTANTS.I_STONEMASON_CHANCE_BOUND)) {
						event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.COBBLESTONE));
						}
					} else {
                                            if (Util.chance(20 * enchants.get(CustomEnchantment.STONEMASON), CONSTANTS.I_STONEMASON_CHANCE_BOUND)) {
						event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.STONE, data));
                                            }
					}
						
                                    }
				}
				// IRON AFFINITY
				if (enchants.containsKey(CustomEnchantment.IRON_AFFINITY)) {
					// How do we want to deal with players placing ore?
					// Drop special iron ore fragments every break.
					if (block.getType() == Material.IRON_ORE) {
						Random rand = new Random();
						int lvl = enchants.get(CustomEnchantment.IRON_AFFINITY);
						int amount = 0 + rand.nextInt(Math.min(lvl,2));
						if(lvl == 5) { amount+=1; }
						lvl-=3;
						amount += rand.nextInt(Math.min(lvl,2));
						
						if(!smelt)
							amount+=4;
						event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(),
								Util.generateItem("IRON_FRAGMENT", amount));
					  
					  block.setType(Material.AIR);
					}

				}
				// GOLD AFFINITY
				if (enchants.containsKey(CustomEnchantment.GOLD_AFFINITY)) {
					if (block.getType() == Material.GOLD_ORE) {
						int lvl = enchants.get(CustomEnchantment.GOLD_AFFINITY);
						Random rand = new Random();
						int amount = 0 + rand.nextInt(Math.min(lvl,2));
						if(lvl == 5) { amount+=1; }
						lvl-=3;
						amount += rand.nextInt(Math.min(lvl,2));
						
						if(!smelt)
							amount+=4;
						event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(),
								Util.generateItem("GOLD_FRAGMENT", amount));

                       block.setType(Material.AIR);
                                            
					}
				}
			}

			// AXE
			if (Util.isAxe(mainHand)) {
				// Placed Axe enchants in methods to accomodate Timber

				// APPLESEED
				if (enchants.containsKey(CustomEnchantment.APPLESEED)) {

					appleSeed(event.getBlock(), enchants.get(CustomEnchantment.APPLESEED));

				}
				// CARPENTRY
				if (enchants.containsKey(CustomEnchantment.CARPENTRY)) {

					carpentry(event.getBlock(), enchants.get(CustomEnchantment.CARPENTRY));

				}
				/*/ TIMBER
				if (enchants.containsKey(CustomEnchantment.TIMBER)) {
					if (block.getType() == Material.LOG || block.getType() == Material.LOG_2) {

						double x = block.getX();
						double y = block.getY();
						double z = block.getZ();

						for (double i = y; i < (y + (enchants.get(CustomEnchantment.TIMBER) * 3)); i++) {

							Location location = new Location(block.getWorld(), x, i, z);

							if (!(reinfrocement == null)) {

								player.sendMessage(TIMBER_REINFORCEMENT);

								break;

							}

							else {

								if (location.getBlock().getType() == Material.LOG
										|| location.getBlock().getType() == Material.LOG_2) {

									if (enchants.containsKey(CustomEnchantment.APPLESEED)) {

										appleSeed(location.getBlock(), enchants.get(CustomEnchantment.APPLESEED));

									}
									// CARPENTRY
									if (enchants.containsKey(CustomEnchantment.CARPENTRY)) {

										carpentry(location.getBlock(), enchants.get(CustomEnchantment.CARPENTRY));

									}

									location.getBlock().breakNaturally();

									if (enchants.containsKey(CustomEnchantment.UNBREAKING)) {

										double random = Math.random();
										double unbreakingLvl = enchants.get(CustomEnchantment.UNBREAKING);

										if (random < (1 / unbreakingLvl)) {

											mainHand.setDurability((short) (mainHand.getDurability() + 1));

										}
									}

									else {

										mainHand.setDurability((short) (mainHand.getDurability() + 1));

									}
								}

								else {
									break;
								}
							}

						}

					}
				}
                                */
			}

			if (Util.isShovel(mainHand)) {

			  
              if (enchants.containsKey(CustomEnchantment.SHIFTING_SANDS)) {
                if(block.getType() == Material.SAND){
                  if(Util.chance(50, 100)){
                    ItemStack drop = new ItemStack(Material.SAND, 1, (short) 1);
                    block.getWorld().dropItemNaturally(block.getLocation(), drop);
                  }
                }
                
                if(block.getType() == Material.SANDSTONE){
                  if(Util.chance(50, 100)){
                    ItemStack drop = new ItemStack(Material.RED_SANDSTONE, 1);
                    block.getWorld().dropItemNaturally(block.getLocation(), drop);
                  }
                }
                
                if(block.getType() == Material.RED_SANDSTONE){
                  if(Util.chance(50, 100)){
                    ItemStack drop = new ItemStack(Material.SANDSTONE, 1);
                    block.getWorld().dropItemNaturally(block.getLocation(), drop);
                  }
                }
              }



                            //BrickLayer
                            if(enchants.containsKey(CustomEnchantment.BRICKLAYER)){
                                if(block.getType() == Material.CLAY){
                                        if (Util.chance(5 * enchants.get(CustomEnchantment.BRICKLAYER), CONSTANTS.I_BRICKLAYER_CHANCE_BOUND)) {
						event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.BRICK));
					}
                                }
                            }

			}

			

		} // if hasitemMeta end

	} // Class end

	private void carpentry(Block theBlock, int level) {

		// LOGS
		if (theBlock.getType() == Material.LOG) {
			// STICK DROP
			if (Util.chance(5 * level, CONSTANTS.I_CARPENTRY_CHANCE_BOUND)) {
				Random rand = new Random();
				int amt = rand.nextInt(level) + 1;
				theBlock.getWorld().dropItemNaturally(theBlock.getLocation(), new ItemStack(Material.STICK, amt));
			}
			// FENCE DROP
			if (Util.chance(2 * level, CONSTANTS.I_CARPENTRY_CHANCE_BOUND)) {
				Byte data = theBlock.getData();
				Random rand = new Random();
				int amt = rand.nextInt(level / 2) + 1;
				theBlock.getWorld().dropItemNaturally(theBlock.getLocation(), new ItemStack(Material.FENCE, amt, data));
			}

			// LOG_2s
		} else if (theBlock.getType() == Material.LOG_2) {
			// STICK DROP
			if (Util.chance(5 * level, CONSTANTS.I_CARPENTRY_CHANCE_BOUND)) {
				Random rand = new Random();
				int amt = rand.nextInt(level) + 1;
				theBlock.getWorld().dropItemNaturally(theBlock.getLocation(), new ItemStack(Material.STICK, amt));
			}
			// FENCE DROP
			if (Util.chance(2 * level, CONSTANTS.I_CARPENTRY_CHANCE_BOUND)) {
				Byte data = theBlock.getData();
				Random rand = new Random();
				int amt = rand.nextInt(level / 2) + 1;
				theBlock.getWorld().dropItemNaturally(theBlock.getLocation(), new ItemStack(Material.FENCE, amt, data));
			}
		}

	}

	private void appleSeed(Block theBlock, int level) {

		// For Logs

		if (theBlock.getType() == Material.LOG || theBlock.getType() == Material.LOG_2) {
			// Apple Drop
			Random rand = new Random();

			// Higher the level, higher chance it fires
			// At max lvl(3) there is 16.6% chance of it firing (5.5% for each lvl)
			// Honestly might need to be lower chance, people will be swimming in apples
			int fireChance = rand.nextInt(CONSTANTS.I_APPLESEED_CHANCE_BOUND) + 1;
			int amt = rand.nextInt(level) + 1;

			if (fireChance <= level) {

				theBlock.getWorld().dropItemNaturally(theBlock.getLocation(), new ItemStack(Material.APPLE, amt));

			}

			// And for fun, a 1/100 chance for gapple drop
			fireChance = rand.nextInt(100) + 1;
			if (fireChance == 100) {

				theBlock.getWorld().dropItemNaturally(theBlock.getLocation(),
						new ItemStack(Material.GOLDEN_APPLE, amt));

			}

		}

	}
        
        @EventHandler
        public void playerInteract(PlayerInteractEvent event){
            
            if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if(event.getClickedBlock().getType() == Material.DIRT && Util.isHoe(event.getPlayer().getInventory().getItemInMainHand())){



                Block block = event.getClickedBlock();
                ItemStack mainHand = event.getPlayer().getInventory().getItemInMainHand();
                Map<CustomEnchantment, Integer> enchants = CustomEnchantmentManager.getCustomEnchantments(mainHand);

                if (Util.isHoe(event.getItem())) {

                                    // Green Thumb
                                    if (enchants.containsKey(CustomEnchantment.GREEN_THUMB)) {
                                            if (block.getType() == Material.DIRT || block.getType() == Material.GRASS) {
                                                
                                                    double x = block.getX();
                                                    double y = block.getY();
                                                    double z = block.getZ();

                                                    Block eastBlock = block.getRelative(BlockFace.NORTH);
                                                    Block westBlock =  block.getRelative(BlockFace.WEST);
                                                    Block northBlock =  block.getRelative(BlockFace.NORTH);
                                                    Block southBlock =  block.getRelative(BlockFace.SOUTH);

                                                    // Because corners are all handled @ lvl 3, I just throw em in a list
                                                    ArrayList<Block> cornerBlocks = new ArrayList<Block>();

                                                    cornerBlocks.add(block.getRelative(BlockFace.NORTH_EAST));
                                                    cornerBlocks.add(block.getRelative(BlockFace.NORTH_WEST));
                                                    cornerBlocks.add(block.getRelative(BlockFace.SOUTH_EAST));
                                                    cornerBlocks.add(block.getRelative(BlockFace.SOUTH_WEST));

                                                    int level = enchants.get(CustomEnchantment.GREEN_THUMB);

                                                    if (level >= 1) {

                                                            // Farm blocks to east/west of target block
                                                            if (eastBlock.getType() == Material.DIRT && eastBlock.getRelative(BlockFace.UP).getType() == Material.AIR) {
                                                                    // setBlock farmland
                                                                    eastBlock.setType(Material.SOIL);
                                                            }
                                                            if (westBlock.getType() == Material.DIRT && westBlock.getRelative(BlockFace.UP).getType() == Material.AIR) {
                                                                    westBlock.setType(Material.SOIL);
                                                            }
                                                    }

                                                    if (level >= 2) {
                                                            // Farm blocks to north/south of target block
                                                            if (northBlock.getType() == Material.DIRT && northBlock.getRelative(BlockFace.UP).getType() == Material.AIR) {
                                                                    // setBlock farmland
                                                                    northBlock.setType(Material.SOIL);
                                                            }
                                                            if (southBlock.getType() == Material.DIRT && southBlock.getRelative(BlockFace.UP).getType() == Material.AIR) {
                                                                    // setBlock farmland
                                                                    southBlock.setType(Material.SOIL);
                                                            }

                                                    }
                                                    if (level >= 3) {

                                                            // Farm blocks in corners
                                                            // Lvl 3 ench will make 3X3 farmland for all elligible blocks (must be dirt &
                                                            // uncovered)
                                                            for (Block corner : cornerBlocks) {

                                                                    if (corner.getType() == Material.DIRT && corner.getRelative(BlockFace.UP).getType() == Material.AIR) {

                                                                            corner.setType(Material.SOIL);

                                                                    }

                                                            }

                                                    }

                                            }
                                    } // green thumb end
                            } 

                }
            }
        }

}
