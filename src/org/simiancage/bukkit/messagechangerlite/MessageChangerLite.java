package org.simiancage.bukkit.messagechangerlite;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class MessageChangerLite extends JavaPlugin {

    private final PlayerListenerMCL playerListener = new PlayerListenerMCL(this);
    Config config;
    Log log;
    public boolean ignore = false;
    MessageChangerLite plugin = this;
    private FileConfiguration configuration;

    public void onEnable() {
        log = Log.getInstance(this);
        config = Config.getInstance();
        config.setupConfig(configuration, plugin);
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_KICK, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_LOGIN, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Priority.Normal, this);
        pm.registerEvent(Type.PLAYER_CHANGED_WORLD, playerListener, Priority.Normal, this);

        log.enableMsg();
    }

    public void onDisable() {
        // There's no easy way :/
        StackTraceElement[] st = new Throwable().getStackTrace();
        for (int i = 0; i < st.length; i++) {
            // Go through the stack trace and look for the stop method
            if (st[i].getMethodName().equals("stop")) {
                // Yay, stop method found
                Player[] players = getServer().getOnlinePlayers();
                String kickMsg = "";
                for (Player player : players) {
                    // Let's kick 'em!
                    kickMsg = getMessage("SERVER_STOP", player, "");
                    if (!kickMsg.equals("")) {
                        // We don't want to override the SERVER_STOP message
                        this.ignore = true;
                        // Cya!
                        player.kickPlayer(kickMsg);
                    }
                }
                return;
            }
        }


        log.disableMsg();
    }


    public String getMessage(String msg, Player player, String defMsg) {
        if (msg == null) {
            return null;
        }
        if (defMsg == null) {
            defMsg = "";
        }

        String pName = player.getDisplayName();
        String world = player.getWorld().getName();
        String perm = config.getCategory(player);
        log.debug("perm", perm);
        log.debug("msg", msg);
        String message = config.getMessages(perm, msg);
        log.debug("message", message);
        return message.replace("%pName", pName).replace("%msg", defMsg).replace("%world", world).replaceAll("(&([a-f0-9]))", "\u00A7$2");
    }


}
