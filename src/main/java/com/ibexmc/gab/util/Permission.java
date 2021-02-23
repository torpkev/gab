package com.ibexmc.gab.util;

import com.ibexmc.gab.Gab;
import com.ibexmc.gab.data.Channel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Permission {
    public enum PermissionType {
        ADMIN,
        CHANNEL,
        CREATE
    }
    private static String permissionTypeToString(PermissionType permissionType) {
        String permissionText = "";
        switch (permissionType) {
            case ADMIN:
                permissionText = "gab.admin";
                break;
            case CHANNEL:
                permissionText = "gab.channel";
                break;
            default:
                break;
        }
        return permissionText;
    }
    public static boolean hasPermission(Player player, PermissionType permission) {
        boolean hasOP = false;
        boolean hasAdmin = false;
        boolean hasPermission = false;
        if (player.isOp()) {
            hasOP = true;
        }
        if (player.hasPermission(permissionTypeToString(PermissionType.ADMIN))) {
            hasAdmin = true;
        }
        if (player.hasPermission(permissionTypeToString(permission))) {
            hasPermission = true;
        }
        Gab.instance.debug().log(
                "Permissions",
                "hasPermission",
                "Has Permission: " + hasPermission,
                "Permission: " + permissionTypeToString(permission) + " Op: " + hasOP + " Admin: " + hasAdmin + " Permission: " + hasPermission
        );
        return (hasOP || hasAdmin || hasPermission);
    }
    public static boolean isChatter(Player player, String channel) {
        boolean hasOP = false;
        boolean hasAdmin = false;
        boolean hasPermission = false;
        if (player.isOp()) {
            hasOP = true;
        }
        if (player.hasPermission(permissionTypeToString(PermissionType.ADMIN))) {
            hasAdmin = true;
        }
        if (player.hasPermission(permissionTypeToString(PermissionType.CHANNEL) + "." + channel)) {
            hasPermission = true;
        }
        Gab.instance.debug().log(
                "Permissions",
                "hasPermission",
                "Has Permission: " + hasPermission,
                "Permission: " +
                        permissionTypeToString(PermissionType.CHANNEL) +
                        "." + channel +
                        " Op: " + hasOP +
                        " Admin: " + hasAdmin +
                        " Permission: " + hasPermission
        );
        return (hasOP || hasAdmin || hasPermission);
    }
    public static boolean isChannelAdmin(Player player, String channel) {
        boolean hasOP = false;
        boolean hasAdmin = false;
        boolean hasPermission = false;
        if (player.isOp()) {
            hasOP = true;
        }
        if (player.hasPermission(permissionTypeToString(PermissionType.ADMIN))) {
            hasAdmin = true;
        }
        if (player.hasPermission(permissionTypeToString(PermissionType.CHANNEL) + "." + channel + ".admin")) {
            hasPermission = true;
        }
        Gab.instance.debug().log(
                "Permissions",
                "hasPermission",
                "Has Permission: " + hasPermission,
                "Permission: " +
                        permissionTypeToString(PermissionType.CHANNEL) +
                        "." + channel + ".admin" +
                        " Op: " + hasOP +
                        " Admin: " + hasAdmin +
                        " Permission: " + hasPermission
        );
        return (hasOP || hasAdmin || hasPermission);
    }
    public static List<Player> chatters(String channel) {
        List<Player> chattersList = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (Permission.isChatter(player, channel)) {
                chattersList.add(player);
            }
        }
        return chattersList;
    }
    public static List<Channel> allowedChannels(Player player) {
        List<Channel> returnChannels = new ArrayList<>();
        Map<String, Channel> channels = Gab.instance.data.getChannels();
        if (channels != null) {
            Gab.instance.log().quick("Channels is not null");
            for (Map.Entry<String, Channel> channelEntry : channels.entrySet()) {
                Gab.instance.log().quick("Current channel: " + channelEntry.getValue().getKey());
                if (Permission.isChatter(player, channelEntry.getValue().getKey())) {
                    Gab.instance.log().quick("Player is a chatter here");
                    returnChannels.add(channelEntry.getValue());
                } else {
                    Gab.instance.log().quick("Player is not a chatter here");
                }
            }
        }
        return returnChannels;
    }
}