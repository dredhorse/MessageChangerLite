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

package net.breiden.spout.messagechanger.events;

import net.breiden.spout.messagechanger.MessageChanger;
import net.breiden.spout.messagechanger.enums.DEFAULT_EVENTS;
import net.breiden.spout.messagechanger.messages.SpoutMessages;
import org.spout.api.event.EventHandler;
import org.spout.api.event.Listener;
import org.spout.api.event.player.*;
import org.spout.api.plugin.CommonPlugin;

/**
 * Contains all the Spout Player events monitored by this plugin
 *
 * @author $Author: dredhorse$
 * @version $FullVersion$
 */
public class SpoutPlayerEvents implements Listener {

	private final MessageChanger plugin;

    private final SpoutMessages spoutMessages;

	public SpoutPlayerEvents(CommonPlugin plugin) {

		this.plugin = (MessageChanger) plugin;
        spoutMessages = SpoutMessages.getInstance();

	}

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
        if (event.isCancelled()){
            return;
        }
        String message = event.getMessage();
        // Is the server full?
        if (!event.isAllowed()){
            event.setMessage(spoutMessages.getNewMessage(DEFAULT_EVENTS.KICK_FULL.toString(),event.getPlayer(),message));
        }
      // todo waiting for PlayerWhiteList event
    	/*switch (event.g) {
			case KICK_BANNED:
				event.setKickMessage(plugin.getMessage("KICK_BANNED", event.getPlayer(), event.getKickMessage()));
				break;
			case KICK_WHITELIST:
				event.setKickMessage(plugin.getMessage("KICK_WHITELIST", event.getPlayer(), event.getKickMessage()));
				break;
			case KICK_FULL:
				event.setKickMessage(plugin.getMessage("KICK_FULL", event.getPlayer(), event.getKickMessage()));
				break;
		}*/
	}

    @EventHandler
    public void onPlayerBan(PlayerBanKickEvent event){
        if (event.isCancelled()){
            return;
        }
        event.setMessage(spoutMessages.getNewMessage(DEFAULT_EVENTS.KICK_BANNED.toString(),event.getPlayer(),event.getMessage()));
    }


	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
        if (event.isCancelled()){
            return;
        }
        event.setMessage(spoutMessages.getNewMessage(DEFAULT_EVENTS.PLAYER_JOIN.toString(),event.getPlayer(),event.getMessage()));
	}

	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
        if (event.isCancelled()){
            return;
        }
        if (plugin.getIgnoreKick()) {
			plugin.disableIgnoreKick();
			return;
		}
        event.setKickReason(spoutMessages.getNewMessage(DEFAULT_EVENTS.KICK_KICK_REASON.toString(),event.getPlayer(),event.getKickReason()));
		event.setMessage(spoutMessages.getNewMessage(DEFAULT_EVENTS.KICK_KICK_LEAVEMSG.toString(),event.getPlayer(),event.getMessage()));
	}

	@EventHandler
	public void onPlayerQuit(PlayerLeaveEvent event) {
        event.setMessage(spoutMessages.getNewMessage(DEFAULT_EVENTS.PLAYER_QUIT.toString(),event.getPlayer(), event.getMessage()));
	}


    // todo wait for implementation of PlayerChangedWorldEvent
/*	@EventHandler
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
		String msg = plugin.getMessage("CHANGED_WORLD", event.getPlayer(), "");
		if (msg.equals("")) {
			return;
		}
		String fromWorld = event.getFrom().getName();
		if (fromWorld == null) {
			fromWorld = "Unknown Territories";
		}
		msg = msg.replace("%fromWorld", fromWorld);
		event.getPlayer().sendMessage(msg);
	}*/


}
