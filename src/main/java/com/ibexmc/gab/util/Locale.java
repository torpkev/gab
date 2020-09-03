package com.ibexmc.gab.util;

import com.ibexmc.gab.Gab;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Locale {

    Gab plugin;
    public Locale(Gab plugin) {
        this.plugin = plugin;
        loadLanguage(FileUtils.getLanguageYml());
    }

    private Map<String, String> locale = new HashMap<>();
    public boolean loadLanguage(File localeFile) {
        if (!localeFile.exists()) {
            plugin.saveResource("lang.yml", false);
        }
        FileConfiguration localeConfig = YamlConfiguration.loadConfiguration(localeFile);
        for (String localeCode : localeConfig.getConfigurationSection("").getKeys(false))
        {
            String localeValue = localeConfig.getString(localeCode);
            if (localeValue != null) {
                if (!localeValue.equalsIgnoreCase("")) {
                    locale.put(localeCode, localeValue);
                }
            }
        }
        return true;
    }
    public Map<String, String> getLocale() {
        return this.locale;
    }
    public boolean localeExists(String code) {
        return this.locale.containsKey(code);
    }
    public String getLocaleTextByCode(String code, String defaultText) {
        return getLocaleTextByCode(code, defaultText, null);
    }
    public String getLocaleTextByCode(String code, String defaultText, HashMap<String, String> placeHolder) {
        String returnValue = defaultText;
        if (this.locale.containsKey(code)) {
            returnValue = this.locale.get(code);
        }
        if (placeHolder != null) {
            for (Map.Entry<String, String> params : placeHolder.entrySet()) {
                returnValue = returnValue.replace(params.getKey(), params.getValue());
            }
        }
        return StringFunctions.colorCode(returnValue);
    }
}
