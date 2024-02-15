package xyz.tomsoz.lifestealcore.Misc;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import xyz.tomsoz.lifestealcore.LifeStealCore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

public class CustomRecepies {
    char[] itemIds = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I'};
    LifeStealCore plugin;

    ShapedRecipe addHeart;

    ShapedRecipe remHeart;

    ShapedRecipe enchantedGapp;

    ShapedRecipe maxHeart;

    ShapedRecipe heartFragments;

    ShapedRecipe reviveBook;

    NamespacedKey addHeartKey;

    NamespacedKey reviveBookKey;

    NamespacedKey enchantedGappKey;

    NamespacedKey maxHeartKey;

    NamespacedKey heartFragmentsKey;

    public CustomRecepies(LifeStealCore plugin) {
        this.addHeart = null;
        this.remHeart = null;
        this.enchantedGapp = null;
        this.maxHeart = null;
        this.heartFragments = null;
        this.reviveBook = null;
        this.addHeartKey = null;
        this.enchantedGappKey = null;
        this.maxHeartKey = null;
        this.heartFragmentsKey = null;
        this.reviveBookKey = null;
        this.plugin = plugin;
    }

    public void initialize() {
        this.addHeart = createHeartRecepie();
        this.enchantedGapp = enchantedGappRecepie();
        this.maxHeart = maxHeartRecepie();
        this.heartFragments = heartFragmentsRecepie();
        this.reviveBook = reviveBookRecepie();
        if (this.plugin.getConfigManager().getConfig().getBoolean("recipes.addHeart.enabled")) {
            if (Bukkit.getServer().getRecipe(this.addHeartKey) != null)
                Bukkit.getServer().removeRecipe(this.addHeartKey);
            Bukkit.getServer().addRecipe((Recipe)getCreateHeart());
        }
        if (this.plugin.getConfigManager().getConfig().getBoolean("recipes.add5MaxHearts.enabled")) {
            if (Bukkit.getServer().getRecipe(this.maxHeartKey) != null)
                Bukkit.getServer().removeRecipe(this.maxHeartKey);
            Bukkit.getServer().addRecipe((Recipe)getMaxHeart());
        }
        if (this.plugin.getConfigManager().getConfig().getBoolean("recipes.enchantedGoldenApple.enabled")) {
            if (Bukkit.getServer().getRecipe(this.enchantedGappKey) != null)
                Bukkit.getServer().removeRecipe(this.enchantedGappKey);
            Bukkit.getServer().addRecipe((Recipe)getEnchantedGapp());
        }
        if (this.plugin.getConfigManager().getConfig().getBoolean("recipes.heartFragments.enabled")) {
            if (Bukkit.getServer().getRecipe(this.heartFragmentsKey) != null)
                Bukkit.getServer().removeRecipe(this.heartFragmentsKey);
            Bukkit.getServer().addRecipe((Recipe)getHeartFragments());
        }
        if (this.plugin.getConfigManager().getConfig().getBoolean("recipes.reviveBook.enabled")) {
            if (Bukkit.getServer().getRecipe(this.reviveBookKey) != null)
                Bukkit.getServer().removeRecipe(this.reviveBookKey);
            Bukkit.getServer().addRecipe((Recipe)getReviveBook());
        }
    }

    public ShapedRecipe getCreateHeart() {
        return this.addHeart;
    }

    public ShapedRecipe getRemHeart() {
        return this.remHeart;
    }

    public ShapedRecipe getEnchantedGapp() {
        return this.enchantedGapp;
    }

    public ShapedRecipe getMaxHeart() {
        return this.maxHeart;
    }

    public ShapedRecipe getHeartFragments() {
        return this.heartFragments;
    }

    public ShapedRecipe getReviveBook() {
        return this.reviveBook;
    }

    private ShapedRecipe heartFragmentsRecepie() {
        ItemStack heart = new ItemStack(Material.RED_DYE);
        ItemMeta meta = heart.getItemMeta();
        meta.setDisplayName(Utils.chatRaw("&c+1 Heart"));
        List<String> lore = new ArrayList<>();
        lore.add(Utils.chatRaw("&7Gives you an extra heart,"));
        lore.add(Utils.chatRaw("&7provided you aren't already"));
        lore.add(Utils.chatRaw("&7maxed out."));
        meta.setLore(lore);
        heart.setItemMeta(meta);
        this.heartFragmentsKey = new NamespacedKey((Plugin)this.plugin, "fragmented_heart");
        ShapedRecipe recepie = new ShapedRecipe(this.heartFragmentsKey, heart);
        HashMap<Character, RecipeChoice> mats = new HashMap<>();

        List<String> rec = new ArrayList<>();
        for (String s : plugin.getConfigManager().getConfig().getStringList("recipes.heartFragments.recipe")) {
            StringBuilder line = new StringBuilder();
            for (String s1 : s.split(";")) {
                RecipeChoice mat = Utils.getMaterial(s1, plugin);
                if (mats.containsValue(mat)) {
                    for (char c : mats.keySet()) {
                        if (mats.get(c).equals(mat)) {
                            line.append(c);
                        }
                    }
                    continue;
                }
                char id = itemIds[mats.size()];
                mats.put(id, mat);
                line.append(id);
            }
            rec.add(line.toString());
        }

        recepie.shape(rec.get(0), rec.get(1), rec.get(2));
        for (char c : mats.keySet()) {
            recepie.setIngredient(c, mats.get(c));
        }
        return recepie;
    }

    private ShapedRecipe createHeartRecepie() {
        ItemStack heart = new ItemStack(Material.RED_DYE);
        ItemMeta meta = heart.getItemMeta();
        meta.setDisplayName(Utils.chatRaw("&c+1 Heart"));
        List<String> lore = new ArrayList<>();
        lore.add(Utils.chatRaw("&7Gives you an extra heart,"));
        lore.add(Utils.chatRaw("&7provided you aren't already"));
        lore.add(Utils.chatRaw("&7maxed out."));
        meta.setLore(lore);
        heart.setItemMeta(meta);
        this.addHeartKey = new NamespacedKey(this.plugin, "extra_heart");

        ShapedRecipe recepie = new ShapedRecipe(this.addHeartKey, heart);

        HashMap<Character, RecipeChoice> mats = new HashMap<>();

        List<String> rec = new ArrayList<>();
        for (String s : plugin.getConfigManager().getConfig().getStringList("recipes.addHeart.recipe")) {
            StringBuilder line = new StringBuilder();
            for (String s1 : s.split(";")) {
                RecipeChoice mat = Utils.getMaterial(s1, plugin);
                if (mats.containsValue(mat)) {
                    for (char c : mats.keySet()) {
                        if (mats.get(c).equals(mat)) {
                            line.append(c);
                        }
                    }
                    continue;
                }
                char id = itemIds[mats.size()];
                mats.put(id, mat);
                line.append(id);
            }
            rec.add(line.toString());
        }
        recepie.shape(rec.get(0), rec.get(1), rec.get(2));
        for (char c : mats.keySet()) {
            recepie.setIngredient(c, mats.get(c));
        }
        return recepie;
    }

    private ShapedRecipe enchantedGappRecepie() {
        ItemStack gapple = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
        this.enchantedGappKey = new NamespacedKey((Plugin)this.plugin, "enchanted_golden_apple");
        ShapedRecipe recepie = new ShapedRecipe(this.enchantedGappKey, gapple);

        HashMap<Character, RecipeChoice> mats = new HashMap<>();

        List<String> rec = new ArrayList<>();
        for (String s : plugin.getConfigManager().getConfig().getStringList("recipes.enchantedGoldenApple.recipe")) {
            StringBuilder line = new StringBuilder();
            for (String s1 : s.split(";")) {
                RecipeChoice mat = Utils.getMaterial(s1, plugin);
                if (mats.containsValue(mat)) {
                    for (char c : mats.keySet()) {
                        if (mats.get(c).equals(mat)) {
                            line.append(c);
                        }
                    }
                    continue;
                }
                char id = itemIds[mats.size()];
                mats.put(id, mat);
                line.append(id);
            }
            rec.add(line.toString());
        }

        recepie.shape(rec.get(0), rec.get(1), rec.get(2));
        for (char c : mats.keySet()) {
            recepie.setIngredient(c, mats.get(c));
        }

        return recepie;
    }

    private ShapedRecipe maxHeartRecepie() {
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
        this.maxHeartKey = new NamespacedKey((Plugin)this.plugin, "change_max_hearts");
        ShapedRecipe recepie = new ShapedRecipe(this.maxHeartKey, heart);
        HashMap<Character, RecipeChoice> mats = new HashMap<>();

        List<String> rec = new ArrayList<>();
        for (String s : plugin.getConfigManager().getConfig().getStringList("recipes.add5MaxHearts.recipe")) {
            StringBuilder line = new StringBuilder();
            for (String s1 : s.split(";")) {
                RecipeChoice mat = Utils.getMaterial(s1, plugin);
                if (mats.containsValue(mat)) {
                    for (char c : mats.keySet()) {
                        if (mats.get(c).equals(mat)) {
                            line.append(c);
                        }
                    }
                    continue;
                }
                char id = itemIds[mats.size()];
                mats.put(id, mat);
                line.append(id);
            }
            rec.add(line.toString());
        }

        recepie.shape(rec.get(0), rec.get(1), rec.get(2));
        for (char c : mats.keySet()) {
            recepie.setIngredient(c, mats.get(c));
        }
        return recepie;
    }

    private ShapedRecipe reviveBookRecepie() {
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = book.getItemMeta();
        meta.setDisplayName(Utils.chatRaw("&6Revive Book"));
        List<String> lore = new ArrayList<>();
        lore.add(Utils.chatRaw("&7This item can bring"));
        lore.add(Utils.chatRaw("&7a player back from the"));
        lore.add(Utils.chatRaw("&7dead."));
        meta.setLore(lore);
        book.setItemMeta(meta);
        this.reviveBookKey = new NamespacedKey(this.plugin, "revive_book");
        ShapedRecipe recepie = new ShapedRecipe(this.reviveBookKey, book);

        HashMap<Character, RecipeChoice> mats = new HashMap<>();
        List<String> rec = new ArrayList<>();
        for (String s : plugin.getConfigManager().getConfig().getStringList("recipes.reviveBook.recipe")) {
            StringBuilder line = new StringBuilder();
            for (String s1 : s.split(";")) {
                RecipeChoice mat = Utils.getMaterial(s1, plugin);
                if (mats.containsValue(mat)) {
                    for (char c : mats.keySet()) {
                        if (mats.get(c).equals(mat)) {
                            line.append(c);
                        }
                    }
                    continue;
                }
                char id = itemIds[mats.size()];
                mats.put(id, mat);
                line.append(id);
            }
            rec.add(line.toString());
        }
        recepie.shape(rec.get(0), rec.get(1), rec.get(2));
        for (char c : mats.keySet()) {
            recepie.setIngredient(c, mats.get(c));
        }

        return recepie;
    }
}
