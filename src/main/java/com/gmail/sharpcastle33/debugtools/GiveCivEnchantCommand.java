package com.gmail.sharpcastle33.debugtools;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.gmail.sharpcastle33.enchantments.CustomEnchantment;
import com.gmail.sharpcastle33.enchantments.CustomEnchantmentManager;

public class GiveCivEnchantCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(!player.isOp()) { 
				player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
				return true;
			} // if
			
			if(args.length != 2) return false;
			
			CustomEnchantment customEnchant;
			try {
				customEnchant = CustomEnchantment.valueOf(args[0]);
				if(customEnchant == null) return false;
			} catch(IllegalArgumentException e) {
				player.sendMessage(ChatColor.RED + "Invalid Enchantment (try /lce to list all enchants)");
				return true;
			} // try/catch
			
			int level;
			try {
				level = Integer.valueOf(args[1]).intValue();
			} catch(NumberFormatException e) {
				return false;
			} // try/catch
			
			ItemStack item = player.getInventory().getItemInMainHand();
			if(item == null || item.getAmount() == 0 || item.getAmount() > 1) return false;
			
			CustomEnchantmentManager.addCustomEnchantment(item, customEnchant, level);
			
			return true;
		} // if
		
		return true;
	} // onCommand

} // class
