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

public class UpdateConstantsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.isOp()) {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
                return true;
            } // if
        }

        Scanner fileread = null;
        try {

            fileread = new Scanner(new File(CivEnchant.plugin.getDataFolder(), "civenchantconfig.txt"));

        } catch (Exception e) {
            Bukkit.getLogger().info("'civenchantconfig.txt' not found, put file in plugins/civenchant folder");
            return true;
        }

        int updateCount = 0;

        while (fileread.hasNextLine()) {

            String line[] = fileread.nextLine().split(" ");
            String field = line[0];
            String value = line[1];

            Bukkit.getLogger().info(field + " " + value);

            for (Field constant : CONSTANTS.class.getDeclaredFields()) {

                if (field.equals(constant.getName())) {
                    try {

                        constant.setAccessible(true);

                        Field modifiers = Field.class.getDeclaredField("modifiers");
                        modifiers.setAccessible(true);
                        modifiers.setInt(constant, constant.getModifiers() & ~Modifier.FINAL);

                        if (constant.getType().equals(Integer.TYPE)) {
                            constant.setInt(constant, Integer.parseInt(value));
                        } else if (constant.getType().equals(Double.TYPE)) {
                            constant.setDouble(constant, Double.parseDouble(value));
                        } else {
                            constant.set(constant, value);
                        }

                        updateCount++;
                    } catch (Exception e) {
                        Bukkit.getLogger().info("Error setting value");
                        e.printStackTrace();
                    }
                }

            }

        }

        Bukkit.getLogger().info("Done Updating Values");
        Bukkit.getLogger().info("Total Fields:  " + CONSTANTS.class.getDeclaredFields().length);
        Bukkit.getLogger().info("Total Updated: " + updateCount);
        fileread.close();
        return true;
    } // onCommand

} // class
