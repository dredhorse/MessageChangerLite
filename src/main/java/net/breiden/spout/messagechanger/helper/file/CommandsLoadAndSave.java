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

//~--- non-JDK imports --------------------------------------------------------

import net.breiden.spout.messagechanger.commands.COMMANDS;
import net.breiden.spout.messagechanger.config.CONFIG;
import net.breiden.spout.messagechanger.helper.Logger;
import org.spout.api.plugin.CommonPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//~--- JDK imports ------------------------------------------------------------

//~--- classes ----------------------------------------------------------------

/**
 * Loading and Saving the COMMANDS properties
 *
 * @author $Author: dredhorse$
 * @version $FullVersion$
 */
@SuppressWarnings("ALL")
public final class CommandsLoadAndSave {
    /**
     * Line for logger
     */
    private static final String LINE = "=====================================";

    /**
     * Filename of the commands property file
     */
    private static final String PROPERTY_FILENAME = "commands.properties";

    /**
     * Tracking success
     */
    private static boolean success = false;

    /**
     * Storage for messages
     */
    private static Map<String, String> msg = new HashMap<String, String>();

    /**
     * Reference to the plugin
     */
    private static CommonPlugin plugin;

    /**
     * Path to the plugin configuration
     */
    private static String pluginPath;

    //~--- constant enums -----------------------------------------------------

    /**
     * Enum for handling the 3 different information values a Command can have
     */
    private enum FIELDS {
        Description, Usage, Alias
    }

    private CommandsLoadAndSave() {
        // constructor is never called
    }

    //~--- methods ------------------------------------------------------------

    /**
     * Initializing the Commands information, this will create the file if not already there. If there is a problem creating the file <br>
     * internal defaults will be used. If there is a problem loading the file already loaded information and internal defaults will be used and a save is tried. <br>
     *
     * @param main
     */
    public static void commandsInit(CommonPlugin main) {
        Logger.config(LINE);
        Logger.config("Reading commands");
        plugin = main;
        pluginPath = plugin.getDataFolder() + System.getProperty("file.separator");

        if (!PropertiesLoader.propertyFileExist(new File(pluginPath), PROPERTY_FILENAME)) {
            Logger.debug("Command Translation file doesn't exist, creating default");

            if (!save()) {
                Logger.config("Using Internal Defaults");

                return;
            }
        }

        if (!load()) {
            Logger.debug("Using Internal Defaults");
            Logger.debug("Saving new Defaults");
            save();
        }
    }

    /**
     * Will load the translations and returns false if a command node wasn't found, this allows for automatic updating <br>
     * of the file if called during the INIT phase.
     *
     * @return false if a command node wasn't found
     */
    public static boolean load() {
        String nodeString;

        success = true;
        Logger.config("Reading the command translations");
        Logger.debug("pluginpath", pluginPath);
        Logger.debug("propertyFile", PROPERTY_FILENAME);
        msg.clear();
        msg = PropertiesLoader.read(new File(pluginPath), PROPERTY_FILENAME);

        for (COMMANDS node : COMMANDS.values()) {
            for (FIELDS field : FIELDS.values()) {
                nodeString = node.toString() + "." + field.toString();
                Logger.debug("Property: " + nodeString + " Value: " + msg.get(nodeString));

                if (msg.get(nodeString) != null) {
                    if (FIELDS.valueOf(field.toString()) == FIELDS.Description) {
                        node.setDescription(msg.get(nodeString));
                        Logger.debug("Translation for " + nodeString + " = [" + node.getCmdDescription() + "]");
                    }

                    if (FIELDS.valueOf(field.toString()) == FIELDS.Usage) {
                        node.setUsage(msg.get(nodeString));
                        Logger.debug("Translation for " + nodeString + " = [" + node.getUsage() + "]");
                    }

                    if (FIELDS.valueOf(field.toString()) == FIELDS.Alias) {
                        node.setAliases(msg.get(nodeString).replace("[", "").replace("]", "").split(","));
                        Logger.debug("Translation for " + nodeString + " = ["
                                + Arrays.toString(node.getAliases()) + "]");
                    }
                } else {
                    Logger.severe("No translation found in " + PROPERTY_FILENAME + " for " + nodeString);
                    success = false;
                }
            }
        }

        return success;
    }

    /**
     * Will save the command information translations to a file
     *
     * @return false if there was a problem with saving
     */
    public static boolean save() {
        success = false;
        Logger.config("Saving the commands file");

        try {
            final File folder = plugin.getDataFolder();

            if (folder != null) {
                if (!folder.mkdirs()){
                 Logger.debug("There was an issue during creation of the plugin directory");
                }
            }

            final File commandProperties = new File(pluginPath + PROPERTY_FILENAME);

            Logger.config("Writing the translations");
            PropertiesHeader.saveTranslationHeader(commandProperties);

            for (COMMANDS node : COMMANDS.values()) {
                UnicodeUtil.saveUTF8File(commandProperties, "\n", true);
                UnicodeUtil.saveUTF8File(commandProperties, "#   " + node.toString() + " Command\n", true);
                UnicodeUtil.saveUTF8File(commandProperties, "#   " + node.getCmdDescription() + "\n", true);
                UnicodeUtil.saveUTF8File(commandProperties, node.toString() + ".Description=\"" + node.getCmdDescription() + "\"\n", true);
                Logger.debug("Saving: " + node.toString() + ".Description with [" + node.getCmdDescription()
                        + "]");
                UnicodeUtil.saveUTF8File(commandProperties, node.toString() + ".Usage=\"" + node.getUsage() + "\"\n", true);
                Logger.debug("Saving: " + node.toString() + ".Usage with [" + node.getUsage() + "]");
                UnicodeUtil.saveUTF8File(commandProperties, node.toString() + ".Alias=" + Arrays.toString(node.getAliases()) + "\n", true);
                Logger.debug("Saving: " + node.toString() + ".Alias with [" + Arrays.toString(node.getAliases())
                        + "]");
            }

            UnicodeUtil.saveUTF8File(commandProperties, "\n", true);
            Logger.config("Finished writing the commands file");
            success = true;
        } catch (FileNotFoundException e) {
            Logger.warning("Error saving the " + PROPERTY_FILENAME + ".", e);
        } catch (IOException e) {
            Logger.warning("Error saving the " + PROPERTY_FILENAME + ".", e);
        }

        return success;
    }

    /**
     * Method to reload the commands translations, will save first if CONFIG_AUTO_SAVE is true
     * Doesn't really change the commands yet when they are already loaded, that needs to be implemented.
     *
     * @return true if reload was successfully
     */
    public static boolean reload() {
        Logger.config("Reloading the commands translations");
        success = true;

        if (CONFIG.CONFIG_AUTO_SAVE.getBoolean()) {
            success = save();
        }

        if (!success) {
            Logger.config("There where some issues saving the commands translations");
            Logger.config("As safety we are not loading the commands translations again.");
        } else {
            success = load();

            // todo implement applying the new stuff to the already existing commands
        }

        return success;
    }
}
