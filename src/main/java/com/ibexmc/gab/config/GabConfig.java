package com.ibexmc.gab.config;

import com.ibexmc.gab.Gab;
import com.ibexmc.gab.util.ConfigParser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GabConfig {
    Gab plugin;
    boolean debugMode = true;
    String defaultPrefix = "";
    String censorCharacter = "*";
    Set<String> bannedWords = new HashSet<>();
    boolean discord = false;

    public GabConfig(Gab plugin) {
        this.plugin = plugin;
        if (plugin != null) {
            plugin.saveDefaultConfig();
        }
    }

    public void getConfig(File file) {

        this.plugin = Gab.instance;

        ConfigParser configParser = new ConfigParser(plugin, file);

        plugin.log().quick("LOADING CONFIG!");

        // debug
        ConfigParser.ConfigReturn debugModeResult = configParser.getValue(
                "debug",
                false,
                false
        );
        if (debugModeResult.isSuccess()) {
            debugMode = debugModeResult.getBoolean();
        }

        // default_prefix
        ConfigParser.ConfigReturn defaultPrefixResult = configParser.getValue(
                "default_prefix",
                "%group%&r ",
                false
        );
        if (defaultPrefixResult.isSuccess()) {
            defaultPrefix = defaultPrefixResult.getString();
            plugin.log().quick("default_prefix success: " + defaultPrefix);
        } else {
            defaultPrefix = "%group%&f<%name%&f>&r";
            plugin.log().quick("default_prefix fail: " + defaultPrefix);
        }

        // censor_character
        ConfigParser.ConfigReturn censorCharacterResult = configParser.getValue(
                "censor_character",
                "*",
                false
        );
        if (censorCharacterResult.isSuccess()) {
            censorCharacter = censorCharacterResult.getString();
        }

        // banned_words
        ConfigParser.ConfigReturn cfgBannedWords = configParser.getValue(
                "banned_words",
                new ArrayList<>(),
                false
        );
        if (cfgBannedWords.isSuccess()) {
            this.bannedWords = new HashSet<>();
            for (String word : cfgBannedWords.getStringList()) {
                bannedWords.add(word);
            }
        }

        // discord
        ConfigParser.ConfigReturn discordResult = configParser.getValue(
                "discord",
                true,
                false
        );
        if (discordResult.isSuccess()) {
            discord = discordResult.getBoolean();
        }
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public String getDefaultPrefix() {
        return defaultPrefix;
    }

    public Set<String> getBannedWords() {
        return bannedWords;
    }

    public String getCensorCharacter() {
        return censorCharacter;
    }

    public boolean getDiscord() { return discord; }
}
