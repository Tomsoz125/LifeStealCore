package xyz.tomsoz.lifestealcore.Events;

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
        boolean isValid = true;
        List<String> validWorlds = this.plugin.getConfigManager().getConfig().getStringList("onlyWorkIn");
        for (String w : validWorlds) {
            if (!e.getWhoClicked().getWorld().getName().equalsIgnoreCase(w)) {
                isValid = false;
                continue;
            }
            isValid = true;
        }
        if (!isValid)
            return;
        ShapedRecipe add5 = this.plugin.getRecepies().getMaxHeart();
        if ((((add5 != null) ? 1 : 0) & ((e.getInventory().getSize() == 10) ? 1 : 0)) != 0 && e.getInventory().getResult().equals(add5.getResult()) &&
                e.getRawSlot() == 0) {
            ItemStack upLeft = e.getInventory().getItem(1);
            ItemStack upRight = e.getInventory().getItem(3);
            ItemStack middle = e.getInventory().getItem(5);
            ItemStack downLeft = e.getInventory().getItem(7);
            ItemStack downRight = e.getInventory().getItem(9);
            ItemStack heart = new ItemStack(Material.RED_DYE);
            ItemMeta meta = heart.getItemMeta();
            meta.setDisplayName(Utils.chatRaw("&c+1 Heart"));
            List<String> lore = new ArrayList<>();
            lore.add(Utils.chatRaw("&7Gives you an extra heart,"));
            lore.add(Utils.chatRaw("&7provided you aren't already"));
            lore.add(Utils.chatRaw("&7maxed out."));
            meta.setLore(lore);
            heart.setItemMeta(meta);
            if (upLeft == null || !upLeft.getItemMeta().hasDisplayName() || !upLeft.getItemMeta().getDisplayName().equals(heart.getItemMeta().getDisplayName()) || upRight == null ||
                    !upRight.getItemMeta().hasDisplayName() || !upRight.getItemMeta().getDisplayName().equals(heart.getItemMeta().getDisplayName()) || middle == null ||
                    !middle.getItemMeta().hasDisplayName() || !middle.getItemMeta().getDisplayName().equals(heart.getItemMeta().getDisplayName()) || downLeft == null ||
                    !downLeft.getItemMeta().hasDisplayName() || !downLeft.getItemMeta().getDisplayName().equals(heart.getItemMeta().getDisplayName()) || downRight == null ||
                    !downRight.getItemMeta().hasDisplayName() || !downRight.getItemMeta().getDisplayName().equals(heart.getItemMeta().getDisplayName()))
                e.setCancelled(true);
        }
        ShapedRecipe fragmentHeart = this.plugin.getRecepies().getHeartFragments();
        if (fragmentHeart != null) {
            ItemStack upLeft = e.getInventory().getItem(1);
            ItemStack upMiddle = e.getInventory().getItem(2);
            ItemStack upRight = e.getInventory().getItem(3);
            ItemStack leftMiddle = e.getInventory().getItem(4);
            ItemStack middle = e.getInventory().getItem(5);
            ItemStack rightMiddle = e.getInventory().getItem(6);
            ItemStack downLeft = e.getInventory().getItem(7);
            ItemStack downMiddle = e.getInventory().getItem(8);
            ItemStack downRight = e.getInventory().getItem(9);
            ItemStack heart1 = new ItemStack(Material.RED_DYE);
            ItemMeta meta = heart1.getItemMeta();
            meta.setDisplayName(Utils.chatRaw("&c+1 Heart"));
            List<String> lore = new ArrayList<>();
            lore.add(Utils.chatRaw("&7Gives you an extra heart,"));
            lore.add(Utils.chatRaw("&7provided you aren't already"));
            lore.add(Utils.chatRaw("&7maxed out."));
            meta.setLore(lore);
            heart1.setItemMeta(meta);
            if (!e.getResult().equals(heart1))
                return;
            ItemStack heart = new ItemStack(Material.FERMENTED_SPIDER_EYE);
            meta = heart.getItemMeta();
            meta.setDisplayName(Utils.chatRaw("&cHeart Fragment"));
            lore = new ArrayList<>();
            lore.add(Utils.chatRaw("&7Combine 9 in a crafting"));
            lore.add(Utils.chatRaw("&7table to get a heart."));
            meta.setLore(lore);
            heart.setItemMeta(meta);
            if (!upLeft.equals(heart) ||
                    !upMiddle.equals(heart) ||
                    !upRight.equals(heart) ||
                    !leftMiddle.equals(heart) ||
                    !middle.equals(heart) ||
                    !rightMiddle.equals(heart) ||
                    !downLeft.equals(heart) ||
                    !downMiddle.equals(heart) ||
                    !downRight.equals(heart))
                e.setCancelled(true);
        }
    }
}
