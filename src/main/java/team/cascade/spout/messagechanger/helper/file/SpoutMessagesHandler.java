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

package team.cascade.spout.messagechanger.helper.file;

import org.spout.api.plugin.CommonPlugin;
import team.cascade.spout.messagechanger.enums.DEFAULT_EVENTS;
import team.cascade.spout.messagechanger.helper.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Handels the general management of the loading / saving the messages used to display during the events.
 *
 * @author $Author: dredhorse$
 * @version $FullVersion$
 */
public class SpoutMessagesHandler {

    /**
     * Instance of this class
     */
    private SpoutMessagesHandler instance;

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

    private String messagesPath;
    private static boolean success;
    private static final String SPOUTMESSAGES_FILENAME = "spoutmessages.yml";


    /**
     * Initialize the Messages
     * @param main  instance of the plugin
     */
    public SpoutMessagesHandler(CommonPlugin main){
        instance = this;
        this.main = main;
        messagesPath = main.getDataFolder() + System.getProperty("file.separator") + "messages"+ System.getProperty("file.separator");
        init();
    }



    private void init(){
        // adding the default values
        HashMap<DEFAULT_EVENTS,String> temp = new HashMap<DEFAULT_EVENTS, String>();
        temp.put(DEFAULT_EVENTS.CHANGED_WORLD,"Welcome traveler from %(fromWorld) in %(world)");
        temp.put(DEFAULT_EVENTS.KICK_BANNED,"%(msg)");
        temp.put(DEFAULT_EVENTS.KICK_FULL,"Man... tight in here, we are full");
        temp.put(DEFAULT_EVENTS.KICK_KICK_LEAVEMSG,"%(player) was shown the door}");
        temp.put(DEFAULT_EVENTS.KICK_KICK_REASON,"%(msg)");
        temp.put(DEFAULT_EVENTS.KICK_WHITELIST,"%(msg)");
        temp.put(DEFAULT_EVENTS.PLAYER_JOIN,"Hello &b%(player)&f  in world %(world)");
        temp.put(DEFAULT_EVENTS.PLAYER_QUIT,"Say byebye to Player %(player)");
        temp.put(DEFAULT_EVENTS.SERVER_STOP,"Testing the server...");
        defaultMessages.put("default",temp);
        temp = new HashMap<DEFAULT_EVENTS, String>();
        temp.put(DEFAULT_EVENTS.PLAYER_JOIN,"Welcome the admin");
        defaultMessages.put("permnode1",temp);
        temp = new HashMap<DEFAULT_EVENTS, String>();
        temp.put(DEFAULT_EVENTS.SERVER_STOP,"Oh well...");
        defaultMessages.put("permnode2",temp);
         save();
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


    /**
     * Will save the message translations to a file
     *
     * @return false if there was a problem with saving
     */
    public boolean save() {
        success = false;
        Logger.config("Saving the Spoutmessages file");


        try {
            File folder = (new File(messagesPath));

            if (folder != null) {
                folder.mkdirs();
            }

            File messageFile = new File(messagesPath + SPOUTMESSAGES_FILENAME);

            Logger.config("Writing the messages");
            MessagesHeader.saveTranslationHeader(messageFile, "SpoutMessages");

            UnicodeUtil.saveUTF8File(messageFile,"# Order in which the categories are parsed.\n",true);
            UnicodeUtil.saveUTF8File(messageFile,"# Categories are permission nodes. You only specify the\n",true);
            UnicodeUtil.saveUTF8File(messageFile,"# XXXXXXX part of messagechanger.message.XXXXXXXX here\n",true);
            UnicodeUtil.saveUTF8File(messageFile,"# Most important category (permission) first\n",true);
            UnicodeUtil.saveUTF8File(messageFile,"categoryOrder: " + categoryOrder+"\n",true);
            UnicodeUtil.saveUTF8File(messageFile,"\n",true);
            UnicodeUtil.saveUTF8File(messageFile,"message-category:\n",true);
            Iterator<String> categories = defaultMessages.keySet().iterator();
            String category;
            while (categories.hasNext()) {
                category = categories.next();
                UnicodeUtil.saveUTF8File(messageFile,"    " + category + ":\n",true);
                HashMap<DEFAULT_EVENTS, String> messagesPerCategory = defaultMessages.get(category.toLowerCase());
                if (category.equalsIgnoreCase("default")) {
                    for (DEFAULT_EVENTS event : DEFAULT_EVENTS.values()) {
                        UnicodeUtil.saveUTF8File(messageFile, "        " + event.toString() + ": \"" + messagesPerCategory.get(event) + "\"\n", true);
                    }
                } else {

                    Iterator<DEFAULT_EVENTS> events = messagesPerCategory.keySet().iterator();
                    DEFAULT_EVENTS event;
                    while (events.hasNext()) {
                        event = events.next();
                        UnicodeUtil.saveUTF8File(messageFile,"        " + event.toString() + ": \"" + messagesPerCategory.get(event) + "\"\n",true);
                    }
                }
            }
            UnicodeUtil.saveUTF8File(messageFile, "\n", true);
            Logger.config("Finished writing the SpoutMmessages file");
            success = true;
        } catch (FileNotFoundException e) {
            Logger.warning("Error saving the " + SPOUTMESSAGES_FILENAME + ".", e);
        } catch (IOException e) {
            Logger.warning("Error saving the " + SPOUTMESSAGES_FILENAME + ".", e);
        }

        return success;
    }

}
