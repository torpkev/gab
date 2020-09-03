package com.ibexmc.gab.commands;

import com.ibexmc.gab.Gab;
import com.ibexmc.gab.data.Channel;
import com.ibexmc.gab.util.Permission;
import com.ibexmc.gab.util.StringFunctions;
import com.ibexmc.gab.util.alert.Chat;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ChatListCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Gab plugin = Gab.getInstance();

        // Check if the plugin is disabled
        if (!plugin.data().isEnabled()) {
            plugin.message().sender(
                    "gab_disabled",
                    "&cGab is disabled",
                    sender,
                    true,
                    null
            );
            return true;
        }

        Map<Integer, String> arguments = StringFunctions.getArguments(args);

        Player player;

        if (sender instanceof Player) {
            player = (Player) sender;
        } else {
            plugin.message().sender(
                    "no_console",
                    "&cSorry, you cannot use this command from the console",
                    sender,
                    true,
                    null
            );
            return true;
        }

        if (arguments.size() == 0) {
            TextComponent result = StringFunctions.getTextComponent(
                    plugin.locale().getLocaleTextByCode(
                            "channel_list_prefix",
                            "&lChannels: &f",
                            null
                            ),
                    null,
                    null,
                    null,
                    ""
            );
            String clickToJoin = plugin.locale().getLocaleTextByCode(
                    "click_to_join",
                    "&bClick to join",
                    null
            );
            Map<String, Channel> channels = plugin.data().getChannels();
            if (!channels.containsKey("global")) {
                channels.put("global", new Channel());
            }
            for (Map.Entry<String, Channel> channelEntry : channels.entrySet()) {
                if (Permission.isChatter(player, channelEntry.getValue().getKey())) {
                    result.addExtra(
                            StringFunctions.getTextComponent(
                                    StringFunctions.colorCode("&f[&b" + channelEntry.getValue().getName() + "&f]"),
                                    HoverEvent.Action.SHOW_TEXT,
                                    clickToJoin,
                                    ClickEvent.Action.RUN_COMMAND,
                                    "/ch " + channelEntry.getValue().getKey()
                            )
                    );
                    result.addExtra(" ");
                }
            }
            Chat chat = new Chat(plugin);
            chat.player(player, result);
        } else {
            HashMap<String, String> chlistPlaceHolder = new HashMap<>();
            chlistPlaceHolder.put("<%cmd%>", label);
            plugin.message().sender(
                    "chlist_usage",
                    "&lUsage:&r /<%cmd%>",
                    sender,
                    true,
                    chlistPlaceHolder
            );
        }
        return true;
    }
}
