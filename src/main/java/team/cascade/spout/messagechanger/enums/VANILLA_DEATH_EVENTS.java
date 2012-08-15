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

package team.cascade.spout.messagechanger.enums;

import team.cascade.spout.messagechanger.helper.Logger;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Vanilla Events which messages will be replaced<br>
 * If new Events are added you also need to change the defaultMessages in {@link team.cascade.spout.messagechanger.vanilla.DeathMessages.VanillaDeathMessages}
 *
 * @author $Author: dredhorse$
 * @version $FullVersion$
 */

public class VANILLA_DEATH_EVENTS implements Comparable{


    /**
     * Enumeration elements are constructed once upon class loading.
     * Order of appearance here determines the order of compareTo.
     * List taken from http://jd.spout.org/vanilla/latest/org/spout/vanilla/controller/living/Creature.html
     * @todo needs adding of vanilla death events if more creatures show up
     */

    // First creatures

    public static final VANILLA_DEATH_EVENTS BLAZE = new VANILLA_DEATH_EVENTS("Blaze") ;
    public static final VANILLA_DEATH_EVENTS CAVE_SPIDER = new VANILLA_DEATH_EVENTS("CaveSpider") ;
    public static final VANILLA_DEATH_EVENTS CHICKEN = new VANILLA_DEATH_EVENTS("Chicken") ;
    public static final VANILLA_DEATH_EVENTS COW = new VANILLA_DEATH_EVENTS("Cow") ;
    public static final VANILLA_DEATH_EVENTS CREEPER = new VANILLA_DEATH_EVENTS("Creeper") ;
    public static final VANILLA_DEATH_EVENTS ENDER_DRAGON = new VANILLA_DEATH_EVENTS("EnderDragon") ;
    public static final VANILLA_DEATH_EVENTS ENDER_MAN = new VANILLA_DEATH_EVENTS("EnderMan") ;
    public static final VANILLA_DEATH_EVENTS GHAST = new VANILLA_DEATH_EVENTS("Ghast") ;
    public static final VANILLA_DEATH_EVENTS GIANT = new VANILLA_DEATH_EVENTS("Giant") ;
    public static final VANILLA_DEATH_EVENTS MAGMA_CUBE = new VANILLA_DEATH_EVENTS("MagmaCube") ;
    public static final VANILLA_DEATH_EVENTS MUSHROOM_COW = new VANILLA_DEATH_EVENTS("MushroomCow") ;
    public static final VANILLA_DEATH_EVENTS IRON_GOLEM = new VANILLA_DEATH_EVENTS("IronGolem") ;
    public static final VANILLA_DEATH_EVENTS OCELOT = new VANILLA_DEATH_EVENTS("Ocelot") ;
    public static final VANILLA_DEATH_EVENTS PIG = new VANILLA_DEATH_EVENTS("Pig") ;
    public static final VANILLA_DEATH_EVENTS PIG_ZOMBIE = new VANILLA_DEATH_EVENTS("PigZombie") ;
    public static final VANILLA_DEATH_EVENTS SHEEP = new VANILLA_DEATH_EVENTS("Sheep") ;
    public static final VANILLA_DEATH_EVENTS SILVERFISH = new VANILLA_DEATH_EVENTS("Silverfish") ;
    public static final VANILLA_DEATH_EVENTS SKELETON = new VANILLA_DEATH_EVENTS("Skeleton") ;
    public static final VANILLA_DEATH_EVENTS SLIME = new VANILLA_DEATH_EVENTS("Slime") ;
    public static final VANILLA_DEATH_EVENTS SNOW_GOLEM = new VANILLA_DEATH_EVENTS("SnowGolem") ;
    public static final VANILLA_DEATH_EVENTS SPIDER = new VANILLA_DEATH_EVENTS("Spider") ;
    public static final VANILLA_DEATH_EVENTS SQUID = new VANILLA_DEATH_EVENTS("Squid") ;
    public static final VANILLA_DEATH_EVENTS VILLAGER = new VANILLA_DEATH_EVENTS("Villager") ;
    public static final VANILLA_DEATH_EVENTS WOLF = new VANILLA_DEATH_EVENTS("Wolf") ;
    public static final VANILLA_DEATH_EVENTS ZOMBIE = new VANILLA_DEATH_EVENTS("Zombie") ;


    // Second environmental causes

    public static final VANILLA_DEATH_EVENTS CONTACT = new VANILLA_DEATH_EVENTS("Contact") ;
    public static final VANILLA_DEATH_EVENTS DROWNING = new VANILLA_DEATH_EVENTS("Drowning") ;
    public static final VANILLA_DEATH_EVENTS FALL = new VANILLA_DEATH_EVENTS("Fall") ;
    public static final VANILLA_DEATH_EVENTS FIRE = new VANILLA_DEATH_EVENTS("Fire") ;
    public static final VANILLA_DEATH_EVENTS FIRE_TICK = new VANILLA_DEATH_EVENTS("FireTick") ;
    public static final VANILLA_DEATH_EVENTS LAVA = new VANILLA_DEATH_EVENTS("Lava") ;
    public static final VANILLA_DEATH_EVENTS LIGHTNING = new VANILLA_DEATH_EVENTS("Lightning") ;
    public static final VANILLA_DEATH_EVENTS SUFFOCATION = new VANILLA_DEATH_EVENTS("Suffocation") ;
    public static final VANILLA_DEATH_EVENTS VOID = new VANILLA_DEATH_EVENTS("Void") ;

    // Explosions.... yeah

    public static final VANILLA_DEATH_EVENTS BLOCK_EXPLOSION = new VANILLA_DEATH_EVENTS("BlockExplosion") ;
    public static final VANILLA_DEATH_EVENTS ENTITY_EXPLOSION = new VANILLA_DEATH_EVENTS("EntityExplosion") ;

    // Player caused

    public static final VANILLA_DEATH_EVENTS SUICIDE = new VANILLA_DEATH_EVENTS("Suicide") ;
    public static final VANILLA_DEATH_EVENTS STARVATION = new VANILLA_DEATH_EVENTS("Starvation") ;


    // and of course there is PVP

    public static final VANILLA_DEATH_EVENTS PVP_FISTS = new VANILLA_DEATH_EVENTS("PvpFists") ;
    public static final VANILLA_DEATH_EVENTS PVP_TAMED = new VANILLA_DEATH_EVENTS("PvpTamed") ;
    public static final VANILLA_DEATH_EVENTS PVP = new VANILLA_DEATH_EVENTS("Pvp") ;

    // and some other stuff

    public static final VANILLA_DEATH_EVENTS DISPENSER = new VANILLA_DEATH_EVENTS("Dispenser") ;
    public static final VANILLA_DEATH_EVENTS POISON = new VANILLA_DEATH_EVENTS("Poison") ;
    public static final VANILLA_DEATH_EVENTS MAGIC = new VANILLA_DEATH_EVENTS("Magic") ;


    // and of course

    public static final VANILLA_DEATH_EVENTS UNKNOWN = new VANILLA_DEATH_EVENTS("Unknown") ;


    public String toString() {
        return fName;
    }

    /**
     * Parse text into an element of this enumeration.
     *
     * @param aText takes one of the values.
     */
    public static VANILLA_DEATH_EVENTS valueOf(String aText){
        Iterator iter = VALUES.iterator();
        while (iter.hasNext()) {
            VANILLA_DEATH_EVENTS type = (VANILLA_DEATH_EVENTS)iter.next();
            if ( aText.equals(type.toString().toLowerCase()) ){
                return type;
            }
        }
        // If we can't parse we return an Unknown cause.
        Logger.debug("Unknown Vanilla Death Event",aText);
        return UNKNOWN;
    }

    public int compareTo(Object that) {
        return fOrdinal - ( (VANILLA_DEATH_EVENTS)that ).fOrdinal;
    }

    private final String fName;
    private static int fNextOrdinal = 0;
    private final int fOrdinal = fNextOrdinal++;

    /**
     * Private constructor prevents construction outside of this class.
     */
    private VANILLA_DEATH_EVENTS(String aName) {
        fName = aName;
    }

    /**
     * These two lines are all that's necessary to export a List of VALUES.
     */
    private static final VANILLA_DEATH_EVENTS[] F_VALUEs = {BLAZE, CAVE_SPIDER, CHICKEN, COW,CREEPER,ENDER_DRAGON,ENDER_MAN,
            GHAST,GIANT, MAGMA_CUBE,MUSHROOM_COW,IRON_GOLEM,OCELOT,PIG,PIG_ZOMBIE,SHEEP,SILVERFISH,SKELETON,SLIME,SNOW_GOLEM,
            SPIDER,SQUID,VILLAGER,WOLF,ZOMBIE, CONTACT,DROWNING,FALL,FIRE,FIRE_TICK,LAVA,LIGHTNING,SUFFOCATION,VOID,BLOCK_EXPLOSION,
            ENTITY_EXPLOSION, SUICIDE, STARVATION, PVP_FISTS,PVP_TAMED,PVP,DISPENSER,POISON,MAGIC,UNKNOWN};
    //VALUES needs to be located here, otherwise illegal forward reference
    public static final List<VANILLA_DEATH_EVENTS> VALUES = Collections.unmodifiableList(Arrays.asList(F_VALUEs));


}
