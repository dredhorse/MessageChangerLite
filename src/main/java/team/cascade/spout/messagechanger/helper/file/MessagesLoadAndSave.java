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

//~--- non-JDK imports --------------------------------------------------------

import team.cascade.spout.messagechanger.config.CONFIG;
import team.cascade.spout.messagechanger.helper.Logger;
import team.cascade.spout.messagechanger.messages.MESSAGES;
import org.spout.api.plugin.CommonPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//~--- JDK imports ------------------------------------------------------------

//~--- classes ----------------------------------------------------------------

/**
 * Loading and saving the Message Properties
 *
 * @author $Author: dredhorse$
 * @version $FullVersion$
 */
public final class MessagesLoadAndSave {
    /**
     * Line for logger
     */
    private static final String LINE = "=====================================";

    /**
     * Filename of the messages property file
     */
    private static final String PROPERTY_FILENAME = "messages.properties";

    /**
     * success indicator
     */
    private static boolean success = false;

    /**
     * Messages which will be passed back to {@Link MESSAGES}
     */
    private static Map<String, String> msg = new HashMap<String, String>();

    /**
     * Reference to the plugin
     */
    private static CommonPlugin plugin;

    /**
     * The path to the plugin configuration
     */
    private static String pluginPath;

    /**
     * The properties for intermediate storage
     */
    private static Properties props;


    private MessagesLoadAndSave() {
        // constructor is never called
    }

    //~--- methods ------------------------------------------------------------

    /**
     * Initializing the Messages, this will create the file if not already there. If there is a problem creating the file <br>
     * internal defaults will be used. If there is a problem loading the file already loaded messages and internal defaults will be used and a save is tried. <br>
     *
     * @param main the plugin
     */
    public static void messageInit(CommonPlugin main) {
        Logger.config(LINE);
        Logger.config("Reading messages");
        plugin = main;
        pluginPath = plugin.getDataFolder() + System.getProperty("file.separator");

        if (!PropertiesLoader.propertyFileExist(new File(pluginPath), PROPERTY_FILENAME)) {
            Logger.debug("Translation file doesn't exist, creating default");

            if (!save()) {
                Logger.config("Using Internal Defaults");

                return;
            }
        }

        if (!load()) {
            Logger.debug("Also using Internal Defaults");
            Logger.debug("Saving new Defaults");
            save();
        }
    }

    /**
     * Will load the translations and returns false if a message node wasn't found, this allows for automatic updating <br>
     * of the file if called during the INIT phase.
     *
     * @return false if a message node wasn't found
     */
    public static boolean load() {
        success = true;
        Logger.config("Reading the translations");
        Logger.debug("pluginpath", pluginPath);
        Logger.debug("propertyFile", PROPERTY_FILENAME);
        msg.clear();
        msg = PropertiesLoader.read(new File(pluginPath), PROPERTY_FILENAME);

        for (MESSAGES node : MESSAGES.values()) {
            Logger.debug("Property: " + node.toNode() + " Value: " + msg.get(node.toNode()));

            if (msg.get(node.toNode()) != null) {
                node.setMessage(msg.get(node.toNode()));
            } else {
                Logger.severe("No translation found in " + PROPERTY_FILENAME + " for " + node.toNode());
                success = false;
            }

            Logger.debug("Translation for " + node.toNode() + " = [" + node.getMessage() + "]");
        }

        return success;
    }

    /**
     * Will save the message translations to a file
     *
     * @return false if there was a problem with saving
     */
    public static boolean save() {
        success = false;
        Logger.config("Saving the messages file");


        try {
            File folder = plugin.getDataFolder();

            if (folder != null) {
                if (!folder.mkdirs()){
                    Logger.debug("There was an issue during creation of the plugin directory");
                }
            }

            File messageFile = new File(pluginPath + PROPERTY_FILENAME);

            Logger.config("Writing the translations");
            PropertiesHeader.saveTranslationHeader(messageFile);

            for (MESSAGES node : MESSAGES.values()) {
                UnicodeUtil.saveUTF8File(messageFile, "#   " + node.getComment() + "\n", true);
                UnicodeUtil.saveUTF8File(messageFile, node.toNode() + "=\"" + node.getMessage() + "\"\n", true);
                Logger.debug("Saving: " + node.toNode() + " with [" + node.getMessage() + "]");
            }
            UnicodeUtil.saveUTF8File(messageFile, "\n", true);
            Logger.config("Finished writing the messages file");
            success = true;
        } catch (FileNotFoundException e) {
            Logger.warning("Error saving the " + PROPERTY_FILENAME + ".", e);
        } catch (IOException e) {
            Logger.warning("Error saving the " + PROPERTY_FILENAME + ".", e);
        }

        return success;
    }

    /**
     * Reloading will save the messages if CONFIG_AUTO_SAVE is true, if there is a problem saving we will not read the file again.<br>
     * If we had success saving or we do not save at all we load the messages translations again.
     *
     * @return
     */
    public static boolean reload() {
        Logger.config("Reloading the message translations");
        success = true;

        if (CONFIG.CONFIG_AUTO_SAVE.getBoolean()) {
            success = save();
        }

        if (!success) {
            Logger.config("There where some issues saving the message translations");
            Logger.config("As safety we are not loading the message translations again.");
        } else {
            success = load();
        }

        return success;
    }
}
