package xyz.tomsoz.lifestealcore.Events;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import xyz.tomsoz.lifestealcore.LifeStealCore;

import java.util.List;

public class JoinEvent implements Listener {
    LifeStealCore plugin;

    public JoinEvent(LifeStealCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        boolean isValid = true;
        List<String> validWorlds = this.plugin.getConfigManager().getConfig().getStringList("onlyWorkIn");
        for (String w : validWorlds) {
            if (!p.getWorld().getName().equalsIgnoreCase(w)) {
                isValid = false;
                continue;
            }
            isValid = true;
        }
        if (isValid) {
            double getMaxHealth = this.plugin.getConfigManager().getData().getDouble("health." + p.getUniqueId());
            if (getMaxHealth != 0.0D)
                p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(getMaxHealth);
            return;
        }
    }
}
