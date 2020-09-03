package com.ibexmc.gab.util.log;

import com.ibexmc.gab.Gab;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

public class Dev {
    Gab plugin;
    public Dev(Gab plugin) {
        this.plugin = plugin;
    }

    public void log(String className, String functionName, String message, String additional)
    {
        if (additional.length() > 0) {
            additional = "\n" + "Additional Information:\n" + additional;
        }
        ConsoleCommandSender ccs = plugin.getServer().getConsoleSender();
        ccs.sendMessage(
                ChatColor.LIGHT_PURPLE + "[" + plugin.getInternalName() + ".Debug]" +
                        ChatColor.AQUA + "[" + className + "." + functionName + "] " + ChatColor.WHITE + message + additional);
    }
}
