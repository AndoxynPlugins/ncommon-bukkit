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
package net.daboross.bukkitdev.commoncommands;

import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.MetricsLite;

/**
 *
 * @author daboross
 */
public final class CommonCommands extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new GodItemFix(this), this);
        pm.registerEvents(new PlayerListener(this), this);
        for (Player p : Bukkit.getOnlinePlayers()) {
            GodItemFix.removeGodEnchants(p);
        }
        MetricsLite metrics = null;
        try {
            metrics = new MetricsLite(this);
        } catch (IOException ex) {
            getLogger().log(Level.WARNING, "Unable to create Metrics", ex);
        }
        if (metrics != null) {
            metrics.start();
        }
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("w")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ColorList.ERR + "Must be run by a player");
                return true;
            }

            Player p = (Player) sender;
            Location loc = p.getLocation();
            sender.sendMessage((args.length == 0 ? "" : ColorList.REG + "Server:" + ColorList.DATA + args[0] + ColorList.REG + ", ") + ColorList.DATA + loc.getBlockX() + ColorList.REG + ", "
                    + ColorList.DATA + loc.getBlockY() + ColorList.REG + ", "
                    + ColorList.DATA + loc.getBlockZ() + ColorList.REG + ", "
                    + ColorList.DATA + loc.getWorld().getName());
        } else if (cmd.getName().equalsIgnoreCase("wi")) {
            if (args.length == 0) {
                sender.sendMessage(ColorList.ERR + "Please specify a player");
                sender.sendMessage(ColorList.REG + "Usage: " + ColorList.CMD + "/" + label + ColorList.ARGS_SURROUNDER + " <" + ColorList.ARGS + "Player" + ColorList.ARGS_SURROUNDER + ">");
                return true;
            }
            Player player = Bukkit.getPlayerExact(args[0]);
            if (player == null) {
                sender.sendMessage(ColorList.ERR + "Player " + ColorList.ERR_ARGS + args[0] + ColorList.ERR + " not found");
                sender.sendMessage(ColorList.REG + "Usage: " + ColorList.CMD + "/" + label + ColorList.ARGS_SURROUNDER + " <" + ColorList.ARGS + "Player" + ColorList.ARGS_SURROUNDER + ">");
                return true;
            }
            Location loc = player.getLocation();
            sender.sendMessage(ColorList.NAME + player.getName() + ColorList.NAME + "'s Location: " + ColorList.DATA + (int) loc.getX() + ColorList.REG + ", "
                    + ColorList.DATA + (int) loc.getY() + ColorList.REG + ", "
                    + ColorList.DATA + (int) loc.getZ() + ColorList.REG + ", "
                    + ColorList.DATA + loc.getWorld().getName());
        } else {
            sender.sendMessage("Command unknown to CommonCommands: " + cmd.getName());
        }
        return true;
    }
}
