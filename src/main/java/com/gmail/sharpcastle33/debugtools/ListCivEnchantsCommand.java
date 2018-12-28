package com.gmail.sharpcastle33.debugtools;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.gmail.sharpcastle33.enchantments.CustomEnchantment;

public class ListCivEnchantsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            return true;
        } // if

        String out = "";

        for (CustomEnchantment ce : CustomEnchantment.values()) {
            out += ce.toString() + ", ";
        }

        out = out.substring(0, out.length() - 2);

        sender.sendMessage(ChatColor.AQUA + out);

        return true;
    } // onCommand

} // class
