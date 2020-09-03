package com.ibexmc.gab.data;

import com.ibexmc.gab.Gab;
import com.ibexmc.gab.config.GabConfig;
import com.ibexmc.gab.hooks.discordsrv.DiscordSRVHook;
import com.ibexmc.gab.util.FileUtils;
import com.ibexmc.gab.util.PlayerFunctions;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.io.File;
import java.util.*;

public class GabData {
    Gab plugin;

    public GabData(Gab plugin) {
        this.plugin = plugin;
    }

    public void load() {
        setReloading(true);
        plugin.log().quick("Loading Config");
        gabConfig = new GabConfig(plugin);
        gabConfig.getConfig(FileUtils.getConfigYml());
        loadChannelFiles();
        setReloading(false);
    }

    // Debug
    boolean debug = false;
    public boolean isDebug() {
        return debug;
    }
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    // Reloading
    private boolean reloading = false;
    public boolean isReloading() {
        return reloading;
    }
    public void setReloading(boolean reloading) {
        this.reloading = reloading;
    }

    private boolean enabled = true;
    public boolean isEnabled() { return enabled; }
    public void toggleEnabled() {
        if (enabled) {
            enabled = false;
        } else {
            enabled = true;
        }

    }
    // Gab Config
    private GabConfig gabConfig;

    public GabConfig getGabConfig() {
        return gabConfig;
    }
    public void setGabConfig(GabConfig gabConfig) {
        this.gabConfig = gabConfig;
    }

    // Channels
    private Map<String, Channel> channels = new HashMap<>();
    private void channelsNullCheck() {
        if (channels == null) {
            channels = new HashMap<>();
        }
    }
    public Map<String, Channel> getChannels() {
        return channels;
    }
    public void setChannels(Map<String, Channel> channels) {
        this.channels = channels;
    }
    public Channel getChannel(String key) {
        channelsNullCheck();
        //plugin.log().quick("key provided: " + key + " size:" + channels.size());
        return channels.get(key.toLowerCase());
    }
    public boolean validChannel(String key) {
        channelsNullCheck();
        return channels.containsKey(key);
    }
    public void putChannel(Channel channel) {
        channelsNullCheck();
        if (channel != null) {
            channels.put(channel.getKey().toLowerCase(), channel);
        }
    }
    public void removeChannel(String key) {
        channelsNullCheck();
        if (channels.containsKey(key.toLowerCase())) {
            channels.remove(key.toLowerCase());
        }
    }
    public void loadChannelFiles() {
        channelsNullCheck();
        String filepath = plugin.getDataFolder().getAbsolutePath() +
                File.separator + "channels" ;
        File datadir = new File(filepath);
        if (!datadir.exists()) {
            return;
        }
        for (File file : new File(filepath).listFiles()) {
            if (file.exists()) {
                plugin.debug().log(
                        "Gab",
                        "loadChannelFiles()",
                        "Processing File",
                        "Filename = " + file.getName()
                );
                Channel channel = new Channel(file);
                this.channels.put(channel.getKey().toLowerCase(), channel);
                plugin.log().console("Channel Loaded", channel.getName() + " added");
            }
        }
    }

    // Channel Notifications Mute
    private Set<UUID> notificationsMuted = new HashSet<>();
    private void notificationsMutedNullCheck() {
        if (notificationsMuted == null) {
            notificationsMuted = new HashSet<>();
        }
    }
    public boolean playerNotificationsMuted(UUID uuid) {
        notificationsMutedNullCheck();
        return notificationsMuted.contains(uuid);
    }
    public void mutePlayerNotifications(UUID uuid) {
        notificationsMutedNullCheck();
        notificationsMuted.add(uuid);
        PlayerFunctions.savePlayerSettingsToFile(uuid);
    }
    public void unmutePlayerNotifications(UUID uuid) {
        notificationsMutedNullCheck();
        if (notificationsMuted.contains(uuid)) {
            notificationsMuted.remove(uuid);
            PlayerFunctions.savePlayerSettingsToFile(uuid);
        }
    }

    // Current Chat
    private Map<UUID, String> currentChat = new HashMap<>();
    private void currentChatNullCheck() {
        if (currentChat == null) {
            currentChat = new HashMap<>();
        }
    }
    public Channel currentChatChannel(UUID uuid) {
        currentChatNullCheck();
        plugin.log().quick("UUID: " + uuid);
        if (currentChat.containsKey(uuid)) {
            plugin.log().quick("Current chat contains UUID");
            String currentChannel = currentChat.get(uuid);
            plugin.log().quick("Current chat is " + currentChannel);
            if (channels.containsKey(currentChannel)) {
                plugin.log().quick("Channel found");
                return channels.get(currentChannel);
            } else {
                plugin.log().quick("Channel not found - invalid channel");
                for (Map.Entry<String, Channel> c : channels.entrySet()) {
                    plugin.log().quick(c.getKey() + " - " + c.getValue().getName());
                }
                return null;
            }
        } else {
            plugin.log().quick("Channel found - no player entry");
            return null;
        }
    }
    public void setCurrentChannel(UUID uuid, String channel) {
        currentChatNullCheck();
        currentChat.put(uuid, channel);
        PlayerFunctions.savePlayerSettingsToFile(uuid);
    }
    public void clearPlayerCurrentChannel(UUID uuid) {
        currentChatNullCheck();
        currentChat.remove(uuid);
        PlayerFunctions.savePlayerSettingsToFile(uuid);
    }

    private Map<UUID, Set<String>> inChannel = new HashMap<>();
    private void inChannelNullCheck() {
        if (inChannel == null) {
            inChannel = new HashMap<>();
        }
    }
    public boolean playerInChannel(UUID uuid, String channelKey) {
        inChannelNullCheck();
        if (inChannel.containsKey(uuid)) {
            if (inChannel.get(uuid) != null) {
                if (inChannel.get(uuid).contains(channelKey)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public void putPlayerInChannel(UUID uuid, String channelKey) {
        inChannelNullCheck();
        Set<String> channelSet = new HashSet<>();
        if (inChannel.containsKey(uuid)) {
            if (inChannel.get(uuid) != null) {
                channelSet = inChannel.get(uuid);
            }
        }
        channelSet.add(channelKey);
        inChannel.put(uuid, channelSet);
        PlayerFunctions.savePlayerSettingsToFile(uuid);
    }
    public void removePlayerFromChannel(UUID uuid, String channelKey) {
        inChannelNullCheck();
        Set<String> channelSet = new HashSet<>();
        if (inChannel.containsKey(uuid)) {
            if (inChannel.get(uuid) != null) {
                channelSet = inChannel.get(uuid);
            }
        }
        channelSet.remove(channelKey);
        inChannel.put(uuid, channelSet);
        PlayerFunctions.savePlayerSettingsToFile(uuid);
    }
    public Set<String> currentlyInChannels(UUID uuid) {
        inChannelNullCheck();
        if (inChannel.containsKey(uuid)) {
            return inChannel.get(uuid);
        } else {
            return new HashSet<>();
        }
    }

    // Global Mute
    private Set<UUID> globalMute = new HashSet<>();
    private void globalMuteNullCheck() {
        if (globalMute == null) {
            globalMute = new HashSet<>();
        }
    }
    public boolean isGlobalMuted(UUID uuid) {
        globalMuteNullCheck();
        return globalMute.contains(uuid);
    }
    public void putGlobalMute(UUID uuid) {
        globalMuteNullCheck();
        if (!globalMute.contains(uuid)) {
            globalMute.add(uuid);
            PlayerFunctions.savePlayerSettingsToFile(uuid);
        }
    }
    public void removeGlobalMute(UUID uuid) {
        globalMuteNullCheck();
        if (!globalMute.contains(uuid)) {
            globalMute.remove(uuid);
            PlayerFunctions.savePlayerSettingsToFile(uuid);
        }
    }
    public void clearGlobalMute() {
        globalMuteNullCheck();
        globalMute = new HashSet<>();
    }

    // Channel Mute
    private Map<UUID, Set<String>> channelMute = new HashMap<>();
    private void channelMuteNullCheck() {
        if (channelMute == null) {
            channelMute = new HashMap<>();
        }
    }
    public boolean isChannelMuted(UUID uuid, String channel) {
        channelMuteNullCheck();
        if (channelMute.containsKey(uuid)) {
            for (Map.Entry<UUID, Set<String>> muteEntry : channelMute.entrySet()) {
                if (muteEntry.getKey().equals(uuid)) {
                    if (muteEntry.getValue() != null) {
                        for (String channelKey : muteEntry.getValue()) {
                            if (channelKey.equalsIgnoreCase(channel)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    public void putChannelMute(UUID uuid, String channel) {
        channelMuteNullCheck();
        Set<String> mutedChannels = channelMute.get(uuid);
        if (mutedChannels == null) {
            mutedChannels = new HashSet<>();
        }
        mutedChannels.add(channel);
        channelMute.put(uuid, mutedChannels);
        PlayerFunctions.savePlayerSettingsToFile(uuid);
    }
    public void removeChannelMute(UUID uuid, String channel) {
        channelMuteNullCheck();
        Set<String> mutedChannels = channelMute.get(uuid);
        if (mutedChannels == null) {
            mutedChannels = new HashSet<>();
        }
        mutedChannels.remove(channel);
        channelMute.put(uuid, mutedChannels);
        PlayerFunctions.savePlayerSettingsToFile(uuid);
    }
    public Set<String> channelMuteChannels(UUID uuid) {
        channelMuteNullCheck();
        if (channelMute.containsKey(uuid)) {
            return channelMute.get(uuid);
        } else {
            return new HashSet<>();
        }
    }
    public void clearGlobalChannelMute() {
        channelMuteNullCheck();
        channelMute = new HashMap<>();
    }

    // DiscordSRV
    boolean discordHooked = false;
    DiscordSRVHook discordSRV = new DiscordSRVHook();

    public void hookDiscordSRV() {
        discordSRV.hook();
    }
    public boolean isDiscordHooked() {
        return discordHooked;
    }
    public void setDiscordHooked(boolean discordHooked) {
        this.discordHooked = discordHooked;
    }

    // Vault
    boolean vaultPermissionHooked = false;
    boolean vaultChatHooked = false;
    net.milkbowl.vault.permission.Permission vaultPermission = null;
    net.milkbowl.vault.chat.Chat vaultChat = null;
    public boolean isVaultPermissionHooked() {
        return vaultPermissionHooked;
    }
    public boolean isVaultChatHooked() {
        return vaultChatHooked;
    }
    public void hookVault() {
        Plugin plug = Gab.getInstance().getServer().getPluginManager().getPlugin("Vault");
        if (plug != null) {
            RegisteredServiceProvider<net.milkbowl.vault.permission.Permission> rspPerm =
                    plugin.getServer()
                            .getServicesManager()
                            .getRegistration(net.milkbowl.vault.permission.Permission.class);
            if (rspPerm != null) {
                vaultPermission = rspPerm.getProvider();
                this.vaultPermissionHooked = true;
            } else {
                this.vaultPermissionHooked = false;
            }

            RegisteredServiceProvider<net.milkbowl.vault.chat.Chat> rspChat =
                    plugin.getServer()
                            .getServicesManager()
                            .getRegistration(net.milkbowl.vault.chat.Chat.class);
            if (rspChat != null) {
                vaultChat = rspChat.getProvider();
                this.vaultChatHooked = true;
            } else {
                this.vaultChatHooked = false;
            }
        } else {
            this.vaultPermissionHooked = false;
            this.vaultChatHooked = false;
        }
    }
    public net.milkbowl.vault.permission.Permission vaultPermission() {
        return this.vaultPermission;
    }
    public net.milkbowl.vault.chat.Chat vaultChat() {
        return this.vaultChat;
    }
}
