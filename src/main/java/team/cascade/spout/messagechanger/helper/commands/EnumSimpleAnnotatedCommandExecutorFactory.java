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

package team.cascade.spout.messagechanger.helper.commands;

import org.spout.api.command.CommandSource;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/**
 * Amended class to handle Enums, based on {@link org.spout.api.command.annotated.SimpleAnnotatedCommandExecutorFactory}
 *
 * @author $Author: dredhorse$
 * @version $FullVersion$
 */
public class EnumSimpleAnnotatedCommandExecutorFactory implements EnumAnnotatedCommandExecutorFactory {
    public EnumAnnotatedCommandExecutor getEnumAnnotatedCommandExecutor(Object instance, Method method) {
        return new EnumSimpleAnnotatedCommandExecutor(instance, method);
    }

    public static class EnumSimpleAnnotatedCommandExecutor extends EnumAnnotatedCommandExecutor {

        public EnumSimpleAnnotatedCommandExecutor(Object instance, Method method) {
            super(instance, method);
        }

        @Override
        public List<Object> getAdditionalArgs(CommandSource source, org.spout.api.command.Command command) {
            return Collections.emptyList();
        }
    }
}
