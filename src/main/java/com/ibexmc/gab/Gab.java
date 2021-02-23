package com.ibexmc.gab;


import com.ibexmc.gab.commands.*;
import com.ibexmc.gab.data.Data;
import com.ibexmc.gab.listeners.PlayerListener;
import com.ibexmc.gab.util.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Gab extends JavaPlugin {

    public static Gab instance;
    public static final String internalName = "Gab";

    public Data data;
    public Log log;
    //public GabAPI api;
    public Locale locale;
    public ConfigManager config;

    @Override
    public void onEnable() {
        instance = this;
        data = new Data();
        config = new ConfigManager(this);
        config.setDebug(); // Set the debug flags before doing anything else
        locale = new Locale();
        log = new Log(this);

        log.quick("&1=========================================", false);
        log.quick("&f                  Gab", false);
        log.quick("&1=========================================", false);

        data.debug.put(DebugType.API, true);
        data.debug.put(DebugType.ENTER, true);
        data.debug.put(DebugType.EVENTS, true);
        data.debug.put(DebugType.EXIT, true);
        data.debug.put(DebugType.INFO, true);
        data.debug.put(DebugType.OTHER, true);
        data.debug.put(DebugType.SCHEDULED, true);
        data.debug.put(DebugType.STARTUP, true);
        data.debug.put(DebugType.TEMP, true);

        loadCommands();
        loadEventListeners();


    }

    @Override
    public void onDisable() {

    }

    private void loadEventListeners() {
        this.log.debug(DebugType.STARTUP, "Registering Event Listeners");
        try {
            Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        } catch (Exception ex) {
            this.log.error(
                    "G_LEL_01",
                    "Gab",
                    "loadEventListeners()",
                    "Unexpected error registering event listeners",
                    ex.getMessage(),
                    Severity.CRITICAL,
                    ex.getStackTrace()
            );
        }
    }
    private void loadCommands() {
        try {
            getCommand("gab").setExecutor(new GabCommand());
            getCommand("chme").setExecutor(new MeCommand());
            //getCommand("chat").setExecutor(new ChatCommand());
            //getCommand("chatleave").setExecutor(new ChatLeaveCommand());
            //getCommand("chatother").setExecutor(new ChatOtherCommand());
            //getCommand("chatlist").setExecutor(new ChatListCommand());
        } catch (Exception ex) {
            log.error(
                    "Gab.loadCommands.001",
                    "Gab",
                    "loadCommands()",
                    "Unexpected Error registering commands",
                    "Error: " + ex.getMessage(),
                    Severity.CRITICAL,
                    ex.getStackTrace()
            );
        }
    }

}
