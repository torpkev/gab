package com.ibexmc.gab.util;

import org.bukkit.Bukkit;

public class SpigotVersion {
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
}
