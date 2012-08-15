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

package team.cascade.spout.messagechanger.exceptions;

import org.spout.api.exception.SpoutException;
import team.cascade.spout.messagechanger.config.CONFIG;

/**
 * Exception which is thrown by the Config enum when the class which is being set is different to the
 * class which is already stored in the enum
 *
 * @author $Author: dredhorse$
 * @version $FullVersion$
 */


public class WrongClassException extends SpoutException {

    private CONFIG configNode;

    private Object setObject;

    public WrongClassException() {
        super("A wrong class was passed");
        configNode = null;
        setObject = null;
    }

    public WrongClassException(CONFIG configNode, Object setObject) {
        super("Class expected: " + configNode.getConfigOption().getClass() + " Class found: " + setObject.getClass() + " for " + configNode);
        this.configNode = configNode;
        this.setObject = setObject;
    }

    public String getMessage() {
        return configNode == null ? "A wrong class was passed" : "Class expected: " + configNode.getConfigOption().getClass() + " Class found: " + setObject.getClass() + " for " + configNode;
    }
}


