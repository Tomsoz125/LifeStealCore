package xyz.tomsoz.lifestealcore.Events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.LootTables;
import xyz.tomsoz.lifestealcore.LifeStealCore;
import xyz.tomsoz.lifestealcore.Misc.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InventoryOpen implements Listener {
    LifeStealCore plugin;
    public InventoryOpen(LifeStealCore plugin) {
        this.plugin = plugin;
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryOpen(InventoryOpenEvent e) {
        if (e.getInventory().getHolder() instanceof Chest) {
            Chest c = (Chest) e.getInventory().getHolder();
            LootTable loot = plugin.getInteractEvent().chests.get(((Chest) e.getInventory().getHolder()).getLocation());
            plugin.getInteractEvent().chests.remove(((Chest) e.getInventory().getHolder()).getLocation());

            if (loot.getKey().equals(LootTables.ABANDONED_MINESHAFT.getKey())) {
                int percentage = this.plugin.getConfigManager().getConfig().getInt("lootChances.abandoned_mineshaft");
                if (percentage > 0) {
                    int big = 100 / percentage;
                    int chance = (new Random()).nextInt(big);
                    if (0 == chance) {
                        List<Integer> slots = new ArrayList<>();
                        int i = 0;
                        for (ItemStack item : c.getInventory().getContents()) {
                            if (item == null || item.getType().equals(Material.AIR)) {
                                slots.add(i);
                            }
                            i++;
                        }
                        ItemStack heart = new ItemStack(Material.FERMENTED_SPIDER_EYE);
                        ItemMeta meta = heart.getItemMeta();
                        meta.setDisplayName(Utils.chatRaw("&cHeart Fragment"));
                        List<String> lore = new ArrayList<>();
                        lore.add(Utils.chatRaw("&7Combine 9 in a crafting"));
                        lore.add(Utils.chatRaw("&7table to get a heart."));
                        meta.setLore(lore);
                        heart.setItemMeta(meta);

                        c.getInventory().setItem(slots.get(new Random().nextInt(slots.size())), heart);
                    }
                }
            }

            if (loot.getKey().equals(LootTables.ANCIENT_CITY.getKey())) {
                int percentage = this.plugin.getConfigManager().getConfig().getInt("lootChances.ancient_city");
                if (percentage > 0) {
                    int big = 100 / percentage;
                    int chance = (new Random()).nextInt(big);
                    if (0 == chance) {
                        List<Integer> slots = new ArrayList<>();
                        int i = 0;
                        for (ItemStack item : c.getInventory().getContents()) {
                            if (item == null || item.getType().equals(Material.AIR)) {
                                slots.add(i);
                            }
                            i++;
                        }
                        ItemStack heart = new ItemStack(Material.FERMENTED_SPIDER_EYE);
                        ItemMeta meta = heart.getItemMeta();
                        meta.setDisplayName(Utils.chatRaw("&cHeart Fragment"));
                        List<String> lore = new ArrayList<>();
                        lore.add(Utils.chatRaw("&7Combine 9 in a crafting"));
                        lore.add(Utils.chatRaw("&7table to get a heart."));
                        meta.setLore(lore);
                        heart.setItemMeta(meta);

                        c.getInventory().setItem(slots.get(new Random().nextInt(slots.size())), heart);
                    }
                }
            }

            if (loot.getKey().equals(LootTables.BASTION_BRIDGE.getKey()) || loot.getKey().equals(LootTables.BASTION_OTHER.getKey()) || loot.getKey().equals(LootTables.BASTION_TREASURE.getKey()) || loot.getKey().equals(LootTables.BASTION_HOGLIN_STABLE.getKey())) {
                int percentage = this.plugin.getConfigManager().getConfig().getInt("lootChances.bastion");
                if (percentage > 0) {
                    int big = 100 / percentage;
                    int chance = (new Random()).nextInt(big);
                    if (0 == chance) {
                        List<Integer> slots = new ArrayList<>();
                        int i = 0;
                        for (ItemStack item : c.getInventory().getContents()) {
                            if (item == null || item.getType().equals(Material.AIR)) {
                                slots.add(i);
                            }
                            i++;
                        }
                        ItemStack heart = new ItemStack(Material.FERMENTED_SPIDER_EYE);
                        ItemMeta meta = heart.getItemMeta();
                        meta.setDisplayName(Utils.chatRaw("&cHeart Fragment"));
                        List<String> lore = new ArrayList<>();
                        lore.add(Utils.chatRaw("&7Combine 9 in a crafting"));
                        lore.add(Utils.chatRaw("&7table to get a heart."));
                        meta.setLore(lore);
                        heart.setItemMeta(meta);

                        c.getInventory().setItem(slots.get(new Random().nextInt(slots.size())), heart);
                    }
                }
            }

            if (loot.getKey().equals(LootTables.BURIED_TREASURE.getKey())) {
                int percentage = this.plugin.getConfigManager().getConfig().getInt("lootChances.buried_treasure");
                if (percentage > 0) {
                    int big = 100 / percentage;
                    int chance = (new Random()).nextInt(big);
                    if (0 == chance) {
                        List<Integer> slots = new ArrayList<>();
                        int i = 0;
                        for (ItemStack item : c.getInventory().getContents()) {
                            if (item == null || item.getType().equals(Material.AIR)) {
                                slots.add(i);
                            }
                            i++;
                        }
                        ItemStack heart = new ItemStack(Material.FERMENTED_SPIDER_EYE);
                        ItemMeta meta = heart.getItemMeta();
                        meta.setDisplayName(Utils.chatRaw("&cHeart Fragment"));
                        List<String> lore = new ArrayList<>();
                        lore.add(Utils.chatRaw("&7Combine 9 in a crafting"));
                        lore.add(Utils.chatRaw("&7table to get a heart."));
                        meta.setLore(lore);
                        heart.setItemMeta(meta);

                        c.getInventory().setItem(slots.get(new Random().nextInt(slots.size())), heart);
                    }
                }
            }

            if (loot.getKey().equals(LootTables.DESERT_PYRAMID.getKey())) {
                int percentage = this.plugin.getConfigManager().getConfig().getInt("lootChances.desert_pyramid");
                if (percentage > 0) {
                    int big = 100 / percentage;
                    int chance = (new Random()).nextInt(big);
                    if (0 == chance) {
                        List<Integer> slots = new ArrayList<>();
                        int i = 0;
                        for (ItemStack item : c.getInventory().getContents()) {
                            if (item == null || item.getType().equals(Material.AIR)) {
                                slots.add(i);
                            }
                            i++;
                        }
                        ItemStack heart = new ItemStack(Material.FERMENTED_SPIDER_EYE);
                        ItemMeta meta = heart.getItemMeta();
                        meta.setDisplayName(Utils.chatRaw("&cHeart Fragment"));
                        List<String> lore = new ArrayList<>();
                        lore.add(Utils.chatRaw("&7Combine 9 in a crafting"));
                        lore.add(Utils.chatRaw("&7table to get a heart."));
                        meta.setLore(lore);
                        heart.setItemMeta(meta);

                        c.getInventory().setItem(slots.get(new Random().nextInt(slots.size())), heart);
                    }
                }
            }

            if (loot.getKey().equals(LootTables.END_CITY_TREASURE.getKey())) {
                int percentage = this.plugin.getConfigManager().getConfig().getInt("lootChances.end_city");
                if (percentage > 0) {
                    int big = 100 / percentage;
                    int chance = (new Random()).nextInt(big);
                    if (0 == chance) {
                        List<Integer> slots = new ArrayList<>();
                        int i = 0;
                        for (ItemStack item : c.getInventory().getContents()) {
                            if (item == null || item.getType().equals(Material.AIR)) {
                                slots.add(i);
                            }
                            i++;
                        }
                        ItemStack heart = new ItemStack(Material.FERMENTED_SPIDER_EYE);
                        ItemMeta meta = heart.getItemMeta();
                        meta.setDisplayName(Utils.chatRaw("&cHeart Fragment"));
                        List<String> lore = new ArrayList<>();
                        lore.add(Utils.chatRaw("&7Combine 9 in a crafting"));
                        lore.add(Utils.chatRaw("&7table to get a heart."));
                        meta.setLore(lore);
                        heart.setItemMeta(meta);

                        c.getInventory().setItem(slots.get(new Random().nextInt(slots.size())), heart);
                    }
                }
            }

            if (loot.getKey().equals(LootTables.IGLOO_CHEST.getKey())) {
                int percentage = this.plugin.getConfigManager().getConfig().getInt("lootChances.igloo");
                if (percentage > 0) {
                    int big = 100 / percentage;
                    int chance = (new Random()).nextInt(big);
                    if (0 == chance) {
                        List<Integer> slots = new ArrayList<>();
                        int i = 0;
                        for (ItemStack item : c.getInventory().getContents()) {
                            if (item == null || item.getType().equals(Material.AIR)) {
                                slots.add(i);
                            }
                            i++;
                        }
                        ItemStack heart = new ItemStack(Material.FERMENTED_SPIDER_EYE);
                        ItemMeta meta = heart.getItemMeta();
                        meta.setDisplayName(Utils.chatRaw("&cHeart Fragment"));
                        List<String> lore = new ArrayList<>();
                        lore.add(Utils.chatRaw("&7Combine 9 in a crafting"));
                        lore.add(Utils.chatRaw("&7table to get a heart."));
                        meta.setLore(lore);
                        heart.setItemMeta(meta);

                        c.getInventory().setItem(slots.get(new Random().nextInt(slots.size())), heart);
                    }
                }
            }

            if (loot.getKey().equals(LootTables.JUNGLE_TEMPLE.getKey())) {
                int percentage = this.plugin.getConfigManager().getConfig().getInt("lootChances.jungle_temple");
                if (percentage > 0) {
                    int big = 100 / percentage;
                    int chance = (new Random()).nextInt(big);
                    if (0 == chance) {
                        List<Integer> slots = new ArrayList<>();
                        int i = 0;
                        for (ItemStack item : c.getInventory().getContents()) {
                            if (item == null || item.getType().equals(Material.AIR)) {
                                slots.add(i);
                            }
                            i++;
                        }
                        ItemStack heart = new ItemStack(Material.FERMENTED_SPIDER_EYE);
                        ItemMeta meta = heart.getItemMeta();
                        meta.setDisplayName(Utils.chatRaw("&cHeart Fragment"));
                        List<String> lore = new ArrayList<>();
                        lore.add(Utils.chatRaw("&7Combine 9 in a crafting"));
                        lore.add(Utils.chatRaw("&7table to get a heart."));
                        meta.setLore(lore);
                        heart.setItemMeta(meta);

                        c.getInventory().setItem(slots.get(new Random().nextInt(slots.size())), heart);
                    }
                }
            }

            if (loot.getKey().equals(LootTables.RUINED_PORTAL.getKey()) || loot.getKey().equals(LootTables.UNDERWATER_RUIN_BIG.getKey()) || loot.getKey().equals(LootTables.UNDERWATER_RUIN_SMALL.getKey())) {
                int percentage = this.plugin.getConfigManager().getConfig().getInt("lootChances.ruined_portal");
                if (percentage > 0) {
                    int big = 100 / percentage;
                    int chance = (new Random()).nextInt(big);
                    if (0 == chance) {
                        List<Integer> slots = new ArrayList<>();
                        int i = 0;
                        for (ItemStack item : c.getInventory().getContents()) {
                            if (item == null || item.getType().equals(Material.AIR)) {
                                slots.add(i);
                            }
                            i++;
                        }
                        ItemStack heart = new ItemStack(Material.FERMENTED_SPIDER_EYE);
                        ItemMeta meta = heart.getItemMeta();
                        meta.setDisplayName(Utils.chatRaw("&cHeart Fragment"));
                        List<String> lore = new ArrayList<>();
                        lore.add(Utils.chatRaw("&7Combine 9 in a crafting"));
                        lore.add(Utils.chatRaw("&7table to get a heart."));
                        meta.setLore(lore);
                        heart.setItemMeta(meta);

                        c.getInventory().setItem(slots.get(new Random().nextInt(slots.size())), heart);
                    }
                }
            }

            if (loot.getKey().equals(LootTables.SHIPWRECK_TREASURE.getKey())) {
                int percentage = this.plugin.getConfigManager().getConfig().getInt("lootChances.shipwreck_treasure");
                if (percentage > 0) {
                    int big = 100 / percentage;
                    int chance = (new Random()).nextInt(big);
                    if (0 == chance) {
                        List<Integer> slots = new ArrayList<>();
                        int i = 0;
                        for (ItemStack item : c.getInventory().getContents()) {
                            if (item == null || item.getType().equals(Material.AIR)) {
                                slots.add(i);
                            }
                            i++;
                        }
                        ItemStack heart = new ItemStack(Material.FERMENTED_SPIDER_EYE);
                        ItemMeta meta = heart.getItemMeta();
                        meta.setDisplayName(Utils.chatRaw("&cHeart Fragment"));
                        List<String> lore = new ArrayList<>();
                        lore.add(Utils.chatRaw("&7Combine 9 in a crafting"));
                        lore.add(Utils.chatRaw("&7table to get a heart."));
                        meta.setLore(lore);
                        heart.setItemMeta(meta);

                        c.getInventory().setItem(slots.get(new Random().nextInt(slots.size())), heart);
                    }
                }
            }

            if (loot.getKey().equals(LootTables.SIMPLE_DUNGEON.getKey())) {
                int percentage = this.plugin.getConfigManager().getConfig().getInt("lootChances.simple_dungeon");
                if (percentage > 0) {
                    int big = 100 / percentage;
                    int chance = (new Random()).nextInt(big);
                    if (0 == chance) {
                        List<Integer> slots = new ArrayList<>();
                        int i = 0;
                        for (ItemStack item : c.getInventory().getContents()) {
                            if (item == null || item.getType().equals(Material.AIR)) {
                                slots.add(i);
                            }
                            i++;
                        }
                        ItemStack heart = new ItemStack(Material.FERMENTED_SPIDER_EYE);
                        ItemMeta meta = heart.getItemMeta();
                        meta.setDisplayName(Utils.chatRaw("&cHeart Fragment"));
                        List<String> lore = new ArrayList<>();
                        lore.add(Utils.chatRaw("&7Combine 9 in a crafting"));
                        lore.add(Utils.chatRaw("&7table to get a heart."));
                        meta.setLore(lore);
                        heart.setItemMeta(meta);

                        c.getInventory().setItem(slots.get(new Random().nextInt(slots.size())), heart);
                    }
                }
            }

            if (loot.getKey().equals(LootTables.STRONGHOLD_CORRIDOR.getKey()) || loot.getKey().equals(LootTables.STRONGHOLD_LIBRARY.getKey()) || loot.getKey().equals(LootTables.STRONGHOLD_CROSSING.getKey())) {
                int percentage = this.plugin.getConfigManager().getConfig().getInt("lootChances.stronghold");
                if (percentage > 0) {
                    int big = 100 / percentage;
                    int chance = (new Random()).nextInt(big);
                    if (0 == chance) {
                        List<Integer> slots = new ArrayList<>();
                        int i = 0;
                        for (ItemStack item : c.getInventory().getContents()) {
                            if (item == null || item.getType().equals(Material.AIR)) {
                                slots.add(i);
                            }
                            i++;
                        }
                        ItemStack heart = new ItemStack(Material.FERMENTED_SPIDER_EYE);
                        ItemMeta meta = heart.getItemMeta();
                        meta.setDisplayName(Utils.chatRaw("&cHeart Fragment"));
                        List<String> lore = new ArrayList<>();
                        lore.add(Utils.chatRaw("&7Combine 9 in a crafting"));
                        lore.add(Utils.chatRaw("&7table to get a heart."));
                        meta.setLore(lore);
                        heart.setItemMeta(meta);

                        c.getInventory().setItem(slots.get(new Random().nextInt(slots.size())), heart);
                    }
                }
            }

            if (loot.getKey().equals(LootTables.WOODLAND_MANSION.getKey())) {
                int percentage = this.plugin.getConfigManager().getConfig().getInt("lootChances.woodland_mansion");
                if (percentage > 0) {
                    int big = 100 / percentage;
                    int chance = (new Random()).nextInt(big);
                    if (0 == chance) {
                        List<Integer> slots = new ArrayList<>();
                        int i = 0;
                        for (ItemStack item : c.getInventory().getContents()) {
                            if (item == null || item.getType().equals(Material.AIR)) {
                                slots.add(i);
                            }
                            i++;
                        }
                        ItemStack heart = new ItemStack(Material.FERMENTED_SPIDER_EYE);
                        ItemMeta meta = heart.getItemMeta();
                        meta.setDisplayName(Utils.chatRaw("&cHeart Fragment"));
                        List<String> lore = new ArrayList<>();
                        lore.add(Utils.chatRaw("&7Combine 9 in a crafting"));
                        lore.add(Utils.chatRaw("&7table to get a heart."));
                        meta.setLore(lore);
                        heart.setItemMeta(meta);

                        c.getInventory().setItem(slots.get(new Random().nextInt(slots.size())), heart);
                    }
                }
            }

            if (loot.getKey().equals(LootTables.VILLAGE_ARMORER.getKey())) {
                int percentage = this.plugin.getConfigManager().getConfig().getInt("lootChances.village");
                if (percentage > 0) {
                    int big = 100 / percentage;
                    int chance = (new Random()).nextInt(big);
                    if (0 == chance) {
                        List<Integer> slots = new ArrayList<>();
                        int i = 0;
                        for (ItemStack item : c.getInventory().getContents()) {
                            if (item == null || item.getType().equals(Material.AIR)) {
                                slots.add(i);
                            }
                            i++;
                        }
                        ItemStack heart = new ItemStack(Material.FERMENTED_SPIDER_EYE);
                        ItemMeta meta = heart.getItemMeta();
                        meta.setDisplayName(Utils.chatRaw("&cHeart Fragment"));
                        List<String> lore = new ArrayList<>();
                        lore.add(Utils.chatRaw("&7Combine 9 in a crafting"));
                        lore.add(Utils.chatRaw("&7table to get a heart."));
                        meta.setLore(lore);
                        heart.setItemMeta(meta);

                        c.getInventory().setItem(slots.get(new Random().nextInt(slots.size())), heart);
                    }
                }
            }
        }
    }
}
