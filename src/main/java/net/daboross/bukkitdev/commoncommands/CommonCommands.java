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

/**
 *
 * @author daboross
 */
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author daboross
 */
public final class CommonCommands extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new GodItemFix(this), this);
        for (Player p : Bukkit.getOnlinePlayers()) {
            GodItemFix.removeGodEnchants(p);
        }
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("list")) {
            Player[] onlinePlayers = Bukkit.getServer().getOnlinePlayers();
            if (onlinePlayers.length == 0) {
                sender.sendMessage(ColorList.REG + "There are no players online.");
            } else if (onlinePlayers.length == 1) {
                sender.sendMessage(ColorList.REG + "There is one player online:");
                sender.sendMessage(ColorList.NAME + onlinePlayers[0].getDisplayName());
            } else {
                sender.sendMessage(ColorList.TOP + "There are " + ColorList.DATA + onlinePlayers.length + ColorList.TOP + " players online:");
                StringBuilder messageBuilder = new StringBuilder(ColorList.NAME).append(onlinePlayers[0].getDisplayName());
                for (int i = 1; i < onlinePlayers.length; i++) {
                    messageBuilder.append(ColorList.DIVIDER).append(", ").append(ColorList.NAME).append(onlinePlayers[i].getDisplayName());
                }
                sender.sendMessage(messageBuilder.toString());
            }
        } else if (cmd.getName().equalsIgnoreCase("w")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ColorList.ERR + "Must be run by a player");
                return true;
            }
            Player p = (Player) sender;
            Location loc = p.getLocation();
            sender.sendMessage(ColorList.DATA + (int) loc.getX() + ColorList.REG + ", "
                    + ColorList.DATA + (int) loc.getY() + ColorList.REG + ", "
                    + ColorList.DATA + (int) loc.getZ() + ColorList.REG + ", "
                    + ColorList.DATA + loc.getWorld().getName());
        } else if (cmd.getName().equalsIgnoreCase("wi")) {
            if (args.length == 0) {
                sender.sendMessage(ColorList.ERR + "Please specify a player");
                sender.sendMessage(ColorList.REG + "Usage: " + ColorList.CMD + "/" + label + ColorList.ARGS_SURROUNDER + " <" + ColorList.ARGS + "Player" + ColorList.ARGS_SURROUNDER + ">");
                return true;
            }
            Player player = null;
            String lowerCaseArg = args[0].toLowerCase();
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getName().toLowerCase().contains(lowerCaseArg) || ChatColor.stripColor(p.getDisplayName()).toLowerCase().contains(lowerCaseArg)) {
                    player = p;
                    break;
                }
            }
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
