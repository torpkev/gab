package com.ibexmc.gab.commands;

import com.ibexmc.gab.Gab;
import com.ibexmc.gab.util.FileUtils;
import com.ibexmc.gab.util.Permission;
import com.ibexmc.gab.util.log.Error;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GabCommand implements CommandExecutor, TabCompleter {
    private Map<Integer, String> getArguments(String[] args) {
        Map<Integer, String> arguments = new HashMap<>();
        int argumentCount = 0;
        if (args.length > 0) // Check to make sure we have some arguments
        {
            for (Object o : args) {
                arguments.put(argumentCount, o.toString());
                argumentCount++;
            }
        }
        return arguments;
    }
    @Override
    public List<String> onTabComplete (CommandSender sender, Command cmd, String label, String[] args){
        List<String> tabCompletions = new ArrayList<>();
        Map<Integer, String> arguments = getArguments(args);
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (Permission.hasPermission(player, Permission.PermissionType.ADMIN)) {
                tabCompletions.add("reload");
            }
        }
        return tabCompletions;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Gab plugin = Gab.getInstance();

        Map<Integer, String> arguments = getArguments(args);

        Player player = null;

        //Chunk chunk = player.getLocation().getBlock().getChunk();

        if (sender instanceof Player) {
            player = (Player) sender;
        }

        boolean hasAdmin = false;
        if (player != null) {
            if (Permission.hasPermission(player, Permission.PermissionType.ADMIN)) {
                hasAdmin = true;
            }
        } else {
            hasAdmin = true; // Console automatically has pocket.admin
        }


        boolean displayUsage = false;

        plugin.log().quick("Count: " + arguments.size());
        for (Map.Entry<Integer, String> entry : arguments.entrySet()) {
            plugin.log().quick("Key: " + entry.getKey() + " Value: " + entry.getValue());
        }

        if (arguments.containsKey(0)) {
            switch (arguments.get(0)) {
                case "toggle":
                    if (hasAdmin) {
                        plugin.data().toggleEnabled();
                        if (plugin.data().isEnabled()) {
                            plugin.message().sender(
                                    "gab_enabled",
                                    "&2Gab is enabled",
                                    sender,
                                    true,
                                    null
                            );
                        } else {
                            plugin.message().sender(
                                    "gab_disabled",
                                    "&cGab is disabled",
                                    sender,
                                    true,
                                    null
                            );
                        }
                    }
                case "reload":
                    // Reloads the Configuration file
                    if (hasAdmin) {
                        File configFile = FileUtils.getConfigYml();
                        if (configFile != null) {
                            try {
                                // Set the reloading flag to true to stop people using Domain
                                plugin.data().load();
                                // Alert the sender
                                plugin.message().sender(
                                        "reload_complete",
                                        "&2Gab configuration has been reloaded",
                                        sender,
                                        true,
                                        null
                                );
                            } catch (Exception ex) {
                                Gab.getInstance().error().save(
                                        "GabCommand.reload.001",
                                        "GabCommand",
                                        "onCommand().reload",
                                        "Unexpected error reloading config",
                                        "Config reload failed.  Error: " + ex.getMessage(),
                                        Error.Severity.CRITICAL,
                                        null
                                );
                                plugin.message().sender(
                                        "reload_failed",
                                        "&4Gab reload failed - Please check the logs",
                                        sender,
                                        true,
                                        null
                                );
                            }
                        } else {
                            plugin.message().sender(
                                    "reload_failed",
                                    "&4Gab reload failed - Please check the logs",
                                    sender,
                                    true,
                                    null
                            );
                        }
                    } else {
                        // no perm
                        plugin.message().player(
                                "no_perm",
                                "&4Sorry, you do not have permission to do that",
                                player,
                                true,
                                null
                        );
                    }
                    break;
                default:
                    displayUsage = true;
                    break;
            }
        } else {
            displayUsage = true;
        }

        if (displayUsage) {
            if (!hasAdmin) {
                // pocket blurb
                plugin.message().sender(
                        "gab_desc",
                        "&3Gab &r- &bChat",
                        sender,
                        true,
                        null
                );
                return true;
            }
            if (hasAdmin) {
                // pocket usage: admin & use
                plugin.message().sender(
                        "domain_usage_admin",
                        "&lUsage: &r&b/pocket &dreload&f|&dinfo",
                        sender,
                        true,
                        null
                );
                return true;
            }
            if (!hasAdmin) {
                // pocket usage: use
                plugin.message().sender(
                        "pocket_usage_user",
                        "&lUsage: &r&b/pocket &dinfo &7(Displays info about the Domain item in your main hand)",
                        sender,
                        true,
                        null
                );
                return true;
            }
        }
        return true;
    }
}

