package xyz.tomsoz.lifestealcore.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import xyz.tomsoz.lifestealcore.LifeStealCore;
import xyz.tomsoz.lifestealcore.Misc.Utils;

public class DropEvent implements Listener {
    LifeStealCore plugin;

    public DropEvent(LifeStealCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDrop(EntityDropItemEvent e) {
        ItemStack drop = e.getItemDrop().getItemStack();
        if (drop.hasItemMeta()) {
            if (drop.getItemMeta().getDisplayName().equals(plugin.getHeartItem().getItemMeta().getDisplayName()) && drop.getItemMeta().getLore().equals(plugin.getHeartItem().getItemMeta().getLore())) {
                e.getItemDrop().setGlowing(true);
            } else if (drop.getItemMeta().getDisplayName().equals(Utils.chatRaw("&9+5 Max Hearts"))) {
                e.getItemDrop().setGlowing(true);
            } else if (drop.getItemMeta().getDisplayName().equals(Utils.chatRaw("&cHeart Fragment"))) {
                e.getItemDrop().setGlowing(true);
            } else if (drop.getItemMeta().getDisplayName().equals(Utils.chatRaw("&6Revive Book"))) {
                e.getItemDrop().setGlowing(true);
            }
        }
    }

    @EventHandler
    public void onBlockDrop(BlockDropItemEvent e) {
        for (Item item : e.getItems()) {
            ItemStack drop = item.getItemStack();
            if (drop.hasItemMeta()) {
                if (drop.getItemMeta().getDisplayName().equals(plugin.getHeartItem().getItemMeta().getDisplayName()) && drop.getItemMeta().getLore().equals(plugin.getHeartItem().getItemMeta().getLore())) {
                    item.setGlowing(true);
                } else if (drop.getItemMeta().getDisplayName().equals(Utils.chatRaw("&9+5 Max Hearts"))) {
                    item.setGlowing(true);
                } else if (drop.getItemMeta().getDisplayName().equals(Utils.chatRaw("&cHeart Fragment"))) {
                    item.setGlowing(true);
                } else if (drop.getItemMeta().getDisplayName().equals(Utils.chatRaw("&6Revive Book"))) {
                    item.setGlowing(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent e) {
        ItemStack drop = e.getItemDrop().getItemStack();
        if (drop.hasItemMeta()) {
            if (drop.getItemMeta().getDisplayName().equals(plugin.getHeartItem().getItemMeta().getDisplayName()) && drop.getItemMeta().getLore().equals(plugin.getHeartItem().getItemMeta().getLore())) {
                e.getItemDrop().setGlowing(true);
            } else if (drop.getItemMeta().getDisplayName().equals(Utils.chatRaw("&9+5 Max Hearts"))) {
                e.getItemDrop().setGlowing(true);
            } else if (drop.getItemMeta().getDisplayName().equals(Utils.chatRaw("&cHeart Fragment"))) {
                e.getItemDrop().setGlowing(true);
            } else if (drop.getItemMeta().getDisplayName().equals(Utils.chatRaw("&6Revive Book"))) {
                e.getItemDrop().setGlowing(true);
            }
        }
    }
}
