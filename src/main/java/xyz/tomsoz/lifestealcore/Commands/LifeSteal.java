package xyz.tomsoz.lifestealcore.Commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.tomsoz.lifestealcore.LifeStealCore;
import xyz.tomsoz.lifestealcore.Misc.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LifeSteal implements CommandExecutor, TabCompleter {
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
                } else if (args[0].equalsIgnoreCase("add")) {
                    if (!sender.hasPermission("lifesteal.add")) {
                        sender.sendMessage(Utils.chat(this.plugin, "&cInsufficient permissions. If you believe this is an error please contact a server administrator."));
                        return true;
                    }

                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                    if (target == null) {
                        sender.sendMessage(Utils.chat(this.plugin, "&cUsage: /" + label.toLowerCase() + " " + args[0].toLowerCase() + " <player> <number>"));
                        return true;
                    }

                    double amount = plugin.getHealth(target);
                    try {
                        amount += Double.parseDouble(args[2]);
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
                } else if (args[0].equalsIgnoreCase("remove")) {
                    if (!sender.hasPermission("lifesteal.remove")) {
                        sender.sendMessage(Utils.chat(this.plugin, "&cInsufficient permissions. If you believe this is an error please contact a server administrator."));
                        return true;
                    }

                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                    if (target == null) {
                        sender.sendMessage(Utils.chat(this.plugin, "&cUsage: /" + label.toLowerCase() + " " + args[0].toLowerCase() + " <player> <number>"));
                        return true;
                    }

                    double amount = plugin.getHealth(target);
                    try {
                        amount -= Double.parseDouble(args[2]);
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

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> completions = new ArrayList<>();
        if (!(commandSender instanceof Player)) return completions;
        Player p = (Player) commandSender;

        if (strings.length == 1) {
            if (p.hasPermission("lifesteal.reload")) completions.add("reload");
            if (p.hasPermission("lifesteal.set")) completions.add("set");
            if (p.hasPermission("lifesteal.get")) completions.add("get");
        }

        if (strings.length >= 1) {
            if (strings[0].equalsIgnoreCase("reload")) return completions;
            else if (strings[0].equalsIgnoreCase("set") && p.hasPermission("lifesteal.set")) {
                if (strings.length == 2) {
                    completions.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()));
                } else if (strings.length == 3) {
                    double maxHealth = plugin.getConfigManager().getConfig().getDouble("maxHealth");
                    for (int i = 0; i < maxHealth; i++) {
                        completions.add(i + 1 + "");
                    }
                }
            } else if (strings[0].equalsIgnoreCase("get") && p.hasPermission("lifesteal.get")) {
                if (strings.length == 2) {
                    completions.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()));
                }
            } else if (strings[0].equalsIgnoreCase("add") && p.hasPermission("lifesteal.add")) {
                if (strings.length == 2) {
                    completions.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()));
                } else if (strings.length == 3) {
                    double maxHealth = plugin.getConfigManager().getConfig().getDouble("maxHealth");
                    for (int i = 0; i < (maxHealth - plugin.getHealth(p)); i++) {
                        completions.add(i + 1 + "");
                    }
                }
            } else if (strings[0].equalsIgnoreCase("remove") && p.hasPermission("lifesteal.remove")) {
                if (strings.length == 2) {
                    completions.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()));
                } else if (strings.length == 3) {
                    double minHealth = plugin.getConfigManager().getConfig().getDouble("minHealth");
                    for (int i = 0; i < (minHealth - plugin.getHealth(p)); i++) {
                        completions.add(i + 1 + "");
                    }
                }
            }
        }

        List<String> list = new ArrayList<>();
        for (String c : completions) {
            if (c.startsWith(strings[strings.length - 1])) list.add(c);
        }
        return list;
    }
}
