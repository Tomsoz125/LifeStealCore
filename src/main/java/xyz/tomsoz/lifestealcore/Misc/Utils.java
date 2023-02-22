package xyz.tomsoz.lifestealcore.Misc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import xyz.tomsoz.lifestealcore.LifeStealCore;

public class Utils {
    public static String chat(LifeStealCore plugin, String textToTranslate) {
        return ChatColor.translateAlternateColorCodes('&', plugin.getConfigManager().getMessages().getString("prefix") + " " + textToTranslate);
    }

    public static String chatRaw(String textToTranslate) {
        return ChatColor.translateAlternateColorCodes('&', textToTranslate);
    }

    public static void sendConsole(String message) {
        Bukkit.getConsoleSender().sendMessage(chatRaw(message));
    }
}
