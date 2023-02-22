package xyz.tomsoz.lifestealcore.Events;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import xyz.tomsoz.lifestealcore.LifeStealCore;

import java.util.List;

public class RespawnEvent implements Listener {
    LifeStealCore plugin;

    public RespawnEvent(LifeStealCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        boolean isValid = true;
        List<String> validWorlds = this.plugin.getConfigManager().getConfig().getStringList("onlyWorkIn");
        for (String w : validWorlds) {
            if (!e.getRespawnLocation().getWorld().getName().equalsIgnoreCase(w)) {
                isValid = false;
                continue;
            }
            isValid = true;
        }
        if (isValid) {
            this.plugin.getConfigManager().getData().set("currentHealth." + p.getUniqueId(), Double.valueOf(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()));
            this.plugin.getConfigManager().saveOtherData();
        }
    }
}
