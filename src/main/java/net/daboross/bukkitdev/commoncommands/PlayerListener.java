/*
 * Copyright (C) 2013 Dabo Ross <www.daboross.net>
 */
package net.daboross.bukkitdev.commoncommands;

import java.util.logging.Level;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author daboross
 */
public class PlayerListener implements Listener {
    
    private final CommonCommands plugin;
    
    public PlayerListener(CommonCommands plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent evt) {
        evt.setJoinMessage(null);
        plugin.getLogger().log(Level.INFO, "{0} joined this server.", evt.getPlayer().getName());
    }
    
    @EventHandler
    public void onQuit(PlayerQuitEvent evt) {
        evt.setQuitMessage(null);
        plugin.getLogger().log(Level.INFO, "{0} left this server.", evt.getPlayer().getName());
    }
}
