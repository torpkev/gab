package com.ibexmc.gab.util.alert;

import com.ibexmc.gab.Gab;
import com.ibexmc.gab.util.StringFunctions;
import com.ibexmc.gab.util.log.Error;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Chat {

    Gab plugin;
    public Chat(Gab plugin) {
        this.plugin = plugin;
    }

    public void player(Player player, String message) {
        if (player != null)
        {
            if (Bukkit.getPlayer(player.getUniqueId()) != null) {
                if (message != null) {
                    player.sendMessage(StringFunctions.colorCode(message));
                } else {
                    plugin.error().save(
                            "Chat.player.001",
                            "Chat",
                            "player(Player, String)",
                            "Null message",
                            "Null message provided",
                            Error.Severity.WARN,
                            null
                    );
                }
            } else {
                plugin.error().save(
                        "Chat.player.002",
                        "Chat",
                        "player(Player, String)",
                        "Invalid Player",
                        "Player not online",
                        Error.Severity.WARN,
                        null
                );
            }
        } else {
            plugin.error().save(
                    "Chat.player.003",
                    "Chat",
                    "player(Player, Chat)",
                    "Invalid Player",
                    "Null player provided",
                    Error.Severity.WARN,
                    null
            );
        }
    }

    public void player(Player player, TextComponent message) {

        //plugin.log().quick("Original message: " + message);

        player.spigot().sendMessage(message);
        //TextComponent msg = new TextComponent(Locale.colorToString(getPrefix(player, channel)));
    }
}
