package com.ibexmc.gab.util.alert;

import com.ibexmc.gab.Gab;
import com.ibexmc.gab.util.log.Error;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Screen {

    Gab plugin;

    public Screen(Gab plugin) {
        this.plugin = plugin;
    }

    public void actionBar(Player player, String messageCode, String message, HashMap<String, String> placeHolders) {
        if (player != null) {
            if (Bukkit.getPlayer(player.getUniqueId()) != null) {
                player.spigot().sendMessage(
                        ChatMessageType.ACTION_BAR,
                        TextComponent.fromLegacyText(
                                plugin.locale().getLocaleTextByCode(
                                        messageCode,
                                        message,
                                        placeHolders
                                )
                        )
                );
            } else {
                plugin.error().save(
                        "Screen.actionBar.001",
                        "Screen",
                        "actionBar(Player, String)",
                        "Invalid Player",
                        "Player not online",
                        Error.Severity.WARN,
                        null
                );
            }
        } else {
            plugin.error().save(
                    "Screen.actionBar.002",
                    "Screen",
                    "actionBar(Player, String)",
                    "Invalid Player",
                    "Null player provided",
                    Error.Severity.WARN,
                    null
            );
        }
    }
    public void title(Player player, String titleCode, String title, String subtitleCode, String subtitle,
                      HashMap<String, String> placeHolder, int fadeIn, int stay, int fadeOut
    ) {
        if (player != null) {
            if (Bukkit.getPlayer(player.getUniqueId()) != null) {
                player.resetTitle();
                player.sendTitle(
                        plugin.locale().getLocaleTextByCode(titleCode, title, placeHolder),
                        plugin.locale().getLocaleTextByCode(subtitleCode, subtitle, placeHolder),
                        fadeIn,
                        stay,
                        fadeOut
                );
            } else {
                plugin.error().save(
                        "Screen.title.001",
                        "Screen",
                        "title(Player, String, String, String, String, HashMap<String, String>, int, int, int)",
                        "Invalid Player",
                        "Player not online",
                        Error.Severity.WARN,
                        null
                );
            }
        } else {
            plugin.error().save(
                    "Screen.title.002",
                    "Screen",
                    "title(Player, String, String, String, String, HashMap<String, String>, int, int, int)",
                    "Invalid Player",
                    "Null player provided",
                    Error.Severity.WARN,
                    null
            );
        }
    }
}
