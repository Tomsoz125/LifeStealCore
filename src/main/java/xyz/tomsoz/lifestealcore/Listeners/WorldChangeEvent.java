package xyz.tomsoz.lifestealcore.Listeners;

import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import xyz.tomsoz.lifestealcore.LifeStealCore;
import xyz.tomsoz.lifestealcore.Misc.Utils;

public class WorldChangeEvent implements Listener {
    LifeStealCore plugin;

    public WorldChangeEvent(LifeStealCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        FileConfiguration config = this.plugin.getConfigManager().getConfig();
        FileConfiguration msgs = this.plugin.getConfigManager().getMessages();
        FileConfiguration data = this.plugin.getConfigManager().getData();

        Player p = e.getPlayer();
        if (plugin.getConfigManager().getData().getBoolean("toSurvival." + p.getUniqueId()) && Utils.isValidWorld(config, p.getWorld())) {
            if (!p.hasPermission("lifesteal.exempt")) p.setGameMode(GameMode.SURVIVAL);
            p.sendMessage(Utils.chat(plugin, "&aYou have been revived!"));
            plugin.getConfigManager().getData().set("toSurvival." + p.getUniqueId(), null);
            plugin.getConfigManager().saveOtherData();
        }

        if (!Utils.isValidWorld(config, e.getPlayer().getWorld())) {
            if (Utils.isValidWorld(config, e.getFrom())) {
                data.set("health." + p.getUniqueId(), Double.valueOf(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()));
                this.plugin.getConfigManager().saveOtherData();
            }
            p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20.0D);
            p.setHealth(20.0D);
            return;
        }
        if (data.getDouble("health." + p.getUniqueId()) == 0.0D) {
            data.set("health." + p.getUniqueId(), Double.valueOf(20.0D));
            this.plugin.getConfigManager().saveOtherData();
            p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20.0D);
        } else {
            double health = data.getDouble("health." + p.getUniqueId());
            p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
            double currentHealth1 = data.getDouble("currentHealth." + p.getUniqueId());
            if (currentHealth1 > 0.0D)
                p.setHealth(currentHealth1);
        }
    }
}
