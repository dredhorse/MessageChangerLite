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

package net.breiden.spout.messagechanger.helper.file;

import net.breiden.spout.messagechanger.enums.DEFAULT_EVENTS;
import org.spout.api.plugin.CommonPlugin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Handels the general management of the loading / saving the messages used to display during the events.
 *
 * @author $Author: dredhorse$
 * @version $FullVersion$
 */
public class DefaultMessagesHandler {

    /**
     * Instance of this class
     */
    private DefaultMessagesHandler instance;

    /**
     * Instance of the Plugin
     */
    private CommonPlugin main;

    /**
     * Contains a mapping between DEFAULT / PERMISSION.NODE and EVENT , MESSAGE for the Server Default Messages
     *
     */
    private HashMap<String, HashMap<DEFAULT_EVENTS, String>> defaultMessages = new HashMap<String, HashMap<DEFAULT_EVENTS, String>>();


    /**
     * Order of the permission nodes
     */
    private List<String> categoryOrder = Arrays.asList("permnode1", "permnode2");


    /**
     * Initialize the Messages
     * @param main  instance of the plugin
     */
    public DefaultMessagesHandler(CommonPlugin main){
        instance = this;
        this.main = main;
        init();
    }



    private void init(){
        // adding the default values
        HashMap<DEFAULT_EVENTS,String> temp = new HashMap<DEFAULT_EVENTS, String>();
        temp.put(DEFAULT_EVENTS.CHANGED_WORLD,"Welcome traveler from %(fromWorld) in %(world)");
        temp.put(DEFAULT_EVENTS.KICK_BANNED,"%(msg)");
        temp.put(DEFAULT_EVENTS.KICK_FULL,"%(msg)");
        temp.put(DEFAULT_EVENTS.KICK_KICK_LEAVEMSG,"%(msg)");
        temp.put(DEFAULT_EVENTS.KICK_KICK_REASON,"%(msg)");
        temp.put(DEFAULT_EVENTS.KICK_KICK_REASON,"%(msg)");
        temp.put(DEFAULT_EVENTS.KICK_WHITELIST,"%(msg)");
        temp.put(DEFAULT_EVENTS.PLAYER_JOIN,"Hello &b%(player)&f  in world %(world)");
        temp.put(DEFAULT_EVENTS.PLAYER_QUIT,"%(msg)");
        temp.put(DEFAULT_EVENTS.SERVER_STOP,"Testing the server...");
        defaultMessages.put("default",temp);
        temp = new HashMap<DEFAULT_EVENTS, String>();
        temp.put(DEFAULT_EVENTS.PLAYER_JOIN,"Welcome the admin");
        defaultMessages.put("permnode1",temp);
        temp = new HashMap<DEFAULT_EVENTS, String>();
        temp.put(DEFAULT_EVENTS.SERVER_STOP,"Oh well...");
        defaultMessages.put("permnode2",temp);

        //todo loading and saving the messages


    }




    /**
     * Return the default event message
     *
     * @param event
     * @return
     */
    public String getMessage (DEFAULT_EVENTS event){
        return defaultMessages.get("default").get(event);
    }


    /**
     * Return the event message for a certain permission node
     *
     * @param permNode
     * @param event
     * @return
     */
    public String getMessage (String permNode, DEFAULT_EVENTS event){
        if (defaultMessages.containsKey(permNode.toLowerCase())){
            return defaultMessages.get(permNode.toLowerCase()).get(event);
        }
        return getMessage(event);
    }

    public List<String> getCategoryOrder(){
        return categoryOrder;
    }

}
