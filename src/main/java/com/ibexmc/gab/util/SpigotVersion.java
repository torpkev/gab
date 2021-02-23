package com.ibexmc.gab.util;

import com.ibexmc.gab.util.functions.NumberFunctions;
import org.bukkit.Bukkit;

public class SpigotVersion {
    /**
     * Checks if the version provided is version safe for current Spigot version
     * Examples: Checking if safe for 1.14 while on 1.16 would return true, but
     * checking if 1.16 safe while on 1.15 would return false.
     * @param version Version to check
     * @return If true, current version is newer or equal to version being checked
     */
    public static boolean isVersionSafe(double version) {
        String[] array = Bukkit.getServer().getClass().getPackage().getName().replace(
                ".",
                ","
        ).split(",");
        double versionNum = 999d;
        if (array.length == 4) {
            versionNum = NumberFunctions.stringToDouble(array[3] + ".");
        } else {
            return false;
        }
        return version >= versionNum;
    }

    /**
     * Returns the current server version
     * @return Version as a string
     */
    public static String getVersion() {
        return getVersionType() + " - " + getBukkitVersion();
    }
    /**
     * Gets the version type (Spigot, Paper etc.)
     * @return Version type as a string
     */
    private static String getVersionType() {
        return Bukkit.getServer().getName();
    }

    /**
     * Gets the bukkit version
     * @return Bukkit version as a string
     */
    private static String getBukkitVersion() {
        return Bukkit.getServer().getBukkitVersion();
    }


}
