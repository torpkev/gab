package com.ibexmc.gab.commands;

import com.ibexmc.gab.Gab;
import com.ibexmc.gab.util.DebugType;
import com.ibexmc.gab.util.Send;
import com.ibexmc.gab.util.functions.StringFunctions;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CommandActions {
    /**
     * Reloads the config files
     * @param sender CommandSender sending the command
     * @param arguments Arguments sent to the command
     * @param commandAlias Command alias used
     */
    public static void reload(CommandSender sender, Map<Integer, String> arguments, String commandAlias) {
        //TODO: Reload
    }

    /**
     * Toggles the debug mode
     * @param sender CommandSender issuing the command.  Must be console or have admin permissions
     * @param arguments Arguments sent to the command
     * @param commandAlias Command alias used
     */
    public static void gabDebug(CommandSender sender, Map<Integer, String> arguments, String commandAlias) {
        Gab plugin = Gab.instance;
        // domain debug <optional type> - Toggles the debug modes
        if (arguments.containsKey(1)) {
            switch (arguments.get(1)) {
                case "info":
                    plugin.log.debug(DebugType.INFO, "Toggling Informational Debug Status");
                    Map<String, String> infoPlaceHolder = new HashMap<>();
                    if (plugin.data.debug.containsKey(DebugType.INFO)) {
                        if (plugin.data.debug.get(DebugType.INFO)) {
                            // Current on, set to off
                            infoPlaceHolder.put(
                                    "%status%",
                                    plugin.locale.get("toggle_off", "&cOff", null)
                            );
                            plugin.data.debug.put(DebugType.INFO, false);
                        } else {
                            // Currently off, set to on
                            infoPlaceHolder.put(
                                    "%status%",
                                    plugin.locale.get("toggle_on", "&aOn", null)
                            );
                            plugin.data.debug.put(DebugType.INFO, true);
                        }
                    } else {
                        // Not present, set to on
                        infoPlaceHolder.put(
                                "%status%",
                                plugin.locale.get("toggle_on", "&aOn", null)
                        );
                        plugin.data.debug.put(DebugType.INFO, true);
                    }
                    Send.sender(
                            "toggle_debug_info_message",
                            "Informational Debug Messages now %status%",
                            sender,
                            true,
                            infoPlaceHolder
                    );
                    break;
                case "startup":
                    plugin.log.debug(DebugType.INFO, "Toggling Startup Debug Status");
                    Map<String, String> startupPlaceHolder = new HashMap<>();
                    if (plugin.data.debug.containsKey(DebugType.STARTUP)) {
                        if (plugin.data.debug.get(DebugType.STARTUP)) {
                            // Current on, set to off
                            startupPlaceHolder.put(
                                    "%status%",
                                    plugin.locale.get("toggle_off", "&cOff", null)
                            );
                            plugin.data.debug.put(DebugType.STARTUP, false);
                        } else {
                            // Currently off, set to on
                            startupPlaceHolder.put(
                                    "%status%",
                                    plugin.locale.get("toggle_on", "&aOn", null)
                            );
                            plugin.data.debug.put(DebugType.STARTUP, true);
                        }
                    } else {
                        // Not present, set to on
                        startupPlaceHolder.put(
                                "%status%",
                                plugin.locale.get("toggle_on", "&aOn", null)
                        );
                        plugin.data.debug.put(DebugType.STARTUP, true);
                    }
                    Send.sender(
                            "toggle_debug_startup_message",
                            "Startup Debug Messages now %status%",
                            sender,
                            true,
                            startupPlaceHolder
                    );
                    break;
                case "enter":
                    plugin.log.debug(DebugType.INFO, "Toggling Function Entry Debug Status");
                    Map<String, String> enterPlaceHolder = new HashMap<>();
                    if (plugin.data.debug.containsKey(DebugType.ENTER)) {
                        if (plugin.data.debug.get(DebugType.ENTER)) {
                            // Current on, set to off
                            enterPlaceHolder.put(
                                    "%status%",
                                    plugin.locale.get("toggle_off", "&cOff", null)
                            );
                            plugin.data.debug.put(DebugType.ENTER, false);
                        } else {
                            // Currently off, set to on
                            enterPlaceHolder.put(
                                    "%status%",
                                    plugin.locale.get("toggle_on", "&aOn", null)
                            );
                            plugin.data.debug.put(DebugType.ENTER, true);
                        }
                    } else {
                        // Not present, set to on
                        enterPlaceHolder.put(
                                "%status%",
                                plugin.locale.get("toggle_on", "&aOn", null)
                        );
                        plugin.data.debug.put(DebugType.ENTER, true);
                    }
                    Send.sender(
                            "toggle_debug_enter_message",
                            "Function Entry Debug Messages now %status%",
                            sender,
                            true,
                            enterPlaceHolder
                    );
                    break;
                case "exit":
                    plugin.log.debug(DebugType.INFO, "Toggling Function Exit Debug Status");
                    Map<String, String> exitPlaceHolder = new HashMap<>();
                    if (plugin.data.debug.containsKey(DebugType.EXIT)) {
                        if (plugin.data.debug.get(DebugType.EXIT)) {
                            // Current on, set to off
                            exitPlaceHolder.put(
                                    "%status%",
                                    plugin.locale.get("toggle_off", "&cOff", null)
                            );
                            plugin.data.debug.put(DebugType.EXIT, false);
                        } else {
                            // Currently off, set to on
                            exitPlaceHolder.put(
                                    "%status%",
                                    plugin.locale.get("toggle_on", "&aOn", null)
                            );
                            plugin.data.debug.put(DebugType.EXIT, true);
                        }
                    } else {
                        // Not present, set to on
                        exitPlaceHolder.put(
                                "%status%",
                                plugin.locale.get("toggle_on", "&aOn", null)
                        );
                        plugin.data.debug.put(DebugType.EXIT, true);
                    }
                    Send.sender(
                            "toggle_debug_exit_message",
                            "Function Exit Debug Messages now %status%",
                            sender,
                            true,
                            exitPlaceHolder
                    );
                    break;
                case "events":
                    plugin.log.debug(DebugType.INFO, "Toggling Events Debug Status");
                    Map<String, String> eventsPlaceHolder = new HashMap<>();
                    if (plugin.data.debug.containsKey(DebugType.EVENTS)) {
                        if (plugin.data.debug.get(DebugType.EVENTS)) {
                            // Current on, set to off
                            eventsPlaceHolder.put(
                                    "%status%",
                                    plugin.locale.get("toggle_off", "&cOff", null)
                            );
                            plugin.data.debug.put(DebugType.EVENTS, false);
                        } else {
                            // Currently off, set to on
                            eventsPlaceHolder.put(
                                    "%status%",
                                    plugin.locale.get("toggle_on", "&aOn", null)
                            );
                            plugin.data.debug.put(DebugType.EVENTS, true);
                        }
                    } else {
                        // Not present, set to on
                        eventsPlaceHolder.put(
                                "%status%",
                                plugin.locale.get("toggle_on", "&aOn", null)
                        );
                        plugin.data.debug.put(DebugType.EVENTS, true);
                    }
                    Send.sender(
                            "toggle_debug_events_message",
                            "Event Debug Messages now %status%",
                            sender,
                            true,
                            eventsPlaceHolder
                    );
                    break;
                case "scheduled":
                    plugin.log.debug(DebugType.INFO, "Toggling Scheduled Task Debug Status");
                    Map<String, String> scheduledPlaceHolder = new HashMap<>();
                    if (plugin.data.debug.containsKey(DebugType.SCHEDULED)) {
                        if (plugin.data.debug.get(DebugType.SCHEDULED)) {
                            // Current on, set to off
                            scheduledPlaceHolder.put(
                                    "%status%",
                                    plugin.locale.get("toggle_off", "&cOff", null)
                            );
                            plugin.data.debug.put(DebugType.SCHEDULED, false);
                        } else {
                            // Currently off, set to on
                            scheduledPlaceHolder.put(
                                    "%status%",
                                    plugin.locale.get("toggle_on", "&aOn", null)
                            );
                            plugin.data.debug.put(DebugType.SCHEDULED, true);
                        }
                    } else {
                        // Not present, set to on
                        scheduledPlaceHolder.put(
                                "%status%",
                                plugin.locale.get("toggle_on", "&aOn", null)
                        );
                        plugin.data.debug.put(DebugType.SCHEDULED, true);
                    }
                    Send.sender(
                            "toggle_debug_scheduled_message",
                            "Scheduled Task Debug Messages now %status%",
                            sender,
                            true,
                            scheduledPlaceHolder
                    );
                    break;
                case "api":
                    plugin.log.debug(DebugType.INFO, "Toggling API Call Debug Status");
                    Map<String, String> apiPlaceHolder = new HashMap<>();
                    if (plugin.data.debug.containsKey(DebugType.API)) {
                        if (plugin.data.debug.get(DebugType.API)) {
                            // Current on, set to off
                            apiPlaceHolder.put(
                                    "%status%",
                                    plugin.locale.get("toggle_off", "&cOff", null)
                            );
                            plugin.data.debug.put(DebugType.API, false);
                        } else {
                            // Currently off, set to on
                            apiPlaceHolder.put(
                                    "%status%",
                                    plugin.locale.get("toggle_on", "&aOn", null)
                            );
                            plugin.data.debug.put(DebugType.API, true);
                        }
                    } else {
                        // Not present, set to on
                        apiPlaceHolder.put(
                                "%status%",
                                plugin.locale.get("toggle_on", "&aOn", null)
                        );
                        plugin.data.debug.put(DebugType.API, true);
                    }
                    Send.sender(
                            "toggle_debug_api_message",
                            "API Call Debug Messages now %status%",
                            sender,
                            true,
                            apiPlaceHolder
                    );
                    break;
                case "other":
                    plugin.log.debug(DebugType.INFO, "Toggling Miscellaneous Debug Status");
                    Map<String, String> otherPlaceHolder = new HashMap<>();
                    if (plugin.data.debug.containsKey(DebugType.OTHER)) {
                        if (plugin.data.debug.get(DebugType.OTHER)) {
                            // Current on, set to off
                            otherPlaceHolder.put(
                                    "%status%",
                                    plugin.locale.get("toggle_off", "&cOff", null)
                            );
                            plugin.data.debug.put(DebugType.OTHER, false);
                        } else {
                            // Currently off, set to on
                            otherPlaceHolder.put(
                                    "%status%",
                                    plugin.locale.get("toggle_on", "&aOn", null)
                            );
                            plugin.data.debug.put(DebugType.OTHER, true);
                        }
                    } else {
                        // Not present, set to on
                        otherPlaceHolder.put(
                                "%status%",
                                plugin.locale.get("toggle_on", "&aOn", null)
                        );
                        plugin.data.debug.put(DebugType.OTHER, true);
                    }
                    Send.sender(
                            "toggle_debug_other_message",
                            "Miscellaneous Debug Messages now %status%",
                            sender,
                            true,
                            otherPlaceHolder
                    );
                    break;
                case "temp":
                    plugin.log.debug(DebugType.INFO, "Toggling Temporary Debug Status");
                    Map<String, String> tempPlaceHolder = new HashMap<>();
                    if (plugin.data.debug.containsKey(DebugType.TEMP)) {
                        if (plugin.data.debug.get(DebugType.TEMP)) {
                            // Current on, set to off
                            tempPlaceHolder.put(
                                    "%status%",
                                    plugin.locale.get("toggle_off", "&cOff", null)
                            );
                            plugin.data.debug.put(DebugType.TEMP, false);
                        } else {
                            // Currently off, set to on
                            tempPlaceHolder.put(
                                    "%status%",
                                    plugin.locale.get("toggle_on", "&aOn", null)
                            );
                            plugin.data.debug.put(DebugType.TEMP, true);
                        }
                    } else {
                        // Not present, set to on
                        tempPlaceHolder.put(
                                "%status%",
                                plugin.locale.get("toggle_on", "&aOn", null)
                        );
                        plugin.data.debug.put(DebugType.TEMP, true);
                    }
                    Send.sender(
                            "toggle_debug_temp_message",
                            "Temporary Debug Messages now %status%",
                            sender,
                            true,
                            tempPlaceHolder
                    );
                    break;
                default:
                    break;
            }
        } else {

            TextComponent textComponent = new TextComponent(
                    plugin.locale.get("toggle_debug_header", "&lToggle Debug: &r", null)
            );
            TextComponent tcSpacer = new TextComponent(StringFunctions.color("&f | "));
            String color = "&a";
            if (plugin.data.debug.containsKey(DebugType.INFO)) {
                if (plugin.data.debug.get(DebugType.INFO)) {
                    color = "&a";
                } else {
                    color = "&c";
                }
            } else {
                color = "&c";
            }

            TextComponent tcInfo = StringFunctions.getTextComponent(
                    StringFunctions.color(
                            color +
                                    plugin.locale.get("toggle_debug_info", "Info&r", null)
                    ),
                    HoverEvent.Action.SHOW_TEXT,
                    StringFunctions.color(
                            StringFunctions.color(
                                    color +
                                            plugin.locale.get(
                                                    "toggle_debug_info_hover",
                                                    "&fToggle Informational Debug Messages"
                                                    , null
                                            )
                            )
                    ),
                    ClickEvent.Action.RUN_COMMAND,
                    "/clan debug info"
            );
            textComponent.addExtra(tcInfo);
            textComponent.addExtra(tcSpacer);

            if (plugin.data.debug.containsKey(DebugType.STARTUP)) {
                if (plugin.data.debug.get(DebugType.STARTUP)) {
                    color = "&a";
                } else {
                    color = "&c";
                }
            } else {
                color = "&c";
            }
            TextComponent tcStartup = StringFunctions.getTextComponent(
                    StringFunctions.color(
                            color + plugin.locale.get("toggle_debug_startup", "Startup&r", null)
                    ),
                    HoverEvent.Action.SHOW_TEXT,
                    StringFunctions.color(
                            StringFunctions.color(
                                    color +
                                            plugin.locale.get(
                                                    "toggle_debug_startup_hover",
                                                    "&fToggle Startup Debug Messages"
                                                    , null
                                            )
                            )
                    ),
                    ClickEvent.Action.RUN_COMMAND,
                    "/clan debug startup"
            );
            textComponent.addExtra(tcStartup);
            textComponent.addExtra(tcSpacer);

            if (plugin.data.debug.containsKey(DebugType.ENTER)) {
                if (plugin.data.debug.get(DebugType.ENTER)) {
                    color = "&a";
                } else {
                    color = "&c";
                }
            } else {
                color = "&c";
            }
            TextComponent tcEnter = StringFunctions.getTextComponent(
                    StringFunctions.color(
                            color + plugin.locale.get("toggle_debug_enter", "Function Entry&r", null)
                    ),
                    HoverEvent.Action.SHOW_TEXT,
                    StringFunctions.color(
                            StringFunctions.color(
                                    color +
                                            plugin.locale.get(
                                                    "toggle_debug_enter_hover",
                                                    "&fToggle Function Entry Debug Messages"
                                                    , null
                                            )
                            )
                    ),
                    ClickEvent.Action.RUN_COMMAND,
                    "/clan debug enter"
            );
            textComponent.addExtra(tcEnter);
            textComponent.addExtra(tcSpacer);

            if (plugin.data.debug.containsKey(DebugType.EXIT)) {
                if (plugin.data.debug.get(DebugType.EXIT)) {
                    color = "&a";
                } else {
                    color = "&c";
                }
            } else {
                color = "&c";
            }
            TextComponent tcExit = StringFunctions.getTextComponent(
                    StringFunctions.color(
                            color + plugin.locale.get("toggle_debug_exit", "Function Exit&r", null)
                    ),
                    HoverEvent.Action.SHOW_TEXT,
                    StringFunctions.color(
                            StringFunctions.color(
                                    color +
                                            plugin.locale.get(
                                                    "toggle_debug_exit_hover",
                                                    "&fToggle Function Exit Debug Messages"
                                                    , null
                                            )
                            )
                    ),
                    ClickEvent.Action.RUN_COMMAND,
                    "/clan debug exit"
            );
            textComponent.addExtra(tcExit);
            textComponent.addExtra(tcSpacer);

            if (plugin.data.debug.containsKey(DebugType.EVENTS)) {
                if (plugin.data.debug.get(DebugType.EVENTS)) {
                    color = "&a";
                } else {
                    color = "&c";
                }
            } else {
                color = "&c";
            }
            TextComponent tcEvents = StringFunctions.getTextComponent(
                    StringFunctions.color(
                            color + plugin.locale.get("toggle_debug_events", "Events&r", null)
                    ),
                    HoverEvent.Action.SHOW_TEXT,
                    StringFunctions.color(
                            StringFunctions.color(
                                    color +
                                            plugin.locale.get(
                                                    "toggle_debug_events_hover",
                                                    "&fToggle Event Debug Messages"
                                                    , null
                                            )
                            )
                    ),
                    ClickEvent.Action.RUN_COMMAND,
                    "/clan debug events"
            );
            textComponent.addExtra(tcEvents);
            textComponent.addExtra(tcSpacer);

            if (plugin.data.debug.containsKey(DebugType.SCHEDULED)) {
                if (plugin.data.debug.get(DebugType.SCHEDULED)) {
                    color = "&a";
                } else {
                    color = "&c";
                }
            } else {
                color = "&c";
            }
            TextComponent tcScheduled = StringFunctions.getTextComponent(
                    StringFunctions.color(
                            color + plugin.locale.get("toggle_debug_scheduled", "Scheduled Task&r", null)
                    ),
                    HoverEvent.Action.SHOW_TEXT,
                    StringFunctions.color(
                            StringFunctions.color(
                                    color +
                                            plugin.locale.get(
                                                    "toggle_debug_scheduled_hover",
                                                    "&fToggle Scheduled Task Debug Messages"
                                                    , null
                                            )
                            )
                    ),
                    ClickEvent.Action.RUN_COMMAND,
                    "/clan debug scheduled"
            );
            textComponent.addExtra(tcScheduled);
            textComponent.addExtra(tcSpacer);

            if (plugin.data.debug.containsKey(DebugType.API)) {
                if (plugin.data.debug.get(DebugType.API)) {
                    color = "&a";
                } else {
                    color = "&c";
                }
            } else {
                color = "&c";
            }
            TextComponent tcAPI = StringFunctions.getTextComponent(
                    StringFunctions.color(
                            color + plugin.locale.get("toggle_debug_api", "API Call&r", null)
                    ),
                    HoverEvent.Action.SHOW_TEXT,
                    StringFunctions.color(
                            StringFunctions.color(
                                    color +
                                            plugin.locale.get(
                                                    "toggle_debug_api_hover",
                                                    "&fToggle API Call Messages"
                                                    , null
                                            )
                            )
                    ),
                    ClickEvent.Action.RUN_COMMAND,
                    "/clan debug api"
            );
            textComponent.addExtra(tcAPI);
            textComponent.addExtra(tcSpacer);

            if (plugin.data.debug.containsKey(DebugType.OTHER)) {
                if (plugin.data.debug.get(DebugType.OTHER)) {
                    color = "&a";
                } else {
                    color = "&c";
                }
            } else {
                color = "&c";
            }
            TextComponent tcOther = StringFunctions.getTextComponent(
                    StringFunctions.color(
                            color + plugin.locale.get("toggle_debug_other", "Miscellaneous&r", null)
                    ),
                    HoverEvent.Action.SHOW_TEXT,
                    StringFunctions.color(
                            StringFunctions.color(
                                    color +
                                            plugin.locale.get(
                                                    "toggle_debug_other_hover",
                                                    "&fToggle Miscellaneous Debug Messages"
                                                    , null
                                            )
                            )
                    ),
                    ClickEvent.Action.RUN_COMMAND,
                    "/clan debug other"
            );
            textComponent.addExtra(tcOther);
            textComponent.addExtra(tcSpacer);

            if (plugin.data.debug.containsKey(DebugType.OTHER)) {
                if (plugin.data.debug.get(DebugType.OTHER)) {
                    color = "&a";
                } else {
                    color = "&c";
                }
            } else {
                color = "&c";
            }
            TextComponent tcTemp = StringFunctions.getTextComponent(
                    StringFunctions.color(
                            color + plugin.locale.get("toggle_debug_temp", "Temporary&r", null)
                    ),
                    HoverEvent.Action.SHOW_TEXT,
                    StringFunctions.color(
                            StringFunctions.color(
                                    color +
                                            plugin.locale.get(
                                                    "toggle_debug_temp_hover",
                                                    "&fToggle Temporary Debug Messages"
                                                    , null
                                            )
                            )
                    ),
                    ClickEvent.Action.RUN_COMMAND,
                    "/clan debug temp"
            );
            textComponent.addExtra(tcTemp);

            Send.sender(textComponent, sender, true);
        }
    }

    /**
     * Displays the current version
     * @param sender CommandSender sending the command
     * @param arguments Arguments sent to the command
     * @param commandAlias Command alias used
     */
    public static void version(CommandSender sender, Map<Integer, String> arguments, String commandAlias) {
        //TODO: Version
    }

    /**
     * Globally mutes a player
     * @param sender CommandSender sending the command
     * @param arguments Arguments sent to the command
     * @param commandAlias Command alias used
     */
    public static void globalMute(CommandSender sender, Map<Integer, String> arguments, String commandAlias) {
        //TODO: Mute
    }

    /**
     * Globally unmutes a player
     * @param sender CommandSender sending the command
     * @param arguments Arguments sent to the command
     * @param commandAlias Command alias used
     */
    public static void globalUnmute(CommandSender sender, Map<Integer, String> arguments, String commandAlias) {
        //TODO: Unmute
    }


    /**
     * Displays the /gab usage
     * @param sender CommandSender sending the command
     * @param arguments Arguments sent to the command
     * @param commandAlias Command alias used
     */
    public static void gabUsage(CommandSender sender, Map<Integer, String> arguments, String commandAlias) {
        //TODO: Usage
    }

    /**
     * Sends a /me action
     * @param sender CommandSender sending the command
     * @param arguments Arguments sent to the command
     * @param commandAlias Command alias used
     */
    public static void me(CommandSender sender, Map<Integer, String> arguments, String commandAlias) {
        if (sender instanceof Player) {
            Player senderPlayer = (Player) sender;
            //TODO: Perm check for gab.mute

            String action = "";
            if (arguments.size() > 0) {
                int i = 0;
                for (Map.Entry<Integer, String> argEntry : arguments.entrySet()) {
                    if (i == 0) {
                        action = Gab.instance.data.configMeColor + senderPlayer.getDisplayName() + " ";
                    }
                    if (i > 0) {
                        action = action + " ";
                    }
                    action = action + argEntry.getValue();
                    i++;
                }
                for (Player player : Bukkit.getOnlinePlayers()) {
                    boolean muted = false;
                    //TODO: Perm check for gab.channel.<channel>.mute
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
    }

    /**
     * Sends a /say action
     * @param sender CommandSender sending the command
     * @param arguments Arguments sent to the command
     * @param commandAlias Command alias used
     */
    public static void say(CommandSender sender, Map<Integer, String> arguments, String commandAlias) {
        boolean canSay = false;
        if (sender instanceof Player) {
            Player senderPlayer = (Player) sender;
            //TODO: Perm check for gab.say
        } else {
            canSay = true; // Console can always say
        }
        String message = "";
        if (arguments.size() > 0) {
            int i = 0;
            for (Map.Entry<Integer, String> argEntry : arguments.entrySet()) {
                if (i == 0) {
                    message = Gab.instance.data.configSayColor;
                }
                if (i > 0) {
                    message = message + " ";
                }
                message = message + argEntry.getValue();
                i++;
            }
            for (Player player : Bukkit.getOnlinePlayers()) {
                Send.player(message, player, false);
            }
        } else {
            //TODO: Usage
        }
    }

    /**
     * Sends the /channel action which changes the current chat channel
     * @param sender CommandSender sending the command
     * @param arguments Arguments sent to the command
     * @param commandAlias Command alias used
     */
    public static void channel(CommandSender sender, Map<Integer, String> arguments, String commandAlias) {
        //TODO: /ch
    }

    /**
     * Sends the /chlist action which lists all channels player has access to
     * @param sender CommandSender sending the command
     * @param arguments Arguments sent to the command
     * @param commandAlias Command alias used
     */
    public static void list(CommandSender sender, Map<Integer, String> arguments, String commandAlias) {
        //TODO: /chlist
    }

    /**
     * Sends the /chspy action which toggles chatspy mode on/off
     * @param sender CommandSender sending the command
     * @param arguments Arguments sent to the command
     * @param commandAlias Command alias used
     */
    public static void spy(CommandSender sender, Map<Integer, String> arguments, String commandAlias) {
        //TODO: /chspy
    }
}
