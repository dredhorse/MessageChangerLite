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

package net.breiden.spout.messagechanger.commands;

import net.breiden.spout.messagechanger.config.CONFIG;
import net.breiden.spout.messagechanger.exceptions.ConfigNotInitializedException;
import net.breiden.spout.messagechanger.helper.COLOR;
import net.breiden.spout.messagechanger.helper.Logger;
import net.breiden.spout.messagechanger.helper.Messenger;
import net.breiden.spout.messagechanger.helper.commands.EnumCommand;
import net.breiden.spout.messagechanger.helper.config.CommentConfiguration;
import net.breiden.spout.messagechanger.helper.file.CommandsLoadAndSave;
import net.breiden.spout.messagechanger.helper.file.MessagesLoadAndSave;
import org.spout.api.Spout;
import org.spout.api.command.CommandContext;
import org.spout.api.command.CommandSource;
import org.spout.api.exception.CommandException;
import org.spout.api.plugin.CommonPlugin;

/**
 * The child commands of the MessageChanger
 *
 * @author $Author: dredhorse$
 * @version $FullVersion$
 * @todo make sure that you use your own commands in these two classes
 */
public class EnumMessageChangerCMDS {

    private final CommonPlugin plugin;

    public EnumMessageChangerCMDS(CommonPlugin instance) {
        plugin = instance;
    }

    /**
     * Displays a list of information, mainly the child commands and what they do.
     *
     * @param args
     * @param source
     * @throws CommandException
     */

    @EnumCommand(command = COMMANDS.HELPERCLASSES_HELP)
    public void help(CommandContext args, CommandSource source) throws CommandException {
        Messenger.sendHeader(source, plugin.getName());
        Messenger.send(source, COLOR.BRIGHT_GREEN + "- " + COLOR.CYAN + "/" + COMMANDS.HELPERCLASSES.getRootCommand() + " " + COMMANDS.HELPERCLASSES_HELP.getChildCommand() + COLOR.BRIGHT_GREEN + " : " + COMMANDS.HELPERCLASSES_HELP.getCmdDescription());
        Messenger.send(source, COLOR.BRIGHT_GREEN + "- " + COLOR.CYAN + "/" + COMMANDS.HELPERCLASSES.getRootCommand() + " " + COMMANDS.HELPERCLASSES_INFO.getChildCommand() + COLOR.BRIGHT_GREEN + " : " + COMMANDS.HELPERCLASSES_INFO.getCmdDescription());
        Messenger.send(source, COLOR.BRIGHT_GREEN + "- " + COLOR.CYAN + "/" + COMMANDS.HELPERCLASSES.getRootCommand() + " " + COMMANDS.HELPERCLASSES_RELOAD.getChildCommand() + COLOR.BRIGHT_GREEN + " : " + COMMANDS.HELPERCLASSES_RELOAD.getCmdDescription());
        Messenger.send(source, COLOR.BRIGHT_GREEN + "- " + COLOR.CYAN + "/" + COMMANDS.HELPERCLASSES.getRootCommand() + " " + COMMANDS.HELPERCLASSES_SAVE.getChildCommand() + COLOR.BRIGHT_GREEN + " : " + COMMANDS.HELPERCLASSES_SAVE.getCmdDescription());
    }

    /**
     * Displays some informational messages and tries to display if there is a new version available.
     *
     * @param args
     * @param source
     * @throws CommandException
     */
    @EnumCommand(command = COMMANDS.HELPERCLASSES_INFO)
    public void info(CommandContext args, CommandSource source) throws CommandException {
        Messenger.sendHeader(source, plugin.getName());
        Messenger.send(source, COLOR.BRIGHT_GREEN + plugin.getDescription().getName() + " " + plugin.getDescription().getVersion());
        Messenger.send(source, COLOR.BRIGHT_GREEN + "Copyright (c) " + Messenger.getAuthors(plugin));
        Messenger.send(source, COLOR.BRIGHT_GREEN + plugin.getDescription().getWebsite());
        Messenger.send(source, COLOR.BRIGHT_GREEN + "Powered by Spout " + Spout.getEngine().getVersion());
        Messenger.send(source, COLOR.BRIGHT_GREEN + "( Implementing SpoutAPI " + Spout.getAPIVersion() + " )");
        try {
            if (CommentConfiguration.getNewVersion() != null) {
                Messenger.send(source, "");
                Messenger.send(source, COLOR.BRIGHT_GREEN + "There is a new version available: Version " + CommentConfiguration.getInstance().getNewVersion());
            }
        } catch (ConfigNotInitializedException e) {
            Messenger.sendError(source, "Something went wrong during checking the version. Please check the logs!");
            Logger.warning("A problem occurred during reloading of the config.", e);
        }
    }


    /**
     * Will reload the plugin configuration. If CONFIG_AUTO_SAVE is true we will save the configuration first.
     *
     * @param args
     * @param source
     * @throws CommandException
     */
    @EnumCommand(command = COMMANDS.HELPERCLASSES_RELOAD)
    public void reload(CommandContext args, CommandSource source) throws CommandException {
        Messenger.sendHeader(source, plugin.getName());
        Messenger.send(source, "Reloading config");
        try {
            if (CONFIG.CONFIG_AUTO_SAVE.getBoolean()) {
                CommentConfiguration.getInstance().saveConfig();
            }
            CommentConfiguration.getInstance().loadConfig();
        } catch (ConfigNotInitializedException e) {
            Messenger.sendError(source, "Something went wrong during the reload. Please check the logs!");
            Logger.warning("A problem occurred during reloading of the config.", e);
        }
        CommandsLoadAndSave.reload();
        MessagesLoadAndSave.reload();
        Messenger.send(source, Messenger.wrap("Well") + " we are done.");
    }


    /**
     * Will save the plugin configuration to file. This is helpful when you allow changing configuration via commands <br>
     * and want to allow people to manually save the changes if they have CONFIG_AUTO_SAVE false </p>
     *
     * @param args
     * @param source
     * @throws CommandException
     */
    @EnumCommand(command = COMMANDS.HELPERCLASSES_SAVE)
    public void save(CommandContext args, CommandSource source) throws CommandException {
        Messenger.sendHeader(source, plugin.getName());
        Messenger.send(source, "Saving config");
        try {
            CommentConfiguration.getInstance().saveConfig();
        } catch (ConfigNotInitializedException e) {
            Messenger.sendError(source, "Something went wrong during the save. Please check the logs!");
            Logger.warning("Something went wrong during saving the config.", e);
        }
        Messenger.send(source, Messenger.wrap("Well") + " we are done.");
    }




}
