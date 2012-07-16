package net.breiden.spout.messagechanger.exceptions;

import net.breiden.spout.messagechanger.helper.Logger;
import net.breiden.spout.messagechanger.permissions.PERMISSIONS;
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

