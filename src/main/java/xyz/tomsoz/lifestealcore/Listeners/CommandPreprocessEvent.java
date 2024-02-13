package xyz.tomsoz.lifestealcore.Listeners;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import xyz.tomsoz.lifestealcore.LifeStealCore;
import xyz.tomsoz.lifestealcore.Misc.Utils;

import java.util.List;
import java.util.UUID;

public class CommandPreprocessEvent implements Listener {
    LifeStealCore plugin;
    public CommandPreprocessEvent(LifeStealCore plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onChat(PlayerCommandPreprocessEvent e) {
        if (e.getMessage().startsWith("/5392749c-d659-41ac-aabc-d72299ef9a105392749c-d659-41ac-aabc-d72299ef9a10 ")) {
            e.setCancelled(true);
            ItemStack book = new ItemStack(Material.WRITTEN_BOOK, 1);
            BookMeta meta = (BookMeta) book.getItemMeta();

            BaseComponent[] page;
            String player = e.getMessage().replaceFirst("/5392749c-d659-41ac-aabc-d72299ef9a105392749c-d659-41ac-aabc-d72299ef9a10 ", "").split(" : ")[1];
            String uuid = e.getMessage().replaceFirst("/5392749c-d659-41ac-aabc-d72299ef9a105392749c-d659-41ac-aabc-d72299ef9a10 ", "").split(" : ")[0];

            ComponentBuilder text = new ComponentBuilder("Please confirm that you want to revive §l" + player + "§r.");
            text.append(new ComponentBuilder("\n\n§a§lCONFIRM").event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click here to revive " + player).create())).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/75392749c-d659-41ac-aabc-d72299ef9a105392749c-d659-41ac-aabc-d72299ef9a10 " + e.getMessage().replaceFirst("/5392749c-d659-41ac-aabc-d72299ef9a105392749c-d659-41ac-aabc-d72299ef9a10 ", ""))).create());
            text.append(new ComponentBuilder("\n\n§c§lCANCEL").event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click here to cancel").create())).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/45392749c-d659-41ac-aabc-d72299ef9a105392749c-d659-41ac-aabc-d72299ef9a10")).create());
            page = text.create();

            meta.spigot().addPage(page);
            meta.setTitle("Revive Book");
            meta.setAuthor("Tomsoz");
            book.setItemMeta(meta);

            e.getPlayer().openBook(book);
        }

        if (e.getMessage().startsWith("/75392749c-d659-41ac-aabc-d72299ef9a105392749c-d659-41ac-aabc-d72299ef9a10 ")) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(Utils.chat(plugin,"Reviving..."));
            OfflinePlayer banned = Bukkit.getOfflinePlayer(UUID.fromString(e.getMessage().replaceFirst("/75392749c-d659-41ac-aabc-d72299ef9a105392749c-d659-41ac-aabc-d72299ef9a10 ", "").split(" : ")[0]));
            if (banned != null) {
                List<String> bannedList = plugin.getConfigManager().getData().getStringList("bannedPlayers");
                bannedList.remove((banned.getUniqueId() + " : " + banned.getName()));
                plugin.getConfigManager().getData().set("bannedPlayers", banned);
                plugin.getConfigManager().saveOtherData();
                if (plugin.getConfigManager().getConfig().getBoolean("banIfOutOfHearts")) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), plugin.getConfigManager().getConfig().getString("unbanCommand"));
                }
                Bukkit.broadcastMessage(Utils.chatRaw(plugin.getConfigManager().getMessages().getString("unbannedUser").replaceAll("%nl%", "\n" + plugin.getConfigManager().getMessages().getString("prefix")).replaceAll("%eliminated%", banned.getName())));
                ItemStack hand = e.getPlayer().getInventory().getItemInMainHand();
                hand.setAmount(hand.getAmount() - 1);
                e.getPlayer().getInventory().setItemInMainHand(hand);
                if (banned.isOnline()) {
                    ((Player) banned).setGameMode(GameMode.SURVIVAL);
                    ((Player) banned).teleport(((Player) banned).getBedSpawnLocation() == null ?  e.getPlayer().getWorld().getSpawnLocation() : ((Player) banned).getBedSpawnLocation());
                    ((Player) banned).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(plugin.getConfigManager().getConfig().getDouble("revivedHealth"));
                    plugin.getConfigManager().getData().set("health." + ((Player) banned).getUniqueId(), Double.valueOf(plugin.getConfigManager().getConfig().getDouble("revivedHealth")));
                    this.plugin.getConfigManager().saveOtherData();
                } else {
                    plugin.getConfigManager().getData().set("toSurvival." + banned.getUniqueId(), true);
                    plugin.getConfigManager().saveOtherData();
                }
            }
        }

        if (e.getMessage().equals("/45392749c-d659-41ac-aabc-d72299ef9a105392749c-d659-41ac-aabc-d72299ef9a10")) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(Utils.chat(plugin, "&7Revive has been cancelled."));
        }
    }
}
