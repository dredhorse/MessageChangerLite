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

package team.cascade.spout.messagechanger.vanilla;

import org.spout.api.player.Player;
import team.cascade.spout.messagechanger.MessageChanger;
import team.cascade.spout.messagechanger.config.CONFIG;
import team.cascade.spout.messagechanger.enums.GAME_TYPES;
import team.cascade.spout.messagechanger.enums.TYPES;
import team.cascade.spout.messagechanger.enums.VANILLA_TYPES;
import team.cascade.spout.messagechanger.helper.Logger;
import team.cascade.spout.messagechanger.messages.MessagesInterface;

/**
 * Contains code to handle the vanilla specific messages
 *
 * @author $Author: dredhorse$
 * @version $FullVersion$
 */
public class VanillaMessagesHandler implements MessagesInterface {

    private static VanillaMessagesHandler instance;

    private VanillaMessages vanillaMessages;

    private MessageChanger main;


    public VanillaMessagesHandler(MessageChanger main) {
        instance = this;
        this.main = main;
        init();
    }

    public static VanillaMessagesHandler getInstance(){
        return instance;
    }

    public void init(){

        // let's parse through the found plugins and enable the corresponding classes
        for (TYPES types : main.getPLUGIN_TYPES(GAME_TYPES.VANILLA)){
            VANILLA_TYPES vanilla_types = (VANILLA_TYPES) types;
            if (vanilla_types == VANILLA_TYPES.DEFAULT){
                // we are vanilla default so let's see what we can do
                if (!CONFIG.VANILLA_SHOW_DEFAULT_DEATH_MESSAGES.getBoolean() && VanillaDeathEvents.getInstance() != null){
                    // we only do this once, first registering the messages
                    vanillaMessages = new VanillaMessages(main);
                    // than registering the Events
                    main.getEngine().getEventManager().registerEvents(new VanillaDeathEvents(main), main);
                }
            }

        }


    }


    @Override
    public String getNewMessage(String event, Player player, String defaultMessage) {
        String msg = "";

        return msg;
    }



    // *******************************************************************************************************************

    public void reload(){
        vanillaMessages.reloadDeathMessages();
    }

    public void save(){

    }
}
