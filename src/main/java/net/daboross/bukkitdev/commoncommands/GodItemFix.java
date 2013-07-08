/*
 * Author: Dabo Ross (Based off of MasterGabeMod's work)
 * Website: www.daboross.net
 * Email: daboross@daboross.net
 */
package net.daboross.bukkitdev.commoncommands;

import java.util.Map;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandException;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author daboross (based off of MasterGabeMod's work)
 */
public class GodItemFix implements Listener {

    private final Plugin plugin;

    public GodItemFix(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent evt) {
        removeGodEnchants(evt.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onWorldChange(PlayerChangedWorldEvent evt) {
        Bukkit.getScheduler().runTaskLater(plugin, new GodItemFixRunnable(evt.getPlayer()), 20);
    }

    public static void removeGodEnchants(Player p) {
        String playerName = p.getName();
        for (ItemStack it : p.getInventory().getArmorContents()) {
            if (removeGodEnchants(it, playerName)) {
                p.sendMessage("Your " + it.getType().toString() + " has been fixed!");
            }
        }
        for (ItemStack it : p.getInventory().getContents()) {
            if (removeGodEnchants(it, playerName)) {
                p.sendMessage("Your " + it.getType().toString() + " has been fixed!");
            }
        }
    }

    public static boolean removeGodEnchants(ItemStack it, String playerName) {
        if (it != null && it.getEnchantments().size() > 0 && it.getType() != Material.AIR) {
            boolean changed = false;
            for (Map.Entry<Enchantment, Integer> entry : it.getEnchantments().entrySet()) {
                Enchantment e = entry.getKey();
                if (entry.getValue() > e.getMaxLevel() || !e.canEnchantItem(it)) {
                    String message;
                    if (e.canEnchantItem(it)) {
                        it.addEnchantment(e, e.getMaxLevel());
                        message = String.format("Changed level of enchantment %s from %s to %s on item %s in inventory of %s", e.getName(), entry.getValue(), e.getMaxLevel(), it.getType().toString(), playerName);
                    } else {
                        it.removeEnchantment(e);
                        message = String.format("Removed enchantment %s level %s on item %s in inventory of %s", e.getName(), entry.getValue(), it.getType().toString(), playerName);
                    }
                    try {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mail send daboross " + message);
                    } catch (CommandException ce) {
                        Bukkit.getLogger().log(Level.INFO, "[GodItemFix] Command Error.", ce);
                    }
                    Bukkit.getLogger().log(Level.INFO, "[GodItemFix] {0}", message);
                    changed = true;
                }
            }
            return changed;
        }
        return false;
    }

    private static class GodItemFixRunnable implements Runnable {

        private final Player p;

        private GodItemFixRunnable(Player p) {
            this.p = p;
        }

        @Override
        public void run() {
            removeGodEnchants(p);
        }
    }
}