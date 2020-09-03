package com.ibexmc.gab.util;

import com.ibexmc.gab.Gab;
import com.ibexmc.gab.util.log.Error;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.UUID;

public class FileUtils {
    public static File getFileByUUID(String folder, UUID uuid) {
        if (StringFunctions.isNullOrEmpty(folder)) {
            return new File(
                    Gab.getInstance().getDataFolder() +
                            File.separator +
                            uuid.toString() +
                            ".yml"
            );
        } else {
            return new File(
                    Gab.getInstance().getDataFolder() +
                            File.separator +
                            folder +
                            File.separator +
                            uuid.toString() +
                            ".yml"
            );
        }
    }
    public static File getConfigYml() {
        File configFile = new File(
                Gab.getInstance().getDataFolder() +
                        File.separator + "config.yml"
        );
        if (configFile.exists()) {
            return configFile;
        } else {
            Gab.getInstance().error().save(
                    "FileUtils.getConfigYml.001",
                    "FileUtils",
                    "getConfigYml()",
                    "Unable to get config.yml",
                    "config.yml does not exist",
                    Error.Severity.CRITICAL,
                    null
            );
            return null;
        }
    }
    public static File getLanguageYml() {
        File configFile = new File(
                Gab.getInstance().getDataFolder() +
                        File.separator + "lang.yml"
        );
        return configFile;
    }
    public static FileConfiguration getFileConfig(File file) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        return config;
    }
}
