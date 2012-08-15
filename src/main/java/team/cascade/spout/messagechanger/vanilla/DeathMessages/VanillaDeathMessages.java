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

package team.cascade.spout.messagechanger.vanilla.DeathMessages;

import org.spout.api.exception.ConfigurationException;
import org.spout.api.util.config.yaml.YamlConfiguration;
import team.cascade.spout.messagechanger.MessageChanger;
import team.cascade.spout.messagechanger.config.CONFIG;
import team.cascade.spout.messagechanger.enums.VANILLA_DEATH_EVENTS;
import team.cascade.spout.messagechanger.helper.Logger;
import team.cascade.spout.messagechanger.helper.Messenger;
import team.cascade.spout.messagechanger.helper.file.MessagesHeader;
import team.cascade.spout.messagechanger.helper.file.UnicodeUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Handels loading and saving of the Vanilla messages
 *
 * @author $Author: dredhorse$
 * @version $FullVersion$
 * @todo implement yaml fix
 */
public class VanillaDeathMessages {


    /*
    * Default Death Message
    */
    private static final String DEFAULT_DEATH_MESSAGE = "%n died from unknown causes";

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
    private static HashMap<VANILLA_DEATH_EVENTS, Set<String>> vanilla_death_events = new HashMap<VANILLA_DEATH_EVENTS, Set<String>>();

    /**
     * Temporary List to hold the messages
     */

    private Set<String> deathMessages;

    private MessageChanger main;

    private static VanillaDeathMessages instance;

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



    public VanillaDeathMessages(MessageChanger main) {
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
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList("&5%n&7 tripped and fell...down a cliff.",
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
                "&5%n&7 must have had their shoelaces tied together."));
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.FALL, deathMessages);
        /** Creating the default drowning messages*/
        deathMessages = new HashSet<String>();
        deathMessages.addAll( Arrays.asList("&5%n&7 drowned",
                "&5%n&7 become one with the ocean!",
                "&5%n&7 sunk to the bottom of the ocean.",
                "&5%n&7 went diving but forgot the diving gear!",
                "&5%n&7 needs swimming lessons.",
                "&5%n 's&7 lungs have been replaced with H20.",
                "&5%n&7 forgot to come up for air!",
                "&5%n&7 is swimming with the fishes!",
                "&5%n&7 had a surfing accident!",
                "&5%n&7 tried to walk on water.",
                "&5%n&7 set a record for holding breath under water."));
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.DROWNING,deathMessages);
        /** Creating the default fire messages*/
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList("&5%n&7 burned to death.",
                "&5%n&7 was set on fire!",
                "&5%n&7 is toast! Literally...",
                "&5%n&7 just got barbequed!",
                "&5%n&7 forgot to stop, drop, and roll!",
                "&5%n&7 is extra-crispy!",
                "&5%n&7 spontaneously combusted!",
                "&5%n&7 put his hands in the toaster!",
                "&5%n&7 just got burned!"));
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.FIRE, deathMessages);
        /** Creating the default fire_tick messages*/
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList("&5%n&7 burned to death.",
                "&5%n&7 was set on fire!",
                "&5%n&7 is toast! Literally...",
                "&5%n&7 just got barbequed!",
                "&5%n&7 forgot to stop, drop, and roll!",
                "%n&7 likes it extra-crispy!",
                "&5%n&7 spontaneously combusted!",
                "&5%n&7 put his hands in the toaster!",
                "&5%n&7 just got burned!"));
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.FIRE_TICK, deathMessages);
        /** Creating the default lava messages*/
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList(
                "&5%n&7 became obsidian.",
                "&5%n&7 was caught in an active volcanic eruption!",
                "&5%n&7 tried to swim in a pool of lava.",
                "&5%n&7 was killed by a lava eruption!",
                "&5%n&7 was forged into obsidian by molten lava.",
                "&5%n&7 took a dip in the wrong kind of pool!",
                "&5%n&7 found out how to encase himself in carbonite.",
                "&5%n&7! the floor is lava! The floor is lava!"

        ));
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.LAVA, deathMessages);
        /** Creating the default creeper messages*/
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList(
                "&5%n&7 was creeper bombed!",
                "A creeper exploded on &5%n&7!",
                "A creeper snuck up on &5%n&7!",
                "A creeper tried to make love with &5%n&7...mmm.",
                "&5%n&7 just got the KiSSssss of death!",
                "&5%n&7 tried to hug a creeper!",
                "&5%n&7 is frowning like a creeper now!"
        ));
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.CREEPER, deathMessages);
        /** Creating the default skeleton messages*/
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList(
                "A skeleton shot &5%n&7 to death!",
                "&5%n&7 was on the wrong end of the bow. ",
                "&5%n&7 had a skeleton in the closet...",
                "&5%n&7! strafe the arrows! Strafe the arrows!",
                "&7A skeleton just got a head shot on &5%n&7!"
        ));
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.SKELETON, deathMessages);
        /** Creating the default spider messages*/
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList(
                "&5%n&7 is all webbed up.",
                "&5%n&7 got trampled by arachnids!",
                "&5%n&7 got jumped by a spidah!",
                "Spiders just climbed all over &5%n&7!",
                "&5%n&7 forgot spiders could climb!"
        ));
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.SPIDER, deathMessages);
        /** Creating the default zombie messages*/
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList(
                "&5%n&7 was punched to death by zombies!",
                "&5%n&7 was bitten by a zombie!",
                "&5%n&7 fell to the hunger of the horde!",
                "&5%n&7 Hasn't played enough L4D2!",
                "&5%n&7 couldn't run faster than the zombie!",
                "&5%n&7 should have invested in a shotgun."
        ));
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.ZOMBIE, deathMessages);
        /** Creating the default pvp messages*/
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList(
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
        ));
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.PVP, deathMessages);
        /** Creating the default pvp-fists messages*/
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList(
                "&f%a&7 pummeled &5%n&7 to death",
                "&f%a&7 crusted &5%n&7 with their bare hands"
        ));
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.PVP_FISTS, deathMessages);
        /** Creating the default block_explosion messages*/
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList(
                "Careful &5%n&7, TNT goes boom!",
                "&5%n&7 was last seen playing with dynamite.",
                "&5%n&7 exploded into a million pieces!",
                "&5%n&7 cut the wrong wire!",
                "&5%n&7 left his (bloody) mark on the world.",
                "&5%n&7 attempted to exterminate gophers with dynamite!",
                "&5%n&7 played land mine hop scotch!",
                "&5%n&7 stuck his head in a microwave!"
        ));
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.BLOCK_EXPLOSION, deathMessages);
        /** Creating the default entity_explosion messages*/
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList(
                "Well... something exploded on &5%n&7!"
        ));
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.ENTITY_EXPLOSION, deathMessages);
        /** Creating the default contact messages*/
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList(
                "&5%n&7 got a little too close to a cactus!",
                "&5%n&7 tried to hug a cactus!",
                "&5%n&7 needs to be more careful around cactuses!",
                "&5%n&7 felt the wrath of Cactus Jack!",
                "&5%n&7 learned the result of rubbing a cactus!",
                "&5%n&7 died from cactus injuries!",
                "&5%n&7 poked himself with a cactus...and died.",
                "&5%n&7 ran into some pointy green stuff that wasn't grass.",
                "&5%n&7 was distracted by a tumble weed and died by cactus."
        ));
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.CONTACT, deathMessages);
        /** Creating the default ghast messages*/
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList(
                "&5%n&7 was blown to bits by a ghast.",
                "Those aren't babies you hear, &5%n&7!",
                "&5%n&7 was killed by a ghostly hadouken!",
                "&5%n&7 just got exploded by a fireball!",
                "&5%n&7 got too comfy in the Nether!"
        ));
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.GHAST, deathMessages);
        /** Creating the default slime messages*/
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList(
                "A slime found &5%n&7. The slime won.",
                "&5%n&7 wanted to play with slime. The slime wasn't happy.",
                "&5%n&7 was killed for saying, \"Eeeeehhhh, he slimed me!\"",
                "&5%n&7 crossed the streams."
        ));
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.SLIME, deathMessages);
        /** Creating the default suffocation messages*/
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList(
                "&5%n&7 suffocated.",
                "&5%n&7 was looking up while digging!",
                "&5%n&7 choked to death on earth!",
                "&5%n&7 choked on a ham sandwich"
        ));
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.SUFFOCATION, deathMessages);
        /** Creating the default pigzombie messages*/
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList(
                "&5%n&7 lost a fight against a zombie pig.",
                "&5%n&7, touching a zombie pig is never a good idea.",
                "&5%n&7, looked at a pigzombie the wrong way."
        ));
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.PIG_ZOMBIE, deathMessages);
        /** Creating the default void messages*/
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList(
                "&5%n&7 died in The Void.",
                "&5%n&7 fell into nothingness",
                "Look, &5%n&7, no floor"
        ));
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.VOID, deathMessages);
        /** Creating the default Wolfs messages*/
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList(
                "&5%n&7 became a wolf's lunch.",
                "&5%n&7 couldn't howl with the wolfs."
        ));
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.WOLF, deathMessages);
        /** Creating the default lightning messages*/
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList(
                "&5%n&7 was struck down by Zeus' bolt.",
                "&5%n&7 was electrecuted.",
                "&5%n&7 figured out that it wasn't a pig's nose in the wall."
        ));
        /** Creating the default lightning messages*/
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.LIGHTNING, deathMessages);
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList(
                "&5%n&7 took matters into his own hands.",
                "&5%n&7 isn't causing NPE''s anymore."
        ));
        /** Creating the default suicide messages*/
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.SUICIDE, deathMessages);
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList(
                "&5%n&7 died from unknown causes.",
                "&5%n&7 imploded into nothingness",
                "&5%n&7 was vaporized",
                "&5%n&7 died from explosive diarrhea",
                "&5%n&7 was killed by Chuck Norris",
                "&5%n&7 was running with scissors...now he runs no more",
                "&5%n&7 was hit by a falling piano",
                "&5%n&7 was assassinated by a shuriken headshot from the shadow",
                "&5%n&7 was barrel rolling...and died",
                "&5%n&7 was killed by Cthulhu",
                "&5%n&7 forgot to wear his spacesuit",
                "&5%n&7 choked on a ham sandwich",
                "&5%n&7 died at the hands of ninja assassins"
        ));
        /** Creating the default unknown messages*/
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.UNKNOWN, deathMessages);
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList(
                "&5%n&7 did forget to eat his lunch.",
                "&5%n&7 didn't find the next Burger.",
                "&5%n&7 became a skeleton.",
                "&5%n&7 TALKS ALL CAPITALS NOW.",
                "&5%n&7 should have packed a lunch."
        ));
        /** Creating the default starvation messages*/
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.STARVATION, deathMessages);
        /** Creating the default enderman messages*/
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList(
                "&5%n&7 looked at a enderman the wrong way.",
                "An enderman pulled &5%n&7 leg..... off!"
        ));
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.ENDER_MAN, deathMessages);
        /** Creating the default cavespider messages*/
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList(
                "&5%n&7 will never sing itsybitsyspider again",
                "&5%n&7 is all webbed up.",
                "&5%n&7 was trampled by arachnids!",
                "&5%n&7 was jumped by a spidah!",
                "Spiders just climbed all over &5%n&7!",
                "&5%n&7 forgot spiders could climb!"
        ));
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.CAVE_SPIDER, deathMessages);
        /** Creating the default silverfish messages*/
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList(
                "&5%n&7 was killed by a silverfish!",
                "&5%n&7 found something hidden below a rock",
                "&5%n&7 You can't stuff that many fish into your mouth!",
                "&5%n&7 activated a silverfish trap",
                "&54%n''s&7 last words  'Oh god they''re coming out of the walls!'"
        ));
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.SILVERFISH, deathMessages);
        /** Creating the default PVP tamed messages*/
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList(
                "&5%n&7 was mauled by &f%a's&7 &3%i",
                "&5%n''s&7 hand was bitten by &f%a's&7 &3%i"
        ));
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.PVP_TAMED, deathMessages);
        /** Creating the default Giant messages*/
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList(
                "&5%n&7 was stomped by a giant!",
                "&5%n&7 was flattened by a giant!",
                "&5%n&7 shouldn't have climbed the bean stalk."
        ));
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.GIANT,deathMessages);
        /** Creating the default Blaze messages*/
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList(
                "&5%n&7 was set on fire at a blaze, well.. by a blaze!",
                "&5%n&7 was airbombed!",
                "&5%n&7, not everything on fire is a player!",
                "&5%n&7 nope, that wasn't a rocket."
        ));
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.BLAZE, deathMessages);
        /** Creating the default Enderdragon messages*/
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList(
                "&5%n&7 died at the end... IN the end.",
                "&5%n&7 looking up would have helped.",
                "Well, Anne McCaffrey didn't talk about that kind of Dragon, right &5%n&7?",
                "No egg for you, &5%n&7.",
                "&5%n&7 took the easy way out of \"The End\".",
                "&5%n&7 will never get to read that end poem!",
                "&5%n&7 made a generous donation to the Ender Dragon's hoard."
        ));
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.ENDER_DRAGON,deathMessages);
        /** Creating the default MagmaCube messages*/
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList(
                "&5%n&7 didn't expect this kind of slinky!",
                "&5%n&7 got eaten by a cube.",
                "&5%n&7 got coombad by a cube.",
                "Well, technically &5%n&7, it was a slime"
        ));
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.MAGMA_CUBE, deathMessages);
        /** Creating the default Dispenser Kill messages*/
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList(
                "&5%n&7 got shot in the back by a dispenser!",
                "Again the wrong weight Indi? well. &5%n&7",
                "&5%n&7 thinks he is Indiana Jones.",
                "&5%n&7 felt for the booby trap."
        ));
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.DISPENSER, deathMessages);

        /** Creating the default Posion Kill messages*/
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList(
                "&5%n&7 swallowed the wrong stuff!",
                "There was a reason the bottle had a skull on it, &5%n&7",
                "&5%n&7 should have asked Flavia de Luce before taking that.",
                "&5%n&7 shouldn't drink tea with the Brewsters.",
                "&5%n&7 is now part of the locks of Panama.",
                "&5%n&7 said: aarrgghhhh."
        ));
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.POISON, deathMessages);

        /** Creating the default Magic Kill messages*/
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList(
                "&5%n&7 got killed by a Harry Potter lookalike!",
                "It was: Klaatu barada nikto. &5%n&7",
                "&5%n&7 felt the force.",
                "&5%n&7 thinks that there should be more to magic than just shizzle",
                "&5%n&7 should ask Rincewind the next time"
        ));
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.MAGIC, deathMessages);

        /** Creating the default IronGolem Kill messages*/
        deathMessages = new HashSet<String>();
        deathMessages.addAll(Arrays.asList(
                "&5%n&7 got killed by an iron fist!",
                "A thing born from a pumpkin killed &5%n&7",
                "&5%n&7 thought hitting a villager was a good idea.",
                "&5%n&7 shouldn't underestimate the local police force."
        ));
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.IRON_GOLEM, deathMessages);

        // ToDo add new messages on top
    }


    /**
     * Method to load the configuration into the deathMessageFileConfig variables
     */

    private void loadCustomConfig() {

        // DeathTp Messages

        Logger.config("Loading Vanilla death messages...");

        for (String eventString : vanillaDeathMessagesConfig.getNode("Messages").getKeys(false)){
            VANILLA_DEATH_EVENTS event = VANILLA_DEATH_EVENTS.valueOf(eventString.toLowerCase());
            vanilla_death_events.put(event, new HashSet<String>((List<String>) vanillaDeathMessagesConfig.getNode("Messages."+eventString).getList()));
        }


        /*
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.FALL, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.FALL.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.DROWNING, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.DROWNING.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.FIRE, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.FIRE.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.FIRE_TICK, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.FIRE_TICK.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.LAVA, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.LAVA.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.CREEPER, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.CREEPER.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.SKELETON, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.SKELETON.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.SPIDER, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.SPIDER.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.ZOMBIE, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.ZOMBIE.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.PVP, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.PVP.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.PVP_FISTS, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.PVP_FISTS.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.BLOCK_EXPLOSION, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.BLOCK_EXPLOSION.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.CONTACT, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.CONTACT.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.GHAST, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.GHAST.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.SLIME, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.SLIME.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.SUFFOCATION, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.SUFFOCATION.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.PIG_ZOMBIE, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.PIG_ZOMBIE.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.VOID, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.VOID.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.WOLF, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.WOLF.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.LIGHTNING, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.LIGHTNING.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.SUICIDE, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.SUICIDE.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.UNKNOWN, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.UNKNOWN.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.STARVATION, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.STARVATION.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.ENDER_MAN, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.ENDER_MAN.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.CAVE_SPIDER, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.CAVE_SPIDER.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.SILVERFISH, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.SILVERFISH.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.PVP_TAMED, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.PVP_TAMED.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.GIANT, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.GIANT.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.BLAZE, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.BLAZE.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.ENDER_DRAGON, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.ENDER_DRAGON.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.MAGMA_CUBE, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.MAGMA_CUBE.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.DISPENSER, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.DISPENSER.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.POISON, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.POISON.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.MAGIC, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.MAGIC.toString()).getList());
        vanilla_death_events.put(VANILLA_DEATH_EVENTS.IRON_GOLEM, (List<String>) (List<?>) vanillaDeathMessagesConfig.getNode(VANILLA_DEATH_EVENTS.IRON_GOLEM.toString()).getList());
*/
        //ToDo add new vanilla_death_events to the top
        for (VANILLA_DEATH_EVENTS deathEventType : VANILLA_DEATH_EVENTS.VALUES) {
            Logger.debug("deathEventType", deathEventType);
            try {
                Logger.config(vanilla_death_events.get(deathEventType).size() + " messages loaded for " + deathEventType);
            } catch (NullPointerException e){
                Logger.config("There where no events for: " + deathEventType);
            }
        }
        // Debugging

        Logger.debug("vanilla_death_events", vanilla_death_events);

    }

    // And than we write it....


    // *******************************************************************************************************

    // And now you need to create the getters and setters if needed for your vanillaDeathMessagesConfig variables


    // The plugin specific getters start here!


    public static String getDeathMessage(VanillaDeathDetail vanillaDeathDetail) {
        String message;
        List<String> messages = new ArrayList<String>(vanilla_death_events.get(vanillaDeathDetail.getCauseOfDeath()));

        if (messages == null) {
            message = DEFAULT_DEATH_MESSAGE;
        } else {
            if (CONFIG.USE_DISPLAYNAME_FOR_BROADCAST.getBoolean()) {
                message = messages.get(random.nextInt(messages.size())).replace("%n", vanillaDeathDetail.getPlayer().getDisplayName());
            } else {
                message = messages.get(random.nextInt(messages.size())).replace("%n", vanillaDeathDetail.getPlayer().getName());
            }
        }

        if (vanillaDeathDetail.isPVPDeath()) {
            if (CONFIG.USE_DISPLAYNAME_FOR_BROADCAST.getBoolean()) {
                message = message.replace("%i", vanillaDeathDetail.getMurderWeapon()).replace("%a", vanillaDeathDetail.getKiller().getDisplayName());
            } else {
                message = message.replace("%i", vanillaDeathDetail.getMurderWeapon()).replace("%a", vanillaDeathDetail.getKiller().getName());
            }
        }

        return Messenger.convertColorCodes(message);
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
     * If the configuration file doesn't exist it will be created
     * After that the configuration is loaded {@link #loadDeathMessages()}
     * We than check if an configuration update is necessary {@link #updateNecessary()}
     * and if {@link team.cascade.spout.messagechanger.config.CONFIG#CONFIG_AUTO_UPDATE} is true we update the configuration {@link #updateDeathMessages()}
     *

     * @see #loadDeathMessages()
     * @see #updateNecessary()
     * @see #updateDeathMessages()
     */

    public void init() {
        success = true;
        setupCustomDefaultVariables();
        // Checking if vanillaDeathMessagesConfig file exists, if not create it
        File tempFile = (new File(messagesPath,VANILLA_DEATHMESSAGES_FILENAME));
        if (!tempFile.exists()){

            if (!writeDeathMessages()){
                success = false;
                Logger.config("There was an issue writing the Vanilla Death Messages file, using Internal defaults");
            }
        }
        if (success){
            if (!loadDeathMessages()){
                Logger.config("There was an issue reading the Vanilla Death Messages file, using Internal defaults");
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
     */

    private boolean writeDeathMessages() {
        boolean success = false;
        try {

            File folder = (new File(messagesPath));

            if (folder != null) {
                folder.mkdirs();
            }

            File messageFile = new File(messagesPath + VANILLA_DEATHMESSAGES_FILENAME);

            Logger.config("Writing the Vanilla Death Messages");
            MessagesHeader.saveTranslationHeader(messageFile, "Vanilla Death Messages");


            UnicodeUtil.saveUTF8File(messageFile, "# DeathMessages Version\n",true);
            UnicodeUtil.saveUTF8File(messageFile, "vanillaDeathMessagesVer: \"" + vanillaDeathMessagesVer + "\"\n",true);
            UnicodeUtil.saveUTF8File(messageFile,"\n",true);

            UnicodeUtil.saveUTF8File(messageFile, "#--------- Messages  for DeathTpPlus\n",true);
            UnicodeUtil.saveUTF8File(messageFile,"\n",true);
            UnicodeUtil.saveUTF8File(messageFile, "# Colors\n",true);
            UnicodeUtil.saveUTF8File(messageFile, "#\n",true);
            UnicodeUtil.saveUTF8File(messageFile, "# &0 Black\n",true);
            UnicodeUtil.saveUTF8File(messageFile, "# &1 Navy\n",true);
            UnicodeUtil.saveUTF8File(messageFile, "# &2 Green\n",true);
            UnicodeUtil.saveUTF8File(messageFile, "# &3 Blue\n",true);
            UnicodeUtil.saveUTF8File(messageFile, "# &4 Red\n",true);
            UnicodeUtil.saveUTF8File(messageFile, "# &5 Purple\n",true);
            UnicodeUtil.saveUTF8File(messageFile, "# &6 Gold\n",true);
            UnicodeUtil.saveUTF8File(messageFile, "# &7 LightGray\n",true);
            UnicodeUtil.saveUTF8File(messageFile, "# &8 Gray\n",true);
            UnicodeUtil.saveUTF8File(messageFile, "# &9 DarkPurple\n",true);
            UnicodeUtil.saveUTF8File(messageFile, "# &a LightGreen\n",true);
            UnicodeUtil.saveUTF8File(messageFile, "# &3 LightBlue\n",true);
            UnicodeUtil.saveUTF8File(messageFile, "# &c Rose\n",true);
            UnicodeUtil.saveUTF8File(messageFile, "# &d LightPurple\n",true);
            UnicodeUtil.saveUTF8File(messageFile, "# &e Yellow\n",true);
            UnicodeUtil.saveUTF8File(messageFile, "# &f White\n",true);
            UnicodeUtil.saveUTF8File(messageFile, "#\n",true);
            UnicodeUtil.saveUTF8File(messageFile, "# Make sure you enclose the messages in \"\n",true);

            UnicodeUtil.saveUTF8File(messageFile, "#\n",true);
            UnicodeUtil.saveUTF8File(messageFile,"\n",true);
            UnicodeUtil.saveUTF8File(messageFile, "#\n",true);
            UnicodeUtil.saveUTF8File(messageFile, "#--------- Deathmessages\n",true);
            UnicodeUtil.saveUTF8File(messageFile, "# Must contain at least 1 line. If there are more, it will appear randomly when a person dies.\n",true);
            UnicodeUtil.saveUTF8File(messageFile, "# %n for player who died\n",true);
            UnicodeUtil.saveUTF8File(messageFile, "# %a name of player who attacked in pvp deaths\n",true);
            UnicodeUtil.saveUTF8File(messageFile, "# %i for item a player was using to kill someone else\n",true);
            UnicodeUtil.saveUTF8File(messageFile, "#\n",true);
            UnicodeUtil.saveUTF8File(messageFile, "Messages:\n",true);

            for (VANILLA_DEATH_EVENTS deathEventType : vanilla_death_events.keySet()) {

                UnicodeUtil.saveUTF8File(messageFile, "    " + deathEventType.toString() + ":\n",true);

                for (String msg : vanilla_death_events.get(deathEventType)) {
                    msg = msg.replace("''", "'");
                    UnicodeUtil.saveUTF8File(messageFile, "        - \"" + msg.replace("\"", "'") + "\"\n",true);
                }

            }

            UnicodeUtil.saveUTF8File(messageFile,"\n",true);


            success = true;

        } catch (FileNotFoundException e) {
            Logger.warning("Error saving the " + VANILLA_DEATHMESSAGES_FILENAME + ".");
        } catch (UnsupportedEncodingException e) {
            Logger.warning("Error saving the " + VANILLA_DEATHMESSAGES_FILENAME + ".");
        } catch (IOException e) {
            Logger.warning("Error saving the " + VANILLA_DEATHMESSAGES_FILENAME + ".");
        }
        Logger.debug("Vanilla Death Messages written", success);
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

    public static VanillaDeathMessages getInstance(){
        return instance;
    }


    public void saveDeathMessages() {
        writeDeathMessages();
    }


}
