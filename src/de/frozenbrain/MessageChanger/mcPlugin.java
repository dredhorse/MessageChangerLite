package de.frozenbrain.MessageChanger;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import com.nijiko.permissions.PermissionHandler;
import com.nijiko.permissions.User;
import com.nijikokun.bukkit.Permissions.Permissions;

public class mcPlugin extends JavaPlugin {
	
	private final mcPlayerListener playerListener = new mcPlayerListener(this);
	public PermissionHandler Permissions;
	Configuration config;
	public boolean ignore = false;
	
	public void onEnable() {
		PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println("[MessageChanger] v" + pdfFile.getVersion() + " enabled.");
        
		if(setupPermissions() == true) {
			PluginManager pm = getServer().getPluginManager();
			pm.registerEvent(Event.Type.PLAYER_KICK, playerListener, Priority.Normal, this);
			pm.registerEvent(Event.Type.PLAYER_LOGIN, playerListener, Priority.Normal, this);
			pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
			pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Priority.Normal, this);
		}
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
	
		
		System.out.println("[MessageChanger] Plugin disabled.");
	}
	
	private boolean setupPermissions() {
	    if (Permissions != null) {
	        return true;
	    }
	    
	    Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");
	    
	    if (permissionsPlugin == null) {
	        getServer().getLogger().warning("[MessageChanger] Permission system not detected, disabling plugin.");
	        return false;
	    }
	    
	    Permissions = ((Permissions) permissionsPlugin).getHandler();
	    getServer().getLogger().info("[MessageChanger] Found "+((Permissions)permissionsPlugin).getDescription().getFullName());
	    
	    return true;
	}
	
	// Thanks Permissions 3 for making things so much easier!
	public String getMessage(String msg, Player player, String defMsg) {
		
		// Validate the arguments
		if(msg == null) return null;
		if(defMsg == null) defMsg = "";
		
		// Define some Variables
		String pName = player.getName();
		String world = player.getWorld().getName();
		
		// Get the user from permissions
		User user = Permissions.getUserObject(world, pName);
		
		// Get the raw message
		String str = user.getString("messageChanger." + msg);
		if(str == null) return defMsg;
		
		// Return the parsed message
		return str.replace("%pName", pName).replace("%msg", defMsg).replaceAll("(&([a-f0-9]))", "\u00A7$2");
		
	}
	
}
