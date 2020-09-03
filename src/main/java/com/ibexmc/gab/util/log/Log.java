package com.ibexmc.gab.util.log;

import com.ibexmc.gab.Gab;
import com.ibexmc.gab.util.StringFunctions;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

public class Log {
    Gab plugin;
    public Log(Gab plugin) {
        this.plugin = plugin;
    }

    public void console(String title, String message) {
        ConsoleCommandSender ccs = plugin.getServer().getConsoleSender();
        ccs.sendMessage(
                ChatColor.DARK_PURPLE + "[" + plugin.getInternalName() + ".Log]" +
                        ChatColor.AQUA + "[" + title + "] " + ChatColor.WHITE + message);
    }
    public void quick(String message)
    {
        ConsoleCommandSender ccs = plugin.getServer().getConsoleSender();
        ccs.sendMessage(
                ChatColor.DARK_PURPLE + "[" + plugin.getInternalName() + ".Log]" +
                        ChatColor.AQUA + "[QUICK] " + ChatColor.WHITE + StringFunctions.colorCode(message));
    }
    public void quick(String message, boolean prefix) {
        if (prefix) {
            quick(message);
        } else {
            ConsoleCommandSender ccs = plugin.getServer().getConsoleSender();
            ccs.sendMessage(StringFunctions.colorCode(message));
        }
    }
}
