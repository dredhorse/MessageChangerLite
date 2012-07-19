/******************************************************************************
 * This file is part of MessageChanger (http://www.spout.org/).               *
 *                                                                            *
 * MessageChanger is licensed under the SpoutDev License Version 1.           *
 *                                                                            *
 * MessageChanger is free software: you can redistribute it and/or modify     *
 * it under the terms of the GNU Lesser General Public License as published by*
 * the Free Software Foundation, either version 3 of the License, or          *
 * (at your option) any later version.                                        *
 *                                                                            *
 * In addition, 180 days after any changes are published, you can use the     *
 * software, incorporating those changes, under the terms of the MIT license, *
 * as described in the SpoutDev License Version 1.                            *
 *                                                                            *
 * MessageChanger is distributed in the hope that it will be useful,          *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of             *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the              *
 * GNU Lesser General Public License for more details.                        *
 *                                                                            *
 * You should have received a copy of the GNU Lesser General Public License,  *
 * the MIT license and the SpoutDev License Version 1 along with this program.*
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://www.spout.org/SpoutDevLicenseV1.txt> for the full license,
 * including the MIT license.                                                 *
 ******************************************************************************/

package team.cascade.spout.messagechanger.exceptions;

import team.cascade.spout.messagechanger.helper.Logger;
import team.cascade.spout.messagechanger.permissions.PERMISSIONS;
import org.spout.api.exception.SpoutException;
import org.spout.api.player.Player;

/**
 * Exception which is thrown by the Permissions enum
 *
 * @author dredhorse
 *         based on code from
 * @author bergerkiller
 *         from http://bit.ly/zlhZg1
 */


public class NoPermissionException extends SpoutException {

    private String player;

    private String permission;

    public NoPermissionException() {
        super("Permission checked failed");
        player = "unknown";
        permission = "unknown";
        Logger.debug("Permission checked failed");
    }

    public NoPermissionException(PERMISSIONS permissionEnum, Player player) {
        super("Player " + player + " failed permission check for: " + permissionEnum.toString());
        this.player = player.getName();
        permission = permissionEnum.toString();
        Logger.debug("Player " + this.player + " failed permission check for: " + permission);
    }

    public String getMessage() {
        return "Player " + player + "failed permission check for: " + permission;
    }


}

