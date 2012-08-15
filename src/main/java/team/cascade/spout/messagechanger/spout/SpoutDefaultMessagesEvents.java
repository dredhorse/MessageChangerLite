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

package team.cascade.spout.messagechanger.spout;

import org.spout.api.event.EventHandler;
import org.spout.api.event.Listener;
import org.spout.api.event.Order;
import org.spout.api.event.entity.EntityChangedWorldEvent;
import org.spout.api.event.player.*;
import org.spout.api.event.server.ServerStopEvent;
import org.spout.api.player.Player;
import org.spout.api.plugin.CommonPlugin;
import team.cascade.spout.messagechanger.MessageChanger;
import team.cascade.spout.messagechanger.enums.DEFAULT_EVENTS;
import team.cascade.spout.messagechanger.helper.Logger;
import team.cascade.spout.messagechanger.helper.Messenger;
import team.cascade.spout.messagechanger.messages.MESSAGES;

/**
 * Contains all the Spout Player events monitored by this plugin
 *
 * @author $Author: dredhorse$
 * @version $FullVersion$
 */
public class SpoutDefaultMessagesEvents implements Listener {

    private final MessageChanger plugin;

    private final SpoutMessagesHandler spoutMessagesHandler;

    public SpoutDefaultMessagesEvents(CommonPlugin plugin) {

        this.plugin = (MessageChanger) plugin;
        spoutMessagesHandler = SpoutMessagesHandler.getInstance();
        Logger.debug("PlayerEvent Listener Activated");

    }

    @EventHandler (order = Order.LATEST_IGNORE_CANCELLED)
    public void onPlayerLogin(PlayerLoginEvent event) {
        Logger.debug("PlayerLoginEvent executed");
        String message = event.getMessage();
        // Is the server full?
        if (!event.isAllowed()){
            event.setMessage(spoutMessagesHandler.getNewMessage(DEFAULT_EVENTS.KICK_FULL.toString(),event.getPlayer(),message));
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
        event.setMessage(spoutMessagesHandler.getNewMessage(DEFAULT_EVENTS.KICK_BANNED.toString(),event.getPlayer(),event.getMessage()));
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (event.isCancelled()){
            Logger.debug("PlayerJoinEvent was cancelled");
            return;
        }
        Logger.debug("PlayerJoinEvent executed");
        event.setMessage(spoutMessagesHandler.getNewMessageFromObjects(DEFAULT_EVENTS.PLAYER_JOIN.toString(),event.getPlayer(),event.getMessage()));
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        if (event.isCancelled()){
            Logger.debug("PlayerKickEvent was cancelled");
            return;
        }
        if (plugin.getIgnoreKick()) {
            Logger.debug("PlayerKickEvent was ignored");
            plugin.disableIgnoreKick();
            return;
        }
        Logger.debug("PlayerKickEvent executed");
        // spout creates a KickEvent first than it does the LeaveEvent
        plugin.enableIgnoreKick();
        event.setKickReason(spoutMessagesHandler.getNewMessageFromObjects(DEFAULT_EVENTS.KICK_KICK_REASON.toString(),event.getPlayer(),event.getKickReason()));
        event.setMessage(spoutMessagesHandler.getNewMessageFromObjects(DEFAULT_EVENTS.KICK_KICK_LEAVEMSG.toString(),event.getPlayer(),event.getMessage()));
    }

    @EventHandler
    public void onPlayerLeave(PlayerLeaveEvent event) {
        Logger.debug("PlayerLeaveEvent executed");
        if (plugin.getIgnoreKick()) {
            Logger.debug("PlayerKickEvent was ignored");
            plugin.disableIgnoreKick();
            return;
        }
        event.setMessage(spoutMessagesHandler.getNewMessageFromObjects(DEFAULT_EVENTS.PLAYER_QUIT.toString(),event.getPlayer(), event.getMessage()));
    }



    @EventHandler
    public void onPlayerChangedWorld(EntityChangedWorldEvent event) {
        if (event.isCancelled()){
            Logger.debug("EntityChangedWorldEvent was cancelled");
            return;
        }
        if (!(event.getEntity() instanceof Player)){
            Logger.debug("Entity is not a player");
            return;
        }
        Player player = (Player) event.getEntity();

        String fromWorld = event.getPrevious().getName();
        if (fromWorld == null) {
            fromWorld = MESSAGES.MISSING_FROM_WORLD.getMessage();
        }
        String msg = spoutMessagesHandler.getNewMessage(DEFAULT_EVENTS.CHANGED_WORLD.toString(), player, "");
        msg = msg.replace("%(fromWorld)", fromWorld);
        Messenger.send(player,Messenger.dictFormat(player, msg));
    }

    @EventHandler
    public void ServerStopEvent(ServerStopEvent event){
        if (event.isCancelled()){
            Logger.debug("ServerStopEvent was cancelled");
            return;
        }
        event.setMessage(spoutMessagesHandler.getNewMessage ("SERVER_STOP", null, event.getMessage()));
    }

}
