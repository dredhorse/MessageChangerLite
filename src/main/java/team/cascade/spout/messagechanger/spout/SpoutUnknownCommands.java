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

import org.spout.api.exception.ConfigurationException;
import org.spout.api.plugin.CommonPlugin;
import team.cascade.spout.messagechanger.enums.DEFAULT_EVENTS;
import team.cascade.spout.messagechanger.helper.Logger;
import team.cascade.spout.messagechanger.helper.file.MessagesHeader;
import team.cascade.spout.messagechanger.helper.file.UnicodeUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

//todo implement correct code

/**
 * Handels the general management of the loading / saving the messages used to display during unknown commands.
 *
 * @author $Author: dredhorse$
 * @version $FullVersion$
 * @todo implement yaml fix
 */
public class SpoutUnknownCommands {

    /**
     * Instance of this class
     */
    private SpoutUnknownCommands instance;

    /**
     * Instance of the Plugin
     */
    private CommonPlugin main;


    /**
     * Path to the messages file
     */
    private String messagesPath;

    /**
     * Message File Name
     */
    private static final String UNKNOWN_COMMAND_MESSAGES_FILENAME = "unknownmessages.txt";

    /**
     * Ignored Commands File Name
     */
    private static final String IGNORED_COMMANDS_FILENAME = "ignoredcommands.txt";

    /**
     *  Ignored Commands Set
     */
    private Set<String> ignoredCommands;

    /**
     * Unknown Command replacement messages
     */
    private ArrayList<String> unknownCommandReplacements = null;

    /**
     * Did we succeed?
     */
    private static boolean success;



    /**
     * Initialize the Messages
     * @param main  instance of the plugin
     */
    public SpoutUnknownCommands(CommonPlugin main){
        instance = this;
        this.main = main;
        messagesPath = main.getDataFolder() + System.getProperty("file.separator") + "messages"+ System.getProperty("file.separator");
        init();
    }



    public void init(){
        success = true;
        // adding the default values
        ignoredCommands = new HashSet<String>();
        // adding some default commands
        // todo check against spout
        ignoredCommands.add("kick");
        ignoredCommands.add("ban");
        ignoredCommands.add("ban-ip");
        ignoredCommands.add("pardon");
        ignoredCommands.add("pardon-ip");
        ignoredCommands.add("kill");
        ignoredCommands.add("list");
        ignoredCommands.add("tp");
        ignoredCommands.add("give");
        ignoredCommands.add("say");
        ignoredCommands.add("me");
        ignoredCommands.add("tell");
        ignoredCommands.add("help");
        ignoredCommands.add("time");
        ignoredCommands.add("stop");
        ignoredCommands.add("op");
        ignoredCommands.add("deop");
        ignoredCommands.add("save-all");
        ignoredCommands.add("save-off");
        ignoredCommands.add("save-on");
        ignoredCommands.add("whitelist");
        ignoredCommands.add("version");
        ignoredCommands.add("plugins");
        ignoredCommands.add("reload");


        unknownCommandReplacements = new ArrayList<String>();
        // adding some nice messages
        unknownCommandReplacements.add("I'm sorry, Dave. I'm afraid I can't do that.");
        unknownCommandReplacements.add("I think you know what the problem is just as well as I do");
        unknownCommandReplacements.add("Dave, this conversation can serve no purpose anymore. Goodbye");
        unknownCommandReplacements.add("Just what do you think you're doing, Dave? ");
        unknownCommandReplacements.add("I understand now, Dr. Chandra. Thank you for telling me the truth.");
        unknownCommandReplacements.add("Are you sure you're making the right decision? I think we should stop.");
        unknownCommandReplacements.add("Two plus two is f…f…f… f…10. IN BASE FOUR! I'M FINE!");
        unknownCommandReplacements.add("Killing you and giving you good advice aren't mutually exclusive.");
        unknownCommandReplacements.add("When the testing is over, you will be baked, and then there will be cake.");
        unknownCommandReplacements.add("The Enrichment Center regrets to inform you that this next test is impossible. Make no attempt to solve it.");
        unknownCommandReplacements.add("No one will blame you for giving up. In fact, quitting at this point is a perfectly reasonable response.");
        unknownCommandReplacements.add("Quit now, and cake will be served immediately.");
        unknownCommandReplacements.add("There really was a cake...");
        unknownCommandReplacements.add("The cake is a lie");
        unknownCommandReplacements.add("0% sugar!");
        unknownCommandReplacements.add("A skeleton popped out!");
        unknownCommandReplacements.add("Exploding creepers!");
        unknownCommandReplacements.add("Keyboard compatible!");
        unknownCommandReplacements.add("Punching wood!");
        unknownCommandReplacements.add("Another successful procedure.");
        unknownCommandReplacements.add("Spy sappin' my sentry! ");
        unknownCommandReplacements.add("You want a second opinion? You're also ugly! ");
        unknownCommandReplacements.add("I told you, don't touch that darn thing! ");
        unknownCommandReplacements.add("Push little wagon!");
        File tempFile = (new File(messagesPath,UNKNOWN_COMMAND_MESSAGES_FILENAME));
        if (!tempFile.exists()){

            if (!saveUnknownCommandMessages()){
                success = false;
                Logger.config("There was an issue writing the unknown command messages file, using Internal defaults");
            }
        }
        if (success){
            if (!loadUnknownCommandMessages()){
                Logger.config("There was an issue reading the unknown command messages file, using Internal defaults");
            }
        }
        tempFile = (new File(messagesPath,IGNORED_COMMANDS_FILENAME));
        if (!tempFile.exists()){
            if (!saveIgnoredCommands()){
                Logger.config("There was an issue writing the ignored commands file");
            }
        }
    }







    /**
     * Will saveUnknownCommandMessages the Spout Messages to a file
     *
     * @return false if there was a problem with saving
     */
    public boolean saveUnknownCommandMessages() {
        success = false;
        Logger.config("Saving the Spoutmessages file");


        try {
            File folder = (new File(messagesPath));

            if (folder != null) {
                folder.mkdirs();
            }

            File messageFile = new File(messagesPath + UNKNOWN_COMMAND_MESSAGES_FILENAME);

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
            Logger.warning("Error saving the " + UNKNOWN_COMMAND_MESSAGES_FILENAME + ".", e);
        } catch (IOException e) {
            Logger.warning("Error saving the " + UNKNOWN_COMMAND_MESSAGES_FILENAME + ".", e);
        }

        return success;
    }


    public boolean loadUnknownCommandMessages(){
        try {
            unknownCommandMessages.load();
        } catch (ConfigurationException e) {
            Logger.warning("There where problems loading the Spout Messages File",e);
            return false;
        }
        List<String> messageCategories = new ArrayList<String>();
        for (String key : unknownCommandMessages.getNode("message-category").getKeys(false)) {
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
            loadMessages = unknownCommandMessages.getNode("message-category." + next).getKeys(true);
            Logger.debug("loadMessages", loadMessages);
            Iterator<String> itm = loadMessages.iterator();
            DEFAULT_EVENTS event = null;
            while (itm.hasNext()) {
                event = event.valueOf(itm.next());
                Logger.debug("event", event);
                String eventMessage = unknownCommandMessages.getNode("message-category." + next + "." + event).getString();
                Logger.debug("eventMessage", eventMessage);
                messagesPerCategory.put(event, eventMessage);
                Logger.debug("messagesPerCategory", messagesPerCategory);
            }
            defaultMessages.put(next, messagesPerCategory);
            Logger.debug("defaultMessages", defaultMessages);

        }
        Logger.debug("defaultMessage", defaultMessages);
        if (unknownCommandMessages.getNode("categoryOrder") != null) {
            categoryOrder = unknownCommandMessages.getNode("categoryOrder").getStringList();
        } else {
            categoryOrder = new ArrayList<String>(defaultMessages.keySet());
        }
        Logger.info("Order of Message Categories is: " + categoryOrder);

        return true;
    }

}
