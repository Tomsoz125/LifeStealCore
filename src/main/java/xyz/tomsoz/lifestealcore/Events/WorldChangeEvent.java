package xyz.tomsoz.lifestealcore.Events;

import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import xyz.tomsoz.lifestealcore.LifeStealCore;

import java.util.List;

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
        boolean isValid = true;
        boolean isTransferredValid = true;
        List<String> validWorlds = config.getStringList("onlyWorkIn");
        for (String w : validWorlds) {
            if (!e.getPlayer().getWorld().getName().equalsIgnoreCase(w)) {
                isValid = false;
                continue;
            }
            isValid = true;
        }
        for (String w : validWorlds) {
            if (!e.getFrom().getName().equalsIgnoreCase(w)) {
                isTransferredValid = false;
                continue;
            }
            isTransferredValid = true;
        }
        Player p = e.getPlayer();
        if (!isValid) {
            if (isTransferredValid) {
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
