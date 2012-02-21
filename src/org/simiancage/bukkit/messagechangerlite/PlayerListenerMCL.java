package org.simiancage.bukkit.messagechangerlite;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class PlayerListenerMCL implements Listener {

	private final MessageChangerLite plugin;

	public PlayerListenerMCL(MessageChangerLite plugin) {

		this.plugin = plugin;

	}

	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		switch (event.getResult()) {
			case KICK_BANNED:
				event.setKickMessage(plugin.getMessage("KICK_BANNED", event.getPlayer(), event.getKickMessage()));
				break;
			case KICK_WHITELIST:
				event.setKickMessage(plugin.getMessage("KICK_WHITELIST", event.getPlayer(), event.getKickMessage()));
				break;
			case KICK_FULL:
				event.setKickMessage(plugin.getMessage("KICK_FULL", event.getPlayer(), event.getKickMessage()));
				break;
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		String msg = plugin.getMessage("PLAYER_JOIN", event.getPlayer(), event.getJoinMessage());
		if (msg.equals("")) {
			event.setJoinMessage(null);
		} else {
			event.setJoinMessage(plugin.getMessage("PLAYER_JOIN", event.getPlayer(), event.getJoinMessage()));
		}
	}

	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		if (plugin.ignore) {
			plugin.ignore = false;
			return;
		}

		String msg = plugin.getMessage("KICK_KICK_LEAVEMSG", event.getPlayer(), event.getReason());
		if (msg.equals("")) {
			event.setLeaveMessage(null);
		} else {
			event.setLeaveMessage(msg);
		}
		event.setReason(plugin.getMessage("KICK_KICK_REASON", event.getPlayer(), event.getReason()));
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		String msg = plugin.getMessage("PLAYER_QUIT", event.getPlayer(), event.getQuitMessage());
		if (msg.equals("")) {
			event.setQuitMessage(null);
		} else {
			event.setQuitMessage(msg);
		}
	}

	@EventHandler
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
		String msg = plugin.getMessage("CHANGED_WORLD", event.getPlayer(), "");
		if (msg.equals("")) {
			return;
		}
		String fromWorld = event.getFrom().getName();
		if (fromWorld == null) {
			fromWorld = "Unknown Territories";
		}
		msg = msg.replace("%fromWorld", fromWorld);
		event.getPlayer().sendMessage(msg);
	}


}
