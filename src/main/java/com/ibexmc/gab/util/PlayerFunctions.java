package com.ibexmc.gab.util;

import com.ibexmc.gab.Gab;
import com.ibexmc.gab.data.Channel;
import com.ibexmc.gab.util.log.Error;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerFunctions {
    public static void loadPlayerSettingsFromFile(Player player) {

        UUID uuid = player.getUniqueId();

        File dataFile = FileUtils.getFileByUUID("players", uuid);
        if (!dataFile.exists()) {
            savePlayerSettingsToFile(uuid);
        }

        Gab plugin = Gab.getInstance();

        ConfigParser configParser = new ConfigParser(plugin, dataFile);

        // Currently In
        ConfigParser.ConfigReturn currentlyInResult = configParser.getValue(
                "currently_in",
                new ArrayList<>(),
                false
        );
        if (currentlyInResult.isSuccess()) {
            for (String currentlyIn : currentlyInResult.getStringList()) {
                if (!currentlyIn.equalsIgnoreCase("global")) {
                    plugin.data().putPlayerInChannel(uuid, currentlyIn);
                    //plugin.log().quick("putting player in " + currentlyIn);
                }
            }
        }

        // Currently Chatting In
        ConfigParser.ConfigReturn currentlyChattingInResult = configParser.getValue(
                "currently_chatting_in",
                "global",
                false
        );
        if (currentlyChattingInResult.isSuccess()) {
            plugin.data().setCurrentChannel(uuid, currentlyChattingInResult.getString());
            plugin.log().quick("player currently chatting in " + currentlyChattingInResult.getString());
            HashMap<String, String> placeHolder = new HashMap<>();
            Channel currentChannel = plugin.data().getChannel(currentlyChattingInResult.getString());
            if (currentChannel == null) {
                currentChannel = new Channel();
            }
            placeHolder.put("<%channel%>", currentChannel.getName());
            plugin.message().player(
                    "current_channel",
                    "&3You are now talking in <%channel%>",
                    player,
                    true,
                    placeHolder
            );
        }

        // Notifications Muted
        ConfigParser.ConfigReturn notificationsMutedResult = configParser.getValue(
                "notifications_muted",
                false,
                false
        );
        if (notificationsMutedResult.isSuccess()) {
            if (notificationsMutedResult.getBoolean()) {
                plugin.data().mutePlayerNotifications(uuid);
                //plugin.log().quick("muting player notifications");
            }
        }

        // Globally Muted
        ConfigParser.ConfigReturn globalMuteResult = configParser.getValue(
                "global_mute",
                false,
                false
        );
        if (globalMuteResult.isSuccess()) {
            if (globalMuteResult.getBoolean()) {
                plugin.data().putGlobalMute(uuid);
                //plugin.log().quick("globally muting player");
            }
        }

        // Channel Mute
        ConfigParser.ConfigReturn channelMuteResult = configParser.getValue(
                "channel_mute",
                new ArrayList<>(),
                false
        );
        if (channelMuteResult.isSuccess()) {
            for (String channelMuteChannel : channelMuteResult.getStringList()) {
                plugin.data().putChannelMute(uuid, channelMuteChannel);
                //plugin.log().quick("channel muting player player in " + channelMuteChannel);
            }
        }

    }
    public static void savePlayerSettingsToFile(UUID uuid) {
        File dataFile = FileUtils.getFileByUUID("players", uuid);
        FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(dataFile);

        // Currently In
        List<String> currentlyIn = new ArrayList<>();
        currentlyIn.add("global");
        for (String channel : Gab.getInstance().data().currentlyInChannels(uuid)) {
            if (!channel.equalsIgnoreCase("global")) {
                currentlyIn.add(channel);
            }
        }
        fileConfig.set("currently_in", currentlyIn);

        // Currently Chatting In
        Channel channel = Gab.getInstance().data().currentChatChannel(uuid);
        if (channel == null) {
           channel = new Channel();
        }
        fileConfig.set("currently_chatting_in", channel.getKey());

        // Notifications Muted List
        boolean notificationsMuted = Gab.getInstance().data().playerNotificationsMuted(uuid);
        fileConfig.set("notifications_muted", notificationsMuted);

        // Globally Muted
        boolean globallyMuted = Gab.getInstance().data().isGlobalMuted(uuid);
        fileConfig.set("global_mute", globallyMuted);

        // Channel Muted
        List<String> channelMuted = new ArrayList<>();
        for (String mutedChannel : Gab.getInstance().data().channelMuteChannels(uuid)) {
            channelMuted.add(mutedChannel);
        }
        fileConfig.set("channel_mute", channelMuted);

        try {
            fileConfig.save(dataFile);
            if (dataFile.length() == 0) {
                //Logging.log("Blank file found", customYml.getName());
            }
        } catch (IOException e) {
            Gab.getInstance().error().save(
                    "PlayerFunctions.savePlayerSettingsToFile.001",
                    "PlayerFunctions",
                    "savePlayerSettingsToFile()",
                    "Unexpected error trying to save",
                    "UUID: " + uuid,
                    Error.Severity.CRITICAL,
                    e.getStackTrace()
            );
        }
    }
}
