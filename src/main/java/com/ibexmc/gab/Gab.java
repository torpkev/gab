package com.ibexmc.gab;


import com.ibexmc.gab.commands.*;
import com.ibexmc.gab.data.GabData;
import com.ibexmc.gab.listeners.PlayerListener;
import com.ibexmc.gab.util.Locale;
import com.ibexmc.gab.util.alert.Message;
import com.ibexmc.gab.util.alert.Screen;
import com.ibexmc.gab.util.log.Debug;
import com.ibexmc.gab.util.log.Error;
import com.ibexmc.gab.util.log.Log;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Gab  extends JavaPlugin {

    public static Gab instance;

    private final String internalName = "Gab";
    public String getInternalName() {
        return internalName;
    }

    public GabData data;


    Log logClass = new Log(this);
    public Log log() {
        return this.logClass;
    }
    Error errorClass;
    public Error error() {
        return this.errorClass;
    }
    Debug debugClass;
    public Debug debug() {
        return this.debugClass;
    }
    Locale locale;
    public Locale locale() { return this.locale; }
    Message message;
    public Message message() { return this.message; }
    Screen screen;
    public Screen screen() { return this.screen; }

    @Override
    public void onEnable() {
        instance = this;

        // Hard-coded false for debug messages
        debugClass = new Debug(this, false);

        // Create error class
        errorClass = new Error(this);

        // Create the live data
        data = new GabData(this);

        // From this point, debug messages will depend on the config
        // value - if debug is required prior to this point, set
        // the debug to true manually above
        debugClass = new Debug(this, data.debug);

        locale = new Locale(this);
        message = new Message(this);
        screen = new Screen(this);


        log().quick("&1=========================================", false);
        log().quick("&f                  Gab", false);
        log().quick("&1=========================================", false);

        loadCommands();
        registerListeners();

        data.hookVault();
        data.hookDiscordSRV();

        data.load();

    }

    @Override
    public void onDisable() {

    }

    // Register Listeners
    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }
    // Load Commands
    private void loadCommands() {
        try {
            getCommand("gab").setExecutor(new GabCommand());
            getCommand("chat").setExecutor(new ChatCommand());
            getCommand("chatleave").setExecutor(new ChatLeaveCommand());
            getCommand("chatother").setExecutor(new ChatOtherCommand());
            getCommand("chatlist").setExecutor(new ChatListCommand());
        } catch (Exception ex) {
            error().save(
                    "Gab.loadCommands.001",
                    "Gab",
                    "loadCommands()",
                    "Unexpected Error registering commands",
                    "Error: " + ex.getMessage(),
                    Error.Severity.CRITICAL,
                    ex.getStackTrace()
            );
        }
    }

}
