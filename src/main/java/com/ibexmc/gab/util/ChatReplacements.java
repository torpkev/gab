package com.ibexmc.gab.util;

import com.ibexmc.gab.Gab;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class ChatReplacements {
    public static TextComponent mainHand(Player player) {
        ItemStack hand = player.getInventory().getItemInMainHand();
        String itemName = "[No item in hand]";
        String handHover = "";
        if (hand != null) {
            if (!hand.getType().equals(Material.AIR)) {
                itemName = "" + hand.getAmount() + "x " + hand.getType().name();
                ItemMeta handMeta = hand.getItemMeta();
                if (handMeta != null) {
                    if (handMeta.getDisplayName() != null) {
                        if (handMeta.getDisplayName() != "") {
                            itemName = "" + hand.getAmount() + "x " +  handMeta.getDisplayName();
                            if (!handMeta.getDisplayName().equalsIgnoreCase(hand.getType().name())) {
                                itemName = itemName + StringFunctions.colorCode(" [&7" + hand.getType().name() + "&f]");
                            }
                        }
                    }

                }
                handHover = itemName;



                if (hand.getEnchantments() != null) {
                    if (hand.getEnchantments().size() > 0) {
                        String enchantLine = "";
                        for (Map.Entry<Enchantment, Integer> enchantment : hand.getEnchantments().entrySet()) {
                            enchantLine = enchantLine + StringFunctions.colorCode("&b" + enchantment.getKey().getKey().toString().replace("minecraft:", "")) + ", ";
                        }
                        if (enchantLine != "") {
                            enchantLine = enchantLine + "#";
                            enchantLine = enchantLine.replace(", #", "");
                            handHover = handHover + "\n" + enchantLine;
                        }
                    }
                }

                if(hand.getItemMeta() instanceof EnchantmentStorageMeta) {
                    EnchantmentStorageMeta meta = (EnchantmentStorageMeta) hand.getItemMeta();
                    if (meta.hasStoredEnchants()) {
                        String enchantLine = "";
                        for (Map.Entry<Enchantment, Integer> enchantment : meta.getStoredEnchants().entrySet()) {
                            enchantLine = enchantLine + StringFunctions.colorCode("&b" + enchantment.getKey().getKey().toString().replace("minecraft:", "")) + ", ";
                        }
                        if (enchantLine != "") {
                            enchantLine = enchantLine + "#";
                            enchantLine = enchantLine.replace(", #", "");
                            handHover = handHover + "\n" + enchantLine;
                        }
                    }
                }

                if (handMeta != null) {
                    if (handMeta instanceof Damageable) {
                        Damageable handDamage = (Damageable) handMeta;
                        if (hand.getType().getMaxDurability() > 0) {
                            int durability = hand.getType().getMaxDurability() - handDamage.getDamage();
                            HashMap<String, String> damagePlaceHolder = new HashMap<>();
                            damagePlaceHolder.put("<%durability%>", "" + durability);
                            damagePlaceHolder.put("<%maxdurability%>", "" + hand.getType().getMaxDurability());
                            handHover = handHover + "\n" + Gab.getInstance().locale().getLocaleTextByCode("na","&f[&7<%durability%>&f/&7<%maxdurability%>&f]", damagePlaceHolder);
                        }
                    }
                }

            }
        }

        TextComponent replacement;
        if (!handHover.equals("")) {
            replacement = StringFunctions.getTextComponent(itemName, HoverEvent.Action.SHOW_TEXT, handHover, null, "");
        } else {
            replacement = new TextComponent(itemName);
        }
        return replacement;

    }
}
