/*
 * Copyright (C) 2013 daboross
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.daboross.bukkitdev.ncommon.goditemfix;

import org.bukkit.entity.Player;

/**
 *
 * @author daboross
 */
public class GodItemFixRunnable implements Runnable {

    private final GodItemFix godItemFix;
    private final Player p;

    public GodItemFixRunnable(GodItemFix godItemFix, Player p) {
        this.godItemFix = godItemFix;
        this.p = p;
    }

    @Override
    public void run() {
        godItemFix.removeGodEnchants(p);
    }
}
