package com.ibexmc.gab.util;

import com.ibexmc.gab.Gab;
import com.ibexmc.gab.util.functions.StringFunctions;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class Send {

    /**
     * Gets the prefix for messages
     * @return Message prefix
     */
    private static String prefix() {
        return Gab.instance.locale.get("prefix", "&f[&7Clans&f]&r ", null);
    }

    /**
     * Sends a message to an offline player.  If the player is online, it will send as a regular
     * message, if the player is offline, it will add a message to the message queue for the player
     * @param message Message to send
     * @param offlinePlayer OfflinePlayer to send message to
     * @param prefix If true, add prefix
     */
    public static void offlinePlayer(String message, OfflinePlayer offlinePlayer, boolean prefix) {
        Player player = offlinePlayer.getPlayer();
        if (player == null) {
            //TODO: Send an offline message
        } else {
            player(message, player, prefix);
        }
    }

    /**
     * Sends a message to an offline player using a locale code.  If the player is online, it will send as a regular
     * message, if the player is offline, it will add a message to the message queue for the player
     * @param code Code to lookup
     * @param defaultText Default text to send if code not found
     * @param offlinePlayer OfflinePlayer to send message to
     * @param prefix If true, add prefix
     * @param placeHolders Map of String, String for replacements
     */
    public static void offlinePlayer(String code, String defaultText, OfflinePlayer offlinePlayer, boolean prefix, Map<String, String> placeHolders) {
        String message = Gab.instance.locale.get(code, defaultText, placeHolders);
        offlinePlayer(message, offlinePlayer, prefix);
    }

    /**
     * Sends a message to an online player
     * @param message Message to send
     * @param player Player to send message to
     * @param prefix If true, add prefix
     */
    public static void player(String message, Player player, boolean prefix) {
        if (player == null) {
            return;
        }
        if (StringFunctions.isNullOrEmpty(message)) {
            return;
        }
        if (prefix) {
            message = prefix() + message;
        }
        player.sendMessage(StringFunctions.color(message));
    }

    /**
     * Sends a message to an online player using a locale code
     * @param code Code to lookup
     * @param defaultText Default text to send if code not found
     * @param player Player to send message to
     * @param prefix If true, add prefix
     * @param placeHolders Map of String, String for replacements
     */
    public static void player(String code, String defaultText, Player player, boolean prefix, Map<String, String> placeHolders) {
        String message = Gab.instance.locale.get(code, defaultText, placeHolders);
        player(message, player, prefix);
    }

    /**
     * Sends a message to a CommandSender (player or console)
     * @param message Message to send
     * @param sender CommandSender to send message to
     * @param prefix If true, add prefix
     */
    public static void sender(String message, CommandSender sender, boolean prefix) {
        if (sender == null) {
            return;
        }
        if (StringFunctions.isNullOrEmpty(message)) {
            return;
        }
        if (prefix) {
            message = prefix() + message;
        }
        sender.sendMessage(StringFunctions.color(message));
    }

    /**
     * Sends a message to a CommandSender using a locale code
     * @param code Code to lookup
     * @param defaultText Default text to send if code not found
     * @param sender CommandSender to send message to
     * @param prefix If true, add prefix
     * @param placeHolders Map of String, String for replacements
     */
    public static void sender(String code, String defaultText, CommandSender sender, boolean prefix, Map<String, String> placeHolders) {
        String message = Gab.instance.locale.get(code, defaultText, placeHolders);
        sender(message, sender, prefix);
    }

    /**
     * Sends a message using a TextComponent to a CommandSender using a locale code
     * @param message Default text to send if code not found
     * @param sender CommandSender to send message to
     * @param prefix If true, add prefix
     */
    public static void sender(TextComponent message, CommandSender sender, boolean prefix) {
        if (prefix) {
            TextComponent msg = new TextComponent(prefix());
            msg.addExtra(message);
            sender.spigot().sendMessage(msg);
        } else {
            sender.spigot().sendMessage(message);
        }
    }
}
