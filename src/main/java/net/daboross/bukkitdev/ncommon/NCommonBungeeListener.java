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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class NCommonBungeeListener implements PluginMessageListener {

    private final JavaPlugin plugin;

    public NCommonBungeeListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void register() {
        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, "NCommon", this);
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "NCommon");
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equalsIgnoreCase("NCommon")) {
            return;
        }
        try (ByteArrayInputStream bais = new ByteArrayInputStream(message)) {
            try (DataInputStream in = new DataInputStream(bais)) {
                String action = in.readUTF();
                switch (action) {
                    case "SetDisplayName":
                        player.setDisplayName(in.readUTF());
                        break;
                    case "ConsoleMessage":
                        Bukkit.getConsoleSender().sendMessage(in.readUTF());
                        break;
                    case "SendWithPermission":
                        String permission = in.readUTF();
                        String spyMessage = in.readUTF();
                        int ignoredLength = 0;
                        try {
                            ignoredLength = in.readInt();
                        } catch (IOException ex) {
                        }
                        if (ignoredLength != 0) {
                            Set<String> ignored = new HashSet<>(ignoredLength);
                            for (int i = 0; i < ignoredLength; i++) {
                                ignored.add(in.readUTF());
                            }
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                if (p.hasPermission(permission) && !ignored.contains(p.getName())) {
                                    p.sendMessage(spyMessage);
                                }
                            }
                        } else {
                            for (Player p : Bukkit.getOnlinePlayers()) {
                                if (p.hasPermission(permission)) {
                                    p.sendMessage(spyMessage);
                                }
                            }
                        }
                        break;
                    case "PermissionCheck":
                        String runPermission = in.readUTF();
                        String key = in.readUTF();
                        permissionCheck(player, runPermission, key);
                        break;
                    default:
                        plugin.getLogger().log(Level.WARNING, "Unknown command received ''{0}''.", action);
                }
            }
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Error recieving message", ex);
        }
    }

    private void permissionCheck(Player player, String permission, String key) {
        byte[] data;
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            try (DataOutputStream out = new DataOutputStream(b)) {
                out.writeUTF("PermissionResult");
                out.writeUTF(key);
                out.writeBoolean(player.hasPermission(permission));
            }
            data = b.toByteArray();
        } catch (IOException ex) {
            plugin.getLogger().log(Level.SEVERE, "Error writing data to byte array", ex);
            return;
        }
        player.sendPluginMessage(plugin, "NCommon", data);
    }
}
