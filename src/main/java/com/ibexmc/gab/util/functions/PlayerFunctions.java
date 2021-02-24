package com.ibexmc.gab.util.functions;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerFunctions {



    /**
     * Teleports a player to the provided location
     * @param player Player to teleport
     * @param location Location to teleport to
     * @param safe If true, check the location is safe before teleporting
     * @return If true, player teleport attempt was successful
     */
    public static boolean teleport(Player player, Location location, boolean safe) {
        return true;
    }

    /**
     * Gets a player head based on the player unique identifier
     * @param uuid Player Unique Identifier to lookup
     * @return ItemStack with player head.  Plain default Steve head if not found
     */
    public static ItemStack getPlayerHead(UUID uuid) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD, 1);
        ItemMeta headMeta = playerHead.getItemMeta();
        if (headMeta instanceof SkullMeta) {
            SkullMeta headSkull = (SkullMeta) headMeta;
            headSkull.setDisplayName(offlinePlayer.getName());
            headSkull.setOwningPlayer(offlinePlayer);
            playerHead.setItemMeta(headSkull);
        }
        return playerHead;
    }

    /**
     * Checks if the player has a particular permission
     * @param player Player to check
     * @param permission Permission to check
     * @return If true, player has permission
     */
    public static boolean hasPermission(Player player, String permission) {
        boolean hasOP = false;
        boolean hasAdmin = false;
        boolean hasPermission = false;
        if (player.isOp()) {
            hasOP = true;
        }
        if (player.hasPermission("gab.admin")) {
            hasAdmin = true;
        }
        if (player.hasPermission(permission)) {
            hasPermission = true;
        }

        return (hasOP || hasAdmin || hasPermission);
    }

    /**
     * Gets an online player by their name, if not found, returns null
     * @param playerName Player name to lookup
     * @return Player
     */
    public static Player getOnlinePlayer(String playerName) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (ChatColor.stripColor(playerName).equalsIgnoreCase(ChatColor.stripColor(player.getName()))) {
                return player;
            }
        }
        return null;
    }

    /**
     * Gets a list of nearby players from the location of the player provided
     * Does not include the player being provided
     * @param center Location to use as the center
     * @param distance Radius to lookup
     * @return List of players, if none, returns a blank location
     */
    public static List<Player> nearbyPlayers(Player center, int distance) {
        List<Player> players = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.getUniqueId().equals(center.getUniqueId())) {
                if (player.getLocation().distanceSquared(center.getLocation()) <= distance * distance) {
                    players.add(player);
                }
            }
        }
        return players;
    }

}
