package net.daboross.bukkitdev.commoncommands;

import java.util.HashMap;
import org.bukkit.ChatColor;

/**
 *
 * @author daboross
 */
public class ColorMap extends HashMap<Integer, String> {

    private static final long serialVersionUID = 1L;

    public ColorMap() {
        put(0, ChatColor.AQUA.toString());
        put(1, ChatColor.BLUE.toString());
        put(2, ChatColor.DARK_BLUE.toString());
        put(3, ChatColor.DARK_PURPLE.toString());
        put(4, ChatColor.DARK_RED.toString());
        put(5, ChatColor.RED.toString());
        put(6, ChatColor.GOLD.toString());
        put(7, ChatColor.GREEN.toString());
        put(8, ChatColor.DARK_GREEN.toString());
    }
}
