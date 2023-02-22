package xyz.tomsoz.lifestealcore.Misc;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import xyz.tomsoz.lifestealcore.LifeStealCore;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class ConfigManager {
    LifeStealCore plugin;

    File configFile;

    FileConfiguration configCfg;

    File dataFile;

    FileConfiguration dataCfg;

    File messagesFile;

    FileConfiguration messagesCfg;

    public ConfigManager(LifeStealCore plugin) {
        this.plugin = plugin;
    }

    public void initialize() {
        saveConfig();
        saveData();
        saveMessages();
    }

    public FileConfiguration getConfig() {
        return this.configCfg;
    }

    public FileConfiguration getData() {
        return this.dataCfg;
    }

    public FileConfiguration getMessages() {
        return this.messagesCfg;
    }

    public void saveOtherData() {
        try {
            this.dataCfg.save(this.dataFile);
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.WARNING, Utils.chat(this.plugin, "&cCouldn't save data.yml!\nIf this persists contact the plugin developer."));
        }
    }

    public void saveData() {
        try {
            if (this.dataFile == null)
                this.dataFile = new File(this.plugin.getDataFolder(), "data.yml");
            if (!this.dataFile.exists())
                this.plugin.saveResource("data.yml", false);
            this.dataCfg = (FileConfiguration) YamlConfiguration.loadConfiguration(this.dataFile);
            this.dataCfg.save(this.dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            if (this.configFile == null)
                this.configFile = new File(this.plugin.getDataFolder(), "config.yml");
            if (!this.configFile.exists())
                this.plugin.saveResource("config.yml", false);
            this.configCfg = (FileConfiguration)YamlConfiguration.loadConfiguration(this.configFile);
            this.configCfg.save(this.configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveMessages() {
        if (this.messagesFile == null)
            this.messagesFile = new File(this.plugin.getDataFolder(), "messages.yml");
        if (!this.messagesFile.exists())
            this.plugin.saveResource("messages.yml", false);
        this.messagesCfg = (FileConfiguration)YamlConfiguration.loadConfiguration(this.messagesFile);
    }

    public void reloadConfig() {
        this.configCfg = (FileConfiguration)YamlConfiguration.loadConfiguration(this.configFile);
    }

    public void reloadData() {
        this.dataCfg = (FileConfiguration)YamlConfiguration.loadConfiguration(this.dataFile);
    }

    public void reloadMessages() {
        this.messagesCfg = (FileConfiguration)YamlConfiguration.loadConfiguration(this.messagesFile);
    }
}
