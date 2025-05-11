package org.kalistudio.kCore.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.LuckPermsProvider;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.kalistudio.kCore.KCore;

public class KCorePlaceholder extends PlaceholderExpansion {

    private final KCore plugin;
    private final LuckPerms luckPerms;
    private final Economy economy;

    public KCorePlaceholder(KCore plugin, Economy economy) {
        this.plugin = plugin;
        this.economy = economy;
        this.luckPerms = LuckPermsProvider.get();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "kcore";
    }

    @Override
    public @NotNull String getAuthor() {
        return "YourName";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onRequest(OfflinePlayer offlinePlayer, @NotNull String params) {
        Player player = offlinePlayer.getPlayer();
        if (player == null || !player.isOnline()) return "";

        switch (params.toLowerCase()) {
            case "rank":
                User user = luckPerms.getUserManager().getUser(player.getUniqueId());
                return user != null ? user.getPrimaryGroup() : "unknown";

            case "ping":
                return String.valueOf(player.getPing());

            case "money":
                return economy != null ? String.format("%.2f", economy.getBalance(player)) : "0";

            case "exp":
                return String.valueOf(player.getExp());

            case "levels":
                return String.valueOf(player.getLevel());

            case "kill":
            case "kills":
                return String.valueOf(player.getStatistic(org.bukkit.Statistic.PLAYER_KILLS));

            case "death":
            case "deaths":
                return String.valueOf(player.getStatistic(org.bukkit.Statistic.DEATHS));

            case "health":
                return String.format("%.1f", player.getHealth());

            case "food":
                return String.valueOf(player.getFoodLevel());

            case "gamemode":
                return player.getGameMode().name().toLowerCase();

            case "playtime":
                long playTime = player.getStatistic(org.bukkit.Statistic.PLAY_ONE_MINUTE) / 20;
                long hours = playTime / 3600;
                long minutes = (playTime % 3600) / 60;
                return String.format("%d:%02d", hours, minutes);

            case "world":
                return player.getWorld().getName();

            case "location_x":
                return String.format("%.2f", player.getLocation().getX());

            case "location_y":
                return String.format("%.2f", player.getLocation().getY());

            case "location_z":
                return String.format("%.2f", player.getLocation().getZ());

            case "direction":
                return player.getLocation().getDirection().toString();

            case "pvp_score":
                int kills = player.getStatistic(org.bukkit.Statistic.PLAYER_KILLS);
                int deaths = player.getStatistic(org.bukkit.Statistic.DEATHS);
                return String.valueOf(kills - deaths); // Điểm PvP = kills - deaths

            // Kết nối với hệ thống nhiệm vụ
            case "quest_progress":
                return getQuestProgress(player);  // Giả sử bạn có một phương thức lấy tiến độ nhiệm vụ

            case "quest_status":
                return getQuestStatus(player);  // Trạng thái nhiệm vụ: "hoàn thành", "đang làm", v.v.

            case "quest_reward":
                return getQuestReward(player);  // Phần thưởng nhiệm vụ (có thể là tiền, vật phẩm, điểm, v.v.)

            default:
                return null;
        }
    }

    public String getQuestProgress(Player player) {
        // Giả sử bạn có hệ thống nhiệm vụ
        int completedTasks = 5;  // Số nhiệm vụ đã hoàn thành
        int totalTasks = 10;     // Tổng số nhiệm vụ
        return completedTasks + "/" + totalTasks;
    }

    public String getQuestStatus(Player player) {
        // Trạng thái nhiệm vụ, có thể lấy từ hệ thống nhiệm vụ
        return "Đang làm"; // Hoặc "Hoàn thành"
    }

    // Phương thức lấy phần thưởng nhiệm vụ từ config.yml
    public String getQuestReward(Player player) {
        FileConfiguration questConfig = plugin.getQuestConfig(); // Lấy từ main class

        String reward = questConfig.getString("quests.reward");
        if (player.hasPermission("kcore.epicreward")) {
            reward = questConfig.getString("quests.epic_reward");
        }

        return reward != null ? reward : "No Reward";
    }

}