package org.kalistudio.kCore.api;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class KCoreAPI {
    // Danh sách các plugin cần kiểm tra
    private static final List<String> REQUIRED_PLUGINS = Arrays.asList("k-TPA", "Vault");

    /**
     * Kiểm tra xem tất cả các plugin yêu cầu có đang bật không.
     * @return true nếu tất cả plugin yêu cầu đã bật, ngược lại false.
     */
    public static boolean areRequiredPluginsEnabled() {
        for (String pluginName : REQUIRED_PLUGINS) {
            Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
            if (plugin == null || !plugin.isEnabled()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Kiểm tra một plugin cụ thể có đang bật không.
     * @param pluginName Tên plugin cần kiểm tra.
     * @return true nếu plugin tồn tại và đã bật, ngược lại false.
     */
    public static boolean isPluginEnabled(String pluginName) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
        return plugin != null && plugin.isEnabled();
    }

    /**
     * Kiểm tra plugin k-TPA có trong danh sách plugin yêu cầu không.
     * @return true nếu plugin k-TPA có trong danh sách và đã bật.
     */
    public static boolean isKTPAEnabled() {
        return isPluginEnabled("k-TPA");
    }

    /**
     * Lấy danh sách tất cả các plugin yêu cầu.
     * @return Danh sách tên plugin yêu cầu.
     */
    public static List<String> getRequiredPlugins() {
        return REQUIRED_PLUGINS;
    }
}
