package com.ibexmc.gab.commands;

import com.ibexmc.gab.Gab;
import com.ibexmc.gab.data.Channel;
import com.ibexmc.gab.util.Permission;
import com.ibexmc.gab.util.StringFunctions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatOtherCommand implements CommandExecutor, TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> tabCompletions = new ArrayList<>();
        Map<Integer, String> arguments = StringFunctions.getArguments(args);
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (arguments.size() == 1) {
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    tabCompletions.add(onlinePlayer.getName());
                }
            }
            if (arguments.containsKey(0)) {
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

        if (arguments.containsKey(0)) {
            if (arguments.get(0).equalsIgnoreCase("?")) {
                HashMap<String, String> choPlaceHolder = new HashMap<>();
                choPlaceHolder.put("%cmd%", label);
                plugin.message().sender(
                        "cho_usage",
                        "&lUsage:&r /%cmd% &b<player> <channel>",
                        sender,
                        true,
                        choPlaceHolder
                );
                return true;
            }
            boolean validPlayer = false;
            Player otherPlayer = null;
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (arguments.get(0).equalsIgnoreCase(ChatColor.stripColor(player.getDisplayName()))) {
                    validPlayer = true;
                    if (arguments.containsKey(1)) {
                        Channel channel = plugin.data.getChannel(arguments.get(1));
                        if (channel != null) {
                            if (plugin.data.playerInChannel(player.getUniqueId(), channel.getKey())) {
                                plugin.message().sender(
                                        "already_chatter",
                                        "&cPlayer is already in this channel",
                                        sender,
                                        true,
                                        null
                                );
                                return true;
                            }
                            if (Permission.isChatter(player, channel.getName().toLowerCase())) {
                                // Add the player to the channel (read others chatting)
                                plugin.data.putPlayerInChannel(player.getUniqueId(), channel.getKey().toLowerCase());

                                // Message the player about the change in channel
                                HashMap<String, String> placeHolder = new HashMap<>();
                                placeHolder.put("%channel%", channel.getName());
                                plugin.message().player(
                                        "added_channel",
                                        "&3You had been added to %channel%",
                                        player,
                                        true,
                                        placeHolder
                                );
                                plugin.message().sender(
                                        "player_added_channel",
                                        "&3Player has been added to the channel",
                                        sender,
                                        true,
                                        null
                                );

                                return true;
                            } else {
                                plugin.message().sender(
                                        "player_no_perm",
                                        "&cPlayer does not have permission to be in this channel",
                                        sender,
                                        true,
                                        null
                                );
                            }
                        } else {
                            plugin.message().sender(
                                    "invalid_channel",
                                    "&cInvalid channel",
                                    sender,
                                    true,
                                    null
                            );
                            return true;
                        }
                    } else {
                        HashMap<String, String> choPlaceHolder = new HashMap<>();
                        choPlaceHolder.put("%cmd%", label);
                        plugin.message().sender(
                                "cho_usage",
                                "&lUsage:&r /%cmd% &b<player> <channel>",
                                sender,
                                true,
                                choPlaceHolder
                        );
                        return true;
                    }
                    break;
                }
            }

            if (!validPlayer) {
                plugin.message().sender(
                        "invalid_player",
                        "&cInvalid player",
                        sender,
                        true,
                        null
                );
                return true;
            }

        } else {
            HashMap<String, String> choPlaceHolder = new HashMap<>();
            choPlaceHolder.put("%cmd%", label);
            plugin.message().sender(
                    "cho_usage",
                    "&lUsage:&r /%cmd% &b<player> <channel>",
                    sender,
                    true,
                    choPlaceHolder
            );
            return true;
        }

        return true;
    }
}
