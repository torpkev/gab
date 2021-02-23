package com.ibexmc.gab.util.functions;

import com.ibexmc.gab.Gab;
import com.ibexmc.gab.util.Severity;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemFunctions {

    /**
     * Gets the Material from its name.  Returns AIR if not valid.
     * @param materialName Material name to lookup
     * @return Material from name, Material.AIR if not valid.
     */
    public static Material materialFromName(String materialName) {
        Material mat;
        if (materialName != null) {
            try {
                mat = Material.getMaterial(materialName);
            } catch (Exception ex) {
                Gab.instance.log.error(
                        "001",
                        "ItemFunctions",
                        "materialFromName",
                        "Item does not match a material",
                        "Name: " + materialName,
                        Severity.WARN,
                        ex.getStackTrace()
                );
                mat = Material.AIR;
            }
        } else{
            mat = Material.AIR;
        }
        return mat;
    }

    /**
     * Converts an ItemStack into a String blob field, which maintains all the settings for that
     * item completely.
     * @param itemStack ItemStack to convert
     * @return String Blob of the ItemStack
     */
    public static String itemToStringBlob(ItemStack itemStack) {
        YamlConfiguration config = new YamlConfiguration();
        config.set("i", itemStack);
        return config.saveToString();
    }

    /**
     * Converts a String blob field into an ItemStack
     * @param stringBlob
     * @return
     */
    public static ItemStack stringBlobToItem(String stringBlob) {
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.loadFromString(stringBlob);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return config.getItemStack("i", null);
    }

    /**
     * Generates a simple ItemStack based on material, name, lore and amount
     * Does not apply potion effects, damage etc.
     * @param material Material to make the ItemStack
     * @param name Display name of the ItemStack
     * @param lore Lore to set to the ItemStack
     * @param amount How many of the ItemStack should be returned
     * @return ItemStack
     */
    public static ItemStack generateItemStack(Material material, String name, List<String> lore, int amount) {
        if (material != null) {
            ItemStack itemStack = new ItemStack(material, amount);
            boolean updateMeta = false;
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta != null) {
                if (name != null) {
                    if (name.length() > 0) {
                        itemMeta.setDisplayName(StringFunctions.color(name));
                        updateMeta = true;
                    }
                }
                if (lore != null) {
                    if (lore.size() > 0) {
                        itemMeta.setLore(lore);
                        updateMeta = true;
                    }
                }
                if (updateMeta) {
                    itemStack.setItemMeta(itemMeta);
                }
            }
            return itemStack;
        } else {
            return null;
        }
    }
}
