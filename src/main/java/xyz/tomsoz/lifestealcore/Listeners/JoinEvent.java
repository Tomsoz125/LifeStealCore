package xyz.tomsoz.lifestealcore.Listeners;

import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.tomsoz.lifestealcore.LifeStealCore;
import xyz.tomsoz.lifestealcore.Misc.Utils;

public class JoinEvent implements Listener {
    LifeStealCore plugin;

    public JoinEvent(LifeStealCore plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        FileConfiguration config = this.plugin.getConfigManager().getConfig();
        Player p = e.getPlayer();
        if (Utils.isValidWorld(config, p.getWorld())) {
            double getMaxHealth = this.plugin.getConfigManager().getData().getDouble("health." + p.getUniqueId());
            if (getMaxHealth != 0.0D)
                p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(getMaxHealth);
        }

        if (plugin.getConfigManager().getData().getBoolean("toSurvival." + p.getUniqueId())) {
            if (!p.hasPermission("lifesteal.exempt")) p.setGameMode(GameMode.SURVIVAL);
            p.sendMessage(Utils.chat(plugin, "&aYou have been revived!"));
            plugin.getConfigManager().getData().set("toSurvival." + p.getUniqueId(), null);
            plugin.getConfigManager().saveOtherData();
        }
    }
}
