/*
 * Copyright (C) 2013-2014 Dabo Ross <http://www.daboross.net/>
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
package net.daboross.bukkitdev.ncommon;

import static net.daboross.bukkitdev.commandexecutorbase.ColorList.*;

public class Messages {

    /**
     * First parameter is server name, Second parameter is X, Third parameter is
     * Y, Fourth parameter is Z, Fifth parameter is world.
     */
    public static final String WHERE_CMD = REG + "Location: " + DATA + "%2$s" + REG + ", " + DATA + "%3$s" + REG + ", " + DATA + "%4$s" + REG + ", " + DATA + "%5$s" + REG + ", " + DATA + "%1$s";
    /**
     * First parameter is player name, Second parameter is X, Third parameter is
     * Y, Fourth parameter is Z, Fifth parameter is world.
     */
    public static final String WHERE_IS_CMD = NAME + "%s" + REG + ": " + DATA + "%s" + REG + ", " + DATA + "%s" + REG + ", " + DATA + "%s" + REG + ", " + DATA + "%s";
}
