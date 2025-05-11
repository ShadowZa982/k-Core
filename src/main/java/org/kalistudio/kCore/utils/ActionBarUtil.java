package org.kalistudio.kCore.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class ActionBarUtil {

    /**
     * Hiển thị action bar có màu cho người chơi.
     * @param player Người chơi nhận thông báo.
     * @param message Nội dung tin nhắn.
     */
    public static void sendActionBar(Player player, String message) {
        String colored = ChatColor.translateAlternateColorCodes('&', message);
        player.spigot().sendMessage(net.md_5.bungee.api.ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(colored));
    }

    public static void broadcastActionBar(String message) {
        for (Player player : org.bukkit.Bukkit.getOnlinePlayers()) {
            sendActionBar(player, message);
        }
    }
}