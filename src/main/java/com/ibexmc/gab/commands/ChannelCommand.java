package com.ibexmc.gab.commands;

import com.ibexmc.gab.util.functions.StringFunctions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class ChannelCommand implements CommandExecutor, TabCompleter {

    /**
     * onTabComplete Returns a list of tab completions for online players
     * @param sender CommandSender sending the command
     * @param cmd Command sent
     * @param label Label of command
     * @param args Arguments sent to the command
     * @return List<String> String list of avaialble tab completions
     */
    @Override
    public List<String> onTabComplete (@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args){
        Map<Integer, String> arguments = StringFunctions.getArguments(args);
        return TabCompletions.channel(sender, arguments);
    }

    /**
     * onCommand Called when the player submits the /gab command
     * @param sender CommandSender sending the command
     * @param cmd Command sent
     * @param label Label of command
     * @param args Arguments sent to the command
     * @return boolean Returns true if successful
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        Map<Integer, String> arguments = StringFunctions.getArguments(args);
        CommandActions.channel(sender, arguments, label);
        return true;
    }
}
