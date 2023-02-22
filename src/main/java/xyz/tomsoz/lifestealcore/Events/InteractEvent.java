package xyz.tomsoz.lifestealcore.Events;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import xyz.tomsoz.lifestealcore.LifeStealCore;
import xyz.tomsoz.lifestealcore.Misc.Utils;

import java.util.List;

public class InteractEvent implements Listener {
    LifeStealCore plugin;

    public InteractEvent(LifeStealCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getHand() != null && e.getHand().equals(EquipmentSlot.OFF_HAND))
            return;
        boolean isValid = true;
        List<String> validWorlds = this.plugin.getConfigManager().getConfig().getStringList("onlyWorkIn");
        for (String w : validWorlds) {
            if (!e.getPlayer().getWorld().getName().equalsIgnoreCase(w)) {
                isValid = false;
                continue;
            }
            isValid = true;
        }
        if (!isValid)
            return;
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e
                .getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Player p = e.getPlayer();
            if (p.getInventory().getItemInMainHand().getItemMeta() != null && p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(this.plugin.getRecepies().getCreateHeart().getResult().getItemMeta().getDisplayName())) {
                e.setCancelled(true);
                int extraHealth = this.plugin.getConfigManager().getData().getInt("maxHealth." + p.getUniqueId());
                int totalHealth = this.plugin.getConfigManager().getConfig().getInt("maxHealth") + extraHealth;
                if (p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() >= totalHealth) {
                    p.sendMessage(Utils.chat(this.plugin, this.plugin.getConfigManager().getMessages().getString("maxHealth").replaceAll("%max%", p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + "")));
                    return;
                }
                ItemStack hand = p.getInventory().getItemInMainHand();
                hand.setAmount(hand.getAmount() - 1);
                p.getInventory().setItemInMainHand(hand);
                p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + 2.0D);
                this.plugin.getConfigManager().getData().set("health." + p.getUniqueId(), Double.valueOf(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()));
                this.plugin.getConfigManager().saveOtherData();
                p.sendMessage(Utils.chat(this.plugin, this.plugin.getConfigManager().getMessages().getString("healthCrafted")));
            }
            if (p.getInventory().getItemInMainHand().getItemMeta() != null && p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(this.plugin.getRecepies().getRemHeart().getResult().getItemMeta().getDisplayName())) {
                e.setCancelled(true);
                int minHealth = this.plugin.getConfigManager().getConfig().getInt("minHealth");
                if (minHealth >= p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()) {
                    p.sendMessage(Utils.chat(this.plugin, this.plugin.getConfigManager().getMessages().getString("minHealth")));
                    return;
                }
                ItemStack hand = p.getInventory().getItemInMainHand();
                hand.setAmount(hand.getAmount() - 1);
                p.getInventory().setItemInMainHand(hand);
                p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() - 2.0D);
                this.plugin.getConfigManager().getData().set("health." + p.getUniqueId(), Double.valueOf(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()));
                this.plugin.getConfigManager().saveOtherData();
                p.sendMessage(Utils.chat(this.plugin, this.plugin.getConfigManager().getMessages().getString("healthRemovedCraft")));
            }
            if (p.getInventory().getItemInMainHand().getItemMeta() != null && p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(this.plugin.getRecepies().getMaxHeart().getResult().getItemMeta().getDisplayName())) {
                e.setCancelled(true);
                int extraHealth = this.plugin.getConfigManager().getData().getInt("maxHealth." + p.getUniqueId());
                if (extraHealth >= 40) {
                    p.sendMessage(Utils.chat(this.plugin, this.plugin.getConfigManager().getMessages().getString("maxExtraHealth")));
                    return;
                }
                ItemStack hand = p.getInventory().getItemInMainHand();
                hand.setAmount(hand.getAmount() - 1);
                p.getInventory().setItemInMainHand(hand);
                p.sendMessage(Utils.chat(this.plugin, (extraHealth + 10.0D) + ""));
                this.plugin.getConfigManager().getData().set("maxHealth." + p.getUniqueId(), Double.valueOf(extraHealth + 10.0D));
                this.plugin.getConfigManager().saveOtherData();
                p.sendMessage(Utils.chat(this.plugin, this.plugin.getConfigManager().getMessages().getString("addedMaxHealth")));
            }
        }
    }
}
