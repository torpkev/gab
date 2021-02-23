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

public class ChatLeaveCommand implements CommandExecutor, TabCompleter {
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
        if (!plugin.data.enabled) {
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
                chxPlaceHolder.put("%cmd%", label);
                plugin.message().sender(
                        "chx_usage",
                        "&lUsage:&r /%cmd% &b<channel>",
                        sender,
                        true,
                        chxPlaceHolder
                );
                return true;
            }
            if (arguments.get(0).equalsIgnoreCase("global")) {
                plugin.message().sender(
                        "leave_global_fail",
                        "&cYou cannot leave global chat",
                        sender,
                        true,
                        null
                );
                return true;
            }
            if (plugin.data.validChannel(arguments.get(0))) {
                channel = plugin.data.getChannel(arguments.get(0));
                if (!channel.getKey().equalsIgnoreCase("global")) {
                    if (plugin.data.playerInChannel(player.getUniqueId(), channel.getKey())) {
                        // Get the current channel the player is chatting in
                        Channel curChatChannel = plugin.data.currentChatChannel(player.getUniqueId());
                        // If the player is currently chatting in the channel they are leaving, then
                        // move them into global
                        if (curChatChannel.getKey().equalsIgnoreCase(channel.getKey())) {
                            Channel global = new Channel();
                            plugin.data.setCurrentChannel(player.getUniqueId(), global.getKey());
                            // Message the player about the change in channel
                            HashMap<String, String> placeHolder = new HashMap<>();
                            placeHolder.put("%channel%", global.getName());
                            plugin.message().sender(
                                    "current_channel",
                                    "&3You are now talking in %channel%",
                                    sender,
                                    true,
                                    placeHolder
                            );
                        }
                        // Remove the player from the channel
                        plugin.data.removePlayerFromChannel(player.getUniqueId(), channel.getKey());
                        HashMap<String, String> placeHolder = new HashMap<>();
                        placeHolder.put("%channel%", channel.getName());
                        plugin.message().sender(
                                "left_channel",
                                "&cYou have left %channel%",
                                sender,
                                true,
                                placeHolder
                        );
                    } else {
                        // Not currently in this channel
                        plugin.message().sender(
                                "not_in_channel",
                                "&cYou are not currently in this channel",
                                sender,
                                true,
                                null
                        );
                        return true;
                    }
                } else {
                    // You cannot leave global
                    plugin.message().sender(
                            "leave_global_fail",
                            "&cYou cannot leave global chat",
                            sender,
                            true,
                            null
                    );
                    return true;
                }
            } else {
                // Invalid Channel
                plugin.message().sender(
                        "invalid_channel",
                        "&cNo such channel exists",
                        sender,
                        true,
                        null
                );
                return true;
            }
        } else {
            // usage
            HashMap<String, String> chxPlaceHolder = new HashMap<>();
            chxPlaceHolder.put("%cmd%", label);
            plugin.message().sender(
                    "chx_usage",
                    "&lUsage:&r /%cmd% &b<channel>",
                    sender,
                    true,
                    chxPlaceHolder
            );
            return true;
        }

        return true;
    }
}
