package com.ibexmc.gab.util.functions;

import com.ibexmc.gab.Gab;
import com.ibexmc.gab.util.SpigotVersion;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.*;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class StringFunctions {

    /**
     * Checks if a string is null or empty
     * @param text String to check
     * @return If true, string is null or empty
     */
    public static boolean isNullOrEmpty(String text) {
        if (text != null) {
            if (text.length() > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * uuidFromString(String)
     * Converts a string value to a UUID.  Returns null if invalid
     * @param uuid String value of the UUID
     * @return UUID
     */
    public static UUID uuidFromString(String uuid) {
        return UUID.fromString(uuid);
    }

    /**
     * Removes non-alphanumeric characters from a string (except spaces)
     * @param str String to remove from
     * @return Sanitized output
     */
    public static String removeNonAlphanumeric(String str)
    {
        // replace the given string
        // with empty string
        // except the pattern "[^a-zA-Z0-9]"
        str = str.replaceAll(
                "[^a-zA-Z0-9\\s]", "");

        // return string
        return str;
    }

    /**
     * Converts a string to a list.  New lines are indicated with a | character
     * @param listString String to convert
     * @return String list (colored)
     */
    public static List<String> stringToList(String listString) {
        String[] new_lore = listString.split("\\|");
        List<String> newLore = new ArrayList<>();
        for (String s : new_lore)
        {
            newLore.add(color(s));
        }
        return newLore;
    }

    /**
     * Converts a string to a list, breaking on delimiter provided
     * @param listString String to convert
     * @param delimiter  Delimiter to break on
     * @return String list
     */
    public static List<String> stringToList(String listString, String delimiter) {
        String[] listArray = listString.split(delimiter);
        List<String> returnList = new ArrayList<>();
        for (String s : listArray)
        {
            returnList.add(s);
        }
        return returnList;
    }

    /**
     * Converts a string list Lore to a | delimited string
     * @param lore Lore to convert
     * @return Lore string (| delimited)
     */
    public static String loreToString(List<String> lore) {
        StringBuilder returnValue = new StringBuilder();
        if (lore != null) {
            if (lore.size() > 0) {
                for (String loreLine : lore) {
                    returnValue.append(loreLine).append("|");
                }
                returnValue = new StringBuilder((returnValue + "!").replace("|!", ""));
            }
        }
        return returnValue.toString();
    }


    /**
     * Converts & color codes to color
     * @param input String to colorize
     * @return Colorized string
     */
    public static String color(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    /**
     * Gets a color by name
     * @param name Color to lookup
     * @return Color requested, WHITE if not found
     */
    public static Color colorFromName(String name) {
        Color ret;
        switch (name)
        {
            case "AQUA" :
                ret = Color.AQUA;
                break;
            case "BLACK" :
                ret = Color.BLACK;
                break;
            case "BLUE" :
                ret = Color.BLUE;
                break;
            case "FUCHSIA" :
                ret = Color.FUCHSIA;
                break;
            case "GRAY" :
                ret = Color.GRAY;
                break;
            case "GREEN" :
                ret = Color.GREEN;
                break;
            case "LIME" :
                ret = Color.LIME;
                break;
            case "MAROON" :
                ret = Color.MAROON;
                break;
            case "NAVY" :
                ret = Color.NAVY;
                break;
            case "OLIVE" :
                ret = Color.OLIVE;
                break;
            case "ORANGE" :
                ret = Color.ORANGE;
                break;
            case "PURPLE" :
                ret = Color.PURPLE;
                break;
            case "SILVER" :
                ret = Color.SILVER;
                break;
            case "TEAL" :
                ret = Color.TEAL;
                break;
            case "YELLOW" :
                ret = Color.YELLOW;
                break;
            case "WHITE" :
            default:
                ret = Color.WHITE;
                break;
        }
        return ret;
    }

    /**
     * Gets a Particle b name
     * @param name Name of particle to lookup
     * @return Particle requested, VILLAGER_ANGRY if not found
     */
    public static Particle particleFromName(String name) {
        Particle ret = Particle.VILLAGER_ANGRY;
        if (name != "") {
            Particle p = Particle.valueOf(name.toUpperCase());
            if (p != null) {
                ret = p;
            }
        }
        return ret;
    }

    /**
     * Gets an effect by name
     * @param name Name of effect to lookup
     * @return Effect requested, SMOKE if not found
     */
    public static Effect effectFromName(String name) {
        Effect ret = Effect.SMOKE;
        Effect e = Effect.valueOf(name.toUpperCase());
        if (e != null)
        {
            ret = e;
        }
        return ret;
    }

    /**
     * Gets a potion effect by name
     * @param name Potion effect to lookup
     * @return Potion Effect requested, LUCK if not found
     */
    public static PotionEffectType potionEffectTypeFromName(String name) {
        PotionEffectType ret = PotionEffectType.LUCK;
        PotionEffectType pet = PotionEffectType.getByName(name.toUpperCase());
        if (pet != null)
        {
            ret = pet;
        }
        return ret;
    }

    /**
     * Gets the arguments into a Map from a string array
     * @param args String array to convert
     * @return Map of Integer and String.  Integer is position, String is argument
     */
    public static Map<Integer, String> getArguments(String[] args) {
        Map<Integer, String> arguments = new HashMap<>();
        int argumentCount = 0;
        if (args.length > 0) // Check to make sure we have some arguments
        {
            for (Object o : args) {
                arguments.put(argumentCount, o.toString());
                argumentCount++;
            }
        }
        return arguments;
    }

    /**
     * Returns a text component
     * @param text Text to display
     * @param action Action to perform
     * @param hoverText Hover text
     * @param commandAction Command action
     * @param command Command
     * @return TextComponent
     */
    public static TextComponent getTextComponent(String text, HoverEvent.Action action, String hoverText, ClickEvent.Action commandAction, String command) {
        TextComponent msg = new TextComponent(text);
        HoverEvent hoverEvent;

        if (action != null) {
            switch (action) {
                case SHOW_TEXT:
                    // Create HoverEvent code - code is deprecated as of 1.16
                    hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverText).create());
                    if (SpigotVersion.isVersionSafe(1.16)) {
                        // This HoverEvent code is new to 1.16
                        hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(hoverText));
                    }
                    msg.setHoverEvent(hoverEvent);
                    if (commandAction != null) {
                        if (commandAction == ClickEvent.Action.RUN_COMMAND) {
                            ClickEvent clickEvent;
                            clickEvent = new ClickEvent(commandAction, command);
                            msg.setClickEvent(clickEvent);
                        }
                    }
                    break;
                default:
                    break;
            }
            return msg;
        }
        return msg;
    }

    /**
     * Converts a string into a NamespacedKey
     * @param input String to convert
     * @return NamespacedKey with original input as its key
     */
    public static NamespacedKey stringToKey(String input) {
        return new NamespacedKey(Gab.instance, input); // Create a new named key for the item
    }
}
