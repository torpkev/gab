package com.ibexmc.gab.commands;

import com.ibexmc.gab.util.functions.PlayerFunctions;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TabCompletions {
    public static List<String> gab(CommandSender sender, Map<Integer, String> arguments) {
        List<String> tabCompletions = new ArrayList<>();
        if (sender instanceof Player) {
            Player senderPlayer = (Player) sender;
            if (arguments.containsKey(0) && !arguments.containsKey(1)) {
                if (PlayerFunctions.hasPermission(senderPlayer, "gab.admin")) {
                    tabCompletions.add("debug");
                    tabCompletions.add("reload");
                    tabCompletions.add("version");
                }
            }
        }
        return tabCompletions;
    }

    public static List<String> channel(CommandSender sender, Map<Integer, String> arguments) {
        List<String> tabCompletions = new ArrayList<>();
        if (sender instanceof Player) {
            Player senderPlayer = (Player) sender;
            if (arguments.containsKey(0) && !arguments.containsKey(1)) {
                // List of channels
            }
        }
        return tabCompletions;
    }
}
