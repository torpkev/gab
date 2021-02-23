package com.ibexmc.gab.commands;

import com.ibexmc.gab.Gab;
import com.ibexmc.gab.data.Channel;
import com.ibexmc.gab.util.StringFunctions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/*
    /chme <action>
*/
public class ChatMeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Gab plugin = Gab.instance;

        // Check if the plugin is disabled
        if (!plugin.data().isEnabled()) {
            plugin.message().sender(
                    "gab_disabled",
                    "&cGab is disabled",
                    sender,
                    true,
                    null
            );
            return true;
        }

        Map<Integer, String> arguments = StringFunctions.getArguments(args);

        Player player;

        if (sender instanceof Player) {
            player = (Player) sender;
        } else {
            plugin.message().sender(
                    "no_console",
                    "&cSorry, you cannot use this command from the console",
                    sender,
                    true,
                    null
            );
            return true;
        }

        Channel channel = new Channel();

        if (arguments.containsKey(0)) {
            if (arguments.get(0).equalsIgnoreCase("?")) {
                // usage
                HashMap<String, String> chxPlaceHolder = new HashMap<>();
                chxPlaceHolder.put("<%cmd%>", label);
                plugin.message().sender(
                        "chme_usage",
                        "&lUsage:&r /<%cmd%> &b<action>",
                        sender,
                        true,
                        chxPlaceHolder
                );
                return true;
            }
        }
        return true;
    }
}
