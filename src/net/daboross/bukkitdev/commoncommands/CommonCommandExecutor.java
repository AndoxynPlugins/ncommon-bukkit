package net.daboross.bukkitdev.commoncommands;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author daboross
 */
public class CommonCommandExecutor implements CommandExecutor {

    private final Map<String, String> aliasMap = new HashMap<String, String>();
    private final Map<String, Boolean> isConsoleMap = new HashMap<String, Boolean>();
    private final Map<String, String> helpList = new HashMap<String, String>();
    private final Map<String, String[]> helpAliasMap = new HashMap<String, String[]>();
    private final Map<String, String> permMap = new HashMap<String, String>();
    private CommonCommands pluginMain;

    /**
     *
     */
    protected CommonCommandExecutor(CommonCommands mainPlugin) {
        pluginMain = mainPlugin;
        initCommand("help", new String[]{"?"}, true, "commoncommands.help", "This Command Views This Page");
        initCommand("getplayernamelist", new String[]{"gpnl"}, true, "commomcommands.getplayernamelist", "This Command Gets a list of online players and their display names");
        initCommand("curseme", new String[]{"cm"}, false, "commoncommands.curseme", "This Command Curses You");
        initCommand("uncurseme", new String[]{"ucm"}, false, "commoncommands.uncurseme", "This Command Un Curses You");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("cc")) {
            if (args.length < 1) {
                sender.sendMessage(ColorList.MAIN + "This is a base command, Please Use a sub command after it.");
                sender.sendMessage(ColorList.MAIN + "To see all possible sub commands, type " + ColorList.CMD + "/" + cmd.getName() + ColorList.SUBCMD + " ?");
                return true;
            }
            String commandName;
            if (aliasMap.containsKey(args[0].toLowerCase())) {
                commandName = aliasMap.get(args[0].toLowerCase());
            } else {
                sender.sendMessage(ColorList.MAIN + "The SubCommand: " + ColorList.CMD + args[0] + ColorList.MAIN + " Does not exist.");
                sender.sendMessage(ColorList.MAIN + "To see all possible sub commands, type " + ColorList.CMD + "/" + cmd.getName() + ColorList.SUBCMD + " ?");
                return true;
            }
            if (sender instanceof Player) {
                if (!sender.hasPermission(permMap.get(commandName))) {
                    sender.sendMessage(ColorList.NOPERM + "You don't have permission to do this command!");
                    return true;
                }
            }
            boolean isConsole;
            if (isConsoleMap.containsKey(commandName)) {
                isConsole = isConsoleMap.get(commandName);
            } else {
                isConsole = false;
            }
            if (!(sender instanceof Player)) {
                if (!isConsole) {
                    sender.sendMessage(ColorList.NOPERM + "This command must be run by a player");
                    return true;
                }
            }
            if (commandName.equalsIgnoreCase("help")) {
                runHelpCommand(sender, cmd, getSubArray(args));
            } else if (commandName.equalsIgnoreCase("curseme")) {
                runCurseMeCommand(sender, cmd, getSubArray(args));
            } else if (commandName.equalsIgnoreCase("uncurseme")) {
                runUnCurseMeCommand(sender, cmd, getSubArray(args));
            } else if (commandName.equalsIgnoreCase("getplayernamelist")) {
                runGetPlayerNameListCommand(sender, cmd, getSubArray(args));
            }
            return true;
        }
        return false;
    }

    private String[] getSubArray(String[] array) {
        if (array.length > 1) {
            return Arrays.asList(array).subList(1, array.length).toArray(new String[0]);
        } else {
            return new String[0];
        }
    }

    private void runHelpCommand(CommandSender sender, Command cmd, String[] args) {
        sender.sendMessage(ColorList.MAIN + "List Of Possible Sub Commands:");
        for (String str : aliasMap.keySet()) {
            if (str.equalsIgnoreCase(aliasMap.get(str))) {
                if (sender.hasPermission(str)) {
                    sender.sendMessage(getMultipleAliasHelpMessage(str, cmd.getLabel()));
                }
            }
        }
    }

    private String getHelpMessage(String alias, String baseCommand) {
        String str = aliasMap.get(alias);
        return (ColorList.CMD + "/" + baseCommand + ColorList.SUBCMD + " " + alias + ColorList.HELP + " " + helpList.get(aliasMap.get(str)));
    }

    private String getMultipleAliasHelpMessage(String subcmd, String baseCommand) {
        String[] aliasList = helpAliasMap.get(subcmd);
        String commandList = subcmd;
        for (String str : aliasList) {
            commandList += ColorList.DIVIDER + "/" + ColorList.SUBCMD + str;
        }
        return (ColorList.CMD + "/" + baseCommand + ColorList.SUBCMD + " " + commandList + ColorList.HELP + " " + helpList.get(subcmd));
    }

    private void initCommand(String cmd, String[] aliases, boolean isConsole, String permission, String helpString) {
        aliasMap.put(cmd, cmd);
        for (String alias : aliases) {
            aliasMap.put(alias, cmd);
        }
        isConsoleMap.put(cmd, isConsole);
        permMap.put(cmd, permission);
        helpList.put(cmd, helpString);
        helpAliasMap.put(cmd, aliases);
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
}
