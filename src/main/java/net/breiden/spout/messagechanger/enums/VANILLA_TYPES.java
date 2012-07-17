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

package net.breiden.spout.messagechanger.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Type Save enum for handling the different Vanilla Message Types the plugin supports
 *
 * @todo Additional Game Types needs to be added here
 *
 * @author $Author: dredhorse$
 * @version $FullVersion$
 */
public final class VANILLA_TYPES implements Comparable, TYPES {




    /**
     * Enumeration elements are constructed once upon class loading.
     * Order of appearance here determines the order of compareTo.
     */
    public static final VANILLA_TYPES DEFAULT = new VANILLA_TYPES("Default") ;
    public static final VANILLA_TYPES MORE_MOBS = new VANILLA_TYPES("MoreMobs");


    public String toString() {
        return fName;
    }

    /**
     * Parse text into an element of this enumeration.
     *
     * @param aText takes one of the values 'Default', 'MoreMobs'.
     */
    public static VANILLA_TYPES valueOf(String aText){
        Iterator iter = VALUES.iterator();
        while (iter.hasNext()) {
            VANILLA_TYPES type = (VANILLA_TYPES)iter.next();
            if ( aText.equals(type.toString()) ){
                return type;
            }
        }
        //this method is unusual in that IllegalArgumentException is
        //possibly thrown not at its beginning, but at its end.
        throw new IllegalArgumentException(
                "Cannot parse into an element of Type : '" + aText + "'"
        );
    }

    public int compareTo(Object that) {
        return fOrdinal - ( (VANILLA_TYPES)that ).fOrdinal;
    }

    private final String fName;
    private static int fNextOrdinal = 0;
    private final int fOrdinal = fNextOrdinal++;

    /**
     * Private constructor prevents construction outside of this class.
     */
    private VANILLA_TYPES(String aName) {
        fName = aName;
    }

    /**
     * These two lines are all that's necessary to export a List of VALUES.
     */
    private static final VANILLA_TYPES[] F_VALUEs = {DEFAULT , MORE_MOBS};
    //VALUES needs to be located here, otherwise illegal forward reference
    public static final List VALUES = Collections.unmodifiableList(Arrays.asList(F_VALUEs));

    @Override
    public void dontUseThis() {
        //will never be called
    }
}
