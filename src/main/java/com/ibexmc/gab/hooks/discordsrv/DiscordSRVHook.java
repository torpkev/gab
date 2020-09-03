package com.ibexmc.gab.hooks.discordsrv;

import com.ibexmc.gab.Gab;
import com.ibexmc.gab.util.log.Error;
import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import org.bukkit.plugin.Plugin;

public class DiscordSRVHook {
    public void hook() {
        Plugin plug = Gab.getInstance().getServer().getPluginManager().getPlugin("DiscordSRV");
        if (plug != null) {
            Gab.getInstance().data().setDiscordHooked(true);
        } else {
            Gab.getInstance().data().setDiscordHooked(false);
        }
    }
    public void message(String message) {
        try {
            if (!Gab.getInstance().data().isDiscordHooked()) {
                // DiscordSRV not found
                return;
            }
            if (!DiscordSRV.isReady) {
                Gab.getInstance().debug().log(
                        "DiscordSRVHook",
                        "messasge(String)",
                        "DiscordSRV returning not ready",
                        "DiscordSRV.isReady == " + DiscordSRV.isReady
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
                        Gab.getInstance().log().quick("Can't talk");
                    }
                } catch (Exception ex) {
                    Gab.getInstance().log().quick(ex.getMessage());
                }
            } else {
                Gab.getInstance().error().save(
                        "001",
                        "DiscordSRVHook",
                        "message(String)",
                        "No main text channel found",
                        "textChannel returned null",
                        Error.Severity.URGENT,
                        null
                );
            }
        } catch (Exception ex) {
            Gab.getInstance().error().save(
                    "002",
                    "DiscordSRVHook",
                    "message(String)",
                    "Unexpected Error",
                    ex.getMessage(),
                    Error.Severity.URGENT,
                    ex.getStackTrace()
            );

        }


    }
}
