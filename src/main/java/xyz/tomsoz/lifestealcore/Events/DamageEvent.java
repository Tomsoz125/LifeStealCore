package xyz.tomsoz.lifestealcore.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import xyz.tomsoz.lifestealcore.LifeStealCore;
import xyz.tomsoz.lifestealcore.Misc.Utils;

import java.util.List;

public class DamageEvent implements Listener {
    LifeStealCore plugin;

    public DamageEvent(LifeStealCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player)e.getEntity();
            if (Utils.isValidWorld(plugin.getConfigManager().getConfig(), p.getWorld())) {
                double current = p.getHealth();
                if (current > 0.0D) {
                    this.plugin.getConfigManager().getData().set("currentHealth." + p.getUniqueId(), Double.valueOf(current));
                    this.plugin.getConfigManager().saveOtherData();
                }
            }
        }
    }

    @EventHandler
    public void onRegen(EntityRegainHealthEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player)e.getEntity();
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
                double current = p.getHealth();
                if (current > 0.0D) {
                    this.plugin.getConfigManager().getData().set("currentHealth." + p.getUniqueId(), Double.valueOf(current));
                    this.plugin.getConfigManager().saveOtherData();
                }
            }
        }
    }
}
