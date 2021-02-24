package com.ibexmc.gab.util;

import com.ibexmc.gab.Gab;
import com.ibexmc.gab.util.functions.FileFunctions;

import java.io.File;

public class ConfigManager {

    Gab plugin;
    YmlParser ymlParser;



    public ConfigManager(Gab plugin) {
        this.plugin = plugin;
        File configFile = FileFunctions.getConfigYml();
        if (configFile != null) {
            ymlParser = new YmlParser(plugin, configFile);
        }
    }

    public void setDebug() {
        YmlParser.ConfigReturn crDebug = ymlParser.getBooleanValue(
                "debug.info",
                false,
                false
        );
        plugin.data.debug.put(DebugType.INFO, crDebug.getBoolean());

        YmlParser.ConfigReturn crStartup = ymlParser.getBooleanValue(
                "debug.startup",
                false,
                false
        );
        plugin.data.debug.put(DebugType.STARTUP, crDebug.getBoolean());

        YmlParser.ConfigReturn crEnter = ymlParser.getBooleanValue(
                "debug.enter",
                false,
                false
        );
        plugin.data.debug.put(DebugType.ENTER, crDebug.getBoolean());

        YmlParser.ConfigReturn crExit = ymlParser.getBooleanValue(
                "debug.exit",
                false,
                false
        );
        plugin.data.debug.put(DebugType.EXIT, crDebug.getBoolean());

        YmlParser.ConfigReturn crEvents = ymlParser.getBooleanValue(
                "debug.events",
                false,
                false
        );
        plugin.data.debug.put(DebugType.EVENTS, crDebug.getBoolean());

        YmlParser.ConfigReturn crScheduled = ymlParser.getBooleanValue(
                "debug.scheduled",
                false,
                false
        );
        plugin.data.debug.put(DebugType.SCHEDULED, crDebug.getBoolean());

        YmlParser.ConfigReturn crAPI = ymlParser.getBooleanValue(
                "debug.api",
                false,
                false
        );
        plugin.data.debug.put(DebugType.API, crDebug.getBoolean());

        YmlParser.ConfigReturn crOther = ymlParser.getBooleanValue(
                "debug.other",
                false,
                false
        );
        plugin.data.debug.put(DebugType.OTHER, crDebug.getBoolean());

        YmlParser.ConfigReturn crTemp = ymlParser.getBooleanValue(
                "debug.temp",
                false,
                false
        );
        plugin.data.debug.put(DebugType.TEMP, crDebug.getBoolean());
    }
    public void load() {
        setDebug(); // Get the debug values
    }
}
