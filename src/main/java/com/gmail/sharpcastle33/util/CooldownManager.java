package com.gmail.sharpcastle33.util;

import com.gmail.sharpcastle33.CivEnchant;
import java.util.ArrayList;
import org.bukkit.entity.Player;
import com.gmail.sharpcastle33.enchantments.CustomEnchantment;
import java.util.logging.Level;
import org.bukkit.Bukkit;

public class CooldownManager {

	static public ArrayList<Player> secondWind;
	static public ArrayList<Player> lastStand;
	static public ArrayList<Player> adrenaline;
	static public ArrayList<Player> divineIntervention;

	static public ArrayList<Player> vitalityPlayers;
	static public ArrayList<RegenerationEffect> vitalityEffects;
	static public ArrayList<RageEffect> rageEffects;
	static public ArrayList<Player> ragePlayers;
        static public ArrayList<Player> DIPlayers;

	

	public CooldownManager() {

		secondWind = new ArrayList<Player>();
		lastStand = new ArrayList<Player>();
	    divineIntervention = new ArrayList<Player>();

		adrenaline = new ArrayList<Player>();

		vitalityPlayers = new ArrayList<Player>();
		vitalityEffects = new ArrayList<RegenerationEffect>();

		rageEffects = new ArrayList<RageEffect>();
		ragePlayers = new ArrayList<Player>();

                DIPlayers = new ArrayList<Player>();
                
		

	}

        //Currently only used for vitality...
	public void addRegen(Player player, int regenAmount) {

		vitalityPlayers.add(player);
		//vitalityEffects.add(new RegenerationEffect(player, regenAmount, vitalityEffects, vitalityPlayers));

	}

	public void add(Player player, CustomEnchantment ench, int duration) {

		EnchantmentCooldown cd = new EnchantmentCooldown(null, 0, null);

		switch (ench) {

		case SECOND_WIND:

			cd.setList(secondWind);
			secondWind.add(player);

			cd = new EnchantmentCooldown(player, duration, secondWind);
			cd.runTaskTimer(CivEnchant.plugin,0,0);

			break;
	   case DIVINE_INTERVENTION:

            cd.setList(divineIntervention);
            divineIntervention.add(player);

            cd = new EnchantmentCooldown(player, duration, divineIntervention);
            cd.runTaskTimer(CivEnchant.plugin,0,0);

            break;


		case LAST_STAND:

			lastStand.add(player);
                        

                        //Bukkit.getLogger().info("Step 1");
			cd.setList(lastStand);
                        
                       // Bukkit.getLogger().info("Step 2");
			cd = new EnchantmentCooldown(player, duration, lastStand);
                        
                        //Bukkit.getLogger().info("Step 3");
			cd.runTaskTimer(CivEnchant.plugin,0,0);
                        
                        //Bukkit.getLogger().info("Step 4");

			break;

		case ADRENALINE:

			/*cd.setList(adrenaline);
			adrenaline.add(player);

			cd = new EnchantmentCooldown(player, duration, adrenaline);
			cd.runTaskTimer(CivEnchant.plugin,0,0);
*/
			break;
             
		//Duplicate, not sure if correct.
        /*case DIVINE_INTERVENTION:
                        cd.setList(DIPlayers);
                        DIPlayers.add(player);
                        
                        cd = new EnchantmentCooldown(player, duration, DIPlayers);
			cd.runTaskTimer(CivEnchant.plugin,0,0);

			break;*/
		default:
                    
                        Bukkit.getLogger().info("Wrong CustomEnchantment passed to CooldownManager.add()");
			break;

		}


	}

}
