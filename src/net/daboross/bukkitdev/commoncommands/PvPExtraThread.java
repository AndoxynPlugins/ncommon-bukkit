package net.daboross.bukkitdev.commoncommands;

import org.bukkit.entity.Player;

/**
 *
 * @author daboross
 */
public class PvPExtraThread implements Runnable {

    private PvPClass pvpClass;
    private Player p;

    public PvPExtraThread(PvPClass pvpClass, Player p) {
        this.pvpClass = pvpClass;
        this.p = p;
    }

    @Override
    public void run() {
        pvpClass.pvpP.remove(p);
    }
}
