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
package net.daboross.bukkitdev.ncommon.commands;

import net.daboross.bukkitdev.commandexecutorbase.ColorList;
import net.daboross.bukkitdev.ncommon.Messages;
import net.daboross.bukkitdev.ncommon.NCommonPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhereIsCommand extends NCommand {

    public static final String USAGE = ColorList.REG + "Usage: " + ColorList.CMD + "/<command>" + ColorList.ARGS_SURROUNDER + " <" + ColorList.ARGS + "Player" + ColorList.ARGS_SURROUNDER + ">";

    public WhereIsCommand(NCommonPlugin plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ColorList.ERR + "Please specify a player");
            return false;
        }
        Player player = Bukkit.getPlayerExact(args[0]);
        if (player == null) {
            sender.sendMessage(ColorList.ERR + "Player " + ColorList.ERR_ARGS + args[0] + ColorList.ERR + " not found");
            return false;
        }
        Location loc = player.getLocation();
        sender.sendMessage(String.format(Messages.WHERE_IS_CMD, player.getName(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getWorld().getName()));
        return true;
    }

    @Override
    public String getUsage() {
        return USAGE;
    }

    @Override
    public String getDescription() {
        return "Gives the position of a given player.";
    }
}
