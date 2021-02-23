package com.ibexmc.gab.hooks.vault;

import com.ibexmc.gab.Gab;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;

public class VaultHook {
    Gab plugin;
    net.milkbowl.vault.permission.Permission permission;
    net.milkbowl.vault.chat.Chat chat;
    public VaultHook(Gab plugin, Permission permission, Chat chat) {
        this.plugin = plugin;
        this.permission = permission;
        this.chat = chat;
    }
    public String getPrefix(Player player) {
        if (plugin.data.isVaultChatHooked()) {
            return this.chat.getPlayerPrefix(player);
        } else {
            return "";
        }
    }
}
