package de.frozenbrain.MessageChanger;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;

public class mcPlayerListener extends PlayerListener {
	
	private final mcPlugin plugin;
	
	public mcPlayerListener(mcPlugin plugin) {
		
		this.plugin = plugin;
		
	}
	
	public void onPlayerLogin(PlayerLoginEvent event) {
		switch(event.getResult()) {
		case KICK_BANNED:
			event.disallow(Result.KICK_BANNED, parseMsg(plugin.msgKickBanned, event));
			break;
		case KICK_WHITELIST:
			event.disallow(Result.KICK_WHITELIST, parseMsg(plugin.msgKickWhitelist, event));
			break;
		case KICK_FULL:
			event.disallow(Result.KICK_FULL, parseMsg(plugin.msgKickFull, event));
			break;
		}
	}
	
	public void onPlayerJoin(PlayerJoinEvent event) {
		event.setJoinMessage(parseMsg(plugin.msgJoin, event));
	}
	
	public void onPlayerKick(PlayerKickEvent event) {
		event.setLeaveMessage(parseMsg(plugin.msgKickLeave, event));
		event.setReason(parseMsg(plugin.msgKickReason, event));
	}
	
	public void onPlayerQuit(PlayerQuitEvent event) {
		event.setQuitMessage(parseMsg(plugin.msgPlayerQuit, event));
	}
	
	
	
	private String parseMsg(String msg, PlayerQuitEvent event) {
		return parseMsg(msg, event.getPlayer().getName());
	}
	
	private String parseMsg(String msg, PlayerJoinEvent event) {
		return parseMsg(msg, event.getPlayer().getName());
	}
	
	private String parseMsg(String msg, PlayerKickEvent event) {
		return parseMsg(msg, event.getPlayer().getName());
	}
	
	private String parseMsg(String msg, PlayerLoginEvent event) {
		return parseMsg(msg, event.getPlayer().getName());
	}
	
	
	private String parseMsg(String msg, String pName) {
		return msg.replace("%pName", pName);
	}
	
}
