package xyz.tomsoz.lifestealcore.Listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.tomsoz.lifestealcore.LifeStealCore;
import xyz.tomsoz.lifestealcore.Misc.Utils;

import java.util.ArrayList;
import java.util.List;

public class CraftEvent implements Listener {
    LifeStealCore plugin;

    public CraftEvent(LifeStealCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        if (!Utils.isValidWorld(plugin.getConfigManager().getConfig(), e.getWhoClicked().getWorld())) {
            e.setCancelled(true);
        }
    }
}
