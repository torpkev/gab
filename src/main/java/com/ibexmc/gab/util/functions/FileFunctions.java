package com.ibexmc.gab.util.functions;

import com.ibexmc.gab.Gab;
import com.ibexmc.gab.util.Severity;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileFunctions {


    /**
     * Gets the config.yml file - If not found, will try to create from resources, if not found there
     * will create a new blank file
     * @return Config File object
     */
    public static File getConfigYml() {
        File configFile = new File(
                Gab.instance.getDataFolder() +
                        File.separator +
                        "config.yml"
        );
        if (!configFile.exists()) {
            if (Gab.instance.getResource("config.yml") != null) {
                Gab.instance.saveResource("config.yml", false);
            } else {
                try {
                    configFile.createNewFile();
                    FileConfiguration configuration = YamlConfiguration.loadConfiguration(configFile);
                    try {
                        configuration.save(configFile);
                    } catch (IOException e) {
                        Gab.instance.log.error(
                                "C_Save_001",
                                "Clan",
                                "save()",
                                "Unexpected Error Saving",
                                e.getMessage(),
                                Severity.URGENT,
                                e.getStackTrace()
                        );
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return configFile;
    }

    /**
     * Gets the yml file provided
     * @return Config File object
     */
    public static File getYMLFile(String filename) {
        File configFile = new File(filename);
        if (!configFile.exists()) {
            if (Gab.instance.getResource("config.yml") != null) {
                Gab.instance.saveResource("config.yml", false);
            } else {
                try {
                    configFile.createNewFile();
                    FileConfiguration configuration = YamlConfiguration.loadConfiguration(configFile);
                    try {
                        configuration.save(configFile);
                    } catch (IOException e) {
                        Gab.instance.log.error(
                                "C_Save_001",
                                "Clan",
                                "save()",
                                "Unexpected Error Saving",
                                e.getMessage(),
                                Severity.URGENT,
                                e.getStackTrace()
                        );
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return configFile;
    }
    public static String fieldsPath() {
        return Gab.instance.getDataFolder() + File.separator + "data";
    }
    public static String fieldsFile(String filename) {
        return fieldsPath() + File.separator + filename;
    }
    public static File newFile(String path, String filename) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        File newFile = new File(path +  File.separator + filename);
        if (!newFile.exists()) {
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                //TODO: Logging.log("Deleting Block File", "&cUnable to write to file: " + e.getMessage());
            }
        } else {
            // TODO: File already exists
        }
        return newFile;
    }
}
