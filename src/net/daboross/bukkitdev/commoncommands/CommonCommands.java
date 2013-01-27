package net.daboross.bukkitdev.commoncommands;

/**
 *
 * @author daboross
 */
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 *
 * @author daboross
 */
public final class CommonCommands extends JavaPlugin {

    private Map<String, String> aliasMap;

    /**
     *
     */
    @Override
    public void onEnable() {
        aliasMap = new HashMap<String, String>();
        aliasMap.put("?", "help");
        aliasMap.put("gpnl", "getplayernamelist");
        aliasMap.put("cm", "curseme");
        aliasMap.put("ucm", "uncurseme");
        aliasMap.put("help", "help");
        aliasMap.put("getplayernamelist", "getplayernamelist");
        aliasMap.put("curseme", "curseme");
        aliasMap.put("uncurseme", "uncurseme");
        getLogger().info("CommonCommands Enabled");
    }

    /**
     *
     */
    @Override
    public void onDisable() {
        getLogger().info("CommonCommands Disabled");
    }
    /**
     *
     * @param sender
     * @param cmd
     * @param label
     * @param args
     * @return
     */
    private Map<Integer, String> colorMap = new ColorMap();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("cc")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("This is a PLAYER command, not a CONSOLE command.");
                return true;
            }
            if (args.length < 1) {
                sender.sendMessage(ChatColor.AQUA + "This Command Is a Base Command, You must put something after the command");
                sender.sendMessage(ChatColor.AQUA + "To see all possible sub commands, type /cc ?");
                return true;
            }
            String commandName;
            if (aliasMap.containsKey(args[0].toLowerCase())) {
                commandName = aliasMap.get(args[0].toLowerCase());
                getLogger().log(Level.INFO, (sender.getName() + " used " + commandName));
            } else {
                sender.sendMessage(ChatColor.AQUA + "The SubCommand: " + ChatColor.RED + args[0] + ChatColor.AQUA + " Does not exist.");
                sender.sendMessage(ChatColor.AQUA + "To see all possible sub commands, type " + ChatColor.RED + cmd.getName() + ChatColor.AQUA + " ?");
                return true;
            }
            Player player = (Player) sender;
            if ("curseme".equals(commandName)) {
                getLogger().log(Level.INFO, ("Cursing " + player.getName() + " / " + player.getDisplayName()));
                PotionEffect effToAdd = new PotionEffect(PotionEffectType.SLOW, 1000000, 1000);
                player.addPotionEffect(effToAdd);
                sender.sendMessage(ChatColor.RED + "You Are Now Cursed");
                sender.sendMessage(ChatColor.RED + "You Can Use /cc uncurseme To Become UnCursed");
                return true;
            }
            if ("uncurseme".equals(commandName)) {
                getLogger().log(Level.INFO, ("UnCursing " + player.getName() + " / " + player.getDisplayName()));
                player.removePotionEffect(PotionEffectType.SLOW);
                sender.sendMessage(ChatColor.BLUE + "You Are No Longer Cursed");
                return true;
            }
            if ("getplayernamelist".equals(commandName)) {
                sender.sendMessage(ChatColor.AQUA + "List Of Players And Their UserNames");
                String message = "";
                for (Player p : getServer().getOnlinePlayers()) {
                    message = message + (p.getDisplayName() + ChatColor.BLUE + "/" + ChatColor.GRAY + p.getName() + "   ");
                }
                sender.sendMessage(message);
                return true;
            }
            if ("help".equals(commandName)) {
                sender.sendMessage(ChatColor.AQUA + "List Of Possible Sub Commands:");
                for (String str : aliasMap.keySet()) {
                    sender.sendMessage(ChatColor.RED + "/cc " + ChatColor.GREEN + str);
                }
                return true;
            }
            return true;
        } else if (cmd.getName().equalsIgnoreCase("color")) {
            String stringToColorize = "";
            for (int i = 0; i < args.length; i++) {
                stringToColorize += args[i];
                if (i != args.length - 1) {
                    stringToColorize += " ";
                }
            }
            char[] charList = stringToColorize.toCharArray();
            String colorizedString = "";
            int num = 0;
            for (int i = 0; i < charList.length; i++) {
                if (charList[i] != ' ') {
                    if (num > 8) {
                        num = 0;
                    }
                    colorizedString += colorMap.get(num);
                    num++;
                }
                colorizedString += charList[i];
            }
            Player player = (Player) sender;
            player.chat(colorizedString);
            return true;
        }
        return false;
    }
}
