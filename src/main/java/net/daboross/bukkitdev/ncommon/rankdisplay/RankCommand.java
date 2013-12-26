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
package net.daboross.bukkitdev.ncommon.rankdisplay;

import net.daboross.bukkitdev.commandexecutorbase.ColorList;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class RankCommand implements CommandExecutor {

    private final Permission permission;

    public RankCommand(Permission permission) {
        this.permission = permission;
    }

    public void setup(JavaPlugin plugin) {
        PluginCommand rank = plugin.getCommand("rank");
        if (rank != null) {
            rank.setExecutor(this);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (permission == null) {
            sender.sendMessage(ColorList.ERR + "Permissions not found");
        }
        if (args.length == 0) {
            sender.sendMessage(ColorList.REG + "You are currently " + ColorList.DATA + getRankName(sender));
        } else if (args.length != 1) {
            sender.sendMessage(ColorList.ERR + "To many Arguments");
            return false;
        } else {
            Player player = getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage(ColorList.ERR + "Player " + ColorList.NAME + args[0] + ColorList.ERR + " not found");
            } else {
                sender.sendMessage(ColorList.NAME + player.getName() + ColorList.REG + " is currently " + getRankName(player));
            }
        }
        return true;
    }

    private Player getPlayer(String name) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getName().toLowerCase().contains(name.toLowerCase())
                    || ChatColor.stripColor(p.getDisplayName()).toLowerCase().contains(name.toLowerCase())) {
                return p;
            }
        }
        return null;
    }

    private String getRankName(CommandSender sender) {
        if (sender instanceof Player) {
            String[] ranks = permission.getPlayerGroups((String) null, sender.getName());
            if (ranks.length == 0) {
                return ColorList.DATA + "None";
            }
            if (ranks.length == 1) {
                return ColorList.DATA + ranks[0];
            }
            StringBuilder builder = new StringBuilder(ColorList.DATA).append(ranks[0]);
            for (int i = 1; i < ranks.length; i++) {
                builder.append(ColorList.REG).append(", ").append(ColorList.DATA).append(ranks[i]);
            }
            return builder.toString();
        } else {
            return ColorList.DATA + "NonPlayer";
        }
    }
}
