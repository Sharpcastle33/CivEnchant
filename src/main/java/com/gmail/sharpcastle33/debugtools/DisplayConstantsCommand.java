package com.gmail.sharpcastle33.debugtools;

import com.gmail.sharpcastle33.CivEnchant;
import com.gmail.sharpcastle33.util.CONSTANTS;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Scanner;
import org.bukkit.Bukkit;

public class DisplayConstantsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.isOp()) {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
                return true;
            } // if
        }

        for (Field field : CONSTANTS.class.getDeclaredFields()) {

            try {
                Bukkit.getLogger().info(field.getName() + " " + field.get(String.class));
            } catch (IllegalAccessException e) {
                Bukkit.getLogger().info("Cannot Display Values");

            }
        }
        return true;
    } // onCommand

} // class
