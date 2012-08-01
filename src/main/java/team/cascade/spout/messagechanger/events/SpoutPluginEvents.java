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

package team.cascade.spout.messagechanger.events;

import org.spout.api.event.EventHandler;
import org.spout.api.event.Listener;
import org.spout.api.event.server.PluginEnableEvent;
import team.cascade.spout.messagechanger.MessageChanger;
import team.cascade.spout.messagechanger.enums.GAME_TYPES;
import team.cascade.spout.messagechanger.enums.VANILLA_TYPES;
import team.cascade.spout.messagechanger.helper.Logger;
import team.cascade.spout.messagechanger.vanilla.VanillaMessagesHandler;

/**
 * Checks for plugin loading so that the additional support modules can be loaded
 *
 * @author $Author: dredhorse$
 * @version $FullVersion$
 */
public class SpoutPluginEvents implements Listener {

    private final MessageChanger main;

    public SpoutPluginEvents(MessageChanger main) {
        this.main = main;
        // todo implement the triggering of additional Messages

    }

    /**
     * Checks if a plugin was enabled which we support and configures the additional modules
     */

    @EventHandler
    public void onPluginEnable ( PluginEnableEvent event){
        String plugin = event.getPlugin().getName();
        Logger.debug("PluginLoaded",plugin);
        if (plugin.equals("Vanilla")){
            main.addGameType(GAME_TYPES.VANILLA);
            main.addPluginType(GAME_TYPES.VANILLA, VANILLA_TYPES.DEFAULT);
            main.setVanillaMessagesHandler(new VanillaMessagesHandler(main));
        }

    }

}
