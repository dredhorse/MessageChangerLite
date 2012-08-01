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

package team.cascade.spout.messagechanger.spout;

import org.spout.api.Engine;
import org.spout.api.Spout;
import org.spout.api.command.CommandExecutor;
import org.spout.api.event.EventHandler;
import org.spout.api.event.Listener;
import org.spout.api.event.server.PreCommandEvent;
import org.spout.api.plugin.CommonPlugin;
import org.spout.api.plugin.Platform;
import team.cascade.spout.messagechanger.MessageChanger;
import team.cascade.spout.messagechanger.helper.Logger;
import team.cascade.spout.messagechanger.helper.Messenger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the Unknown Command Messages
 *
 * @author $Author: dredhorse$
 * @version $FullVersion$
 */
public class SpoutUnknownCommandEvent implements Listener {

    private final MessageChanger plugin;

    private final SpoutUnknownCommandHandler spoutUnknownCommandHandler;

    protected Map<Platform, CommandExecutor> executors = new HashMap<Platform, CommandExecutor>();

    public SpoutUnknownCommandEvent(CommonPlugin plugin) {

        this.plugin = (MessageChanger) plugin;
        spoutUnknownCommandHandler = SpoutUnknownCommandHandler.getInstance();
        Logger.debug("UnknownCommand Listener Activated");

    }

    @EventHandler
    public void onPreCommandEvent (PreCommandEvent event){
        if (event.isCancelled()){
            Logger.debug("PreCommandEvent was cancelled");
            return;
        }
        if (spoutUnknownCommandHandler.getCommandsToIgnore().contains(event.getCommand())) {
            Logger.debug("Command is to be ignored",event.getCommand());
            return;
        }

        CommandExecutor executor = getActiveExecutor();
        List<Object> args = event.getArguments().getArguments();
        if (executor == null || 0 > args.size()){
            Messenger.send(event.getCommandSource(),spoutUnknownCommandHandler.getNewMessage());
        }
    }

    public CommandExecutor getActiveExecutor() {
        final Engine engine = Spout.getEngine();
        final Platform platform;
        if (engine == null) {
            platform = Platform.ALL;
        } else {
            platform = engine.getPlatform();
        }
        CommandExecutor exec = executors.get(platform);
        if (exec == null) {
            exec = executors.get(Platform.ALL);
        }
        return exec;
    }

}
