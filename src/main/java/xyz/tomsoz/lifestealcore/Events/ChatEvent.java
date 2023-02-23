package xyz.tomsoz.lifestealcore.Events;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import xyz.tomsoz.lifestealcore.LifeStealCore;
import xyz.tomsoz.lifestealcore.Misc.Utils;

import java.util.List;

public class ChatEvent implements Listener {
    LifeStealCore plugin;
    public ChatEvent(LifeStealCore plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (e.getMessage().startsWith("5392749c-d659-41ac-aabc-d72299ef9a105392749c-d659-41ac-aabc-d72299ef9a10 ")) {
            e.setCancelled(true);
            ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
            BookMeta meta = (BookMeta) book.getItemMeta();

            BaseComponent[] page;
            OfflinePlayer banned = Bukkit.getOfflinePlayer(e.getMessage().replaceFirst("5392749c-d659-41ac-aabc-d72299ef9a105392749c-d659-41ac-aabc-d72299ef9a10 ", ""));
            if (banned != null) {
                ComponentBuilder text = new ComponentBuilder("Please confirm that you want to revive " + banned.getName() + ".");
                text.append(new ComponentBuilder("\n§a§lCONFIRM").event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click here to revive " + banned.getName()).create())).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "75392749c-d659-41ac-aabc-d72299ef9a105392749c-d659-41ac-aabc-d72299ef9a10 " + banned.getUniqueId())).create());
                text.append(new ComponentBuilder("\n§c§lCANCEL").event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click here to cancel").create())).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "45392749c-d659-41ac-aabc-d72299ef9a105392749c-d659-41ac-aabc-d72299ef9a10")).create());
                page = text.create();

                meta.spigot().addPage(page);
                meta.setTitle("Revive Book");
                meta.setAuthor("Tomsoz");
                book.setItemMeta(meta);

                e.getPlayer().openBook(book);
            }
        }

        if (e.getMessage().startsWith("75392749c-d659-41ac-aabc-d72299ef9a105392749c-d659-41ac-aabc-d72299ef9a10 ")) {
            e.setCancelled(true);

            OfflinePlayer banned = Bukkit.getOfflinePlayer(e.getMessage().replaceFirst("75392749c-d659-41ac-aabc-d72299ef9a105392749c-d659-41ac-aabc-d72299ef9a10 ", ""));
            if (banned != null) {
                List<String> bannedPs = plugin.getConfigManager().getData().getStringList("bannedPlayers");
                bannedPs.remove(banned.getUniqueId().toString());
                plugin.getConfigManager().getData().set("bannedPlayers", bannedPs);
                plugin.getConfigManager().saveOtherData();
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), plugin.getConfigManager().getConfig().getString("unbanCommand"));
                Bukkit.broadcastMessage(Utils.chat(plugin, plugin.getConfigManager().getMessages().getString("unbannedUser")));
                ItemStack hand = e.getPlayer().getInventory().getItemInMainHand();
                hand.setAmount(hand.getAmount() - 1);
                e.getPlayer().getInventory().setItemInMainHand(hand);
                if (banned.isOnline()) ((Player) banned).setGameMode(GameMode.SURVIVAL);
                else {
                    plugin.getConfigManager().getData().set("toSurvival." + banned.getUniqueId(), true);
                    plugin.getConfigManager().saveOtherData();
                }
            }
        }

        if (e.getMessage().equals("45392749c-d659-41ac-aabc-d72299ef9a105392749c-d659-41ac-aabc-d72299ef9a10")) {
            e.getPlayer().sendMessage(Utils.chat(plugin, "&7Revive has been cancelled."));
        }
    }
}
