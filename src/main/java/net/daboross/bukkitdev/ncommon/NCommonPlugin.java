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
package net.daboross.bukkitdev.ncommon;

import net.daboross.bukkitdev.ncommon.commands.WhereCommand;
import net.daboross.bukkitdev.ncommon.commands.WhereIsCommand;
import net.daboross.bukkitdev.ncommon.goditemfix.GodItemFix;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author daboross
 */
public final class NCommonPlugin extends JavaPlugin {

    private final MessageFormats formats = MessageFormats.DEFAULT;

    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new GodItemFix(this), this);
        pm.registerEvents(new JoinMessageListener(this), this);
        new WhereIsCommand(this).registerIfExists(getCommand("wi"));
        new WhereCommand(this).registerIfExists(getCommand("w"));
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage("Command unknown to NCommon: " + cmd.getName());
        return true;
    }

    public MessageFormats getFormats() {
        return formats;
    }
}
