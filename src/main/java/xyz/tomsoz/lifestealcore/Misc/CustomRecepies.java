package xyz.tomsoz.lifestealcore.Misc;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import xyz.tomsoz.lifestealcore.LifeStealCore;

import java.util.ArrayList;
import java.util.List;

public class CustomRecepies {
    LifeStealCore plugin;

    ShapedRecipe addHeart;

    ShapedRecipe remHeart;

    ShapedRecipe enchantedGapp;

    ShapedRecipe maxHeart;

    ShapedRecipe heartFragments;

    NamespacedKey addHeartKey;

    NamespacedKey remHeartKey;

    NamespacedKey enchantedGappKey;

    NamespacedKey maxHeartKey;

    NamespacedKey heartFragmentsKey;

    public CustomRecepies(LifeStealCore plugin) {
        this.addHeart = null;
        this.remHeart = null;
        this.enchantedGapp = null;
        this.maxHeart = null;
        this.heartFragments = null;
        this.addHeartKey = null;
        this.remHeartKey = null;
        this.enchantedGappKey = null;
        this.maxHeartKey = null;
        this.heartFragmentsKey = null;
        this.plugin = plugin;
    }

    public void initialize() {
        this.addHeart = createHeartRecepie();
        this.remHeart = remHeartRecepie();
        this.enchantedGapp = enchantedGappRecepie();
        this.maxHeart = maxHeartRecepie();
        this.heartFragments = heartFragmentsRecepie();
        if (this.plugin.getConfigManager().getConfig().getBoolean("recipes.addHeart")) {
            if (Bukkit.getServer().getRecipe(this.addHeartKey) != null)
                Bukkit.getServer().removeRecipe(this.addHeartKey);
            Bukkit.getServer().addRecipe((Recipe)getCreateHeart());
        }
        if (this.plugin.getConfigManager().getConfig().getBoolean("recipes.removeHeart")) {
            if (Bukkit.getServer().getRecipe(this.remHeartKey) != null)
                Bukkit.getServer().removeRecipe(this.remHeartKey);
            Bukkit.getServer().addRecipe((Recipe)getRemHeart());
        }
        if (this.plugin.getConfigManager().getConfig().getBoolean("recipes.add5MaxHearts")) {
            if (Bukkit.getServer().getRecipe(this.maxHeartKey) != null)
                Bukkit.getServer().removeRecipe(this.maxHeartKey);
            Bukkit.getServer().addRecipe((Recipe)getMaxHeart());
        }
        if (this.plugin.getConfigManager().getConfig().getBoolean("recipes.enchantedGoldenApple")) {
            if (Bukkit.getServer().getRecipe(this.enchantedGappKey) != null)
                Bukkit.getServer().removeRecipe(this.enchantedGappKey);
            Bukkit.getServer().addRecipe((Recipe)getEnchantedGapp());
        }
        if (this.plugin.getConfigManager().getConfig().getBoolean("recipes.heartFragments")) {
            if (Bukkit.getServer().getRecipe(this.heartFragmentsKey) != null)
                Bukkit.getServer().removeRecipe(this.heartFragmentsKey);
            Bukkit.getServer().addRecipe((Recipe)getHeartFragments());
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
        recepie.shape(new String[] { "***", "***", "***" });
        recepie.setIngredient('*', Material.FERMENTED_SPIDER_EYE);
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
        this.addHeartKey = new NamespacedKey((Plugin)this.plugin, "extra_heart");
        ShapedRecipe recepie = new ShapedRecipe(this.addHeartKey, heart);
        recepie.shape(new String[] { "*%*", "%#%", "*%*" });
        recepie.setIngredient('*', Material.DIAMOND);
        recepie.setIngredient('%', Material.EMERALD_BLOCK);
        recepie.setIngredient('#', Material.TOTEM_OF_UNDYING);
        return recepie;
    }

    private ShapedRecipe remHeartRecepie() {
        ItemStack heart = new ItemStack(Material.BLACK_DYE);
        ItemMeta meta = heart.getItemMeta();
        meta.setDisplayName(Utils.chatRaw("&0-1 Heart"));
        List<String> lore = new ArrayList<>();
        lore.add(Utils.chatRaw("&7Takes one of your hearts,"));
        lore.add(Utils.chatRaw("&7provided you aren't at"));
        lore.add(Utils.chatRaw("&7your minimum."));
        meta.setLore(lore);
        heart.setItemMeta(meta);
        this.remHeartKey = new NamespacedKey((Plugin)this.plugin, "remove_heart");
        ShapedRecipe recepie = new ShapedRecipe(this.remHeartKey, heart);
        recepie.shape(new String[] { "*%*", "%#%", "*%*" });
        recepie.setIngredient('*', Material.QUARTZ);
        recepie.setIngredient('%', Material.ANCIENT_DEBRIS);
        recepie.setIngredient('#', Material.WITHER_SKELETON_SKULL);
        return recepie;
    }

    private ShapedRecipe enchantedGappRecepie() {
        ItemStack gapple = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
        this.enchantedGappKey = new NamespacedKey((Plugin)this.plugin, "enchanted_golden_apple");
        ShapedRecipe recepie = new ShapedRecipe(this.enchantedGappKey, gapple);
        recepie.shape(new String[] { "***", "*#*", "***" });
        recepie.setIngredient('*', Material.GOLD_BLOCK);
        recepie.setIngredient('#', Material.GOLDEN_APPLE);
        return recepie;
    }

    private ShapedRecipe maxHeartRecepie() {
        ItemStack heart = new ItemStack(Material.BLUE_GLAZED_TERRACOTTA);
        ItemMeta meta = heart.getItemMeta();
        meta.setDisplayName(Utils.chatRaw("&9+5 Max Hearts"));
        List<String> lore = new ArrayList<>();
        lore.add(Utils.chatRaw("&7Increases the total amount"));
        lore.add(Utils.chatRaw("&7of hearts you can have."));
        lore.add(Utils.chatRaw("&7Only four can be used"));
        lore.add(Utils.chatRaw("&7per player."));
        meta.setLore(lore);
        heart.setItemMeta(meta);
        this.maxHeartKey = new NamespacedKey((Plugin)this.plugin, "change_max_hearts");
        ShapedRecipe recepie = new ShapedRecipe(this.maxHeartKey, heart);
        recepie.shape(new String[] { "<%<", "%<%", "<#<" });
        recepie.setIngredient('<', Material.RED_DYE);
        recepie.setIngredient('%', Material.NETHERITE_BLOCK);
        recepie.setIngredient('#', Material.NETHER_STAR);
        return recepie;
    }
}
