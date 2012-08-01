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

import org.spout.api.player.Player;
import org.spout.api.plugin.CommonPlugin;
import team.cascade.spout.messagechanger.config.CONFIG;
import team.cascade.spout.messagechanger.helper.COLOR;
import team.cascade.spout.messagechanger.messages.MessagesInterface;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;


/**
 * Contains code to handle the Unknown command messages
 *
 * @author $Author: dredhorse$
 * @version $FullVersion$
 */
public class SpoutUnknownCommandHandler implements MessagesInterface {

    private final static String[] NULL_RESPONSE_ARRAY = new String[0];

    private  Set<String> commandsToIgnore;

    private ArrayList<String> unknownCommandMessages;

    private Random rand = new Random();

    private SpoutUnknownCommands spoutUnknownCommands;

    private static SpoutUnknownCommandHandler instance;

    public SpoutUnknownCommandHandler(CommonPlugin main){
        instance = this;
        spoutUnknownCommands = new SpoutUnknownCommands(main);
        main.getEngine().getEventManager().registerEvents(new SpoutUnknownCommandEvent(main), main);

    }

    public static SpoutUnknownCommandHandler getInstance(){
        return instance;
    }

    public void reload(){
        spoutUnknownCommands.init();
    }


    public String getNewMessage() {
        String msg = COLOR.RED + "Unknown Command";
        if (unknownCommandMessages.size() == 1){
            msg = unknownCommandMessages.get(0);
        } else {
            if (unknownCommandMessages.size() > 1){
                msg = unknownCommandMessages.get(rand.nextInt(unknownCommandMessages.size()));
            }
        }
        return CONFIG.UNKNOWN_COMMAND_PREFIX.getString() + msg;
    }

    public void setCommandsToIgnore(Set<String> commandsToIgnore){
        this.commandsToIgnore = commandsToIgnore;
    }

    public void setUnknownCommandMessages (ArrayList<String> unknownCommandMessages){
        this.unknownCommandMessages = unknownCommandMessages;
    }

    public Set<String> getCommandsToIgnore(){
        return commandsToIgnore;
    }


    /**
     * Should not be used
     * @param event
     * @param player
     * @param defaultMessage
     * @return
     */
    @Override
    public String getNewMessage(String event, Player player, String defaultMessage) {
        return COLOR.RED + "Unknown Command";
    }
}
