package xyz.tomsoz.lifestealcore.Events;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.tomsoz.lifestealcore.LifeStealCore;
import xyz.tomsoz.lifestealcore.Misc.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MobDeathEvent implements Listener {
    LifeStealCore plugin;

    public MobDeathEvent(LifeStealCore plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        FileConfiguration config = this.plugin.getConfigManager().getConfig();
        if (!Utils.isValidWorld(config, e.getEntity().getWorld())) return;
        int percentage = this.plugin.getConfigManager().getConfig().getInt("heartFragmentMobs." + e.getEntity().getType().getName().toLowerCase());
        if (percentage > 0) {
            int big = 100 / percentage;
            int chance = (new Random()).nextInt(big - 1 + 1) + 1;
            if (percentage == chance) {
                ItemStack heart = new ItemStack(Material.FERMENTED_SPIDER_EYE);
                ItemMeta meta = heart.getItemMeta();
                meta.setDisplayName(Utils.chatRaw("&cHeart Fragment"));
                List<String> lore = new ArrayList<>();
                lore.add(Utils.chatRaw("&7Combine 9 in a crafting"));
                lore.add(Utils.chatRaw("&7table to get a heart."));
                meta.setLore(lore);
                heart.setItemMeta(meta);
                e.getDrops().add(heart);
            }
        }
    }
}
