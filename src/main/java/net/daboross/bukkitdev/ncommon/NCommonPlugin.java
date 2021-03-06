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
package net.daboross.bukkitdev.ncommon;

import java.util.logging.Level;
import net.daboross.bukkitdev.ncommon.commands.WhereCommand;
import net.daboross.bukkitdev.ncommon.commands.WhereIsCommand;
import net.daboross.bukkitdev.ncommon.rankdisplay.RankCommand;
import net.daboross.bukkitdev.ncommon.removegoditems.GodItemChecker;
import net.daboross.bukkitdev.ncommon.removegoditems.RemoveGodItemsListener;
import net.daboross.bukkitdev.ncommon.removegoditems.SkyLog;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class NCommonPlugin extends JavaPlugin {

    private Permission permission;
    private GodItemChecker checker;

    @Override
    public void onEnable() {
        SkyLog.setLogger(getLogger());
        checker = new GodItemChecker(this);
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new RemoveGodItemsListener(this), this);
        pm.registerEvents(new JoinListener(), this);
        new NCommonBungeeListener(this).register();
        new WhereIsCommand(this).registerIfExists(getCommand("wi"));
        new WhereCommand(this).registerIfExists(getCommand("w"));
        getPermission(pm);
        new RankCommand(permission).setup(this);
    }

    @Override
    public void onDisable() {
        SkyLog.setLogger(null);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage("Command unknown to NCommon: " + cmd.getName());
        return true;
    }

    private void getPermission(PluginManager pm) {
        if (pm.isPluginEnabled("Vault")) {
            RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
            permission = rsp.getProvider();
        }
        if (permission == null) {
            getLogger().log(Level.WARNING, "Permissions not found");
        }
    }

    public boolean isRemove() {
        return false;
    }

    public GodItemChecker getChecker() {
        return checker;
    }
}
