package com.ibexmc.gab.listeners;

import com.ibexmc.gab.Gab;
import com.ibexmc.gab.data.Channel;
import com.ibexmc.gab.hooks.discordsrv.DiscordSRVHook;
import com.ibexmc.gab.util.Permission;
import com.ibexmc.gab.util.PlayerFunctions;
import com.ibexmc.gab.util.Sounds;
import com.ibexmc.gab.util.StringFunctions;
import com.ibexmc.gab.util.alert.Chat;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

public class PlayerListener implements Listener {
    private static Gab plugin = Gab.instance;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        PlayerFunctions.loadPlayerSettingsFromFile(event.getPlayer());
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        PlayerFunctions.savePlayerSettingsToFile(event.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {

        // If the plugin is disabled, exit out
        if (!plugin.data().isEnabled()) {
            return;
        }

        plugin.debug().enter("PlayerListener", "onPlayerChat");

        // Check to make sure there is a player (there should be)
        if (event.getPlayer() != null) {
            // Get the players current channel
            Channel channel = plugin.data().currentChatChannel(event.getPlayer().getUniqueId());

            // If there is no channel, put the player in global
            if (channel == null) {
                channel = new Channel();
            }

            // Cancel the chat event so we don't send it to global
            event.setCancelled(true);

            // Check if globally muted
            if (plugin.data().isGlobalMuted(event.getPlayer().getUniqueId())) {
                // global muted
                plugin.debug().log(
                        "PlayerListener",
                        "onPlayerChat",
                        event.getPlayer().getDisplayName() + " tried to talk in " + channel.getKey() +
                                " but is globally muted",
                        "Player UUID: " + event.getPlayer().getUniqueId()
                );
                plugin.message().player(
                        "global_muted",
                        "&cSorry, you are muted in all channels",
                        event.getPlayer(),
                        true,
                        null
                );
                return;
            }

            // Check if muted in this channel
            if (plugin.data().isChannelMuted(event.getPlayer().getUniqueId(), channel.getName())) {
                // channel muted
                plugin.debug().log(
                        "PlayerListener",
                        "onPlayerChat",
                        event.getPlayer().getDisplayName() + " tried to talk in " + channel.getKey() +
                                " but is muted in this channel",
                        "Player UUID: " + event.getPlayer().getUniqueId()
                );
                plugin.message().player(
                        "channel_muted",
                        "&cSorry, you are muted in this channel",
                        event.getPlayer(),
                        true,
                        null
                );
                return;
            }

            // Get the prefix for in-game chat
            String prefix = channel.prefix(event.getPlayer());

            // Get the message to be sent
            String message = event.getMessage();

            // Get the discord message in its own variable so we can apply its own formatting
            String discordMessage = message;

            // Get chatters by permission for this channel
            List<Player> chatters = Permission.chatters(channel.getName());

            // Check to make sure that a list was returned
            if (chatters != null) {
                // Check if the list is empty - How can it be if someone is chatting there?
                if (chatters.size() <= 0) {
                    plugin.log().quick("No chatters found - 0 count");
                }

                // Build a TextComponent with the prefix
                TextComponent textComponent = new TextComponent(prefix);
                // Add the message (via prepMessage in StringFunctions)
                textComponent.addExtra(StringFunctions.prepMessage(event.getPlayer(), channel, message));

                // Loop through all chatters
                for (Player player : chatters) {
                    // Check if the channel is global OR if the player has this channel active
                    // Global chat will always be sent to all players
                    if (
                            channel.getKey().equalsIgnoreCase("global") ||
                            plugin.data().playerInChannel(player.getUniqueId(), channel.getKey())
                    ) {

                        // Send in-game message
                        Chat chat = new Chat(plugin);
                        chat.player(player, textComponent);

                        plugin.debug().log(
                                "PlayerListener",
                                "onPlayerChat",
                                "Message sent to player in-game",
                                "Player UUID: " + player.getUniqueId()
                        );

                        // If the message contains the players name, play a sound if notifications aren't muted
                        if (event.getMessage().contains(ChatColor.stripColor(player.getDisplayName()))) {
                            if (!plugin.data().playerNotificationsMuted(player.getUniqueId())) {
                                Sounds.playerSoundPlayer(player, Sound.ENTITY_CHICKEN_EGG, 1, 1);
                                plugin.debug().log(
                                        "PlayerListener",
                                        "onPlayerChat",
                                        "Player was mentioned in chat and received a sound notification",
                                        "Player UUID: " + player.getUniqueId()
                                );
                            } else {
                                plugin.debug().log(
                                        "PlayerListener",
                                        "onPlayerChat",
                                        "Player was mentioned in chat, but has notifications muted",
                                        "Player UUID: " + player.getUniqueId()
                                );
                            }
                        }

                    } else {
                        // Player is not currently active in this channel
                        plugin.debug().log(
                                "PlayerListener",
                                "onPlayerChat",
                                "Player did not receive message as they are not active in the channel",
                                "Player UUID: " + player.getUniqueId() + "\n" +
                                        "Channel: " + channel.getKey()
                        );
                    }
                } // end of player loop
            } else {
                // chatters is null (how?)
                plugin.debug().log(
                        "PlayerListener",
                        "onPlayerChat",
                        "Player talked in chhannel that has null chatters list",
                        "Player UUID: " + event.getPlayer().getUniqueId() + "\n" +
                                "Channel: " + channel.getKey()
                );
            }

            // If DiscordSRV hooked, send message to Discord
            if (channel.sendToDiscord()) {
                plugin.debug().log(
                        "PlayerListener",
                        "onPlayerChat",
                        "Send to Discord is enabled for channel",
                        "Channel: " + channel.getKey()
                );
                String fullMessage = "";
                // If the channel is not global, the prefix the chat with the Channel name
                // global will not have a prefix as it is the default
                if (!channel.getKey().equalsIgnoreCase("global")) {
                    fullMessage = "**" + channel.getName() + "**» ";
                }
                // Add the Discord formatting to the message
                fullMessage = fullMessage + channel.discordFormat(event.getPlayer(), discordMessage) + message;

                // Convert color codes to color, then strip color
                fullMessage = ChatColor.stripColor(StringFunctions.colorCode(fullMessage));

                // Send the message to the DiscordSRC class
                DiscordSRVHook discord = new DiscordSRVHook();
                discord.message(fullMessage);
            }
        }

    }
}
