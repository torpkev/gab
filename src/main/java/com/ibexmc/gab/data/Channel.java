package com.ibexmc.gab.data;

import com.ibexmc.gab.Gab;
import com.ibexmc.gab.util.ConfigParser;
import com.ibexmc.gab.util.StringFunctions;
import com.ibexmc.gab.hooks.vault.VaultHook;
import com.ibexmc.gab.util.log.Error;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Channel {

    private String key = "";
    private String name = "";
    private String prefix = "";
    private Set<String> bannedWords = new HashSet<>();
    private boolean sendToDiscord = false; // This is only used if DiscordSRV is running
    private boolean invalid = false;
    private String defaultColor = "&f";

    public Channel(boolean blank) {
        // Blank channel
        if (blank) {
            this.key = "";
        }
    }
    public Channel() {
        // Default channel
        this.key = "global";
        this.name = "Global";
        this.prefix = Gab.instance.data().getGabConfig().getDefaultPrefix();
        this.sendToDiscord = true;
        this.defaultColor = "&f";
    }
    public Channel(File file) {
        ConfigParser configParser = new ConfigParser(Gab.instance, file);


        // key
        ConfigParser.ConfigReturn cfgKey = configParser.getValue(
                "key",
                "",
                true
        );
        if (cfgKey.isSuccess()) {

            this.key = cfgKey.getString();


            switch (this.key) {
                case "global":
                    Gab.instance.error().save(
                            "Channel.name.001",
                            "Channel",
                            "Constructor(File)",
                            "Unable to load channel",
                            "global is a protected channel name",
                            Error.Severity.CRITICAL,
                            null
                    );
                    invalid = true;
                    break;
                case "local":
                    Gab.instance.error().save(
                            "Channel.name.001",
                            "Channel",
                            "Constructor(File)",
                            "Unable to load channel",
                            "local is a protected channel name",
                            Error.Severity.CRITICAL,
                            null
                    );
                    invalid = true;
                    break;
                default:
                    break;
            }

            // name
            ConfigParser.ConfigReturn cfgName = configParser.getValue(
                    "name",
                    "",
                    true
            );
            if (cfgName.isSuccess()) {
                this.name = cfgName.getString();

                // prefix
                ConfigParser.ConfigReturn cfgPrefix = configParser.getValue(
                        "prefix",
                        "",
                        false
                );
                if (cfgPrefix.isSuccess()) {
                    this.prefix = cfgPrefix.getString();
                }

                // default color
                ConfigParser.ConfigReturn cfgColor = configParser.getValue(
                        "default_color",
                        "&f",
                        false
                );
                if (cfgColor.isSuccess()) {
                    if (cfgColor.getString().length() == 2) {
                        if (cfgColor.getString().startsWith("&")) {
                            this.defaultColor = cfgColor.getString();
                        }
                    }
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

                // send_to_discord
                if (!this.key.equalsIgnoreCase("global")) {
                    ConfigParser.ConfigReturn cfgDiscord = configParser.getValue(
                            "discord",
                            true,
                            false
                    );
                    if (cfgDiscord.isSuccess()) {
                        this.sendToDiscord = cfgDiscord.getBoolean();
                    } else {
                        this.sendToDiscord = false;
                    }
                } else {
                    this.sendToDiscord = Gab.instance.data().getGabConfig().getDiscord();
                }

            } else {
                this.invalid = true;
            }
        }


    }

    public String getKey() {
        return key;
    }
    public String getName() {
        return name;
    }
    public String getPrefix() {
        return prefix;
    }
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    public Set<String> getBannedWords() {
        return bannedWords;
    }
    public void setBannedWords(Set<String> bannedWords) {
        this.bannedWords = bannedWords;
    }
    public void addBannedWord(String word) {
        if (bannedWords == null) {
            this.bannedWords = new HashSet<>();
        }
        bannedWords.add(word);
    }
    public void removeBannedWord(String word) {
        if (bannedWords == null) {
            this.bannedWords = new HashSet<>();
        }
        if (bannedWords.contains(word)) {
            bannedWords.remove(word);
        }
    }
    public boolean sendToDiscord() {
        if (Gab.instance.data().isDiscordHooked()) {
            return sendToDiscord;
        } else {
            return false;
        }
    }
    public void setSendToDiscord(boolean sendToDiscord) {
        this.sendToDiscord = sendToDiscord;
    }

    public String getDefaultColor() {
        return defaultColor;
    }

    public String prefix(Player player) {

        Gab.instance.log().quick("Prefix: " + this.prefix);
        // Get the channel prefix and replace %world% with the player world name
        String messagePrefix = this.prefix.replace(
                "%world%",
                player.getLocation().getWorld().getName()
        );

        // If we have %group% in our prefix, get the group prefix
        // from Vault if is hooked.
        if (this.prefix.toLowerCase().contains("%group%")) {
            if (Gab.instance.data().isVaultChatHooked()) {
                VaultHook vaultHook = new VaultHook(
                        Gab.instance,
                        Gab.instance.data().vaultPermission,
                        Gab.instance.data().vaultChat
                );
                messagePrefix = messagePrefix.replace("%group%", vaultHook.getPrefix(player));
            }
        }

        messagePrefix = messagePrefix.replace("%name%", player.getDisplayName());


        // Add the complete prefix to the front of the message
        return StringFunctions.colorCode(messagePrefix + " ");

    }
    public String discordFormat(Player player, String message) {
        String returnMessage = "";
        if (Gab.instance.data().isVaultChatHooked()) {
            VaultHook vaultHook = new VaultHook(
                    Gab.instance,
                    Gab.instance.data().vaultPermission,
                    Gab.instance.data().vaultChat
            );
            String groupPrefix = vaultHook.getPrefix(player);
            groupPrefix = "**" + groupPrefix.replace("[", "").replace("]", "") + "**";
            returnMessage = groupPrefix + " " + player.getDisplayName() + " » ";
        } else {
            returnMessage = player.getDisplayName() + " » ";
        }

        return returnMessage;
    }

    public void save() {
        // TODO: save to file
    }
    public void update() {
        if (!invalid) {
            this.save();
            Gab.instance.data().putChannel(this);
        } else {
            Gab.instance.log().console("Channel Update", "Unable to update channel, config is invalid");
        }
    }
}
