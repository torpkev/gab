package com.ibexmc.gab.commands;

import com.ibexmc.gab.util.functions.StringFunctions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

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
        Map<Integer, String> arguments = StringFunctions.getArguments(args);
        CommandActions.me(sender, arguments, label);
        return true;
    }
}
