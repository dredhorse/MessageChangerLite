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

import org.spout.api.exception.ConfigurationException;
import org.spout.api.plugin.CommonPlugin;
import org.spout.api.util.config.yaml.YamlConfiguration;
import team.cascade.spout.messagechanger.enums.DEFAULT_EVENTS;
import team.cascade.spout.messagechanger.helper.Logger;
import team.cascade.spout.messagechanger.helper.file.MessagesHeader;
import team.cascade.spout.messagechanger.helper.file.UnicodeUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Handels the general management of the loading / saving the messages used to display during the events.
 *
 * @author $Author: dredhorse$
 * @version $FullVersion$
 */
public class SpoutMessages {

    /**
     * Instance of this class
     */
    private SpoutMessages instance;

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
     * Path to the messages file
     */
    private String messagesPath;

    /**
     * Message File Name
     */
    private static final String SPOUTMESSAGES_FILENAME = "spoutmessages.yml";

    /**
     * Did we succeed?
     */
    private static boolean success;


    /**
     *
     */
    private final YamlConfiguration spoutMessagesConfig;


    /**
     * Initialize the Messages
     * @param main  instance of the plugin
     */
    public SpoutMessages(CommonPlugin main){
        instance = this;
        this.main = main;
        messagesPath = main.getDataFolder() + System.getProperty("file.separator") + "messages"+ System.getProperty("file.separator");
        spoutMessagesConfig = new YamlConfiguration(new File(messagesPath,SPOUTMESSAGES_FILENAME));
        init();
    }



    public void init(){
        success = true;
        // adding the default values
        defaultMessages = new HashMap<String, HashMap<DEFAULT_EVENTS, String>>();
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
        File tempFile = (new File(messagesPath,SPOUTMESSAGES_FILENAME));
        if (!tempFile.exists()){

            if (!save()){
                success = false;
                Logger.config("There was an issue writing the spoutmessages file, using Internal defaults");
            }
        }
        if (success){
            if (!load()){
                Logger.config("There was an issue reading the spoutmessages file, using Internal defaults");
            }
        }
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
     * Will save the Spout Messages to a file
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

            UnicodeUtil.saveUTF8File(messageFile, "# Order in which the categories are parsed.\n", true);
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


    public boolean load(){
        try {
            spoutMessagesConfig.load();
        } catch (ConfigurationException e) {
            Logger.warning("There where problems loading the Spout Messages File",e);
            return false;
        }
        List<String> messageCategories = new ArrayList<String>();
        for (String key : spoutMessagesConfig.getNode("message-category").getKeys(false)) {
            messageCategories.add(key);
        }
        Logger.debug("messageCategories", messageCategories);
        Iterator<String> it = messageCategories.iterator();
        HashMap<DEFAULT_EVENTS,String>messagesPerCategory;
        String next = null;
        while (it.hasNext()) {
            messagesPerCategory = new HashMap<DEFAULT_EVENTS, String>();
            next = it.next();
            Logger.debug("next", next);
            Set<String> loadMessages;
            loadMessages = spoutMessagesConfig.getNode("message-category." + next).getKeys(true);
            Logger.debug("loadMessages", loadMessages);
            Iterator<String> itm = loadMessages.iterator();
            DEFAULT_EVENTS event = null;
            while (itm.hasNext()) {
                event = event.valueOf(itm.next());
                Logger.debug("event", event);
                String eventMessage = spoutMessagesConfig.getNode("message-category." + next + "." + event).getString();
                Logger.debug("eventMessage", eventMessage);
                messagesPerCategory.put(event, eventMessage);
                Logger.debug("messagesPerCategory", messagesPerCategory);
            }
            defaultMessages.put(next, messagesPerCategory);
            Logger.debug("defaultMessages", defaultMessages);

        }
        Logger.debug("defaultMessage", defaultMessages);
        if (spoutMessagesConfig.getNode("categoryOrder") != null) {
            categoryOrder = spoutMessagesConfig.getNode("categoryOrder").getStringList();
        } else {
            categoryOrder = new ArrayList<String>(defaultMessages.keySet());
        }
        Logger.info("Order of Message Categories is: " + categoryOrder);

        return true;
    }

}
