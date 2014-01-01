/*
 * Copyright (C) 2013-2014 Dabo Ross <http://www.daboross.net/>
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
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhereCommand extends NCommand {

    public WhereCommand(NCommonPlugin plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ColorList.DATA + "NonPlayer");
            return false;
        }
        if (args.length < 1) {
            sender.sendMessage(ColorList.DATA + "Too many arguments.");
            return false;
        }
        Player p = (Player) sender;
        Location loc = p.getLocation();
        sender.sendMessage(String.format(Messages.WHERE_CMD, args[0], loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getWorld().getName()));
        return true;
    }

    @Override
    public String getUsage() {
        return ColorList.REG + "Usage: " + ColorList.CMD + "/<command>";
    }

    @Override
    public String getDescription() {
        return "Gives your current position.";
    }
}
