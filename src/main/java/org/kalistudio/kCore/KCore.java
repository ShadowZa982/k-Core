package org.kalistudio.kCore;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.kalistudio.kCore.placeholder.CorePlaceholder;
import org.kalistudio.kCore.placeholder.KCorePlaceholder;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KCore extends JavaPlugin {
    private static KCore instance;
    private Economy economy;

    private FileConfiguration questConfig;
    private File questConfigFile;
    private FileConfiguration placeholderConfig;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {

        createQuestConfig();
        createPlaceholderConfig();
        setupEconomy();
        String prefix = "§a===== §6Kali Studio §a=====";
        Bukkit.getConsoleSender().sendMessage(prefix);
        Bukkit.getConsoleSender().sendMessage("§bĐang kiểm tra Plugin...");

        saveDefaultConfig(); 

        getLogger().info("✅ Đang kiểm tra các plugin yêu cầu...");

       
        Bukkit.getScheduler().runTaskLater(this, () -> {
            List<String> requiredPlugins = Arrays.asList("k-TPA", "Vault");
            List<String> connectedPlugins = new ArrayList<>();

            for (String pluginName : requiredPlugins) {
                Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
                if (plugin == null || !plugin.isEnabled()) {
                    getLogger().warning("❌   Plugin thiếu hoặc chưa bật: " + pluginName);
                } else {
                    getLogger().info("✔️   Plugin đã kết nối: " + pluginName + " v" + plugin.getDescription().getVersion());
                    connectedPlugins.add(pluginName);
                }
            }

            if (!connectedPlugins.isEmpty()) {
                getLogger().info("🔗   Các plugin đã kết nối: " + String.join(", ", connectedPlugins));
            }

            if (connectedPlugins.size() == requiredPlugins.size()) {
                getLogger().info("✅   Tất cả plugin yêu cầu đã được đồng bộ.");
            } else {
                getLogger().warning("⚠️   Một hoặc nhiều plugin yêu cầu bị thiếu. Một số chức năng có thể không hoạt động.");
            }
        }, 20L);
        
        Bukkit.getConsoleSender().sendMessage("§a✅   Tất cả plugin yêu cầu đều đã bật. Tiếp tục khởi động...");

        Bukkit.getConsoleSender().sendMessage("§bPhiên bản: §f1.0");
        Bukkit.getConsoleSender().sendMessage("§dAuthor by §lkaLi Studio");
        Bukkit.getConsoleSender().sendMessage("§bDiscord§f: https://discord.gg/kQsg6JyT");
        Bukkit.getConsoleSender().sendMessage("§ak-Core đã khởi động thành công!");
        Bukkit.getConsoleSender().sendMessage(prefix);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new CorePlaceholder().register();
            getLogger().info("Đã đăng ký PlaceholderAPI cho MyCore.");
        }

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Economy economy = getServer().getServicesManager().getRegistration(Economy.class).getProvider();
            new KCorePlaceholder(this, economy).register();
        }
    }


    @Override
    public void onDisable() {
        getLogger().info("§ck-CORE đã tắt.");
    }

    public static KCore getInstance() {
        return instance;
    }

    private void setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) return;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp != null) {
            economy = rsp.getProvider();
        }
    }

    public String getQuestProgress(Player player) {
        int completedTasks = 5; 
        int totalTasks = 10;     
        return completedTasks + "/" + totalTasks;
    }

    public String getQuestStatus(Player player) {
        return "Đang làm";
    }

    public String getQuestReward(Player player) {
        return "100 Coins";  
    }

    private void createQuestConfig() {
        File dataFolder = new File(getDataFolder(), "data");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        questConfigFile = new File(dataFolder, "quests.yml");
        if (!questConfigFile.exists()) {
            saveResource("data/quests.yml", false); 
        }

        questConfig = YamlConfiguration.loadConfiguration(questConfigFile);
    }

    public FileConfiguration getQuestConfig() {
        return questConfig;
    }

    private void createPlaceholderConfig() {
        File dataFolder = new File(getDataFolder(), "data");
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        File placeholderFile = new File(dataFolder, "placeholder_core.yml");
        if (!placeholderFile.exists()) {
            saveResource("data/placeholder_core.yml", false); 
        }

        placeholderConfig = YamlConfiguration.loadConfiguration(placeholderFile);
    }


}
