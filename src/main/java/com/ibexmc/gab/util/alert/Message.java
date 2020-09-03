package com.ibexmc.gab.util.alert;

import com.ibexmc.gab.Gab;
import com.ibexmc.gab.util.StringFunctions;
import com.ibexmc.gab.util.log.Error;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Message {

    Gab plugin;
    public Message(Gab plugin) {
        this.plugin = plugin;
    }

    public void sender(String messageCode, String message, CommandSender sender, boolean includeHeader, HashMap<String, String> placeHolders) {
        String header = "";
        if (includeHeader)
        {
            header = StringFunctions.colorCode(plugin.locale().getLocaleTextByCode("prefix", "&3[&fGab&3] "));
        }
        if (sender != null)
        {
            if (message != null) {
                if (messageCode != "na" && messageCode != "") {
                    if (plugin.locale().localeExists(messageCode)) {
                        message = plugin.locale().getLocaleTextByCode(
                                messageCode,
                                message,
                                placeHolders
                        );
                    } else {
                        plugin.log().console(
                                "Missing Locale Code",
                                "Locale Code: " +
                                        messageCode + " missing.  Default text = " +
                                        message
                        );
                        if (placeHolders != null) {
                            for (Map.Entry<String, String> params : placeHolders.entrySet()) {
                                message = message.replace(params.getKey(), params.getValue());
                            }
                        }
                    }
                }
                sender.sendMessage(header + ChatColor.WHITE + StringFunctions.colorCode(message));
            }
        }
    }
    public void player(String messageCode, String message, Player player, boolean includeHeader, HashMap<String, String> placeHolders) {
        String header = "";
        if (includeHeader)
        {
            header = StringFunctions.colorCode(plugin.locale().getLocaleTextByCode("prefix", "&3[&fGab&3] "));
        }
        if (player != null)
        {
            if (Bukkit.getPlayer(player.getUniqueId()) != null) {
                if (message != null) {
                    if (plugin.locale().localeExists(messageCode)) {
                        message = plugin.locale().getLocaleTextByCode(
                                messageCode,
                                message,
                                placeHolders
                        );
                    } else {
                        plugin.log().console(
                                "Missing Locale Code",
                                "Locale Code: " +
                                        messageCode + " missing.  Default text = " +
                                        message
                        );
                        if (placeHolders != null) {
                            for (Map.Entry<String, String> params : placeHolders.entrySet()) {
                                message = message.replace(params.getKey(), params.getValue());
                            }
                        }
                    }
                    player.sendMessage(header + ChatColor.WHITE + StringFunctions.colorCode(message));
                }
            } else {
                plugin.error().save(
                        "Message.player.001",
                        "Message",
                        "player(String, String, Player, boolean, HashMap<String, String>)",
                        "Invalid Player",
                        "Player not online",
                        Error.Severity.WARN,
                        null
                );
            }
        } else {
            plugin.error().save(
                    "Message.player.002",
                    "Message",
                    "player(String, String, Player, boolean, HashMap<String, String>)",
                    "Invalid Player",
                    "Null player provided",
                    Error.Severity.WARN,
                    null
            );
        }
    }
}
