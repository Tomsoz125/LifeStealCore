package xyz.tomsoz.lifestealcore.Misc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import xyz.tomsoz.lifestealcore.LifeStealCore;
import xyz.tomsoz.pluginbase.Text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

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

    public static RecipeChoice getMaterial(String name, LifeStealCore plugin) {
        if (name.equalsIgnoreCase("HEART_FRAGMENT")) {
            ItemStack heart = new ItemStack(Material.FERMENTED_SPIDER_EYE);
            ItemMeta meta = heart.getItemMeta();
            meta.setDisplayName(Utils.chatRaw("&cHeart Fragment"));
            List<String> lore = new ArrayList<>();
            lore.add(Utils.chatRaw("&7Combine 9 in a crafting"));
            lore.add(Utils.chatRaw("&7table to get a heart."));
            meta.setLore(lore);
            heart.setItemMeta(meta);
            return new RecipeChoice.ExactChoice(heart);
        } else if (name.equalsIgnoreCase("ADD_HEART")) {
            ItemStack heart = new ItemStack(Material.RED_DYE);
            ItemMeta meta = heart.getItemMeta();
            meta.setDisplayName(Utils.chatRaw("&c+1 Heart"));
            List<String> lore = new ArrayList<>();
            lore.add(Utils.chatRaw("&7Gives you an extra heart,"));
            lore.add(Utils.chatRaw("&7provided you aren't already"));
            lore.add(Utils.chatRaw("&7maxed out."));
            meta.setLore(lore);
            heart.setItemMeta(meta);
            return new RecipeChoice.ExactChoice(heart);
        } else if (name.equalsIgnoreCase("PLUS_MAX_HEART")) {
            ItemStack heart = new ItemStack(Material.BLUE_GLAZED_TERRACOTTA);
            ItemMeta meta = heart.getItemMeta();
            meta.setDisplayName(Utils.chatRaw("&9+5 Max Hearts"));
            List<String> lore = new ArrayList<>();
            lore.add(Utils.chatRaw("&7Increases the total amount"));
            lore.add(Utils.chatRaw("&7of hearts you can have."));
            lore.add(Utils.chatRaw("&7Only " + plugin.getConfigManager().getConfig().getInt("plusMaxHeartLimit") + " can be used"));
            lore.add(Utils.chatRaw("&7per player."));
            meta.setLore(lore);
            heart.setItemMeta(meta);
            return new RecipeChoice.ExactChoice(heart);
        } else if (name.equalsIgnoreCase("REVIVE_BOOK")) {
            ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
            ItemMeta meta = book.getItemMeta();
            meta.setDisplayName(Utils.chatRaw("&6Revive Book"));
            List<String> lore = new ArrayList<>();
            lore.add(Utils.chatRaw("&7This item can bring"));
            lore.add(Utils.chatRaw("&7a player back from the"));
            lore.add(Utils.chatRaw("&7dead."));
            meta.setLore(lore);
            book.setItemMeta(meta);
            return new RecipeChoice.ExactChoice(book);
        } else {
            Material mat = Material.getMaterial(name.toUpperCase());
            if (mat == null) {
                Bukkit.getLogger().log(Level.SEVERE, "Invalid item id \"" + name + "\" in a recipe in the config. Disabling the plugin to prevent corruption!");
                Bukkit.getPluginManager().disablePlugin(plugin);
                return null;
            }
            return new RecipeChoice.MaterialChoice(mat);
        }
    }
}
