package com.ibexmc.gab.commands;

import com.ibexmc.gab.Gab;
import com.ibexmc.gab.data.Channel;
import com.ibexmc.gab.util.Permission;
import com.ibexmc.gab.util.StringFunctions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
  Commands
    /chadmin create <key>                               // Creates a new channel
    /chadmin set prefix <new prefix>                    // Sets the channel prefix
    /chadmin set name <new name>                        // Sets the channel name
    /chadmin set discord <true/false>                   // Sets the Send to Discord flag
    /chadmin set color <color code>                     // Sets the default chat color
    /chadmin set banned_word add <word>                 // Adds a banned word
    /chadmin set banned_word del <word>                 // Removes a banned word
    /chadmin get prefix                                 // Shows the current prefix
    /chadmin get name                                   // Shows the current name
    /chadmin get discord                                // Displays current Send to Discord flag
    /chadmin get color                                  // Displays the current default chat color
    /chadmin get banned_word                            // Lists all banned words
    /chadmin delete <key>                               // Deletes a channel
    /chadmin list                                       // Lists all the current chatters online
    /chadmin mute <player>                              // Mutes player in current channel (if player run)
    /chadmin mute <player> -global                      // Mutes player globally
    /chadmin mute <player> <channel>                    // Mutes player in specified channel
    /chadmin unmute <player>                            // Unmutes player in current channel (if player run)
    /chadmin unmute <player> -global                    // Unmutes player globally
    /chadmin unmute <player> <channel>                  // Unmutes player in specified channel
    /chadmin kick <player>                              // Kicks player from current channel (if player run)
    /chadmin kick <player> <channel>                    // Kicks player from specified channel
  Aliases:
    /chatadmin
    /chadmin
    /cha
  Permissions:
    Channel create      gab.admin
    Channel delete      gab.delete
    Set                 gab.channel.<channel key>.admin
    Get                 gab.channel.<channel key>.admin
    Channel list        gab.channel.<channel key>
    Channel mute        gab.channel.<channel key>.admin
    Channel unmute      gab.channel.<channel key>.admin
    Channel kick        gab.channel.<channel key>.admin
    Global mute         gab.admin
    Global unmute       gab.admin
*/
public class ChatAdminCommand implements CommandExecutor, TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> tabCompletions = new ArrayList<>();
        Map<Integer, String> arguments = StringFunctions.getArguments(args);
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (arguments.size() < 2) {
                List<Channel> allowedChannels = Permission.allowedChannels(player);
                if (allowedChannels != null) {
                    for (Channel allowedChannel : allowedChannels) {
                        tabCompletions.add(allowedChannel.getKey());
                    }
                }
            }
        }
        return tabCompletions;
    }
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
                        "chx_usage",
                        "&lUsage:&r /<%cmd%> &b<channel>",
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
