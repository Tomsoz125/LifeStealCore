package xyz.tomsoz.lifestealcore.Commands;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import xyz.tomsoz.lifestealcore.LifeStealCore;
import xyz.tomsoz.lifestealcore.Misc.Utils;

import java.util.ArrayList;
import java.util.List;

public class TransferHeart implements CommandExecutor {
    LifeStealCore plugin;

    public TransferHeart(LifeStealCore plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (label.equalsIgnoreCase("transferheart")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.chat(this.plugin, "&cThis is a player command."));
                return true;
            }
            Player p = (Player)sender;
            if (p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() <= this.plugin.getConfigManager().getConfig().getDouble("minHealth")) {
                p.sendMessage(Utils.chat(this.plugin, "&cYou don't have enough hearts for this."));
                return true;
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
            boolean canFit = false;
            for (ItemStack item : p.getInventory().getContents()) {
                if (item != null) {
                    heart.setAmount(item.getAmount());
                    if (item.equals(heart) &&
                            item.getAmount() < 64)
                        canFit = true;
                }
            }
            heart.setAmount(1);
            if (p.getInventory().firstEmpty() != -1)
                canFit = true;
            if (canFit) {
                p.getInventory().addItem(new ItemStack[] { heart });
                p.sendMessage(Utils.chat(this.plugin, "&aA heart has been transferred."));
                double newVal = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() - this.plugin.getConfigManager().getConfig().getDouble("removePerDeath");
                p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(newVal);
                this.plugin.getConfigManager().getData().set("health." + p.getUniqueId(), Double.valueOf(newVal));
                this.plugin.getConfigManager().saveOtherData();
            } else {
                p.sendMessage(Utils.chat(this.plugin, "&cYou can't fit it in your inventory."));
            }
            return true;
        }
        return false;
    }
}
