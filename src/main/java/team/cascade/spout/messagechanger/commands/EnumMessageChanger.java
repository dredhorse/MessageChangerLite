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

package team.cascade.spout.messagechanger.commands;

import org.spout.api.command.CommandContext;
import org.spout.api.command.CommandSource;
import org.spout.api.command.annotated.NestedCommand;
import org.spout.api.exception.CommandException;
import org.spout.api.plugin.CommonPlugin;
import team.cascade.spout.messagechanger.helper.commands.EnumCommand;

/**
 * Set of default commands which are normally useful for plugin management. As this is a nested command <br>
 * there is nothing in this class really except the root command and the annotation @NestedCommand which than references</p>
 * the other class which holds all the child commands. {@link EnumMessageChangerCMDS}
 *
 * @author $Author: dredhorse$
 * @version $FullVersion$
 * @todo make sure that you use your own commands in these two classes
 */
public class EnumMessageChanger {
    private final CommonPlugin plugin;

    /**
     * We must pass in an instance of our plugin's object for the annotation to register under the factory.
     *
     * @param instance
     */
    public EnumMessageChanger(CommonPlugin instance) {
        plugin = instance;
    }

    @EnumCommand(command = COMMANDS.MESSAGECHANGER)
    @NestedCommand(EnumMessageChangerCMDS.class)
    public void helperClasses(CommandContext args, CommandSource source) throws CommandException {
    }

}
