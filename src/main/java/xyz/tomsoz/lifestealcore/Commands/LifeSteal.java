package xyz.tomsoz.lifestealcore.Commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.tomsoz.lifestealcore.LifeStealCore;
import xyz.tomsoz.lifestealcore.Misc.Utils;

public class LifeSteal implements CommandExecutor {
    LifeStealCore plugin;

    public LifeSteal(LifeStealCore plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (label.equalsIgnoreCase("lifesteal") || label.equalsIgnoreCase("ls")) {
            if (args.length == 0) {
                sender.sendMessage(Utils.chat(this.plugin, "&a" + this.plugin.getDescription().getName() + " v" + this.plugin.getDescription().getVersion() + " by " + (String)this.plugin.getDescription().getAuthors().get(0) + ".\nDownload soon!"));
                sender.sendMessage("");
                sender.sendMessage(Utils.chat(this.plugin, "/" + label.toLowerCase() + " reload"));
                sender.sendMessage(Utils.chat(this.plugin, "/" + label.toLowerCase() + " set <player> <amount>"));
                sender.sendMessage(Utils.chat(this.plugin, "/" + label.toLowerCase() + " get <player>"));
                return true;
            }

            String name = "&c&lConsole";
            if (sender instanceof Player) {
                name = ((Player)sender).getDisplayName();
            }

            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                if (!sender.hasPermission("lifesteal.reload")) {
                    sender.sendMessage(Utils.chat(this.plugin, "&cInsufficient permissions. If you believe this is an error please contact a server administrator."));
                    return true;
                }

                long startTime = System.currentTimeMillis();
                this.plugin.getConfigManager().reloadConfig();
                this.plugin.getConfigManager().reloadData();
                this.plugin.getConfigManager().reloadMessages();
                long timeTaken = System.currentTimeMillis() - startTime;

                sender.sendMessage(Utils.chat(this.plugin, "&aLifeSteal config files have been reloaded successfully. (" + timeTaken + " ms)"));
                return true;
            }

            if (args.length == 2 && args[0].equalsIgnoreCase("get")) {
                if (!sender.hasPermission("lifesteal.get")) {
                    sender.sendMessage(Utils.chat(this.plugin, "&cInsufficient permissions. If you believe this is an error please contact a server administrator."));
                    return true;
                }

                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(Utils.chat(this.plugin, "&cUsage: /" + label.toLowerCase() + " " + args[0].toLowerCase() + " <player>"));
                    return true;
                }

                sender.sendMessage(Utils.chat(this.plugin, "&a" + target.getName() + " &ahas " + (plugin.getHealth(target) / 2) + " &ahearts."));
            }

            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("set")) {
                    if (!sender.hasPermission("lifesteal.set")) {
                        sender.sendMessage(Utils.chat(this.plugin, "&cInsufficient permissions. If you believe this is an error please contact a server administrator."));
                        return true;
                    }

                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                    if (target == null) {
                        sender.sendMessage(Utils.chat(this.plugin, "&cUsage: /" + label.toLowerCase() + " " + args[0].toLowerCase() + " <player> <number>"));
                        return true;
                    }

                    double amount = 0.0D;
                    try {
                        amount = Double.parseDouble(args[2]);
                    } catch (NumberFormatException e) {
                        sender.sendMessage(Utils.chat(this.plugin, "&cUsage: /" + label.toLowerCase() + " " + args[0].toLowerCase() + " <player> <number>"));
                        return true;
                    }

                    plugin.setHealth(target, amount);

                    String display = target.getName();
                    if (target.isOnline()) {
                        Player online = (Player) target;
                        display = online.getDisplayName();

                        online.sendMessage(Utils.chat(this.plugin, "&a" + name + " &ahas set your health to " + amount + "."));
                    }
                    Utils.sendConsole("&a" + name + " &ahas set " + display + "&a's health to " + amount + ".");
                    sender.sendMessage(Utils.chat(plugin, "&aYou've set " + display + "&a's health to " + amount + "."));
                    return true;
                }
            }
        }
        return true;
    }
}
