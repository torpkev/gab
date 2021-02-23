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

public class ChatCommand implements CommandExecutor, TabCompleter {
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

        plugin.log().quick(label);

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

            // If the argument passed is ?, then display usage
            if (arguments.get(0).equalsIgnoreCase("?")) {
                plugin.message().sender(
                        "chat_usage",
                        "&lUsage:&r /ch &b<channel> - Puts you in the channel.  &6(If no channel is passed, you will be returned to global)",
                        sender,
                        true,
                        null
                );
                return true;
            }

            // Get the channnel
            channel =  plugin.data().getChannel(arguments.get(0));
        } else {
            // Get the default channel (global)
            channel = new Channel();
        }

        // Check if the channel was returned
        if (channel != null) {
            if (Permission.isChatter(player, channel.getName().toLowerCase())) {
                // Change the players current channel (sending chat)
                plugin.data().setCurrentChannel(player.getUniqueId(), channel.getKey().toLowerCase());

                // Add the player to the channel (read others chatting)
                plugin.data().putPlayerInChannel(player.getUniqueId(), channel.getKey().toLowerCase());

                // Message the player about the change in channel
                HashMap<String, String> placeHolder = new HashMap<>();
                placeHolder.put("<%channel%>", channel.getName());
                plugin.message().sender(
                        "current_channel",
                        "&3You are now talking in <%channel%>",
                        sender,
                        true,
                        placeHolder
                );
            } else {
                plugin.message().sender(
                        "no_channel_perm",
                        "&cSorry, you do not have permission to join this channel",
                        sender,
                        true,
                        null
                );
            }
        } else {
            // No channel was returned with the key provided - return error
            // As we didn't move the player from their current channel, no
            // further action is needed
            plugin.message().sender(
                    "invalid_channel",
                    "&cNo such channel exists",
                    sender,
                    true,
                    null
            );
        }
        return true;
    }
}
