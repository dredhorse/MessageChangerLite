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

package team.cascade.spout.messagechanger.vanilla.DeathMessages;

import org.spout.api.entity.Player;
import org.spout.api.event.EventHandler;
import org.spout.api.event.Listener;
import org.spout.api.event.entity.EntityDeathEvent;
import org.spout.api.plugin.CommonPlugin;
import team.cascade.spout.messagechanger.MessageChanger;
import team.cascade.spout.messagechanger.config.CONFIG;
import team.cascade.spout.messagechanger.helper.Logger;
import team.cascade.spout.messagechanger.vanilla.VanillaMessagesHandler;

/**
 * Contains all the Vanilla Death events monitored by this plugin
 *
 * @author $Author: dredhorse$
 * @version $FullVersion$
 */
public class VanillaDeathEvents implements Listener {

    private final MessageChanger plugin;

    private final VanillaMessagesHandler vanillaMessagesHandler;

    private static VanillaDeathEvents instance;

    public VanillaDeathEvents(CommonPlugin plugin) {
        instance = this;
        this.plugin = (MessageChanger) plugin;
        vanillaMessagesHandler = VanillaMessagesHandler.getInstance();
        Logger.debug("VanillaDeathEvent Listener Activated");

    }

    public static VanillaDeathEvents getInstance(){
        return instance;
    }

    @EventHandler
    public void onPlayerDeathEvent(EntityDeathEvent event) {
        if (event.isCancelled()){
            Logger.debug("EntityDeathEvent was cancelled");
            return;
        }

        if (!(event.getEntity() instanceof Player)) {
            Logger.debug("Entity is not a player",event.getEntity().getController().getType());
            return;
        }
        String msg ="";


        if (!CONFIG.VANILLA_DISABLE_DEATH_MESSAGES_IN_SPECIFIED_WORLDS.getStringList().isEmpty()){
            for (String world : CONFIG.VANILLA_DISABLE_DEATH_MESSAGES_IN_SPECIFIED_WORLDS.getStringList()){
                if (world.matches(".*\\b"+event.getEntity().getWorld().getName()+"\\b.*")){
                    Logger.debug("World is configured to not broadcast too",event.getEntity().getWorld().getName());
                    return;
                }
            }
        }

        Logger.debug("World is configured to broadcast too",event.getEntity().getWorld().getName());
        if (CONFIG.VANILLA_SHOW_DEATH_MESSAGES_IN_DEATH_WORLD_ONLY.getBoolean()){
            for (Player onlinePlayer : plugin.getEngine().getOnlinePlayers()){
                if (onlinePlayer.getWorld() == event.getEntity().getWorld()){
                    //todo set event message to ""
                    onlinePlayer.sendMessage(msg);
                }
            }
        } else {
            // todo event set message needed

        }

        if (CONFIG.LOG_MESSAGES_ON_CONSOLE.getBoolean()){
            Logger.info(msg.toString());
        }
        Logger.debug("DeathMessage",msg);
    }





}
