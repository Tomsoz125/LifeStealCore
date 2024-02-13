package xyz.tomsoz.lifestealcore;

import jline.internal.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.tomsoz.lifestealcore.Commands.LifeSteal;
import xyz.tomsoz.lifestealcore.Commands.Withdraw;
import xyz.tomsoz.lifestealcore.Listeners.*;
import xyz.tomsoz.lifestealcore.Misc.ConfigManager;
import xyz.tomsoz.lifestealcore.Misc.CustomRecepies;
import xyz.tomsoz.lifestealcore.Misc.Utils;
import xyz.tomsoz.pluginbase.BaseSettings;
import xyz.tomsoz.pluginbase.PluginBase;
import xyz.tomsoz.pluginbase.PluginManager;

import java.util.ArrayList;
import java.util.List;

public final class LifeStealCore extends PluginBase {
    ConfigManager config = new ConfigManager(this);

    CustomRecepies recepies = new CustomRecepies(this);
    InteractEvent interactEvent;

    public void enable() {
        BaseSettings settings = new LifeStealCore.Settings();
        PluginManager.setBaseSettings(settings);

        this.interactEvent = new InteractEvent(this);
        this.config.initialize();
        Utils.sendConsole(Utils.chat(this, "&aConfiguration files have been initialised."));
        this.recepies.initialize();
        Utils.sendConsole(Utils.chat(this, "&aCrafting recepies have been initialised."));
        registerEvents();
        Utils.sendConsole(Utils.chat(this, "&aEvents have been initialised."));
        registerCommands();
        Utils.sendConsole(Utils.chat(this, "&aCommands have been initialised."));
        Utils.sendConsole(Utils.chat(this, "&a" + getDescription().getFullName() + " &7by&a " + String.join(", ", getDescription().getAuthors()) + " &7has successfully enabled."));
    }

    public void disable() {
        Utils.sendConsole(Utils.chat(this, "&c" + getDescription().getFullName() + " &7by&c " + String.join(", ", getDescription().getAuthors()) + " &7has successfully disabled."));
    }

    public ConfigManager getConfigManager() {
        return this.config;
    }

    public CustomRecepies getRecepies() {
        return this.recepies;
    }
    public InteractEvent getInteractEvent() {
        return interactEvent;
    }

    public void registerEvents() {
        registerEvent(new DeathEvent(this));
        registerEvent(new WorldChangeEvent(this));
        registerEvent(new CraftEvent(this));
        registerEvent(interactEvent);
        registerEvent(new JoinEvent(this));
        registerEvent(new DamageEvent(this));
        registerEvent(new RespawnEvent(this));
        registerEvent(new MobDeathEvent(this));
        registerEvent(new CommandPreprocessEvent(this));
        registerEvent(new InventoryOpen(this));
        registerEvent(new BlockBreak(this));
    }

    public void registerCommands() {
        registerCommand("lifesteal", new LifeSteal(this), new LifeSteal(this));
        registerCommand("withdraw", new Withdraw(this), null);
    }

    private void registerEvent(Listener event) {
        Bukkit.getPluginManager().registerEvents(event, this);
    }

    private void registerCommand(String command, CommandExecutor file, @Nullable TabCompleter tabCompleter) {
        if (tabCompleter == null) getCommand(command).setExecutor(file);
        else {
            PluginCommand cmd = getCommand(command);
            cmd.setExecutor(file);
            cmd.setTabCompleter(tabCompleter);
        }
    }


    private class Settings implements BaseSettings {
        @Override
        public String prefix() {
            return getConfigManager().getMessages().getString("prefix");
        }
    }

    public void setHealth(OfflinePlayer op, double hearts) {
        if (op.isOnline()) {
            Player p = (Player) op;
            if (Utils.isValidWorld(getConfigManager().getConfig(), p.getWorld()))
                p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(hearts);
        }

        getConfigManager().getData().set("maxHealth." + op.getUniqueId(), hearts);
        getConfigManager().saveOtherData();
    }

    public String addHealth(Player p, double hearts) {
        double newHealth = getHealth(p) + hearts;
        if (newHealth > getConfigManager().getConfig().getDouble("maxHealth")) {
            return "&cYou're over the maximum number of hearts.";
        }
        if (newHealth < getConfigManager().getConfig().getDouble("minHealth")) {
            return "&cYou're under the minumum number of hearts.";
        }
        setHealth(p, newHealth);
        return "&7Successfully added &a" + hearts + " &7heart(s) to your total.";
    }

    public String withdrawHealth(Player p, double hearts) {
        double newHealth = getHealth(p) - hearts;
        if (newHealth > getConfigManager().getConfig().getDouble("maxHealth")) {
            return "&cYou're over the maximum number of hearts.";
        }
        if (newHealth < getConfigManager().getConfig().getDouble("minHealth")) {
            return "&cYou're under the minumum number of hearts.";
        }
        setHealth(p, newHealth);
        return "&7Successfully removed &a" + hearts + " &7heart(s) from your total.";
    }

    public double getHealth(OfflinePlayer op) {
        if (op.isOnline() && Utils.isValidWorld(getConfigManager().getConfig(), ((Player) op).getWorld())) {
            Player p = (Player) op;
            if (p.getAttribute(Attribute.GENERIC_MAX_HEALTH) == null) {
                return 0;
            }
            return p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        } else {
            return getConfigManager().getData().getDouble("maxHealth." + op.getUniqueId());
        }
    }

    public ItemStack getHeartItem() {
        ItemStack heart = new ItemStack(Material.RED_DYE);
        ItemMeta meta = heart.getItemMeta();
        meta.setDisplayName(Utils.chatRaw("&c+1 Heart"));
        List<String> lore = new ArrayList<>();
        lore.add(Utils.chatRaw("&7Gives you an extra heart,"));
        lore.add(Utils.chatRaw("&7provided you aren't already"));
        lore.add(Utils.chatRaw("&7maxed out."));
        meta.setLore(lore);
        heart.setItemMeta(meta);

        return heart;
    }
}
