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

import org.spout.api.event.entity.EntityDeathEvent;
import org.spout.api.geo.World;
import org.spout.api.player.Player;
import org.spout.vanilla.event.player.PlayerDeathEvent;
import team.cascade.spout.messagechanger.enums.VANILLA_DEATH_EVENTS;

/**
 * Contains all the information about a Vanilla Death Event
 *
 * @author $Author: dredhorse$
 * @version $FullVersion$
 */
public class VanillaDeathDetail {
    private Player player;
   	private VANILLA_DEATH_EVENTS causeOfDeath;
   	private Player killer;
   	private String murderWeapon;
   	private PlayerDeathEvent playerDeathEvent;

   	public VanillaDeathDetail() {
   	}

   	public VanillaDeathDetail(PlayerDeathEvent event) {
   		player = event.getPlayer();
   		playerDeathEvent = event;

           // todo wait for implementation of damager

/*   		// Support for setHealth(0) which is used by essentials to do a suicide
   		try {
   			PlayerDeathEvent damageEvent = event;
   			if (damageEvent instanceof Damag) {
   				Entity damager = ((EntityDamageByEntityEvent) damageEvent).getDamager();
   				Logger.debug("damager", damager.toString());
   				if (damager instanceof Player) {
   					Logger.debug("Killed by an other player");
   					if (((Player) damager).getItemInHand().getType().equals(Material.AIR)) {
   						causeOfDeath = VANILLA_DEATH_EVENTS.PVP_FISTS;
   					} else {
   						causeOfDeath = VANILLA_DEATH_EVENTS.PVP;
   					}
   					murderWeapon = ((Player) damager).getItemInHand().getType().toString();
   					killer = (Player) damager;
   				} else if (damager instanceof Creature || damager instanceof Slime) {
   					Logger.debug("We have a creature or slime");
   					if (damager instanceof Tameable && ((Tameable) damager).isTamed()) {
   						causeOfDeath = VANILLA_DEATH_EVENTS.PVP_TAMED;
   						murderWeapon = UtilsDTP.getEntityType(damager).toString();
   						killer = (Player) ((Tameable) damager).getOwner();

   					} else {

   						try {

   							causeOfDeath = VANILLA_DEATH_EVENTS.valueOf(UtilsDTP.getEntityType(damager).toString());
   						} catch (IllegalArgumentException iae) {
   							Logger.severe("Please notify the developer of the following Error:");
   							Logger.severe("The following damager is not correctly implemented: " + UtilsDTP.getEntityType(damager));
   							causeOfDeath = VANILLA_DEATH_EVENTS.UNKNOWN;
   						}
   						Logger.debug("and it is: " + causeOfDeath);
   					}
   				} else if (damager instanceof Projectile) {
   					Logger.debug("this is a projectile");
   					Logger.debug("shooter", ((Projectile) damager).getShooter());
   					if (((Projectile) damager).getShooter() instanceof Player) {
   						causeOfDeath = VANILLA_DEATH_EVENTS.PVP;
   						murderWeapon = ((Projectile) damager).toString().replace("Craft", "");
   						killer = (Player) ((Projectile) damager).getShooter();
   					}
   					if (((Projectile) damager).getShooter() == null) {
   						//let's assume that null will only be caused by a dispenser!
   						causeOfDeath = VANILLA_DEATH_EVENTS.DISPENSER;
   						murderWeapon = ((Projectile) damager).toString().replace("Craft", "");
   					}
   					if (((Projectile) damager).getShooter().toString().equalsIgnoreCase("CraftSkeleton")) {
   						causeOfDeath = VANILLA_DEATH_EVENTS.SKELETON;
   						murderWeapon = ((Projectile) damager).toString().replace("Craft", "");
   					}

   				} else if (damager instanceof TNTPrimed) {
   					causeOfDeath = VANILLA_DEATH_EVENTS.BLOCK_EXPLOSION;
   				} else {
   					Logger.info("unknown enitity damager" + damager);
   				}
   			} else if (damageEvent != null) {
   				Logger.debug("DamageEvent is not by Entity");
   				try {
   					causeOfDeath = VANILLA_DEATH_EVENTS.valueOf(damageEvent.getCause().toString());
   				} catch (IllegalArgumentException e) {
   					causeOfDeath = VANILLA_DEATH_EVENTS.UNKNOWN;
   				}
   			}
   		} catch (NullPointerException npe) {
   			Logger.debug("normal detection of damageevent failed", npe);
   			Logger.debug("assuming you did use essentials or similar");
   			Logger.debug("which uses setHealth(0) to kill people");
   			Logger.info("Deathcause is being set to SUICIDE!");
   			causeOfDeath = VANILLA_DEATH_EVENTS.SUICIDE;
   			murderWeapon = "Essentials";
   		}


   		if (causeOfDeath == null) {
   			causeOfDeath = VANILLA_DEATH_EVENTS.UNKNOWN;
   			murderWeapon = "unknown";
   		}
   		Logger.debug("causeOfDeath", causeOfDeath);
   		Logger.debug("murderWeapon", murderWeapon);
   		Logger.debug("killer", killer);*/
   	}

   	public World getWorld() {
   		return player.getEntity().getWorld();
   	}

   	public Player getPlayer() {
   		return player;
   	}

   	public void setPlayer(Player player) {
   		this.player = player;
   	}

   	public VANILLA_DEATH_EVENTS getCauseOfDeath() {
   		return causeOfDeath;
   	}

   	public void setCauseOfDeath(VANILLA_DEATH_EVENTS causeOfDeath) {
   		this.causeOfDeath = causeOfDeath;
   	}

   	public Player getKiller() {
   		return killer;
   	}

   	public void setKiller(Player killer) {
   		this.killer = killer;
   	}

   	public String getMurderWeapon() {
   		return toCamelCase(murderWeapon);
   	}

   	public void setMurderWeapon(String murderWeapon) {
   		this.murderWeapon = murderWeapon;
   	}

   	public EntityDeathEvent getPlayerDeathEvent() {
   		return playerDeathEvent;
   	}

   	public void setPlayerDeathEvent(PlayerDeathEvent playerDeathEvent) {
   		this.playerDeathEvent = playerDeathEvent;
   	}

   	public Boolean isPVPDeath() {
   		return causeOfDeath == VANILLA_DEATH_EVENTS.PVP || causeOfDeath == VANILLA_DEATH_EVENTS.PVP_FISTS || causeOfDeath == VANILLA_DEATH_EVENTS.PVP_TAMED;
   	}

   	private static String toCamelCase(String rawItemName) {
   		String[] rawItemNameParts = rawItemName.split("_");
   		String itemName = "";

   		for (String itemNamePart : rawItemNameParts) {
   			itemName = itemName + " " + toProperCase(itemNamePart);
   		}

   		if (itemName.trim().equals("Air")) {
   			itemName = "Fists";
   		}

   		if (itemName.trim().equals("Bow")) {
   			itemName = "Bow & Arrow";
   		}

   		return itemName.trim();
   	}

   	private static String toProperCase(String str) {
   		if (str.length() < 1) {
   			return str;
   		}
   		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
   	}
   }
