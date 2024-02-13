package xyz.tomsoz.lifestealcore.Listeners;

import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import xyz.tomsoz.lifestealcore.LifeStealCore;
import xyz.tomsoz.lifestealcore.Misc.Utils;

public class RespawnEvent implements Listener {
    LifeStealCore plugin;

    public RespawnEvent(LifeStealCore plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        FileConfiguration config = this.plugin.getConfigManager().getConfig();
        Player p = e.getPlayer();

        if (Utils.isValidWorld(config, e.getRespawnLocation().getWorld())) {
            this.plugin.getConfigManager().getData().set("currentHealth." + p.getUniqueId(), Double.valueOf(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()));
            this.plugin.getConfigManager().saveOtherData();
        }
    }
}
