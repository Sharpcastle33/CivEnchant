package com.gmail.sharpcastle33.util;

import com.gmail.sharpcastle33.CivEnchant;
import java.util.ArrayList;
import org.bukkit.entity.Player;
import com.gmail.sharpcastle33.enchantments.CustomEnchantment;

public class CooldownManager {

	public ArrayList<Player> secondWind;
	public ArrayList<Player> lastStand;
	public ArrayList<Player> adrenaline;

	public ArrayList<Player> vitalityPlayers;
	public ArrayList<RegenerationEffect> vitalityEffects;
	public ArrayList<RageEffect> rageEffects;
	public ArrayList<Player> ragePlayers;

	CivEnchant plugin;

	public CooldownManager() {

		secondWind = new ArrayList<Player>();
		lastStand = new ArrayList<Player>();
		adrenaline = new ArrayList<Player>();

		vitalityPlayers = new ArrayList<Player>();
		vitalityEffects = new ArrayList<RegenerationEffect>();

		rageEffects = new ArrayList<RageEffect>();
		ragePlayers = new ArrayList<Player>();

		plugin = CivEnchant.plugin;

	}

	public void addRegen(Player player, int regenAmount) {

		vitalityPlayers.add(player);
		vitalityEffects.add(new RegenerationEffect(player, regenAmount, vitalityEffects, vitalityPlayers));

	}

	public void add(Player player, CustomEnchantment ench, int duration) {

		EnchantmentCooldown cd = new EnchantmentCooldown(null, 0, null);

		switch (ench) {

		case SECOND_WIND:

			cd.setList(secondWind);
			secondWind.add(player);

			cd = new EnchantmentCooldown(player, duration, secondWind);
			cd.runTask(plugin);

			break;

		case LAST_STAND:

			cd.setList(lastStand);
			lastStand.add(player);

			cd = new EnchantmentCooldown(player, duration, lastStand);
			cd.runTask(plugin);

			break;

		case ADRENALINE:

			cd.setList(adrenaline);
			adrenaline.add(player);

			cd = new EnchantmentCooldown(player, duration, adrenaline);
			cd.runTask(plugin);

			break;
		default:
			break;

		}

		cd.runTask(plugin);

	}

}
