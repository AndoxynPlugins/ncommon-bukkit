package net.daboross.bukkitdev.commoncommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author daboross
 */
public class CommonCommandExecutor extends CommandExecutorBase {

    /**
     *
     */
    protected CommonCommandExecutor() {
        initCommand("help", new String[]{"?"}, true, "commoncommands.help", "This Command Views This Page");
        initCommand("getplayernamelist", new String[]{"gpnl"}, true, "commomcommands.getplayernamelist", "This Command Gets a list of online players and their display names");
        initCommand("curseme", new String[]{"cm"}, false, "commoncommands.curseme", "This Command Curses You");
        initCommand("uncurseme", new String[]{"ucm"}, false, "commoncommands.uncurseme", "This Command Un Curses You");
    }

    @Override
    public void runCommand(CommandSender sender, Command mainCommand, String mainCommandLabel, String subCommand, String subCommandLabel, String[] subCommandArgs) {
        if (subCommand.equalsIgnoreCase("help")) {
            runHelpCommand(sender, mainCommand, subCommandArgs);
        } else if (subCommand.equalsIgnoreCase("curseme")) {
            runCurseMeCommand(sender, mainCommand, subCommandArgs);
        } else if (subCommand.equalsIgnoreCase("uncurseme")) {
            runUnCurseMeCommand(sender, mainCommand, subCommandArgs);
        } else if (subCommand.equalsIgnoreCase("getplayernamelist")) {
            runGetPlayerNameListCommand(sender, mainCommand, subCommandArgs);
        }
    }

    private void runGetPlayerNameListCommand(CommandSender sender, Command cmd, String[] args) {
        sender.sendMessage(ColorList.MAIN + "List Of Players And Their UserNames");
        String message = "";
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            message = message + (p.getDisplayName() + ColorList.DIVIDER + "/" + ColorList.NAME + p.getName() + "   ");
        }
        sender.sendMessage(message);
    }

    private void runCurseMeCommand(CommandSender sender, Command cmd, String[] args) {
        Player player = (Player) sender;
        PotionEffect effToAdd = new PotionEffect(PotionEffectType.SLOW, 1000000, 1000);
        player.addPotionEffect(effToAdd);
        sender.sendMessage(ChatColor.RED + "You Are Now Cursed");
        sender.sendMessage(ChatColor.RED + "You Can Use /cc uncurseme To Become UnCursed");
    }

    private void runUnCurseMeCommand(CommandSender sender, Command cmd, String[] args) {
        Player player = (Player) sender;
        player.removePotionEffect(PotionEffectType.SLOW);
        sender.sendMessage(ChatColor.BLUE + "You Are No Longer Cursed");
    }

    @Override
    public String getCommandName() {
        return "cc";
    }
}
