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

import java.util.List;
import net.daboross.bukkitdev.commandexecutorbase.ColorList;
import net.daboross.bukkitdev.ncommon.NCommonPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;

public abstract class NCommand implements CommandExecutor, TabCompleter {

    protected final NCommonPlugin plugin;

    public NCommand(NCommonPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerIfExists(PluginCommand command) {
        if (command == null) {
            return;
        }
        command.setExecutor(this);
        command.setTabCompleter(this);
        command.setPermission(this.getPermission());
        command.setPermissionMessage(this.getPermissionDeniedMessage());
        command.setUsage(this.getUsage());
        command.setDescription(this.getDescription());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("NCommand - default response for - " + command.getName());
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }

    public String getPermissionDeniedMessage() {
        return ColorList.ERR + "You do not have permission to execute this command.";
    }

    public String getPermission() {
        return null;
    }

    public String getUsage() {
        return "/<command> - default usage";
    }

    public String getDescription() {
        return "NCommand default description";
    }
}
