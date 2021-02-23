package com.ibexmc.gab.commands;

import com.ibexmc.gab.Gab;
import com.ibexmc.gab.util.Send;
import com.ibexmc.gab.util.functions.StringFunctions;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

public class MeCommand implements CommandExecutor {

    /**
     * onCommand Called when the /chme command
     * @param sender CommandSender sending the command
     * @param cmd Command sent
     * @param label Label of command
     * @param args Arguments sent to the command
     * @return boolean Returns true if successful
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            Player senderPlayer = (Player) sender;
            if (Gab.instance.data.globalMuted.contains(senderPlayer.getUniqueId())) {
                //TODO: You are globally muted
                return true;
            }
            Map<Integer, String> arguments = StringFunctions.getArguments(args);
            String action = "";
            if (arguments.size() > 0) {
                int i = 0;
                for (Map.Entry<Integer, String> argEntry : arguments.entrySet()) {
                    if (i == 0) {
                        action = Gab.instance.data.meColor + senderPlayer.getDisplayName() + " ";
                    }
                    if (i > 0) {
                        action = action + " ";
                    }
                    action = action + argEntry.getValue();
                    i++;
                }
                for (Player player : Bukkit.getOnlinePlayers()) {
                    boolean muted = false;
                    if (Gab.instance.data.muted.containsKey(player.getUniqueId())) {
                        for (Map.Entry<UUID, UUID> mutedEntry : Gab.instance.data.muted.entrySet()) {
                            if (mutedEntry.getValue().equals(player.getUniqueId())) {
                                muted = true;
                            }
                        }
                    }
                    if (!muted) {
                        Send.player(action, player, false);
                    }
                }
            } else {
                //TODO: Usage
            }
        } else {
            //TODO: no console
        }
        return true;
    }
}
