package xyz.tomsoz.lifestealcore.Commands;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.tomsoz.lifestealcore.LifeStealCore;
import xyz.tomsoz.lifestealcore.Misc.Utils;

public class Withdraw implements CommandExecutor {
    LifeStealCore plugin;

    public Withdraw(LifeStealCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.chat(plugin, "&cThis is a player command!"));
            return true;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("lifesteal.withdraw")) {
            p.sendMessage(Utils.chat(plugin, "&cInsufficient permissions. If you believe this is an error please contact a server administrator."));
            return true;
        }

        int minHearts = plugin.getConfigManager().getConfig().getInt("minHealth");
        double current = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();

        if (current <= minHearts) {
            p.sendMessage(Utils.chat(plugin, "&cYou are already at the minimum hearts."));
            return true;
        }

        if (p.getInventory().containsAtLeast(plugin.getHeartItem(), 1) || p.getInventory().contains(Material.AIR)) {
            p.sendMessage(Utils.chat(plugin, "&cYou don't have space in your inventory!"));
            return true;
        }

        plugin.withdrawHealth(p, 2);

        p.getInventory().addItem(plugin.getHeartItem());

        p.sendMessage(Utils.chat(plugin, "&7Successfully withdrew a heart!"));
        return true;
    }
}
