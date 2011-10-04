package org.simiancage.bukkit.messagechangerlite;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;



public class MessageChangerLite extends JavaPlugin {
	
	private final mclPlayerListener playerListener = new mclPlayerListener(this);
	Configuration config;
	public boolean ignore = false;
	
	public void onEnable() {


        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_KICK, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_LOGIN, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Priority.Normal, this);

        reloadConfig();

        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println("[MessageChangerLite] v" + pdfFile.getVersion() + " enabled.");
    }
	
	public void onDisable() {
		// There's no easy way :/
		StackTraceElement[] st = new Throwable().getStackTrace();
		for(int i=0;i<st.length;i++) {
			// Go through the stack trace and look for the stop method
			if(st[i].getMethodName().equals("stop")) {
				// Yay, stop method found
				Player[] players = getServer().getOnlinePlayers();
				String kickMsg = "";
				for (Player player: players) {
					// Let's kick 'em!
					kickMsg = getMessage("SERVER_STOP", player, "");
					if(!kickMsg.equals("")) {
						// We don't want to override the SERVER_STOP message
						this.ignore = true;
						// Cya!
						player.kickPlayer(kickMsg);
					}
				}
				return;
			}
		}
	
		
		System.out.println("[MessageChangerLite] Plugin disabled.");
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

        return config.getString(msg).replace("%pName", pName).replace("%msg", defMsg).replaceAll("(&([a-f0-9]))", "\u00A7$2");
    }


}
