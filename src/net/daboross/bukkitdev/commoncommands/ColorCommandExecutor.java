package net.daboross.bukkitdev.commoncommands;

import java.util.Map;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author daboross
 */
public class ColorCommandExecutor implements CommandExecutor {

    public ColorCommandExecutor() {
    }
    private Map<Integer, String> colorMap = new ColorMap();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("color")) {
            if (args.length == 0) {
                return false;
            }
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
