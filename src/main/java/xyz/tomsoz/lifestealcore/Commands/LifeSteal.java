package xyz.tomsoz.lifestealcore.Commands;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.tomsoz.lifestealcore.LifeStealCore;
import xyz.tomsoz.lifestealcore.Misc.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class LifeSteal implements CommandExecutor, TabCompleter {
    LifeStealCore plugin;

    public LifeSteal(LifeStealCore plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (label.equalsIgnoreCase("lifesteal") || label.equalsIgnoreCase("ls")) {
            if (args.length == 0) {
                sender.sendMessage(Utils.chat(this.plugin, "&a" + this.plugin.getDescription().getName() + " &7v&a" + this.plugin.getDescription().getVersion() + "&7 by &a" + (String) this.plugin.getDescription().getAuthors().get(0) + "&7.\nDownload here: &ahttps://www.spigotmc.org/resources/lifesteal.103378/&7!"));
                return true;
            } else if (args[0].equalsIgnoreCase("help")) {
                if (!plugin.hasPermission(sender, "lifesteal.admin")) {
                    sender.sendMessage(Utils.chat(this.plugin, "&cInsufficient permissions. If you believe this is an error please contact a server administrator."));
                    return true;
                }

                sender.sendMessage(Utils.chatRaw("&7&m-------&7[ &a&lLifeSteal &7]&m-------"));
                sender.sendMessage(Utils.chatRaw("&a/" + label.toLowerCase() + " help"));
                sender.sendMessage(Utils.chatRaw("&a/" + label.toLowerCase() + " reload"));
                sender.sendMessage(Utils.chatRaw("&a/" + label.toLowerCase() + " get <player>"));
                sender.sendMessage(Utils.chatRaw("&a/" + label.toLowerCase() + " revive <player>"));
                sender.sendMessage(Utils.chatRaw("&a/" + label.toLowerCase() + " set <player> <amount>"));
                sender.sendMessage(Utils.chatRaw("&a/" + label.toLowerCase() + " add <player> <amount>"));
                sender.sendMessage(Utils.chatRaw("&a/" + label.toLowerCase() + " remove <player> <amount>"));
                sender.sendMessage(Utils.chatRaw("&a/" + label.toLowerCase() + " give <player> <item> [quantity]"));
            }

            String name = "&c&lConsole";
            if (sender instanceof Player) {
                name = ((Player)sender).getDisplayName();
            }

            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                if (!plugin.hasPermission(sender, "lifesteal.reload")) {
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

            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("get")) {
                    if (!plugin.hasPermission(sender, "lifesteal.get")) {
                        sender.sendMessage(Utils.chat(this.plugin, "&cInsufficient permissions. If you believe this is an error please contact a server administrator."));
                        return true;
                    }

                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                    if (target == null) {
                        sender.sendMessage(Utils.chat(this.plugin, "&cUsage: /" + label.toLowerCase() + " " + args[0].toLowerCase() + " <player>"));
                        return true;
                    }

                    sender.sendMessage(Utils.chat(this.plugin, "&a" + target.getName() + " &ahas " + (plugin.getHealth(target) / 2) + " &ahearts."));
                } else if (args[0].equalsIgnoreCase("revive")) {
                    if (!plugin.hasPermission(sender, "lifesteal.revive")) {
                        sender.sendMessage(Utils.chat(this.plugin, "&cInsufficient permissions. If you believe this is an error please contact a server administrator."));
                        return true;
                    }

                    sender.sendMessage(Utils.chat(plugin, "Reviving..."));
                    OfflinePlayer banned = Bukkit.getOfflinePlayer(args[1]);
                    if (banned != null) {
                        List<String> bannedList = plugin.getConfigManager().getData().getStringList("bannedPlayers");
                        if (!bannedList.contains(banned.getName())) {
                            sender.sendMessage(Utils.chat(this.plugin, "&cThat user isn't dead."));
                            return true;
                        }
                        bannedList.remove((banned.getUniqueId() + " : " + banned.getName()));
                        plugin.getConfigManager().getData().set("bannedPlayers", bannedList);
                        plugin.getConfigManager().saveOtherData();
                        if (plugin.getConfigManager().getConfig().getBoolean("banIfOutOfHearts")) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), plugin.getConfigManager().getConfig().getString("unbanCommand").replaceAll("%eliminated%", banned.getName()));
                        }
                        Bukkit.broadcastMessage(Utils.chatRaw(plugin.getConfigManager().getMessages().getString("unbannedUser").replaceAll("%nl%", "\n" + plugin.getConfigManager().getMessages().getString("prefix")).replaceAll("%eliminated%", banned.getName())));
                        plugin.getConfigManager().getData().set("health." + ((Player) banned).getUniqueId(), Double.valueOf(plugin.getConfigManager().getConfig().getDouble("revivedHealth")));
                        this.plugin.getConfigManager().saveOtherData();
                        if (banned.isOnline()) {
                            ((Player) banned).setGameMode(GameMode.SURVIVAL);
                            ((Player) banned).teleport(((Player) banned).getBedSpawnLocation() == null ? ((Player) banned).getWorld().getSpawnLocation() : ((Player) banned).getBedSpawnLocation());
                            ((Player) banned).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(plugin.getConfigManager().getConfig().getDouble("revivedHealth"));

                        } else {
                            plugin.getConfigManager().getData().set("toSurvival." + banned.getUniqueId(), true);
                            plugin.getConfigManager().saveOtherData();
                        }
                    }
                }
            }

            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("set")) {
                    if (!plugin.hasPermission(sender, "lifesteal.set")) {
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
                    if (!plugin.hasPermission(sender, "lifesteal.add")) {
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
                    if (!plugin.hasPermission(sender, "lifesteal.remove")) {
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

            if (args.length >= 3) {
                if (!plugin.hasPermission(sender, "lifesteal.give")) {
                    sender.sendMessage(Utils.chat(this.plugin, "&cInsufficient permissions. If you believe this is an error please contact a server administrator."));
                    return true;
                }

                Player target = Bukkit.getPlayerExact(args[1]);
                if (target == null) {
                    sender.sendMessage(Utils.chat(this.plugin, "&cUsage: /" + label.toLowerCase() + " " + args[0].toLowerCase() + " <player> <item> [quantity]"));
                    return true;
                }

                ItemStack item;
                if (args[2].equalsIgnoreCase("HEART_FRAGMENT")) {
                    item = new ItemStack(Material.FERMENTED_SPIDER_EYE);
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(Utils.chatRaw("&cHeart Fragment"));
                    List<String> lore = new ArrayList<>();
                    lore.add(Utils.chatRaw("&7Combine 9 in a crafting"));
                    lore.add(Utils.chatRaw("&7table to get a heart."));
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                } else if (args[2].equalsIgnoreCase("ADD_HEART")) {
                    item = new ItemStack(Material.RED_DYE);
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(Utils.chatRaw("&c+1 Heart"));
                    List<String> lore = new ArrayList<>();
                    lore.add(Utils.chatRaw("&7Gives you an extra heart,"));
                    lore.add(Utils.chatRaw("&7provided you aren't already"));
                    lore.add(Utils.chatRaw("&7maxed out."));
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                } else if (args[2].equalsIgnoreCase("PLUS_MAX_HEART")) {
                    item = new ItemStack(Material.BLUE_GLAZED_TERRACOTTA);
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(Utils.chatRaw("&9+5 Max Hearts"));
                    List<String> lore = new ArrayList<>();
                    lore.add(Utils.chatRaw("&7Increases the total amount"));
                    lore.add(Utils.chatRaw("&7of hearts you can have."));
                    lore.add(Utils.chatRaw("&7Only " + plugin.getConfigManager().getConfig().getInt("plusMaxHeartLimit") + " can be used"));
                    lore.add(Utils.chatRaw("&7per player."));
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                } else if (args[2].equalsIgnoreCase("REVIVE_BOOK")) {
                    item = new ItemStack(Material.ENCHANTED_BOOK);
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(Utils.chatRaw("&6Revive Book"));
                    List<String> lore = new ArrayList<>();
                    lore.add(Utils.chatRaw("&7This item can bring"));
                    lore.add(Utils.chatRaw("&7a player back from the"));
                    lore.add(Utils.chatRaw("&7dead."));
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                } else {
                    sender.sendMessage(Utils.chat(plugin, "&cInvalid item!"));
                    return true;
                }
                int amount = 1;
                try {
                    amount = Integer.parseInt(args.length < 4 ? "1" : args[3]);
                } catch (NumberFormatException e) {
                }

                item.setAmount(amount);
                int stacks = (int) Math.floor((double) amount / 64);
                int remaining = amount % 64;
                List<ItemStack> failed = new ArrayList<>();
                for (int i = 0; i < stacks; i++) {
                    item.setAmount(64);
                    HashMap<Integer, ItemStack> map = target.getInventory().addItem(item);
                    failed.addAll(map.values());
                }
                if (remaining != 0) {
                    item.setAmount(remaining);
                    HashMap<Integer, ItemStack> map = target.getInventory().addItem(item);
                    failed.addAll(map.values());
                }
                if (failed.isEmpty()) {
                    sender.sendMessage(Utils.chat(plugin, "&7Successfully given &a" + target.getDisplayName() + "&a " + amount + "&7 of &a" + args[2] + "&7!"));
                } else {
                    if (failed.get(0).getAmount() == amount) {
                        sender.sendMessage(Utils.chat(plugin, "&c" + target.getDisplayName() + " &cdoesn't have space in their inventory!"));
                    } else {
                        sender.sendMessage(Utils.chat(plugin, "&7Successfully given &a" + target.getDisplayName() + "&a " + (amount - failed.get(0).getAmount()) + "&7 of &a" + args[2] + "&7!"));
                        sender.sendMessage(Utils.chat(plugin, "&c" + target.getDisplayName() + " &cwas only given " + (amount - failed.get(0).getAmount()) + " as they don't have enough space in their inventory."));
                    }
                }
                return true;
            }

            sender.sendMessage(Utils.chatRaw("&7&m-------&7[ &a&lLifeSteal &7]&m-------"));
            sender.sendMessage(Utils.chatRaw("&a/" + label.toLowerCase() + " help"));
            sender.sendMessage(Utils.chatRaw("&a/" + label.toLowerCase() + " reload"));
            sender.sendMessage(Utils.chatRaw("&a/" + label.toLowerCase() + " get <player>"));
            sender.sendMessage(Utils.chatRaw("&a/" + label.toLowerCase() + " set <player> <amount>"));
            sender.sendMessage(Utils.chatRaw("&a/" + label.toLowerCase() + " add <player> <amount>"));
            sender.sendMessage(Utils.chatRaw("&a/" + label.toLowerCase() + " remove <player> <amount>"));
            sender.sendMessage(Utils.chatRaw("&a/" + label.toLowerCase() + " give <player> <item> [quantity]"));
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
            if (p.hasPermission("lifesteal.revive")) completions.add("revive");
            if (p.hasPermission("lifesteal.give")) completions.add("give");
            if (p.hasPermission("lifesteal.admin")) completions.add("help");
        }

        if (strings.length >= 1) {
            if (strings[0].equalsIgnoreCase("reload")) return completions;
            else if (strings[0].equalsIgnoreCase("help")) return completions;
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
            } else if (strings[0].equalsIgnoreCase("revive") && p.hasPermission("lifesteal.revive")) {
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
            } else if (strings[0].equalsIgnoreCase("give") && p.hasPermission("lifesteal.give")) {
                if (strings.length == 2) {
                    completions.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()));
                } else if (strings.length == 3) {
                    completions.add("heart_fragment");
                    completions.add("add_heart");
                    completions.add("plus_max_heart");
                    completions.add("revive_book");
                } else if (strings.length == 4) {
                    for (int i = 0; i < 64; i++) {
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
