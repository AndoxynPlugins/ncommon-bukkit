/*
 * Copyright (C) 2013 Dabo Ross <www.daboross.net>
 */
package net.daboross.bukkitdev.ncommon;

import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author daboross
 */
public class JoinMessageListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent evt) {
        evt.setJoinMessage(null);
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + evt.getPlayer().getName() + " joined this server.");
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent evt) {
        evt.setQuitMessage(null);
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + evt.getPlayer().getName() + " left this server.");
    }
}
