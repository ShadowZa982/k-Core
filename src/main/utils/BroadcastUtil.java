package org.kalistudio.kCore.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class BroadcastUtil {

    /**
     * Gửi thông báo tới toàn bộ server với mã màu &.
     * @param message Nội dung tin nhắn.
     */
    public static void broadcast(String message) {
        String coloredMessage = ChatColor.translateAlternateColorCodes('&', message);
        Bukkit.broadcastMessage(coloredMessage);
    }

    /**
     * Gửi thông báo với prefix sẵn có.
     * @param message Nội dung tin nhắn.
     */
    public static void broadcastWithPrefix(String message) {
        String prefix = "&6[Server] &f"; // có thể lấy từ config nếu cần
        broadcast(prefix + message);
    }
}