package xyz.tomsoz.lifestealcore.Misc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import xyz.tomsoz.lifestealcore.LifeStealCore;
import xyz.tomsoz.pluginbase.Text.Text;

import java.util.List;

public class Utils {
    public static String chat(LifeStealCore plugin, String textToTranslate) {
        return Text.format(textToTranslate);
    }

    public static String chatRaw(String textToTranslate) {
        return Text.colourize(textToTranslate);
    }

    public static void sendConsole(String message) {
        Bukkit.getConsoleSender().sendMessage(chatRaw(message));
    }

    public static boolean isValidWorld(FileConfiguration config, World world) {
        boolean isValid = true;
        List<String> validWorlds = config.getStringList("onlyWorkIn");
        for (String w : validWorlds) {
            if (!world.getName().equalsIgnoreCase(w)) {
                isValid = false;
            } else {
                isValid = true;
                break;
            }
        }
        return isValid;
    }
}
