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

package team.cascade.spout.messagechanger.vanilla;

import org.spout.api.exception.ConfigurationException;
import org.spout.api.plugin.PluginDescriptionFile;
import org.spout.api.util.config.yaml.YamlConfiguration;
import team.cascade.spout.messagechanger.MessageChanger;
import team.cascade.spout.messagechanger.config.CONFIG;
import team.cascade.spout.messagechanger.enums.VANILLA_DEATH_EVENTS;
import team.cascade.spout.messagechanger.helper.Logger;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Handels loading and saving of the Vanilla messages
 *
 * @author $Author: dredhorse$
 * @version $FullVersion$
 */
public class VanillaMessages {


    /*
    * Default Death Message
    */
    private static final String DEFAULT_DEATH_MESSAGE = "%n died from unknown causes";


    /**
     * Configuration File Name
     */
    private static String VANILLA_DEATHMESSAGES = "vanilla_deathmessages.yml";

    /**
     * Configuration File
     */

    private File deathMessageFile;

    /**
     * Random Number
     */
    private static Random random = new Random();


    private String pluginName;

    private boolean vanillaDeathMessagesRequiresUpdate;

    // ToDo Change the vanillaDeathMessagesCurrent if the messages change!
    /**
     * This is the internal deathMessageFileConfig version
     */
    private final String vanillaDeathMessagesCurrent = "1";
    /**
     * This is the DEFAULT for the deathMessageFileConfig file version, should be the same as deathMessagesCurrent. Will afterwards be changed
     */
    private String vanillaDeathMessagesVer = vanillaDeathMessagesCurrent;

    // Helper Variables

    /**
     * Array which holds default fall messages
     */
    private String[] defaultFallMessages;
    /**
     * Array which holds default drowning messages
     */
    private String[] defaultDrowningMessages;
    /**
     * Array which holds default fire messages
     */
    private String[] defaultFireMessages;
    /**
     * Array which holds default fire_tick messages
     */
    private String[] defaultFireTickMessages;
    /**
     * Array which holds default Lava Messages
     */
    private String[] defaultLavaMessages;
    /**
     * Array which holds default creeper messages
     */
    private String[] defaultCreeperMessages;
    /**
     * Array which holds default skeleton messages
     */
    private String[] defaultSkeletonMessages;
    /**
     * Array which holds default spider messages
     */
    private String[] defaultSpiderMessages;
    /**
     * Array which holds default zombie messages
     */
    private String[] defaultZombieMessages;
    /**
     * Array which holds default PVP messages
     */
    private String[] defaultPVPMessages;
    /**
     * Array which holds default PVP-Fist messages
     */
    private String[] defaultPVPFistMessages;
    /**
     * Array which holds default block_explosion messages
     */
    private String[] defaultBlockExplosionMessages;
    /**
     * Array which holds default contact messages
     */
    private String[] defaultContactMessages;
    /**
     * Array which holds default ghast messages
     */
    private String[] defaultGhastMessages;
    /**
     * Array which holds default slime messages
     */
    private String[] defaultSlimeMessages;
    /**
     * Array which holds default suffocation messages
     */
    private String[] defaultSuffocationMessages;
    /**
     * Array which holds default pigzombie messages
     */
    private String[] defaultPigzombieMessages;
    /**
     * Array which holds default void messages
     */
    private String[] defaultVoidMessages;
    /**
     * Array which holds default wolf messages
     */
    private String[] defaultWolfMessages;
    /**
     * Array which holds default Lightning messages
     */
    private String[] defaultLightningMessages;
    /**
     * Array which holds default suicide messages
     */
    private String[] defaultSuicideMessages;
    /**
     * Array which holds default unknown messages
     */
    private String[] defaultUnknownMessages;
    /**
     * Array which holds default starvation messages
     */
    private String[] defaultStarvationMessages;
    /**
     * Array which holds default enderman messages
     */
    private String[] defaultEndermanMessages;
    /**
     * Array which holds default CaveSpider messages
     */
    private String[] defaultCaveSpiderMessages;
    /**
     * Array which holds default silverfish messages
     */
    private String[] defaultSilverfishMessages;
    /**
     * Array which holds default PVP Tamed messages
     */
    private String[] defaultPVPTamedMessages;
    /**
     * Array which holds default entity_explosion messages
     */
    private String[] defaultEntityExplosionMessages;
    /**
     * Array which holds default Giant messages
     */
    private String[] defaultGiantMessages;
    /**
     * Array which holds default Blaze messages
     */
    private String[] defaultBlazeMessages;
    /**
     * Array which holds default Enderdragon messages
     */
    private String[] defaultEnderDragonMessages;
    /**
     * Array which holds default MagmaCube messages
     */
    private String[] defaultMagmaCubeMessages;

    /**
     * Array which holds default Dispenser messages
     */
    private String[] defaultDispenserMessages;

    /**
     * Array which holds default Posion messages
     */
    private String[] defaultPosionMessages;

    /**
     * Array which holds default Magic messages
     */
    private String[] defaultMagicMessages;

    /**
     * Array which holds default IronGolem messages
     */
    private String[] defaultIronGolemMessages;


    // ToDo add new variables on top

    // Deathmessages
    /**
     * Must contain at least 1 line. If there are more, it will appear randomly when a person dies.
     * %n for player who died
     * %a name of player who attacked in pvp deaths
     * %i for item a player was using to kill someone else
     * <p/>
     * Colors
     * <p/>
     * &0 Black
     * &1 Navy
     * &2 Green
     * &3 Blue
     * &4 Red
     * &5 Purple
     * &6 Gold
     * &7 LightGray
     * &8 Gray
     * &9 DarkPurple
     * &a LightGreen
     * &3 LightBlue
     * &c Rose
     * &d LightPurple
     * &e Yellow
     * &f White
     */
    private static HashMap<VANILLA_DEATH_EVENTS, List<String>> deathMessages = new HashMap<VANILLA_DEATH_EVENTS, List<String>>();

    private MessageChanger main;

    private static VanillaMessages instance;

    /**
     * Path to the messages file
     */
    private String messagesPath;

    /**
     * Message File Name
     */
    private static final String VANILLA_DEATHMESSAGES_FILENAME = "vanilla_death_messages.yml";

    /**
     * Did we succeed?
     */
    private static boolean success;


    /**
     *
     */
    private final YamlConfiguration vanillaDeathMessagesConfig;



    public VanillaMessages(MessageChanger main) {
        instance = this;
        this.main = main;
        messagesPath = main.getDataFolder() + System.getProperty("file.separator") + "messages"+ System.getProperty("file.separator");
        vanillaDeathMessagesConfig = new YamlConfiguration(new File(messagesPath, VANILLA_DEATHMESSAGES_FILENAME));
        init();

    }


    // First we have the default part..
    // Which is devided in setting up some variables first

    /**
     * Method to setup the deathMessageFileConfig variables with default values
     */

    private void setupCustomDefaultVariables() {

        // Default Death Messages

        /** Creating the default fall messages*/
        defaultFallMessages = new String[]{
                "&5%n&7 tripped and fell...down a cliff.",
                "&5%n&7 leapt before looking.",
                "&5%n&7 forgot to bring a parachute!",
                "&5%n&7 learned to fly...briefly...",
                "&5%n&7 felt the full effect of gravity.",
                "&5%n&7 just experienced physics in action.",
                "&5%n&7 fell to his death.",
                "&5%n&7 forgot to look out below!",
                "&5%n&7 got a little too close to the edge!",
                "&5%n&7, gravity is calling your name!",
                "&5%n&7 face planted into the ground!",
                "&5%n&7 yells, 'Geronimo!'....*thud*",
                "What goes up must come down, right &5%n&7?",
                "&5%n&7 must have had their shoelaces tied together."
        };
        deathMessages.put(VANILLA_DEATH_EVENTS.FALL, Arrays.asList(defaultFallMessages));
        /** Creating the default drowning messages*/
        defaultDrowningMessages = new String[]{
                "&5%n&7 drowned",
                "&5%n&7 become one with the ocean!",
                "&5%n&7 sunk to the bottom of the ocean.",
                "&5%n&7 went diving but forgot the diving gear!",
                "&5%n&7 needs swimming lessons.",
                "&5%n 's&7 lungs have been replaced with H20.",
                "&5%n&7 forgot to come up for air!",
                "&5%n&7 is swimming with the fishes!",
                "&5%n&7 had a surfing accident!",
                "&5%n&7 tried to walk on water.",
                "&5%n&7 set a record for holding breath under water."
        };
        deathMessages.put(VANILLA_DEATH_EVENTS.DROWNING, Arrays.asList(defaultDrowningMessages));
        /** Creating the default fire messages*/
        defaultFireMessages = new String[]{
                "&5%n&7 burned to death.",
                "&5%n&7 was set on fire!",
                "&5%n&7 is toast! Literally...",
                "&5%n&7 just got barbequed!",
                "&5%n&7 forgot to stop, drop, and roll!",
                "&5%n&7 is extra-crispy!",
                "&5%n&7 spontaneously combusted!",
                "&5%n&7 put his hands in the toaster!",
                "&5%n&7 just got burned!"
        };
        deathMessages.put(VANILLA_DEATH_EVENTS.FIRE, Arrays.asList(defaultFireMessages));
        /** Creating the default fire_tick messages*/
        defaultFireTickMessages = new String[]{
                "&5%n&7 burned to death.",
                "&5%n&7 was set on fire!",
                "&5%n&7 is toast! Literally...",
                "&5%n&7 just got barbequed!",
                "&5%n&7 forgot to stop, drop, and roll!",
                "%n&7 likes it extra-crispy!",
                "&5%n&7 spontaneously combusted!",
                "&5%n&7 put his hands in the toaster!",
                "&5%n&7 just got burned!"
        };
        deathMessages.put(VANILLA_DEATH_EVENTS.FIRE_TICK, Arrays.asList(defaultFireTickMessages));
        /** Creating the default lava messages*/
        defaultLavaMessages = new String[]{
                "&5%n&7 became obsidian.",
                "&5%n&7 was caught in an active volcanic eruption!",
                "&5%n&7 tried to swim in a pool of lava.",
                "&5%n&7 was killed by a lava eruption!",
                "&5%n&7 was forged into obsidian by molten lava.",
                "&5%n&7 took a dip in the wrong kind of pool!",
                "&5%n&7 found out how to encase himself in carbonite.",
                "&5%n&7! the floor is lava! The floor is lava!"

        };
        deathMessages.put(VANILLA_DEATH_EVENTS.LAVA, Arrays.asList(defaultLavaMessages));
        /** Creating the default creeper messages*/
        defaultCreeperMessages = new String[]{
                "&5%n&7 was creeper bombed!",
                "A creeper exploded on &5%n&7!",
                "A creeper snuck up on &5%n&7!",
                "A creeper tried to make love with &5%n&7...mmm.",
                "&5%n&7 just got the KiSSssss of death!",
                "&5%n&7 tried to hug a creeper!",
                "&5%n&7 is frowning like a creeper now!"
        };
        deathMessages.put(VANILLA_DEATH_EVENTS.CREEPER, Arrays.asList(defaultCreeperMessages));
        /** Creating the default skeleton messages*/
        defaultSkeletonMessages = new String[]{
                "A skeleton shot &5%n&7 to death!",
                "&5%n&7 was on the wrong end of the bow. ",
                "&5%n&7 had a skeleton in the closet...",
                "&5%n&7! strafe the arrows! Strafe the arrows!",
                "&7A skeleton just got a head shot on &5%n&7!"
        };
        deathMessages.put(VANILLA_DEATH_EVENTS.SKELETON, Arrays.asList(defaultSkeletonMessages));
        /** Creating the default spider messages*/
        defaultSpiderMessages = new String[]{
                "&5%n&7 is all webbed up.",
                "&5%n&7 got trampled by arachnids!",
                "&5%n&7 got jumped by a spidah!",
                "Spiders just climbed all over &5%n&7!",
                "&5%n&7 forgot spiders could climb!"
        };
        deathMessages.put(VANILLA_DEATH_EVENTS.SPIDER, Arrays.asList(defaultSpiderMessages));
        /** Creating the default zombie messages*/
        defaultZombieMessages = new String[]{
                "&5%n&7 was punched to death by zombies!",
                "&5%n&7 was bitten by a zombie!",
                "&5%n&7 fell to the hunger of the horde!",
                "&5%n&7 Hasn't played enough L4D2!",
                "&5%n&7 couldn't run faster than the zombie!",
                "&5%n&7 should have invested in a shotgun."
        };
        deathMessages.put(VANILLA_DEATH_EVENTS.ZOMBIE, Arrays.asList(defaultZombieMessages));
        /** Creating the default pvp messages*/
        defaultPVPMessages = new String[]{
                "&f%a&7 killed &5%n&7 using a(n) &3%i&7!",
                "&f%a&7 slays &5%n&7 with a &3%i&7!",
                "&f%a&7 hunts &5%n&7 down with a &3%i&7!",
                "&5%n&7 was killed by a &3%i&7 wielding %a!",
                "&f%a&7 leaves &5%n&7 a bloody mess!",
                "&f%a&7 uses a &3%i&7 to end &5%n''s&7 life!",
                "&5%n&7 collapses due to &3%i&7 attacks from %a!",
                "&5%n&7 is now a bloody mess thanks to %a''s &3%i&7!",
                "&f%a&7 beats &5%n&7 with a &3%i&7!",
                "&5%n&7 was killed by %a''s &3%i&7 attack!",
                "&f%a&7 defeats &5%n&7 with a &3%i&7 attack!",
                "&f%a&7 raises a &3%i&7 and puts and end to &5%n''s&7 life!",
                "&f%a&7 took out &5%n&7 with a &3%i&7!",
                "&5%n&7 was victimized by &f%a''s&7 &3%i&7!",
                "&5%n&7 was eliminated by %a''s &3%i&7!",
                "&f%a&7 executes &5%n&7 with a &3%i&7!",
                "&f%a&7 finishes &5%n&7 with a &3%i&7!",
                "&f%a&7's &3%i&7 has claimed &5%n&7 as another victim!",
                "&5%n&7 lost a savage duel to %a!",
                "&f%a&7 has beaten &5%n&7 to a pulp!",
                "&f%a&7 pwns &5%n&7 in a vicious duel!",
                "&5Score %a 1 - &5%n&7 0!",
                "&f%a&7 has defeated &5%n&7 in battle!",
                "&5%n&7 was slain by &f%a&7!",
                "&f%a&7 emerges victorious in a duel with &5%n&7!",
                "&5%n&7 was pwned by &f%a&7!",
                "&5%n&7 was killed by %a!",
                "&5%n&7 was dominated by %a!",
                "&5%n&7 was fragged by %a!",
                "&5%n&7 needs more practice and was killed by %a!",
                "&5%n&7 was beheaded by %a!"
        };
        deathMessages.put(VANILLA_DEATH_EVENTS.PVP, Arrays.asList(defaultPVPMessages));
        /** Creating the default pvp-fists messages*/
        defaultPVPFistMessages = new String[]{
                "&f%a&7 pummeled &5%n&7 to death",
                "&f%a&7 crusted &5%n&7 with their bare hands"
        };
        deathMessages.put(VANILLA_DEATH_EVENTS.PVP_FISTS, Arrays.asList(defaultPVPFistMessages));
        /** Creating the default block_explosion messages*/
        defaultBlockExplosionMessages = new String[]{
                "Careful &5%n&7, TNT goes boom!",
                "&5%n&7 was last seen playing with dynamite.",
                "&5%n&7 exploded into a million pieces!",
                "&5%n&7 cut the wrong wire!",
                "&5%n&7 left his (bloody) mark on the world.",
                "&5%n&7 attempted to exterminate gophers with dynamite!",
                "&5%n&7 played land mine hop scotch!",
                "&5%n&7 stuck his head in a microwave!"
        };
        deathMessages.put(VANILLA_DEATH_EVENTS.BLOCK_EXPLOSION, Arrays.asList(defaultBlockExplosionMessages));
        /** Creating the default entity_explosion messages*/
        defaultEntityExplosionMessages = new String[]{
                "Well... something exploded on &5%n&7!"
        };
        deathMessages.put(VANILLA_DEATH_EVENTS.ENTITY_EXPLOSION, Arrays.asList(defaultEntityExplosionMessages));
        /** Creating the default contact messages*/
        defaultContactMessages = new String[]{
                "&5%n&7 got a little too close to a cactus!",
                "&5%n&7 tried to hug a cactus!",
                "&5%n&7 needs to be more careful around cactuses!",
                "&5%n&7 felt the wrath of Cactus Jack!",
                "&5%n&7 learned the result of rubbing a cactus!",
                "&5%n&7 died from cactus injuries!",
                "&5%n&7 poked himself with a cactus...and died.",
                "&5%n&7 ran into some pointy green stuff that wasn't grass.",
                "&5%n&7 was distracted by a tumble weed and died by cactus."
        };
        deathMessages.put(VANILLA_DEATH_EVENTS.CONTACT, Arrays.asList(defaultContactMessages));
        /** Creating the default ghast messages*/
        defaultGhastMessages = new String[]{
                "&5%n&7 was blown to bits by a ghast.",
                "Those aren't babies you hear, &5%n&7!",
                "&5%n&7 was killed by a ghostly hadouken!",
                "&5%n&7 just got exploded by a fireball!",
                "&5%n&7 got too comfy in the Nether!"
        };
        deathMessages.put(VANILLA_DEATH_EVENTS.GHAST, Arrays.asList(defaultGhastMessages));
        /** Creating the default slime messages*/
        defaultSlimeMessages = new String[]{
                "A slime found &5%n&7. The slime won.",
                "&5%n&7 wanted to play with slime. The slime wasn't happy.",
                "&5%n&7 was killed for saying, \"Eeeeehhhh, he slimed me!\"",
                "&5%n&7 crossed the streams."
        };
        deathMessages.put(VANILLA_DEATH_EVENTS.SLIME, Arrays.asList(defaultSlimeMessages));
        /** Creating the default suffocation messages*/
        defaultSuffocationMessages = new String[]{
                "&5%n&7 suffocated.",
                "&5%n&7 was looking up while digging!",
                "&5%n&7 choked to death on earth!",
                "&5%n&7 choked on a ham sandwich"
        };
        deathMessages.put(VANILLA_DEATH_EVENTS.SUFFOCATION, Arrays.asList(defaultSuffocationMessages));
        /** Creating the default pigzombie messages*/
        defaultPigzombieMessages = new String[]{
                "&5%n&7 lost a fight against a zombie pig.",
                "&5%n&7, touching a zombie pig is never a good idea.",
                "&5%n&7, looked at a pigzombie the wrong way."
        };
        deathMessages.put(VANILLA_DEATH_EVENTS.PIG_ZOMBIE, Arrays.asList(defaultPigzombieMessages));
        /** Creating the default void messages*/
        defaultVoidMessages = new String[]{
                "&5%n&7 died in The Void."
        };
        deathMessages.put(VANILLA_DEATH_EVENTS.VOID, Arrays.asList(defaultVoidMessages));
        /** Creating the default Wolfs messages*/
        defaultWolfMessages = new String[]{
                "&5%n&7 became a wolf's lunch.",
                "&5%n&7 couldn't howl with the wolfs."
        };
        deathMessages.put(VANILLA_DEATH_EVENTS.WOLF, Arrays.asList(defaultWolfMessages));
        /** Creating the default lightning messages*/
        defaultLightningMessages = new String[]{
                "&5%n&7 was struck down by Zeus' bolt.",
                "&5%n&7 was electrecuted.",
                "&5%n&7 figured out that it wasn't a pig's nose in the wall."
        };
        /** Creating the default lightning messages*/
        deathMessages.put(VANILLA_DEATH_EVENTS.LIGHTNING, Arrays.asList(defaultLightningMessages));
        defaultSuicideMessages = new String[]{
                "&5%n&7 took matters into his own hands.",
                "&5%n&7 isn't causing NPE''s anymore."
        };
        /** Creating the default suicide messages*/
        deathMessages.put(VANILLA_DEATH_EVENTS.SUICIDE, Arrays.asList(defaultSuicideMessages));
        defaultUnknownMessages = new String[]{
                "&5%n&7 died from unknown causes.",
                "&5%n&7 imploded into nothingness",
                "&5%n&7 was vaporized",
                "&5%n&7 died from explosive diarrhea",
                "&5%n&7 was killed by Chuck Norris",
                "&5%n&7 was running with scissors...now he runs no more",
                "&5%n&7 was hit by a falling piano",
                "&5%n&7 was assasinated by a shuriken headshot from the shadow",
                "&5%n&7 was barrel rolling...and died",
                "&5%n&7 was killed by Cthulhu",
                "&5%n&7 forgot to wear his spacesuit",
                "&5%n&7 choked on a ham sandwich",
                "&5%n&7 died at the hands of ninja assassins"
        };
        /** Creating the default unknown messages*/
        deathMessages.put(VANILLA_DEATH_EVENTS.UNKNOWN, Arrays.asList(defaultUnknownMessages));
        defaultStarvationMessages = new String[]{
                "&5%n&7 did forget to eat his lunch.",
                "&5%n&7 didn't find the next Burger.",
                "&5%n&7 became a skeleton.",
                "&5%n&7 TALKS ALL CAPITALS NOW.",
                "&5%n&7 should have packed a lunch."
        };
        /** Creating the default starvation messages*/
        deathMessages.put(VANILLA_DEATH_EVENTS.STARVATION, Arrays.asList(defaultStarvationMessages));
        /** Creating the default enderman messages*/
        defaultEndermanMessages = new String[]{
                "&5%n&7 looked at a enderman the wrong way.",
                "An enderman pulled &5%n&7 leg..... off!"
        };
        deathMessages.put(VANILLA_DEATH_EVENTS.ENDER_MAN, Arrays.asList(defaultEndermanMessages));
        /** Creating the default cavespider messages*/
        defaultCaveSpiderMessages = new String[]{
                "&5%n&7 will never sing itsybitsyspider again",
                "&5%n&7 is all webbed up.",
                "&5%n&7 was trampled by arachnids!",
                "&5%n&7 was jumped by a spidah!",
                "Spiders just climbed all over &5%n&7!",
                "&5%n&7 forgot spiders could climb!"
        };
        deathMessages.put(VANILLA_DEATH_EVENTS.CAVE_SPIDER, Arrays.asList(defaultCaveSpiderMessages));
        /** Creating the default silverfish messages*/
        defaultSilverfishMessages = new String[]{
                "&5%n&7 was killed by a silverfish!",
                "&5%n&7 found something hidden below a rock",
                "&5%n&7 You can't stuff that many fish into your mouth!",
                "&5%n&7 activated a silverfish trap",
                "&54%n''s&7 last words  'Oh god they''re coming out of the walls!'"
        };
        deathMessages.put(VANILLA_DEATH_EVENTS.SILVERFISH, Arrays.asList(defaultSilverfishMessages));
        /** Creating the default PVP tamed messages*/
        defaultPVPTamedMessages = new String[]{
                "&5%n&7 was mauled by &f%a's&7 &3%i",
                "&5%n''s&7 hand was bitten by &f%a's&7 &3%i"
        };
        deathMessages.put(VANILLA_DEATH_EVENTS.PVP_TAMED, Arrays.asList(defaultPVPTamedMessages));
        /** Creating the default Giant messages*/
        defaultGiantMessages = new String[]{
                "&5%n&7 was stomped by a giant!",
                "&5%n&7 was flattened by a giant!",
                "&5%n&7 shouldn't have climbed the bean stalk."
        };
        deathMessages.put(VANILLA_DEATH_EVENTS.GIANT, Arrays.asList(defaultGiantMessages));
        /** Creating the default Blaze messages*/
        defaultBlazeMessages = new String[]{
                "&5%n&7 was set on fire at a blaze, well.. by a blaze!",
                "&5%n&7 was airbombed!",
                "&5%n&7, not everything on fire is a player!",
                "&5%n&7 nope, that wasn't a rocket."
        };
        deathMessages.put(VANILLA_DEATH_EVENTS.BLAZE, Arrays.asList(defaultBlazeMessages));
        /** Creating the default Enderdragon messages*/
        defaultEnderDragonMessages = new String[]{
                "&5%n&7 died at the end... IN the end.",
                "&5%n&7 looking up would have helped.",
                "Well, Anne McCaffrey didn't talk about that kind of Dragon, right &5%n&7?",
                "No egg for you, &5%n&7.",
                "&5%n&7 took the easy way out of \"The End\".",
                "&5%n&7 will never get to read that end poem!",
                "&5%n&7 made a generous donation to the Ender Dragon's hoard."
        };
        deathMessages.put(VANILLA_DEATH_EVENTS.ENDER_DRAGON, Arrays.asList(defaultEnderDragonMessages));
        /** Creating the default MagmaCube messages*/
        defaultMagmaCubeMessages = new String[]{
                "&5%n&7 didn't expect this kind of slinky!",
                "&5%n&7 got eaten by a cube.",
                "&5%n&7 got coombad by a cube."


        };
        deathMessages.put(VANILLA_DEATH_EVENTS.MAGMA_CUBE, Arrays.asList(defaultMagmaCubeMessages));
        /** Creating the default Dispenser Kill messages*/
        defaultDispenserMessages = new String[]{
                "&5%n&7 got shot in the back by a dispenser!",
                "Again the wrong weight Indi? well. &5%n&7",
                "&5%n&7 thinks he is Indiana Jones.",
                "&5%n&7 felt for the booby trap."
        };
        deathMessages.put(VANILLA_DEATH_EVENTS.DISPENSER, Arrays.asList(defaultDispenserMessages));

        /** Creating the default Posion Kill messages*/
        defaultPosionMessages = new String[]{
                "&5%n&7 swalloed the wrong stuff!",
                "There was a reason the bottle had a skull on it, &5%n&7",
                "&5%n&7 should have asked Flavia de Luce before taking that.",
                "&5%n&7 shouldn't drink tea with the Brewsters.",
                "&5%n&7 is now part of the locks of Panama.",
                "&5%n&7 said: aarrgghhhh."
        };
        deathMessages.put(VANILLA_DEATH_EVENTS.POISON, Arrays.asList(defaultPosionMessages));

        /** Creating the default Magic Kill messages*/
        defaultMagicMessages = new String[]{
                "&5%n&7 got killed by a Harry Potter lookalike!",
                "It was: Klaatu barada nikto. &5%n&7",
                "&5%n&7 felt the force.",
                "&5%n&7 thinks that there should be more to magic than just shizzle",
                "&5%n&7 should ask Rincewind the next time"
        };
        deathMessages.put(VANILLA_DEATH_EVENTS.MAGIC, Arrays.asList(defaultMagicMessages));

        /** Creating the default IronGolem Kill messages*/
        defaultIronGolemMessages = new String[]{
                "&5%n&7 got killed by an iron fist!",
                "A thing born from a pumpkin killed &5%n&7",
                "&5%n&7 thought hitting a villager was a good idea.",
                "&5%n&7 shouldn't underestimate the local police force."
        };
        deathMessages.put(VANILLA_DEATH_EVENTS.IRON_GOLEM, Arrays.asList(defaultIronGolemMessages));

        // ToDo add new messages on top
    }


    /**
     * Method to load the configuration into the deathMessageFileConfig variables
     */

    private void loadCustomConfig() {

        // DeathTp Messages

        Logger.config("Loading Vanilla death messages...");
        deathMessages.put(VANILLA_DEATH_EVENTS.FALL, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.FALL.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.DROWNING, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.DROWNING.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.FIRE, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.FIRE.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.FIRE_TICK, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.FIRE_TICK.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.LAVA, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.LAVA.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.CREEPER, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.CREEPER.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.SKELETON, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.SKELETON.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.SPIDER, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.SPIDER.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.ZOMBIE, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.ZOMBIE.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.PVP, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.PVP.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.PVP_FISTS, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.PVP_FISTS.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.BLOCK_EXPLOSION, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.BLOCK_EXPLOSION.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.CONTACT, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.CONTACT.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.GHAST, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.GHAST.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.SLIME, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.SLIME.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.SUFFOCATION, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.SUFFOCATION.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.PIG_ZOMBIE, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.PIG_ZOMBIE.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.VOID, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.VOID.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.WOLF, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.WOLF.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.LIGHTNING, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.LIGHTNING.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.SUICIDE, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.SUICIDE.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.UNKNOWN, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.UNKNOWN.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.STARVATION, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.STARVATION.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.ENDER_MAN, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.ENDER_MAN.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.CAVE_SPIDER, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.CAVE_SPIDER.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.SILVERFISH, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.SILVERFISH.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.PVP_TAMED, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.PVP_TAMED.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.GIANT, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.GIANT.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.BLAZE, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.BLAZE.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.ENDER_DRAGON, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.ENDER_DRAGON.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.MAGMA_CUBE, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.MAGMA_CUBE.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.DISPENSER, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.DISPENSER.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.POISON, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.POISON.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.MAGIC, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.MAGIC.toString()).getList());
        deathMessages.put(VANILLA_DEATH_EVENTS.IRON_GOLEM, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.IRON_GOLEM.toString()).getList());

        //ToDo add new deathMessages to the top
        for (VANILLA_DEATH_EVENTS deathEventType : VANILLA_DEATH_EVENTS.VALUES) {
            Logger.debug("deathEventType", deathEventType);
            Logger.config(deathMessages.get(deathEventType).size() + " messages loaded for " + deathEventType);
        }
        // Debugging

        Logger.debug("deathMessages", deathMessages);

    }

    // And than we write it....


    /**
     * Method to write the custom vanillaDeathMessagesConfig variables into the vanillaDeathMessagesConfig file
     *
     * @param stream will be handed over by  writeConfig
     */

    private void writeCustomDeathMessages(PrintWriter stream) {
        //Start here writing your vanillaDeathMessagesConfig variables into the vanillaDeathMessagesConfig file inkl. all comments



    }


    // *******************************************************************************************************

    // And now you need to create the getters and setters if needed for your vanillaDeathMessagesConfig variables


    // The plugin specific getters start here!


    public static String getDeathMessage(DeathDetailDTP deathDetail) {
        String message;
        List<String> messages = deathMessages.get(deathDetail.getCauseOfDeath());

        if (messages == null) {
            message = DEFAULT_DEATH_MESSAGE;
        } else {
            if (config.isUseDisplayNameforBroadcasts()) {
                message = messages.get(random.nextInt(messages.size())).replace("%n", deathDetail.getPlayer().getDisplayName());
            } else {
                message = messages.get(random.nextInt(messages.size())).replace("%n", deathDetail.getPlayer().getName());
            }
        }

        if (deathDetail.isPVPDeath()) {
            if (config.isUseDisplayNameforBroadcasts()) {
                message = message.replace("%i", deathDetail.getMurderWeapon()).replace("%a", deathDetail.getKiller().getDisplayName());
            } else {
                message = message.replace("%i", deathDetail.getMurderWeapon()).replace("%a", deathDetail.getKiller().getName());
            }
        }

        return UtilsDTP.convertColorCodes(message);
    }



    /**
     * Method to return the Config File Version
     *
     * @return configVer  Config File Version
     */
    public String getVanillaDeathMessagesVer() {
        return vanillaDeathMessagesVer;
    }

    public boolean isVanillaDeathMessagesRequiresUpdate() {
        return vanillaDeathMessagesRequiresUpdate;
    }

    // And the rest

    // Setting up the vanillaDeathMessagesConfig

    /**
     * Method to setup the configuration.
     * If the configuration file doesn't exist it will be created by {@link #defaultDeathMessages()}
     * After that the configuration is loaded {@link #loadDeathMessages()}
     * We than check if an configuration update is necessary {@link #updateNecessary()}
     * and if {@link team.cascade.spout.messagechanger.config.CONFIG#CONFIG_AUTO_UPDATE} is true we update the configuration {@link #updateDeathMessages()}
     *
     * @see #defaultDeathMessages()
     * @see #loadDeathMessages()
     * @see #updateNecessary()
     * @see #updateDeathMessages()
     */

    public void init() {
        setupCustomDefaultVariables();
        // Checking if vanillaDeathMessagesConfig file exists, if not create it
        File tempFile = (new File(messagesPath,VANILLA_DEATHMESSAGES_FILENAME));
        if (!tempFile.exists()){

            if (!writeDeathMessages()){
                success = false;
                Logger.config("There was an issue writing the spoutmessages file, using Internal defaults");
            }
        }
        if (success){
            if (!loadDeathMessages()){
                Logger.config("There was an issue reading the spoutmessages file, using Internal defaults");
            }
        }

        // Checking internal deathMessagesCurrent and vanillaDeathMessagesConfig file vanillaDeathMessagesVer

        updateNecessary();
        // If vanillaDeathMessagesConfig file has new options update it if enabled
        if (CONFIG.CONFIG_AUTO_UPDATE.getBoolean()) {
            updateDeathMessages();
        }
    }


    // Loading the configuration

    /**
     * Method for loading the configuration from disk.
     * First we get the vanillaDeathMessagesConfig object from disk, than we
     * read in the standard configuration part.
     * We also log a message if #debugLogEnabled
     * and we produce some debug logs.
     * After that we load the custom configuration part #loadCustomConfig()
     *
     * @see #loadCustomConfig()
     */

    private boolean loadDeathMessages() {
        try {
                    vanillaDeathMessagesConfig.load();
                } catch (ConfigurationException e) {
                    Logger.warning("There where problems loading the Vanilla Death Messages File",e);
                    return false;
                }
        // Starting to update the standard configuration
        vanillaDeathMessagesVer = vanillaDeathMessagesConfig.getNode("vanillaDeathMessagesVer").getString();
        // Debug OutPut NOW!
        Logger.debug("deathMessagesCurrent", vanillaDeathMessagesCurrent);
        Logger.debug("vanillaDeathMessagesVer", vanillaDeathMessagesVer);
        loadCustomConfig();
        Logger.config("Vanilla Death Messages v." + vanillaDeathMessagesVer + " loaded.");
        return true;
    }


    //  Writing the vanillaDeathMessagesConfig file

    /**
     * Method for writing the deathmessages file.
     * First we write the standard configuration part, than we
     * write the custom configuration part via #writeCustomConfig()
     *
     * @return true if writing the vanillaDeathMessagesConfig was successful
     *
     * @see #writeCustomDeathMessages(java.io.PrintWriter)
     */

    private boolean writeDeathMessages() {
        boolean success = false;
        try {

            File folder = main.getDataFolder();
            if (folder != null) {
                folder.mkdirs();
            }
            String pluginPath = plugin.getDataFolder() + System.getProperty("file.separator");
            PluginDescriptionFile pdfFile = this.plugin.getDescription();
            String authors = getAuthors();
            pluginName = pdfFile.getName();
            OutputStream outputStream = new FileOutputStream(pluginPath + deathMessageFileName);
            stream = new PrintWriter(new OutputStreamWriter(outputStream, "utf-8"));
            //Let's write our vanillaDeathMessagesConfig ;)
            stream.println("# " + pluginName + " " + pdfFile.getVersion() + " by " + authors);
            stream.println("#");
            stream.println("# Deathmessage File for " + pluginName + ".");
            stream.println("#");
            stream.println("# DeathMessages Version");
            stream.println("vanillaDeathMessagesVer: \"" + vanillaDeathMessagesVer + "\"");
            stream.println();

            stream.println("#--------- Messages  for DeathTpPlus");
            stream.println();
            stream.println("# Colors");
            stream.println("#");
            stream.println("# &0 Black");
            stream.println("# &1 Navy");
            stream.println("# &2 Green");
            stream.println("# &3 Blue");
            stream.println("# &4 Red");
            stream.println("# &5 Purple");
            stream.println("# &6 Gold");
            stream.println("# &7 LightGray");
            stream.println("# &8 Gray");
            stream.println("# &9 DarkPurple");
            stream.println("# &a LightGreen");
            stream.println("# &3 LightBlue");
            stream.println("# &c Rose");
            stream.println("# &d LightPurple");
            stream.println("# &e Yellow");
            stream.println("# &f White");
            stream.println("#");
            stream.println("# Make sure you enclose the messages in \"");

            stream.println("#");
            stream.println();
            stream.println("#");
            stream.println("#--------- Deathmessages");
            stream.println("# Must contain at least 1 line. If there are more, it will appear randomly when a person dies.");
            stream.println("# %n for player who died");
            stream.println("# %a name of player who attacked in pvp deaths");
            stream.println("# %i for item a player was using to kill someone else");
            stream.println("#");

            for (VANILLA_DEATH_EVENTS deathEventType : VANILLA_DEATH_EVENTS.VALUES) {

                stream.println(deathEventType.toString() + ":");

                for (String msg : deathMessages.get(deathEventType)) {
                    msg = msg.replace("''", "'");
                    stream.println("    - \"" + msg.replace("\"", "'") + "\"");
                }

            }

            stream.println();

            stream.close();

            success = true;

        } catch (FileNotFoundException e) {
            log.warning("Error saving the " + deathMessageFileName + ".");
        } catch (UnsupportedEncodingException e) {
            log.warning("Error saving the " + deathMessageFileName + ".");
        }
        log.debug("Default DeathMessages written", success);
        return success;
    }


    // Checking if the deathMessagesVersions differ

    /**
     * Method to check if the configuration version are different.
     * will set #deathMessagesRequiresUpdate to true if versions are different
     */
    private void updateNecessary() {
        if (vanillaDeathMessagesVer.equalsIgnoreCase(vanillaDeathMessagesCurrent)) {
            Logger.config("Vanilla Death Messages are up to date");
        } else {
            Logger.warning("Vanilla Death Messages are not up to date!");
            Logger.config("Vanilla Death Messages File Version: " + vanillaDeathMessagesVer);
            Logger.config("Internal Vanilla Death Messages Version: " + vanillaDeathMessagesCurrent);
            Logger.info("It is suggested to update the " + VANILLA_DEATHMESSAGES_FILENAME + "!");
            vanillaDeathMessagesRequiresUpdate = true;
        }
    }


    // Updating the vanillaDeathMessagesConfig

    /**
     * Method to update the configuration if it is necessary.
     */
    private void updateDeathMessages() {
        if (vanillaDeathMessagesRequiresUpdate) {
            vanillaDeathMessagesVer = vanillaDeathMessagesCurrent;
            if (writeDeathMessages()) {
                Logger.config("Vanilla Death Messages were updated with new default values.");
                Logger.config("Please change them to your liking.");
            } else {
                Logger.warning("Vanilla Death Messages file could not be auto updated.");
                Logger.config("Please rename " + VANILLA_DEATHMESSAGES_FILENAME + " and try again.");
            }
        }
    }

    // Reloading the vanillaDeathMessagesConfig

    /**
     * Method to reload the configuration.
     */

    public void reloadDeathMessages() {
        loadDeathMessages();
        Logger.config("Vanilla Death Messages reloaded");

    }

    public static VanillaMessages getInstance(){
        return instance;
    }


    public void saveDeathMessages() {
        writeDeathMessages();
    }
}
