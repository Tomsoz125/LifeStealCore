package xyz.tomsoz.lifestealcore.Events;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
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
            if (p.getInventory().getItemInMainHand().getItemMeta() != null && p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(this.plugin.getRecepies().getReviveBook().getResult().getItemMeta().getDisplayName())) {
                e.setCancelled(true);
                ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
                BookMeta meta = (BookMeta) book.getItemMeta();

                BaseComponent[] page;
                if (plugin.getConfigManager().getData().getStringList("bannedPlayers").size() == 0) {
                    page = new ComponentBuilder("There are no eliminated players at the moment.").event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Wait for somebody to be eliminated!").create())).create();
                } else {
                    ComponentBuilder text = new ComponentBuilder("Please click on a player name to revive them: ");
                    for (String s : plugin.getConfigManager().getData().getStringList("bannedPlayers")) {
                        OfflinePlayer banned = Bukkit.getOfflinePlayer(s);
                        if (banned != null) {
                            text.append(new ComponentBuilder("\n> " + banned.getName()).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click here to revive " + banned.getName()).create())).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "5392749c-d659-41ac-aabc-d72299ef9a105392749c-d659-41ac-aabc-d72299ef9a10 " + banned.getUniqueId())).create());
                        }
                    }
                    page = text.create();
                }

                meta.spigot().addPage(page);
                meta.setTitle("Revive Book");
                meta.setAuthor("Tomsoz");
                book.setItemMeta(meta);

                p.openBook(book);
            }
        }
    }
}
