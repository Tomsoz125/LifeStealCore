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

import java.util.List;

public class LSAdmin implements CommandExecutor {
    LifeStealCore plugin;

    public LSAdmin(LifeStealCore plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (label.equalsIgnoreCase("lsadmin")) {
            if (args.length == 0) {
                sender.sendMessage(Utils.chat(this.plugin, "&a" + this.plugin.getDescription().getName() + " v" + this.plugin.getDescription().getVersion() + " by " + (String)this.plugin.getDescription().getAuthors().get(0) + ".\nDownload soon!"));
                sender.sendMessage("");
                sender.sendMessage(Utils.chat(this.plugin, "/lsadmin setHealth <player> <amount>"));
                sender.sendMessage(Utils.chat(this.plugin, "/lsadmin setMaxHealth <player> <amount>"));
                sender.sendMessage(Utils.chat(this.plugin, "/lsadmin get <player>"));
                return true;
            }
            String name = "&c&lConsole";
            if (sender instanceof Player) {
                if (!((Player)sender).hasPermission("lifesteal.admin")) {
                    ((Player)sender).sendMessage(Utils.chat(this.plugin, "&cInsufficient permissions. If you believe this is an error please contact a server administrator."));
                    return true;
                }
                name = ((Player)sender).getDisplayName();
            }
            if (args.length == 1 &&
                    args[0].equalsIgnoreCase("reload")) {
                this.plugin.getConfigManager().reloadConfig();
                this.plugin.getConfigManager().reloadData();
                this.plugin.getConfigManager().reloadMessages();
                sender.sendMessage(Utils.chat(this.plugin, "&aLifeSteal config files have been reloaded successfully."));
                return true;
            }
            if (args.length == 2 &&
                    args[0].equalsIgnoreCase("get")) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(Utils.chat(this.plugin, "&cUsage: /lsadmin " + args[0] + " <player>"));
                    return true;
                }
                sender.sendMessage(Utils.chat(this.plugin, "&a" + target.getName() + " &ahas " + (this.plugin.getConfigManager().getData().getInt("health." + target.getUniqueId()) / 2) + " &ahearts."));
            }
            if (args.length == 3)
                if (args[0].equalsIgnoreCase("setHealth")) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                    if (target == null) {
                        sender.sendMessage(Utils.chat(this.plugin, "&cUsage: /lsadmin " + args[0] + " <player> <number>"));
                        return true;
                    }
                    double amount = 0.0D;
                    try {
                        amount = Double.parseDouble(args[2]);
                    } catch (NumberFormatException e) {
                        sender.sendMessage(Utils.chat(this.plugin, "&cUsage: /lsadmin " + args[0] + " <player> <number>"));
                        return true;
                    }
                    this.plugin.getConfigManager().getData().set("health." + target.getUniqueId(), Double.valueOf(amount));
                    this.plugin.getConfigManager().saveOtherData();
                    String display = target.getName();
                    if (target.isOnline()) {
                        Player online = (Player)target;
                        display = online.getDisplayName();
                        boolean isValid = false;
                        List<String> validWorlds = this.plugin.getConfigManager().getConfig().getStringList("onlyWorkIn");
                        for (String w : validWorlds) {
                            if (!online.getWorld().getName().equalsIgnoreCase(w)) {
                                isValid = false;
                                continue;
                            }
                            isValid = true;
                        }
                        if (isValid) {
                            online.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(amount);
                            online.sendMessage(Utils.chat(this.plugin, "&a" + name + " &ahas set your health to " + amount + "."));
                        }
                    }
                    Utils.sendConsole("&a" + name + " &ahas set " + display + "&a's health to " + amount + ".");
                    sender.sendMessage(Utils.chat(this.plugin, "&aYou've set " + display + "&a's health to " + amount + "."));
                } else if (args[0].equalsIgnoreCase("setMaxHealth")) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                    if (target == null) {
                        sender.sendMessage(Utils.chat(this.plugin, "&cUsage: /lsadmin " + args[0] + " <player> <number>"));
                        return true;
                    }
                    double amount = 0.0D;
                    try {
                        amount = Double.parseDouble(args[2]);
                    } catch (NumberFormatException e) {
                        sender.sendMessage(Utils.chat(this.plugin, "&cUsage: /lsadmin " + args[0] + " <player> <number>"));
                        return true;
                    }
                    this.plugin.getConfigManager().getData().set("maxHealth." + target.getUniqueId(), Double.valueOf(amount));
                    this.plugin.getConfigManager().saveOtherData();
                    String display = target.getName();
                    if (target.isOnline()) {
                        Player online = (Player)target;
                        display = online.getDisplayName();
                        boolean isValid = false;
                        List<String> validWorlds = this.plugin.getConfigManager().getConfig().getStringList("onlyWorkIn");
                        for (String w : validWorlds) {
                            if (!online.getWorld().getName().equalsIgnoreCase(w)) {
                                isValid = false;
                                continue;
                            }
                            isValid = true;
                        }
                        if (isValid)
                            online.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(amount);
                        online.sendMessage(Utils.chat(this.plugin, "&a" + name + " &ahas set your health to " + amount + "."));
                    }
                    Utils.sendConsole("&a" + name + " &ahas set " + display + "&a's health to " + amount + ".");
                    sender.sendMessage(Utils.chat(this.plugin, "&aYou've set " + display + "&a's health to " + amount + "."));
                }
        }
        return false;
    }
}
