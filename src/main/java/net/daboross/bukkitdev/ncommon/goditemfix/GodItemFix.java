/*
 * Copyright (C) 2013 Dabo Ross <http://www.daboross.net/>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.daboross.bukkitdev.ncommon.goditemfix;

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
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author daboross
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
        Bukkit.getScheduler().runTaskLater(plugin, new GodItemFixRunnable(this, evt.getPlayer()), 20);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInventoryCreative(InventoryCreativeEvent evt) {
        removeGodEnchants(evt.getCurrentItem(), evt.getWhoClicked().getName());
    }

    public void removeGodEnchants(Player p) {
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

    public boolean removeGodEnchants(ItemStack it, String playerName) {
        if (it != null && it.getEnchantments().size() > 0 && it.getType() != Material.AIR) {
            boolean changed = false;
            for (Map.Entry<Enchantment, Integer> entry : it.getEnchantments().entrySet()) {
                Enchantment e = entry.getKey();
                if (entry.getValue() > e.getMaxLevel() || !e.canEnchantItem(it)) {
                    String message;
                    if (e.canEnchantItem(it)) {
                        it.addEnchantment(e, e.getMaxLevel());
                        message = String.format("Changed level of enchantment %s from %s to %s on item %s in inventory of %s", e.getName(), entry.getValue(), e.getMaxLevel(), it.getType(), playerName);
                    } else {
                        it.removeEnchantment(e);
                        message = String.format("Removed enchantment %s level %s on item %s in inventory of %s", e.getName(), entry.getValue(), it.getType(), playerName);
                    }
                    try {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mail send daboross " + message);
                    } catch (CommandException ce) {
                        plugin.getLogger().log(Level.INFO, "Command error", ce);
                    }
                    plugin.getLogger().log(Level.INFO, message);
                    changed = true;
                }
            }
            return changed;
        }
        return false;
    }
}
