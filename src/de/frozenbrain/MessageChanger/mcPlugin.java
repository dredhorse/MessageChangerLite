package de.frozenbrain.MessageChanger;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;
import org.bukkit.util.config.ConfigurationNode;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class mcPlugin extends JavaPlugin {
	
	private final mcPlayerListener playerListener = new mcPlayerListener(this);
	public PermissionHandler Permissions;
	Configuration config;
	
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_KICK, playerListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_LOGIN, playerListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
		pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Priority.Normal, this);
		
		setupPermissions();
		reloadConfig();
		
		PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
	}
	
	public void onDisable() {
		Player[] players = getServer().getOnlinePlayers();
		String kickMsg = "";
		for (Player player: players) {
			kickMsg = getMessage("SERVER_STOP", player, "");
			if(!kickMsg.equals("")) {
				player.kickPlayer(kickMsg);
			}
		}
		System.out.println("MessageChanger disabled.");
	}
	
	private void setupPermissions() {
	      Plugin test = this.getServer().getPluginManager().getPlugin("Permissions");

	      if (this.Permissions == null) {
	          if (test != null) {
	              this.Permissions = ((Permissions)test).getHandler();
	              System.out.println("[MessageChanger] Permission system detected.");
	          } else {
	              System.out.println("[MessageChanger] Permission system not detected.");
	          }
	      }
	  }
	
	public void reloadConfig() {
		config = getConfiguration();
		config.load();
		config.getString("KICK_BANNED", "%msg");
		config.getString("KICK_WHITELIST", "%msg");
		config.getString("KICK_FULL", "%msg");
		config.getString("PLAYER_JOIN", "%msg");
		config.getString("KICK_KICK_REASON", "%msg");
		config.getString("KICK_KICK_LEAVEMSG", "%msg");
		config.getString("PLAYER_QUIT", "%msg");
		config.getString("SERVER_STOP", "Stopping the server..");
		config.save();
	}
	
	public String getMessage(String msg, Player player, String defMsg) {
		if(msg == null) return null;
		if(defMsg == null) defMsg = "";
		String pName = player.getName();
		String world = player.getWorld().getName();
		ConfigurationNode node = config.getNode(world);
		if(node != null) {
			if(Permissions != null) {
				String group = Permissions.getGroup(world, pName);
				if(group != null) {
					ConfigurationNode nodeGroup = config.getNode(world).getNode(group);
					if((nodeGroup != null) && (config.getNode(world).getNode(group).getString(msg) != null)) {
						return config.getNode(world).getNode(group).getString(msg).replace("%pName", pName).replace("%msg", defMsg).replaceAll("(&([a-f0-9]))", "\u00A7$2");
					}
				}
			}
			ConfigurationNode nodeWild = config.getNode(world).getNode("*");
			if((nodeWild != null) && (config.getNode(world).getNode("*").getString(msg) != null)) {
				return config.getNode(world).getNode("*").getString(msg).replace("%pName", pName).replace("%msg", defMsg).replaceAll("(&([a-f0-9]))", "\u00A7$2");
			}
		}
		return config.getString(msg).replace("%pName", pName).replace("%msg", defMsg).replaceAll("(&([a-f0-9]))", "\u00A7$2");
	}
	
}
