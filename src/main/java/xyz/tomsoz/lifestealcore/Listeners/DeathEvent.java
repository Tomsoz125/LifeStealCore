package xyz.tomsoz.lifestealcore.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
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
        Player attacker = victim.getKiller();

        if (!Utils.isValidWorld(config, victim.getWorld())) return;
        if (!config.getBoolean("mobsAllowed") && attacker == null) {
            return;
        }

        ItemStack heart = new ItemStack(Material.RED_DYE);
        ItemMeta meta = heart.getItemMeta();
        meta.setDisplayName(Utils.chatRaw("&c+1 Heart"));
        List<String> lore = new ArrayList<>();
        lore.add(Utils.chatRaw("&7Gives you an extra heart,"));
        lore.add(Utils.chatRaw("&7provided you aren't already"));
        lore.add(Utils.chatRaw("&7maxed out."));
        meta.setLore(lore);
        heart.setItemMeta(meta);

        if (plugin.CLXManager != null && plugin.CLXManager.getDeathManager().wasPunishKilled(victim)) {
            attacker = plugin.lastDamager.get(victim.getUniqueId());
        }
        if (victim.hasPermission("lifesteal.exempt") || (attacker != null && attacker.hasPermission("lifesteal.exempt")))
            return;
        if (attacker != null && attacker.hasPermission("lifesteal.use")) {
            double current = attacker.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
            if (current < config.getDouble("maxHealth")) {
                double newVal = current + config.getDouble("addPerKill");
                attacker.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(newVal);
                attacker.sendMessage(Utils.chat(this.plugin, msgs.getString("healthAdded").replaceAll("%stolen%", config.getDouble("addPerKill") + "").replaceAll("%victim%", victim.getDisplayName()).replaceAll("%health%", attacker.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + "").replaceAll("%nl%", "\n" + msgs.getString("prefix"))));
            } else if (victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() > config.getDouble("minHealth")) {
                e.getDrops().add(heart);
                attacker.sendMessage(Utils.chat(this.plugin, msgs.getString("maxHealth").replaceAll("%max%", attacker.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + "").replaceAll("%nl%", "\n" + msgs.getString("prefix"))));
            }
        }
        if (victim.hasPermission("lifesteal.use")) {
            double current = victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
            if (current > config.getDouble("minHealth")) {
                if (attacker == null) {
                    victim.sendMessage(Utils.chat(this.plugin, msgs.getString("healthRemovedNoKiller").replaceAll("%nl%", "\n" + msgs.getString("prefix")).replaceAll("%lost%", config.getDouble("removePerDeath") + "").replaceAll("%health%", victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + "")));
                    e.getDrops().add(heart);
                    double newVal = current - config.getDouble("removePerDeath");
                    victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(newVal);
                } else if (attacker.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() >= config.getDouble("maxHealth")) {
                    victim.sendMessage(Utils.chat(this.plugin, msgs.getString("noHealthRemovedKillerMax").replaceAll("%nl%", "\n" + msgs.getString("prefix"))));
                    double newVal = current - config.getDouble("removePerDeath");
                    victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(newVal);
                } else {
                    double newVal = current - config.getDouble("removePerDeath");
                    victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(newVal);
                    victim.sendMessage(Utils.chat(this.plugin, msgs.getString("healthRemoved").replaceAll("%nl%", "\n" + msgs.getString("prefix")).replaceAll("%killer%", attacker.getDisplayName()).replaceAll("%stolen%", config.getDouble("removePerDeath") + "").replaceAll("%health%", victim.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + "")));
                }
            } else {
                if (config.getBoolean("banIfOutOfHearts")) {
                    if (victim.hasPermission("lifesteal.spectator")) {
                        victim.setGameMode(GameMode.SPECTATOR);
                        victim.sendMessage(Utils.chat(plugin, msgs.getString("userPutInSpec").replaceAll("%nl%", "\n" + msgs.getString("prefix"))));
                    } else {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), config.getString("banCommand").replaceAll("%eliminated%", victim.getName()));
                    }
                    List<String> banned = data.getStringList("bannedPlayers");
                    banned.add(victim.getUniqueId() + " : " + victim.getName());
                    data.set("bannedPlayers", banned);
                    plugin.getConfigManager().saveOtherData();
                    Bukkit.broadcastMessage(Utils.chatRaw(msgs.getString("bannedUser").replaceAll("%nl%", "\n" + msgs.getString("prefix")).replaceAll("%eliminated%", victim.getDisplayName())));
                } else {
                    victim.sendMessage(Utils.chat(plugin, msgs.getString("minHealth").replaceAll("%nl%", "\n" + msgs.getString("prefix"))));
                }
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
