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

package net.breiden.spout.messagechanger;

//~--- non-JDK imports --------------------------------------------------------

import net.breiden.spout.messagechanger.commands.EnumMessageChanger;
import net.breiden.spout.messagechanger.config.CONFIG;
import net.breiden.spout.messagechanger.enums.GAME_TYPES;
import net.breiden.spout.messagechanger.enums.TYPES;
import net.breiden.spout.messagechanger.events.SpoutPluginEvents;
import net.breiden.spout.messagechanger.exceptions.ConfigNotAvailableException;
import net.breiden.spout.messagechanger.helper.Logger;
import net.breiden.spout.messagechanger.helper.Metrics;
import net.breiden.spout.messagechanger.helper.commands.EnumAnnotatedCommandRegistrationFactory;
import net.breiden.spout.messagechanger.helper.commands.EnumSimpleAnnotatedCommandExecutorFactory;
import net.breiden.spout.messagechanger.helper.commands.EnumSimpleInjector;
import net.breiden.spout.messagechanger.helper.config.CommentConfiguration;
import net.breiden.spout.messagechanger.helper.config.Configuration;
import net.breiden.spout.messagechanger.helper.file.CommandsLoadAndSave;
import net.breiden.spout.messagechanger.messages.SpoutMessages;
import org.spout.api.Engine;
import org.spout.api.Spout;
import org.spout.api.command.CommandRegistrationsFactory;
import org.spout.api.player.Player;
import org.spout.api.plugin.CommonPlugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import static net.breiden.spout.messagechanger.helper.file.CommandsLoadAndSave.commandsInit;

//~--- JDK imports ------------------------------------------------------------

//~--- classes ----------------------------------------------------------------

/**
 * MessageChanger is a plugin allowing you to change the default messages of Spout / Vanilla / and other games (hopefully)
 * based on permissions. It also allows changing of the default deathmessages with random ones based on the deathcause.
 *
 * @author $Author: dredhorse$
 * @version $FullVersion$
 */
public class MessageChanger extends CommonPlugin {


    private HashSet<TYPES> NULL_TYPES = new HashSet<TYPES>(0);

    /**
     * Needed during shutdown event to think the player is kicked
     */
    private boolean ignoreKick = false;

    SpoutMessages spoutMessages;

    private HashMap<GAME_TYPES,HashSet<TYPES>> types = new HashMap<GAME_TYPES, HashSet<TYPES>>();

    /**
     * Instance of the plugin
     */
    private static MessageChanger instance;


    //~--- fields -------------------------------------------------------------

    /**
     * Reference to the Configuration
     */
    private Configuration configuration;

    /**
     * The Engine running the plugin, can be Client or Server
     */
    private Engine engine;

    /**
     * Reference to the command Registration Factory
     */
    private CommandRegistrationsFactory<Class<?>> commandRegFactory = null;

    //~--- methods ------------------------------------------------------------

    /**
     * This method is triggered during the loading of the plugin, only implement code which is really
     * needed before the plugin is enabled.
     */
    @Override
    public void onLoad() {
        instance = this;

        /** Logger.getLogger (this) is required if you use Logger log; */
        Logger.getLogger(this);

        /** from here onwards you can use log.whatever or Logger.whatever, see for yourself */
        Logger.debug("Loading");
        engine = getEngine();
    }

    /**
     * This method is triggered when the plugin is enabled. You should implement your main central code in here.
     * <br>
     * In the code here you will find a lot of examples what you are able to do with these classes, but also necessary
     * code to make the classes work.
     * <br>
     * Make changes wisely.
     */
    @Override
    public void onEnable() {


        // creating the new configuration for this plugin
        configuration = new Configuration(this);
        Logger.debug("Adding configuration not directly generated via the ENUMS");

        /**
         * From here onwards it is really up to you.. depending on what configuration options you integrated
         */

        // todo enter special configuration in here

        /**
         * OK, THIS is really needed now..
         */

        // lets initialize the config, which means if not there write the default to file and read it, otherwise just read it
        try {
            configuration.initializeConfig();
        } catch (ConfigNotAvailableException e) {
            Logger.warning("Well...no config.");
        }

        /**
         * Now let's init the Spout messages
         */
        spoutMessages = new SpoutMessages(this);

        /**
         * Let's init the Plugin Listener to handle the rest of the messages
         */
        new SpoutPluginEvents(this);

        /*
         * Now let's read the translations for the commands in game
         */
        commandsInit(this);

        /**
         * Enabling the commands
         */
        Logger.config("Enabling Commands now!");


        // couldn't get easier or?
        registerCommand(EnumMessageChanger.class);


        /**
         * Enable Metrics  https://github.com/Hidendra/mcstats.org/wiki
         */

        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            Logger.debug("Problems with contacting mcstats.org", e);
        }


        // and now we are done..
        Logger.enableMsg();
    }

    /**
     * This method is called during disabling of the plugin, this should hold your
     * saving and other cleanup code.
     */
    @Override
    public void onDisable() {
        ServerDownEvent();
        if (CONFIG.CONFIG_AUTO_SAVE.getBoolean()) {
            Logger.config("Saving configuration");
            if (configuration.saveConfig()){
                Logger.debug("Config saved");
            } else {
                Logger.config("There was a problem saving the configuration.");
            }
            if (CommandsLoadAndSave.reload()){
                Logger.debug("Commands saved");
            } else {
                Logger.config("There was a problem saving the commands.");
            }
            // todo adapt to MessageChangerHandler
            /*if (MessagesLoadAndSave.reload()){
                Logger.debug("Messages saved");
            } else {
                Logger.config("There was a problem saving the Messages.");
            }*/
        }
        Logger.disableMsg();
    }

    /**
     * This method is called during reloading of the spout server. Make sure that you do the right thing.
     */
    @Override
    public void onReload() {
        Logger.info("Reloading configuration");
        configuration.reloadConfig();
        configuration.loadConfig();
        CommandsLoadAndSave.reload();
        // todo adapt to MessageChangerHandler
        // MessagesLoadAndSave.reload();
    }


    /**
     * Method to register the commands, you need to pass the CLASS, for example if your command
     * is in the EnumMemory class you need to pass the EnumMemory.class object
     *
     * @param commandClass command object to be registered
     */
    private void registerCommand(Class<?> commandClass) {
        if (commandRegFactory == null) {
            commandRegFactory = new EnumAnnotatedCommandRegistrationFactory(new EnumSimpleInjector(this),
                    new EnumSimpleAnnotatedCommandExecutorFactory());
        }
        Spout.getEngine().getRootCommand().addSubCommands(this, commandClass, commandRegFactory);
    }

    //~--- get methods --------------------------------------------------------

    /**
     * Get the instance of the plugin
     *
     * @return Plugin instance
     */
    public static MessageChanger getInstance() {
        return instance;
    }

    /**
     * Get the instance of the configuration
     */

    public CommentConfiguration getConfig() {
        return configuration;
    }

    public void addGameType ( GAME_TYPES gameType){
        if (!types.containsKey(gameType)){
            types.put(gameType,NULL_TYPES);
        }
    }

    public void addPluginType ( GAME_TYPES gameType, TYPES pluginType){
        if (types.containsKey(gameType)){
            HashSet<TYPES> temp = types.get(gameType);
            temp.add(pluginType);
            types.put(gameType, temp);
        } else {
            HashSet<TYPES> temp = new HashSet<TYPES>();
            temp.add(pluginType);
            types.put(gameType, temp);
        }
    }

    public void removePluginType (GAME_TYPES gameType, TYPES pluginType){
        if (types.containsKey(gameType)){
            HashSet<TYPES> temp = types.get(gameType);
            temp.remove(pluginType);
            types.put(gameType,temp);
        }
    }

    public void removeGameType (GAME_TYPES gameType){
        if (types.containsKey(gameType)){
            types.remove(gameType);
        }
    }

    public boolean getIgnoreKick(){
        return ignoreKick;
    }

    public void enableIgnoreKick(){
        ignoreKick = true;
    }

    public void disableIgnoreKick(){
        ignoreKick = false;
    }

    private void ServerDownEvent(){
        // There's no easy way :/
        StackTraceElement[] st = new Throwable().getStackTrace();
        for (int i = 0; i < st.length; i++) {
            // Go through the stack trace and look for the stop method
            if (st[i].getMethodName().equals("stop")) {
                // Yay, stop method found
                Player[] players = getEngine().getOnlinePlayers();
                String kickMsg = "";
                for (Player player : players) {
                    // Let's kick 'em!
                    kickMsg = spoutMessages.getNewMessage ("SERVER_STOP", player, "");
                    if (!kickMsg.equals("")) {
                        // We don't want to override the SERVER_STOP message
                        this.ignoreKick = true;
                        // Cya!
                        player.kick(kickMsg);
                    }
                }
            }
        }
    }
}
