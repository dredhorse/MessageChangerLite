package de.frozenbrain.MessageChanger;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.config.ConfigurationNode;

public class mcPlayerListener extends PlayerListener {
	
	private final mcPlugin plugin;
	
	public mcPlayerListener(mcPlugin plugin) {
		
		this.plugin = plugin;
		
	}
	
	public void onPlayerLogin(PlayerLoginEvent event) {
		switch(event.getResult()) {
		case KICK_BANNED:
			event.setKickMessage(getMessage("KICK_BANNED", event.getPlayer(), event.getKickMessage()));
			break;
		case KICK_WHITELIST:
			event.setKickMessage(getMessage("KICK_WHITELIST", event.getPlayer(), event.getKickMessage()));
			break;
		case KICK_FULL:
			event.setKickMessage(getMessage("KICK_FULL", event.getPlayer(), event.getKickMessage()));
			break;
		}
	}
	
	public void onPlayerJoin(PlayerJoinEvent event) {
		if(getMessage("PLAYER_JOIN", event.getPlayer(), event.getJoinMessage()).equals("")) {
			event.setJoinMessage(null);
		} else {
			event.setJoinMessage(getMessage("PLAYER_JOIN", event.getPlayer(), event.getJoinMessage()));
		}
	}
	
	public void onPlayerKick(PlayerKickEvent event) {
		if(getMessage("KICK_KICK_LEAVEMSG", event.getPlayer(), event.getLeaveMessage()).equals("")) {
			event.setLeaveMessage(null);
		} else {
			event.setLeaveMessage(getMessage("KICK_KICK_LEAVEMSG", event.getPlayer(), event.getLeaveMessage()));
		}
		event.setReason(getMessage("KICK_KICK_REASON", event.getPlayer(), event.getReason()));
	}
	
	public void onPlayerQuit(PlayerQuitEvent event) {
		if(getMessage("PLAYER_QUIT", event.getPlayer(), event.getQuitMessage()).equals("")) {
			event.setQuitMessage(null);
		} else {
			event.setQuitMessage(getMessage("PLAYER_QUIT", event.getPlayer(), event.getQuitMessage()));
		}
	}
	
	private String getMessage(String msg, Player player, String defMsg) {
		String pName = player.getName();
		String world = player.getWorld().getName();
		ConfigurationNode node = plugin.config.getNode(world);
		if(node != null) {
			if(plugin.Permissions != null) {
				String group = plugin.Permissions.getGroup(world, pName);
				if(group != null) {
					ConfigurationNode nodeGroup = plugin.config.getNode(world).getNode(group);
					if((nodeGroup != null) && (plugin.config.getNode(world).getNode(group).getString(msg) != null)) {
						return plugin.config.getNode(world).getNode(group).getString(msg).replace("%pName", pName).replace("%msg", defMsg).replaceAll("(&([a-f0-9]))", "\u00A7$2");
					}
				}
			}
			ConfigurationNode nodeWild = plugin.config.getNode(world).getNode("*");
			if((nodeWild != null) && (plugin.config.getNode(world).getNode("*").getString(msg) != null)) {
				return plugin.config.getNode(world).getNode("*").getString(msg).replace("%pName", pName).replace("%msg", defMsg).replaceAll("(&([a-f0-9]))", "\u00A7$2");
			}
		}
		return plugin.config.getString(msg).replace("%pName", pName).replace("%msg", defMsg).replaceAll("(&([a-f0-9]))", "\u00A7$2");
	}
	
}
