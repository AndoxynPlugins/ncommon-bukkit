package net.daboross.bukkitdev.commoncommands;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 *
 * @author daboross
 */
public class PvPClass implements Listener, CommandExecutor {
    
    protected PvPClass() {
    }
    protected ArrayList<Player> pvpP = new ArrayList<Player>();

    /**
     *
     * @param evt
     */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerTeleport(PlayerTeleportEvent evt) {
        if (!pvpP.contains(evt.getPlayer())) {
            String worldName = evt.getTo().getWorld().getName().toLowerCase();
            if (worldName.equals("pvpworld")) {
                pvp(evt.getPlayer());
            }
        }
    }
    
    protected void pvp(Player p) {
        pvpP.add(p);
        Random r = new Random();
        int n = r.nextInt(4);
        n += 1;
        p.performCommand("ewarp PvP" + n);
        p.sendMessage(ColorList.MAIN + "PVP!");
        makeExtraThread(p);
    }
    
    private void makeExtraThread(Player p) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(CommonCommands.getCurrentInstance(), new PvPExtraThread(this, p), 20L);
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pvp")) {
            if (sender instanceof Player) {
                pvp((Player) sender);
            } else {
                sender.sendMessage(ColorList.MAIN + "You have to be a player to run this command!");
            }
            return true;
        }
        return false;
    }
}
