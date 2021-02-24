package com.ibexmc.gab.hooks.discordsrv;

import com.ibexmc.gab.Gab;
import com.ibexmc.gab.util.DebugType;
import com.ibexmc.gab.util.Severity;
import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import org.bukkit.plugin.Plugin;

public class DiscordSRVHook {
    public void hook() {
        Plugin plug = Gab.instance.getServer().getPluginManager().getPlugin("DiscordSRV");
        if (plug != null) {
            Gab.instance.data.setDiscordHooked(true);
        } else {
            Gab.instance.data.setDiscordHooked(false);
        }
    }
    public void message(String message) {
        try {
            if (!Gab.instance.data.isDiscordHooked()) {
                // DiscordSRV not found
                return;
            }
            if (!DiscordSRV.isReady) {
                Gab.instance.log.debug(
                        DebugType.INFO,
                        "DiscordSRVHook",
                        "message(String)",
                        "DiscordSRV returning not ready",
                        "DiscordSRV.isReady == false"
                );
                return;
            }
            DiscordSRV discord = DiscordSRV.getPlugin();

            TextChannel textChannel = DiscordSRV.getPlugin().getMainTextChannel();
            if (textChannel != null) {
                try {
                    if (textChannel.canTalk()) {
                        textChannel.sendMessage(message).queue();
                        String a = "B";
                    } else {
                        Gab.instance.log.quick("Can't talk");
                    }
                } catch (Exception ex) {
                    Gab.instance.log.quick(ex.getMessage());
                }
            } else {
                Gab.instance.log.error(
                        "001",
                        "DiscordSRVHook",
                        "message(String)",
                        "No main text channel found",
                        "textChannel returned null",
                        Severity.URGENT,
                        null
                );
            }
        } catch (Exception ex) {
            Gab.instance.log.error(
                    "002",
                    "DiscordSRVHook",
                    "message(String)",
                    "Unexpected Error",
                    ex.getMessage(),
                    Severity.URGENT,
                    ex.getStackTrace()
            );

        }


    }
}
