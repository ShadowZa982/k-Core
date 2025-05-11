package org.kalistudio.kCore.utils;

import org.bukkit.entity.Player;
import net.md_5.bungee.api.ChatColor;

public class MessageUtil {
    public static void sendColoredMessage(Player player, String message) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
