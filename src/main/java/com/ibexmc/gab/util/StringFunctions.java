package com.ibexmc.gab.util;

import com.ibexmc.gab.Gab;
import com.ibexmc.gab.data.Channel;
import com.ibexmc.gab.util.log.Error;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;

public class StringFunctions {
    /**
     * stringToLore(String)
     * Converts a string to a list which can be used for Lore - New lines are indicated with a | character
     * @param loreString String to convert
     * @return List which can be used for a lore
     */
    public static List<String> stringToLore(String loreString) {
        String[] new_lore = loreString.split("\\|");
        List<String> newLore = new ArrayList<>();
        for (String s : new_lore)
        {
            newLore.add(s);
        }
        return newLore;
    }

    /**
     * stringToMaterial(String)
     * Converts a string value to the appropriate Minecraft Material.  If invalid, returns AIR
     * @param materialName String value of the material.  Must match Material exactly
     * @return Material based on name provided, returns AIR if invalid
     */
    public static Material stringToMaterial(String materialName)
    {
        Material mat;

        if (materialName == null)
        {
            materialName = "<null>";
        }
        try
        {
            mat = Material.getMaterial(materialName.toUpperCase());
        }
        catch (Exception ex)
        {
            mat = Material.AIR;
        }
        return mat;
    }

    /**
     * uuidFromString(String)
     * Converts a string value to a UUID.  Returns null if invalid
     * @param uuid String value of the UUID
     * @return UUID
     */
    public static UUID uuidFromString(String uuid) {
        UUID returnUUID;
        try {
            returnUUID = UUID.fromString(uuid);
            return returnUUID;
        } catch (Exception ex) {
            Gab.getInstance().error().save(
                    "01",
                    "StringFunctions",
                    "uuidFromString(String)",
                    "Unexpected Error",
                    "Unable to convert string to UUID.\nString value: " + uuid,
                    Error.Severity.URGENT,
                    ex.getStackTrace()
            );
            return null;
        }
    }
    /**
     * colorCode(String)
     * Applies ChatColor alternate color code of &
     * @param input String to use translate on
     * @return String
     */
    public static String colorCode(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static boolean isNullOrEmpty(String input) {
        if (input == null) {
            return true;
        }
        if (input == "") {
            return true;
        }
        return false;
    }

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

    public static String[] stringToArray(String string) {
        if (string != null) {
            if (string.length() > 0) {
                String[] array = string.split(" ");
                if (array != null) {
                    return array;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static TextComponent getTextComponent(String text, HoverEvent.Action action, String hoverText, ClickEvent.Action commandAction,  String command) {
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
    public static boolean bannedWord(Set<String> bannedWords, String word) {
        if (bannedWords == null) {
            return false;
        }
        for (String w :bannedWords) {
            if (word.toLowerCase().contains(w.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
    public static String paddedText(String character, int amount) {
        if (character.length() > 1) {
            character = character.substring(0, 1);
        }
        String text = "";
        for (int i = 0; i < amount; ++i) {
            text = text + character;
        }
        return text;
    }
    public static TextComponent prepMessage(Player player, Channel channel, String message) {
        TextComponent msg = new TextComponent("");
        message = channel.getDefaultColor() + message;
        String[] messageArray = StringFunctions.stringToArray(message);
        if (messageArray != null) {
            for (int i = 0; i < messageArray.length; ++i) {

                //Gab.getInstance().log().quick("messageArray[" + i + "] = " + messageArray[i]);

                TextComponent textComponent;
                switch (messageArray[i]) {
                    case "!loc":
                        textComponent = getTextComponent("myloc", HoverEvent.Action.SHOW_TEXT, "Teleport", null, "");

                        break;
                    case "!hand":
                        textComponent = ChatReplacements.mainHand(player);
                        break;
                    default:
                        textComponent = new TextComponent(StringFunctions.colorCode(messageArray[i]));
                        // Global banned word
                        if (bannedWord(Gab.getInstance().data().getGabConfig().getBannedWords(), messageArray[i])) {
                            textComponent = getTextComponent(
                                    paddedText(
                                            Gab.getInstance().data().getGabConfig().getCensorCharacter(),
                                            messageArray[i].length()
                                    ),
                                    HoverEvent.Action.SHOW_TEXT,
                                    StringFunctions.colorCode("This word is not allowed"),
                                    null,
                                    ""
                            );
                        }
                        // Channel banned word
                        if (bannedWord(channel.getBannedWords(), messageArray[i])) {
                            textComponent = getTextComponent(
                                    paddedText(
                                            Gab.getInstance().data().getGabConfig().getCensorCharacter(),
                                            messageArray[i].length()
                                    ),
                                    HoverEvent.Action.SHOW_TEXT,
                                    StringFunctions.colorCode("This word is not allowed"),
                                    null,
                                    ""
                            );
                        }
                        break;
                }

                //Gab.getInstance().log().quick(textComponent.toString());
                textComponent.addExtra(" ");
                msg.addExtra(textComponent);

            }
        }
        return msg;
    }
}
