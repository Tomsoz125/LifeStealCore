package xyz.tomsoz.lifestealcore.Events;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.tomsoz.lifestealcore.LifeStealCore;
import xyz.tomsoz.lifestealcore.Misc.Utils;

import java.util.ArrayList;
import java.util.List;

public class DeathEvent implements Listener {
    LifeStealCore plugin;

    public DeathEvent(LifeStealCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onDeath(PlayerDeathEvent e) {
        FileConfiguration config = this.plugin.getConfigManager().getConfig();
        FileConfiguration msgs = this.plugin.getConfigManager().getMessages();
        FileConfiguration data = this.plugin.getConfigManager().getData();
        Player victim = e.getEntity();
        boolean isValid = true;
        List<String> validWorlds = config.getStringList("onlyWorkIn");
        for (String w : validWorlds) {
            if (!victim.getWorld().getName().equalsIgnoreCase(w)) {
                isValid = false;
                continue;
            }
            isValid = true;
        }
        if (!isValid)
            return;
        ItemStack heart = new ItemStack(Material.RED_DYE);
        ItemMeta meta = heart.getItemMeta();
        meta.setDisplayName(Utils.chatRaw("&c+1 Heart"));
        List<String> lore = new ArrayList<>();
        lore.add(Utils.chatRaw("&7Gives you an extra heart,"));
        lore.add(Utils.chatRaw("&7provided you aren't already"));
        lore.add(Utils.chatRaw("&7maxed out."));
        meta.setLore(lore);
        heart.setItemMeta(meta);
        Player attacker = victim.getKiller();
        if (attacker != null && attacker.hasPermission(config.getString("heartChangePerm"))) {
            double current = attacker.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
            if (current < config.getDouble("maxHealth")) {
                if (victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() <= config.getDouble("minHealth")) {
                    attacker.sendMessage(Utils.chat(this.plugin, msgs.getString("noHealthAddedVictimMin")));
                } else {
                    double newVal = current + config.getDouble("addPerKill");
                    attacker.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(newVal);
                    attacker.sendMessage(Utils.chat(this.plugin, msgs.getString("healthAdded").replaceAll("%stolen%", config.getDouble("addPerKill") + "").replaceAll("%victim%", victim.getDisplayName()).replaceAll("%health%", attacker.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + "")));
                }
            } else if (victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() > config.getDouble("minHealth")) {
                e.getDrops().add(heart);
                attacker.sendMessage(Utils.chat(this.plugin, msgs.getString("maxHealth").replaceAll("%max%", attacker.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + "")));
            }
        }
        if (victim.hasPermission(config.getString("heartChangePerm"))) {
            double current = victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
            if (current > config.getDouble("minHealth")) {
                if (attacker == null) {
                    victim.sendMessage(Utils.chat(this.plugin, msgs.getString("healthRemovedNoKiller").replaceAll("%lost%", config.getDouble("removePerDeath") + "").replaceAll("%health%", victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + "")));
                    e.getDrops().add(heart);
                    double newVal = current - config.getDouble("removePerDeath");
                    victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(newVal);
                } else if (attacker.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() >= config.getDouble("maxHealth")) {
                    victim.sendMessage(Utils.chat(this.plugin, msgs.getString("noHealthRemovedKillerMax")));
                    double newVal = current - config.getDouble("removePerDeath");
                    victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(newVal);
                } else {
                    double newVal = current - config.getDouble("removePerDeath");
                    victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(newVal);
                    victim.sendMessage(Utils.chat(this.plugin, msgs.getString("healthRemoved").replaceAll("%killer%", attacker.getDisplayName()).replaceAll("%stolen%", config.getDouble("removePerDeath") + "").replaceAll("%health%", victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + "")));
                }
            } else {
                victim.sendMessage(Utils.chat(this.plugin, msgs.getString("minHealth")));
            }
        }
        data.set("health." + victim.getUniqueId(), Double.valueOf(victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()));
        this.plugin.getConfigManager().saveOtherData();
        if (attacker != null) {
            data.set("health." + attacker.getUniqueId(), Double.valueOf(attacker.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()));
            this.plugin.getConfigManager().saveOtherData();
        }
    }
}
