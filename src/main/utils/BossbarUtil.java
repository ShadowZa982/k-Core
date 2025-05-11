package org.kalistudio.kCore.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.kalistudio.kCore.KCore;

public class BossbarUtil {
    public static void showBossbar(Player player, String text, BarColor color, BarStyle style, int durationTicks) {
        BossBar bar = Bukkit.createBossBar(ChatColor.translateAlternateColorCodes('&', text), color, style);
        bar.addPlayer(player);
        bar.setVisible(true);

        new BukkitRunnable() {
            public void run() {
                bar.removeAll();
            }
        }.runTaskLater(KCore.getInstance(), durationTicks);
    }
}