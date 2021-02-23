package com.ibexmc.gab.util;

import com.ibexmc.gab.Gab;
import com.ibexmc.gab.util.functions.StringFunctions;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Locale {
    //region Objects
    Gab plugin = Gab.instance;
    //endregion
    //region Constructors
    /**
     * Constructor used to load the lang.yml file
     */
    public Locale() {
        load();
    }
    //endregion
    //region Methods

    /**
     * Gets locale map from PluginData, if it is null, returns a new HashMap
     * @return Locale HashMap (or blank HashMap if null)
     */
    private Map<String, String> locale() {
        if (plugin.data.locale != null) {
            return plugin.data.locale;
        } else {
            return new HashMap<>();
        }
    }

    /**
     * Gets the locale file, if it doens't exist, will create it from resource
     * or as a new blank document if not available
     * @return Locale file
     */
    private File getFile() {
        File localeFile = new File(
                plugin +
                        File.separator + "lang.yml"
        );
        if (!localeFile.exists()) {
            if (plugin.getResource("lang.yml") != null) {
                plugin.saveResource("lang.yml", false);
            } else {
                try {
                    localeFile.createNewFile();
                    FileConfiguration localeConfig = YamlConfiguration.loadConfiguration(localeFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return localeFile;
    }

    /**
     * Checks if the lang.yml file exists, if not, saves the resource copy.  Then reads
     * the lang.yml file and puts the values into PluginData.locale
     */
    public void load() {
        File localeFile = getFile();
        FileConfiguration localeConfig = YamlConfiguration.loadConfiguration(localeFile);
        if (plugin.data.locale == null) {
            plugin.data.locale = new HashMap<>();
        }
        for (String localeCode : localeConfig.getConfigurationSection("").getKeys(false))
        {
            String localeValue = localeConfig.getString("en." + localeCode);
            if (localeValue != null) {
                if (!localeValue.equalsIgnoreCase("")) {
                    plugin.data.locale.put("en." + localeCode, localeValue);
                }
            }
        }
    }

    /**
     * Gets the text for the provided code.  Returns defaultText if not found
     * Also writes out to the file if not found
     * @param code Locale Code
     * @param defaultText Default Text
     * @param placeHolder Placeholder map (replacement tag, replacement text)
     * @return Colorized text
     */
    public String get(String code, String defaultText, Map<String, String> placeHolder) {
        String returnValue = defaultText;
        if (locale().containsKey(code)) {
            returnValue = locale().get(code);
        } else {
            locale().put(code, defaultText);
            write(code, defaultText);
        }
        if (placeHolder != null) {
            for (Map.Entry<String, String> params : placeHolder.entrySet()) {
                returnValue = returnValue.replace(params.getKey(), params.getValue());
            }
        }
        return StringFunctions.color(returnValue);
    }

    /**
     * Writes the code/text out to the locale file
     * @param code Code to apply
     * @param text Text to apply
     */
    public void write(String code, String text) {
        File localeFile = getFile();
        FileConfiguration localeConfig = YamlConfiguration.loadConfiguration(localeFile);
        localeConfig.set("en." + code, text);
        try {
            localeConfig.save(localeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //endregion
}
