package xyz.tomsoz.lifestealcore;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.tomsoz.lifestealcore.Commands.LSAdmin;
import xyz.tomsoz.lifestealcore.Commands.TransferHeart;
import xyz.tomsoz.lifestealcore.Events.*;
import xyz.tomsoz.lifestealcore.Misc.ConfigManager;
import xyz.tomsoz.lifestealcore.Misc.CustomRecepies;
import xyz.tomsoz.lifestealcore.Misc.Utils;

public final class LifeStealCore extends JavaPlugin {
    ConfigManager config = new ConfigManager(this);

    CustomRecepies recepies = new CustomRecepies(this);

    public void onEnable() {
        this.config.initialize();
        this.recepies.initialize();
        registerEvents();
        registerCommands();
        Utils.sendConsole(Utils.chat(this, "&a" + getDescription().getFullName() + " v" + getDescription().getVersion() + " &7by&a " + String.join(", ", getDescription().getAuthors()) + " &7has successfully enabled."));
    }

    public void onDisable() {
        Utils.sendConsole(Utils.chat(this, "&c" + getDescription().getFullName() + " v" + getDescription().getVersion() + " &7by&c " + String.join(", ", getDescription().getAuthors()) + " &7has successfully disabled."));
    }

    public ConfigManager getConfigManager() {
        return this.config;
    }

    public CustomRecepies getRecepies() {
        return this.recepies;
    }

    public void registerEvents() {
        registerEvent(new DeathEvent(this));
        registerEvent(new WorldChangeEvent(this));
        registerEvent(new CraftEvent(this));
        registerEvent(new InteractEvent(this));
        registerEvent(new JoinEvent(this));
        registerEvent(new DamageEvent(this));
        registerEvent(new RespawnEvent(this));
        registerEvent(new MobDeathEvent(this));
    }

    public void registerCommands() {
        registerCommand("lsadmin", new LSAdmin(this));
        registerCommand("transferheart", new TransferHeart(this));
    }

    private void registerEvent(Listener event) {
        Bukkit.getPluginManager().registerEvents(event, (Plugin)this);
    }

    private void registerCommand(String command, CommandExecutor file) {
        getCommand(command).setExecutor(file);
    }
}
