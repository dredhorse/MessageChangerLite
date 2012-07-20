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
        vanillaMessages = new VanillaMessages(main);
        main.getEngine().getEventManager().registerEvents(new VanillaDeathEvents(main), main);
        this.main = main;
    }

    public static VanillaMessagesHandler getInstance(){
        return instance;
    }


    @Override
    public String getNewMessage(String event, Player player, String defaultMessage) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    // *******************************************************************************************************************

    public void reload(){
        vanillaMessages.reloadDeathMessages();
    }

    public void save(){
        vanillaMessages.saveDeathMessages();
    }
}
