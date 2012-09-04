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

package team.cascade.spout.messagechanger.spout.UnknownCommand;

import org.spout.api.exception.ConfigurationException;
import org.spout.api.plugin.CommonPlugin;
import org.spout.api.util.config.yaml.YamlConfiguration;
import team.cascade.spout.messagechanger.helper.Logger;
import team.cascade.spout.messagechanger.helper.file.MessagesHeader;
import team.cascade.spout.messagechanger.helper.file.UnicodeUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
    private static final String UNKNOWN_COMMAND_MESSAGES_FILENAME = "unknownmessages.yml";

    /**
     * Ignored Commands File Name
     */
    private static final String IGNORED_COMMANDS_FILENAME = "ignoredcommands.yml";

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
     * Unknown Command Config
     */
    private final YamlConfiguration unknownCommandConfig;

    /**
     * Ignored commands config
     */
    private final YamlConfiguration ignoredCommandsConfig;


    /**
     * Initialize the Messages
     * @param main  instance of the plugin
     */
    public SpoutUnknownCommands(CommonPlugin main){
        instance = this;
        this.main = main;
        messagesPath = main.getDataFolder() + System.getProperty("file.separator") + "messages"+ System.getProperty("file.separator");
        unknownCommandConfig = new YamlConfiguration(new File(messagesPath,UNKNOWN_COMMAND_MESSAGES_FILENAME));
        ignoredCommandsConfig = new YamlConfiguration(new File(messagesPath,IGNORED_COMMANDS_FILENAME));
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
        unknownCommandReplacements.add("{{Red}}I'm sorry, Dave. I'm afraid I can't do that.");
        unknownCommandReplacements.add("{{Red}}I think you know what the problem is just as well as I do");
        unknownCommandReplacements.add("{{Red}}Dave, this conversation can serve no purpose anymore. Goodbye");
        unknownCommandReplacements.add("{{Red}}Just what do you think you're doing, Dave? ");
        unknownCommandReplacements.add("{{Red}}I understand now, Dr. Chandra. Thank you for telling me the truth.");
        unknownCommandReplacements.add("{{Red}}Are you sure you're making the right decision? I think we should stop.");
        unknownCommandReplacements.add("{{Red}}Two plus two is f…f…f… f…10. IN BASE FOUR! I'M FINE!");
        unknownCommandReplacements.add("{{Red}}Killing you and giving you good advice aren't mutually exclusive.");
        unknownCommandReplacements.add("{{Red}}When the testing is over, you will be baked, and then there will be cake.");
        unknownCommandReplacements.add("{{Red}}The Enrichment Center regrets to inform you that this next test is impossible. Make no attempt to solve it.");
        unknownCommandReplacements.add("{{Red}}No one will blame you for giving up. In fact, quitting at this point is a perfectly reasonable response.");
        unknownCommandReplacements.add("{{Red}}Quit now, and cake will be served immediately.");
        unknownCommandReplacements.add("{{Red}}There really was a cake...");
        unknownCommandReplacements.add("{{Red}}The cake is a lie");
        unknownCommandReplacements.add("{{Red}}0% sugar!");
        unknownCommandReplacements.add("{{Red}}A skeleton popped out!");
        unknownCommandReplacements.add("{{Red}}Exploding creepers!");
        unknownCommandReplacements.add("{{Red}}Keyboard compatible!");
        unknownCommandReplacements.add("{{Red}}Punching wood!");
        unknownCommandReplacements.add("{{Red}}Another successful procedure.");
        unknownCommandReplacements.add("{{Red}}Spy sappin' my sentry! ");
        unknownCommandReplacements.add("{{Red}}You want a second opinion? You're also ugly! ");
        unknownCommandReplacements.add("{{Red}}I told you, don't touch that darn thing! ");
        unknownCommandReplacements.add("{{Red}}Push little wagon!");
        unknownCommandReplacements.add("{{Red}}#Ignore this line!");
        unknownCommandReplacements.add("{{Red}}It's time to play spout and chew bubblegum...");
        unknownCommandReplacements.add("{{Red}}Please stand by while your brain is uploaded");
        unknownCommandReplacements.add("{{Red}}Do You spout the difference?");
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
                success = false;
                Logger.config("There was an issue writing the ignored commands file");
            }
        }
        if (success){
            if (!loadIgnoredCommands()){
                Logger.config("There was an issue reading the ignored commands file, using Internal values");
            }
        }
    }







    /**
     * Will save the Unknown Command Messsages to a file
     *
     * @return false if there was a problem with saving
     */
    public boolean saveUnknownCommandMessages() {
        success = false;
        Logger.config("Saving the Unknown Command messages file");


        try {
            File folder = (new File(messagesPath));

            if (folder != null) {
                folder.mkdirs();
            }

            File messageFile = new File(messagesPath + UNKNOWN_COMMAND_MESSAGES_FILENAME);

            Logger.config("Writing the messages");
            MessagesHeader.saveTranslationHeader(messageFile, "Unknown Command Messages");

            UnicodeUtil.saveUTF8File(messageFile,"\n",true);
            UnicodeUtil.saveUTF8File(messageFile,"unkownCommandMessages:\n",true);
            Iterator<String> iterator = unknownCommandReplacements.iterator();
            String message;
            while (iterator.hasNext()) {
                message = iterator.next();
                UnicodeUtil.saveUTF8File(messageFile,"     - \"" + message +"\"\n",true);
            }
            UnicodeUtil.saveUTF8File(messageFile, "\n", true);
            Logger.config("Finished writing the Unknown Command Messages file");
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
            unknownCommandConfig.load();
        } catch (ConfigurationException e) {
            Logger.warning("There where problems loading the Unknown Command Messages File",e);
            return false;
        }
        unknownCommandReplacements = new ArrayList(unknownCommandConfig.getNode("unkownCommandMessages").getList());

        Logger.debug("Unknown Command Messages",unknownCommandReplacements.toString());
        // adding some copyright :-)
        unknownCommandReplacements.add("{{GOLD}}An unknown command, brought to you by MessageChanger");
        unknownCommandReplacements.add("{{GOLD}}An unknown command, consider donating if you like this");
        return true;
    }


    /**
      * Will save the Unknown Command Messsages to a file
      *
      * @return false if there was a problem with saving
      */
     public boolean saveIgnoredCommands() {
         success = false;
         Logger.config("Saving the Ignored Commands file");


         try {
             File folder = (new File(messagesPath));

             if (folder != null) {
                 folder.mkdirs();
             }

             File messageFile = new File(messagesPath + IGNORED_COMMANDS_FILENAME);

             Logger.config("Writing the messages");
             MessagesHeader.saveTranslationHeader(messageFile, "Unknown Command - Ignored Commands");

             UnicodeUtil.saveUTF8File(messageFile,"\n",true);
             UnicodeUtil.saveUTF8File(messageFile,"ignoredCommands:\n",true);
             Iterator<String> iterator = ignoredCommands.iterator();
             String message;
             while (iterator.hasNext()) {
                 message = iterator.next();
                 UnicodeUtil.saveUTF8File(messageFile,"     - \"" + message +"\"\n",true);
             }
             UnicodeUtil.saveUTF8File(messageFile, "\n", true);
             Logger.config("Finished writing the Ignored Commands file");
             success = true;
         } catch (FileNotFoundException e) {
             Logger.warning("Error saving the " + IGNORED_COMMANDS_FILENAME + ".", e);
         } catch (IOException e) {
             Logger.warning("Error saving the " + IGNORED_COMMANDS_FILENAME + ".", e);
         }

         return success;
     }


     public boolean loadIgnoredCommands(){
         try {
             ignoredCommandsConfig.load();
         } catch (ConfigurationException e) {
             Logger.warning("There where problems loading the Ignored Commands File",e);
             return false;
         }
         ignoredCommands = new HashSet(ignoredCommandsConfig.getNode("ignoredCommands").getList());

         Logger.debug("Ignored Commands",ignoredCommands.toString());

         return true;
     }

    public void load(){
        if (!loadUnknownCommandMessages()|| !loadIgnoredCommands()){
            Logger.config("There was a problem loading the unknown command files");
        }
    }

    public void save(){
        if (!saveIgnoredCommands() || !saveUnknownCommandMessages()){
            Logger.config("There was a problem saving the unknown command files");
        }
    }

    public SpoutUnknownCommands getInstance() {
        return instance;
    }

    public void setInstance(SpoutUnknownCommands instance) {
        this.instance = instance;
    }

    public Set<String> getIgnoredCommands() {
        return ignoredCommands;
    }

    public void setIgnoredCommands(Set<String> ignoredCommands) {
        this.ignoredCommands = ignoredCommands;
    }

    public ArrayList<String> getUnknownCommandReplacements() {
        return unknownCommandReplacements;
    }

    public void setUnknownCommandReplacements(ArrayList<String> unknownCommandReplacements) {
        this.unknownCommandReplacements = unknownCommandReplacements;
    }
}
