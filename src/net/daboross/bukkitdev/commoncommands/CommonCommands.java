package net.daboross.bukkitdev.commoncommands;

/**
 *
 * @author daboross
 */
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author daboross
 */
public final class CommonCommands extends JavaPlugin {

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("list")) {
            Player[] onlinePlayers = Bukkit.getServer().getOnlinePlayers();
            if (onlinePlayers.length == 0) {
                sender.sendMessage(ColorList.REG + "There are no players online.");
            } else if (onlinePlayers.length == 1) {
                sender.sendMessage(ColorList.REG + "There is one player online:");
                sender.sendMessage(ColorList.NAME + onlinePlayers[0].getDisplayName());
            } else {
                sender.sendMessage(ColorList.TOP + "There are " + ColorList.DATA + onlinePlayers.length + ColorList.TOP + " players online:");
                StringBuilder messageBuilder = new StringBuilder(ColorList.NAME).append(onlinePlayers[0].getDisplayName());
                for (int i = 1; i < onlinePlayers.length; i++) {
                    messageBuilder.append(ColorList.DIVIDER).append(", ").append(ColorList.NAME).append(onlinePlayers[i].getDisplayName());
                }
                sender.sendMessage(messageBuilder.toString());
            }
        } else {
            sender.sendMessage("Command unknown to CommonCommands");
        }
        return true;
    }
}
