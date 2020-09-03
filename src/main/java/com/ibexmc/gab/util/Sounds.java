package com.ibexmc.gab.util;

import org.bukkit.Bukkit;
        import org.bukkit.Location;
        import org.bukkit.Sound;
        import org.bukkit.entity.Player;

public class Sounds {
    public static void playSoundWorld(Location soundLocation, float volume, float pitch, Sound sound) {
        Bukkit.getWorld(soundLocation.getWorld().getUID()).playSound(soundLocation, sound, volume, pitch);
    }
    public static void playerSoundPlayer(Player player, Sound sound, float volume, float pitch) {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }
}
