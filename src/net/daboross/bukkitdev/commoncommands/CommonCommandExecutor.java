package net.daboross.bukkitdev.commoncommands;

import net.daboross.bukkitdev.commandexecutorbase.CommandExecutorBase;
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
        initCommand("getplayernamelist", new String[]{"gpnl"}, true, "commomcommands.getplayernamelist", "This Command Gets a list of online players and their display names", new CommandReactor() {
            public void runCommand(CommandSender sender, Command mainCommand, String mainCommandLabel, String subCommand, String subCommandLabel, String[] subCommandArgs, CommandExecutorBridge executorBridge) {
                runGetPlayerNameListCommand(sender, mainCommand, subCommandArgs);
            }
        });
        initCommand("curseme", new String[]{"cm"}, false, "commoncommands.curseme", "This Command Curses You", new CommandReactor() {
            public void runCommand(CommandSender sender, Command mainCommand, String mainCommandLabel, String subCommand, String subCommandLabel, String[] subCommandArgs, CommandExecutorBridge executorBridge) {
                runCurseMeCommand(sender, mainCommand, subCommandArgs);
            }
        });
        initCommand("uncurseme", new String[]{"ucm"}, false, "commoncommands.uncurseme", "This Command Un Curses You", new CommandReactor() {
            public void runCommand(CommandSender sender, Command mainCommand, String mainCommandLabel, String subCommand, String subCommandLabel, String[] subCommandArgs, CommandExecutorBridge executorBridge) {
                runUnCurseMeCommand(sender, mainCommand, subCommandArgs);
            }
        });
    }

    private void runGetPlayerNameListCommand(CommandSender sender, Command cmd, String[] args) {
        sender.sendMessage(ColorList.MAIN + "List Of Players And Their UserNames");
        StringBuilder messageBuilder = new StringBuilder();
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            messageBuilder.append(p.getDisplayName()).append(ColorList.DIVIDER).append("/").append(ColorList.NAME).append(p.getName()).append("   ");
        }
        sender.sendMessage(messageBuilder.toString());
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

    @Override
    protected String getMainCmdPermission() {
        return "commoncommands.help";
    }
}
