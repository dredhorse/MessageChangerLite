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

package net.breiden.spout.messagechanger.exceptions;


import net.breiden.spout.messagechanger.helper.Logger;
import org.spout.api.exception.SpoutException;

/**
 * Exception which is thrown by the CommentConfiguration when the configuration is not available.
 * NOTE: THIS IS SEVERE AND YOU SHOULD DISABLE YOUR PLUGIN IN THIS CASE!
 *
 * @author $Author: dredhorse$
 * @version $FullVersion$
 */


@SuppressWarnings("ALL")
public class ConfigNotAvailableException extends SpoutException {

    private static final String NO_CONFIG = "There is no configuration available for this plugin!";
    private Exception exception;

    public ConfigNotAvailableException() {
        super(NO_CONFIG);
        exception = null;
        Logger.severe(NO_CONFIG);
        Logger.severe("This is an major error and you should notify the developer!");
    }

    public ConfigNotAvailableException(Exception ex) {
        super(NO_CONFIG, ex);
        exception = ex;
        Logger.severe(NO_CONFIG);
        Logger.severe("The following Exception was thrown:", exception);
        Logger.severe("This is an major error and you should notify the developer!");
    }


    public String getMessage() {
        return NO_CONFIG + "\nPlease contact the developer as this is a major error!";
    }

    public Exception getException() {
        return exception;
    }
}
