package org.simiancage.bukkit.messagechangerlite; /**
 *
 * PluginName: DeathTpPlus
 * Class: Config
 * User: DonRedhorse
 * Date: 05.12.11
 * Time: 20:45
 *
 */

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;


/**
 * The Config Class allows you to write a custom config file for craftbukkit plugins incl. comments.
 * It allows autoupdating config changes, checking for plugin updates and writing back the configuration.
 * Please note that writing to the config file will overwrite any manual changes.<p>
 * You NEED to fix all ToDos, otherwise the class will NOT work!<p>
 *
 * @author Don Redhorse
 */
@SuppressWarnings({"UnusedDeclaration"})
public class Config {

	private enum EVENTS {
		SERVER_STOP, KICK_FULL, PLAYER_QUIT, KICK_WHITELIST, KICK_KICK_LEAVEMSG, KICK_KICK_REASON, PLAYER_JOIN, CHANGED_WORLD, KICK_BANNED
	}

	/**
	 * Instance of the Configuration Class
	 */
	private static Config instance = null;

// Nothing to change from here to ==>>>
	/**
	 * Object to handle the configuration
	 *
	 * @see org.bukkit.configuration.file.FileConfiguration
	 */
	private FileConfiguration config;
	/**
	 * Object to handle the plugin
	 */
	private Plugin plugin;
	/**
	 * Configuration File Name
	 */
	private static String configFile = "config.yml";
	/**
	 * Is the configuration available or did we have problems?
	 */
	private boolean configAvailable = false;
// Default plugin configuration
	/**
	 * Enables logging to server console. Warning and Severe will still be logged.
	 */
	private boolean errorLogEnabled = true;
	/**
	 * Enable more logging.. could be messy!
	 */
	private boolean debugLogEnabled = false;
	/**
	 * Check if there is a new version of the plugin out.
	 */
	private boolean checkForUpdate = true;
	/**
	 * AutoUpdate the config file if necessary. This will overwrite any changes outside the configuration parameters!
	 */
	private boolean autoUpdateConfig = false;
	/**
	 * Enable saving of the config file
	 */
	private boolean saveConfig = false;
	/**
	 * Contains the plugin name
	 */
	private String pluginName;
	/**
	 * Contains the plugin version
	 */
	private String pluginVersion;
	/**
	 * Do we require a config update?
	 */
	private boolean configRequiresUpdate = false;

// <<<<=== here..


// Stuff with minor changes

	/**
	 * Link to the location of the plugin website
	 */
	@SuppressWarnings({"FieldCanBeLocal"})
	private final String pluginSlug = "http://dev.bukkit.org/server-mods/messagechangerlite/";
	/**
	 * Link to the location of the recent version number, the file should be a text with just the number
	 */
	@SuppressWarnings({"FieldCanBeLocal"})
	private final String versionURL = "https://raw.github.com/dredhorse/MessageChangerLite/master/resources/messagechangerlite.ver";
	// The org.simiancage.bukkit.messagechangerlite.LoggerClass should be renamed to the name of the class you did change the original org.simiancage.bukkit.messagechangerlite.LoggerClass too.
	/**
	 * Reference of the org.simiancage.bukkit.messagechangerlite.LoggerClass, needs to be renamed to correct name.
	 *
	 * @see Log
	 */
	private static Log log;

	// ToDo Change the configCurrent if the config changes!
	/**
	 * This is the internal config version
	 */
	private final String configCurrent = "2.2";
	/**
	 * This is the DEFAULT for the config file version, should be the same as configCurrent. Will afterwards be changed
	 */
	private String configVer = configCurrent;


// and now the real stuff


// ********************************************************************************************************************

// Default Config Variables start here!

	private HashMap<String, String> messagesPerCategory;
	private HashMap<String, HashMap<String, String>> messages = new HashMap<String, HashMap<String, String>>();
	private HashMap<String, String> defaultMessages = new HashMap<String, String>();
	private HashMap<String, String> permnode1Messages = new HashMap<String, String>();
	private HashMap<String, String> permnode2Messages = new HashMap<String, String>();
	private List<String> categoryOrder = Arrays.asList("permnode1", "permnode2");

// *******************************************************************************************************************


/*  Here comes the custom config, the default config is later on in the class
Keep in mind that you need to create your config file in a way which is
afterwards parsable again from the configuration class of bukkit
*/

// First we have the default part..
// Which is devided in setting up some variables first

	/**
	 * Method to setup the config variables with default values
	 */

	private void setupCustomDefaultVariables() {

		defaultMessages.put("SERVER_STOP", "Testing the server...");
		defaultMessages.put("KICK_FULL", "%msg");
		defaultMessages.put("PLAYER_QUIT", "%msg");
		defaultMessages.put("KICK_WHITELIST", "%msg");
		defaultMessages.put("KICK_KICK_LEAVEMSG", "%msg");
		defaultMessages.put("KICK_KICK_REASON", "%msg");
		defaultMessages.put("PLAYER_JOIN", "Hello &b%pName&f  in world %world");
		defaultMessages.put("CHANGED_WORLD", "Welcome traveler from %fromWorld in %world");
		defaultMessages.put("KICK_BANNED", "%msg");
		log.debug("defaultMessages", defaultMessages);

		permnode1Messages.put("SERVER_STOP", "Oh well...");
		log.debug("permnode1Messages", permnode1Messages);

		permnode2Messages.put("PLAYER_JOIN", "Welcome the admin");
		log.debug("permnode2Messages", permnode2Messages);

		messages.put("default", defaultMessages);
		messages.put("permnode1", permnode1Messages);
		messages.put("permnode2", permnode2Messages);
	}

// And than we add the defaults

	/**
	 * Method to add the config variables to the default configuration
	 */

	private void customDefaultConfig() {

	}


// Than we load it....

	/**
	 * Method to load the configuration into the config variables
	 */

	private void loadCustomConfig() {

		List<String> messageCategories = new ArrayList<String>();
		for (String key : config.getConfigurationSection("message-category").getKeys(false)) {
			messageCategories.add(key);
		}
		log.debug("messageCategories", messageCategories);
		Iterator<String> it = messageCategories.iterator();
		String next = null;
		while (it.hasNext()) {
			messagesPerCategory = new HashMap<String, String>();
			next = it.next();
			log.debug("next", next);
			Set<String> loadMessages;
			loadMessages = config.getConfigurationSection("message-category." + next).getKeys(true);
			log.debug("loadMessages", loadMessages);
			Iterator<String> itm = loadMessages.iterator();
			String event = null;
			while (itm.hasNext()) {
				event = itm.next();
				log.debug("event", event);
				String eventMessage = config.getString("message-category." + next + "." + event);
				log.debug("eventMessage", eventMessage);
				messagesPerCategory.put(event, eventMessage);
				log.debug("messagesPerCategory", messagesPerCategory);
			}
			messages.put(next, messagesPerCategory);
			log.debug("messages", messages);

		}
		log.debug("messages", messages);
		if (config.contains("categoryOrder")) {
			categoryOrder = config.getStringList("categoryOrder");
		} else {
			categoryOrder = new ArrayList<String>(messages.keySet());
		}
		log.info("Order of Message Categories is: " + categoryOrder);
	}


// And than we write it....


	/**
	 * Method to write the custom config variables into the config file
	 *
	 * @param stream will be handed over by  writeConfig
	 */

	private void writeCustomConfig(PrintWriter stream) {

		stream.println("#-------- Plugin Configuration");
		stream.println();
		stream.println("# You can use the following variables:");
		stream.println("# %pName = DisplayName of the Player");
		stream.println("# %world = World the player is in");
		stream.println("# %fromWorld = World the player was in (Only CHANGED_WORLD Message)");
		stream.println("# %msg = default message");
		stream.println();
		stream.println("# Order in which the categories are parsed");
		stream.println("categoryOrder: " + categoryOrder);
		stream.println();
		stream.println("message-category:");
		Iterator<String> categories = messages.keySet().iterator();
		String category;
		while (categories.hasNext()) {
			category = categories.next();
			stream.println("    " + category + ":");
			HashMap<String, String> messagesPerCategory = messages.get(category);
			if (category.equalsIgnoreCase("default")) {
				for (EVENTS event : EVENTS.values()) {
					stream.println("        " + event.toString() + ": \"" + messagesPerCategory.get(event.toString()) + "\"");
				}
			} else {

				Iterator<String> events = messagesPerCategory.keySet().iterator();
				String event;
				while (events.hasNext()) {
					event = events.next();
					stream.println("        " + event + ": \"" + messagesPerCategory.get(event) + "\"");
				}
			}
		}
	}


// *******************************************************************************************************

// And now you need to create the getters and setters if needed for your config variables


// The plugin specific getters start here!


	public String getMessages(String perm, String event) {
		HashMap<String, String> categorieMessages = messages.get(perm);
		log.debug("categoryMessages", categorieMessages);
		String msg = categorieMessages.get(event);
		if (msg == null) {
			log.debug("no message for " + event + " found");
			msg = messages.get("default").get(event);
		}
		log.debug("msg", msg);
		return msg;
	}

	public String getCategory(Player player) {
		for (String category : categoryOrder) {
			if (player.hasPermission("messagechange.message." + category) || player.hasPermission("messagechanger.message." + category)) {
				return category;
			}
		}
		return "default";
	}


// Last change coming up... choosing the right ClassName for the Logger..

	/**
	 * Method to get the Instance of the Class, if the class hasn't been initialized yet it will.
	 *
	 * @return instance of class
	 */

	public static Config getInstance() {
		if (instance == null) {
			instance = new Config();
		}
		log = Log.getLogger();
		return instance;
	}

	/**
	 * Method to get the Instance of the Class and pass over a different name for the config file, if the class
	 * hasn't been initialized yet it will.
	 *
	 * @param configuratonFile name of the config file
	 *
	 * @return instance of class
	 */
	public static Config getInstance(String configuratonFile) {
		if (instance == null) {
			instance = new Config();
		}


		log = Log.getLogger();
		configFile = configuratonFile;
		return instance;
	}


// Well that's it.... at least in this class... thanks for reading...


// NOTHING TO CHANGE NORMALLY BELOW!!!

// *******************************************************************************************************************
// Other Methods no change normally necessary


// The class stuff first


	private Config() {

	}


// than the getters

	/**
	 * Method to return the PluginName
	 *
	 * @return PluginName
	 */

	public String pluginName() {
		return pluginName;
	}

	/**
	 * Method to return the PluginVersion
	 *
	 * @return PluginVersion
	 */
	public String pluginVersion() {
		return pluginVersion;
	}

	/**
	 * Method to return the Config File Version
	 *
	 * @return configVer  Config File Version
	 */
	public String configVer() {
		return configVer;
	}

	/**
	 * Method to return if Error Logging is enabled
	 *
	 * @return errorLogEnabled
	 */

	public boolean isErrorLogEnabled() {
		return errorLogEnabled;
	}

	/**
	 * Method to return if Debug Loggin is enabled
	 *
	 * @return debugLogEnabled
	 */
	public boolean isDebugLogEnabled() {
		return debugLogEnabled;
	}

	/**
	 * Method to return if we are checking for updates
	 *
	 * @return checkForUpdate
	 */
	public boolean isCheckForUpdate() {
		return checkForUpdate;
	}

	/**
	 * Method to return if we are AutoUpdating the Config File
	 *
	 * @return autoUpdateConfig
	 */
	public boolean isAutoUpdateConfig() {
		return autoUpdateConfig;
	}

	/**
	 * Method to return if we are saving the config automatically
	 *
	 * @return saveConfig
	 */
	public boolean isSaveConfig() {
		return saveConfig;
	}

	/**
	 * Method to return if we need to update the config
	 *
	 * @return configRequiresUpdate
	 */
	public boolean isConfigRequiresUpdate() {
		return configRequiresUpdate;
	}

// And the rest

// Setting up the config

	/**
	 * Method to setup the configuration.
	 * If the configuration file doesn't exist it will be created by {@link #defaultConfig()}
	 * After that the configuration is loaded {@link #loadConfig()}
	 * We than check if an configuration update is necessary {@link #updateNecessary()}
	 * and if {@link #autoUpdateConfig} is true we update the configuration {@link #updateConfig()}
	 * If {@link #checkForUpdate} is true we check if there is a new version of the plugin {@link #versionCheck()}
	 * and set {@link #configAvailable} to true
	 *
	 * @param config references the config file
	 * @param plugin references the plugin for this configuration
	 *
	 * @see #defaultConfig()
	 * @see #loadConfig()
	 * @see #updateNecessary()
	 * @see #updateConfig()
	 * @see #versionCheck()
	 */

	public void setupConfig(FileConfiguration config, Plugin plugin) {

		this.config = config;
		this.plugin = plugin;
// Checking if config file exists, if not create it
		if (!(new File(plugin.getDataFolder(), configFile)).exists()) {
			log.info("Creating default configuration file");
			defaultConfig();
		}
// Loading the config from file
		loadConfig();

// Checking internal configCurrent and config file configVer

		updateNecessary();
// If config file has new options update it if enabled
		if (autoUpdateConfig) {
			updateConfig();
		}
// Also check for New Version of the plugin
		if (checkForUpdate) {
			versionCheck();
		}
		configAvailable = true;
	}


// Creating the defaults

// Configuring the Default options..

	/**
	 * Method to write and create the default configuration.
	 * The custom configuration variables are added via #setupCustomDefaultVariables()
	 * Than we write the configuration to disk  #writeConfig()
	 * Than we get the config object from disk
	 * We are adding the default configuration for the variables and load the
	 * defaults for the custom variables  #customDefaultConfig()
	 *
	 * @see #setupCustomDefaultVariables()
	 * @see #customDefaultConfig()
	 */

	private void defaultConfig() {
		setupCustomDefaultVariables();
		if (!writeConfig()) {
			log.info("Using internal Defaults!");
		}
		config = plugin.getConfig();
		config.addDefault("configVer", configVer);
		config.addDefault("errorLogEnabled", errorLogEnabled);
		config.addDefault("DebugLogEnabled", debugLogEnabled);
		config.addDefault("checkForUpdate", checkForUpdate);
		config.addDefault("autoUpdateConfig", autoUpdateConfig);
		config.addDefault("saveConfig", saveConfig);
		customDefaultConfig();
	}


// Loading the configuration

	/**
	 * Method for loading the configuration from disk.
	 * First we get the config object from disk, than we
	 * read in the standard configuration part.
	 * We also log a message if #debugLogEnabled
	 * and we produce some debug logs.
	 * After that we load the custom configuration part #loadCustomConfig()
	 *
	 * @see #loadCustomConfig()
	 */

	private void loadConfig() {
		config = plugin.getConfig();
		// Starting to update the standard configuration
		configVer = config.getString("configVer");
		errorLogEnabled = config.getBoolean("errorLogEnabled");
		debugLogEnabled = config.getBoolean("DebugLogEnabled");
		checkForUpdate = config.getBoolean("checkForUpdate");
		autoUpdateConfig = config.getBoolean("autoUpdateConfig");
		saveConfig = config.getBoolean("saveConfig");
		// Debug OutPut NOW!
		if (debugLogEnabled) {
			log.info("Debug Logging is enabled!");
		}
		log.debug("configCurrent", configCurrent);
		log.debug("configVer", configVer);
		log.debug("errorLogEnabled", errorLogEnabled);
		log.debug("checkForUpdate", checkForUpdate);
		log.debug("autoUpdateConfig", autoUpdateConfig);
		log.debug("saveConfig", saveConfig);

		loadCustomConfig();

		log.info("Configuration v." + configVer + " loaded.");
	}


//  Writing the config file

	/**
	 * Method for writing the configuration file.
	 * First we write the standard configuration part, than we
	 * write the custom configuration part via #writeCustomConfig()
	 *
	 * @return true if writing the config was successful
	 *
	 * @see #writeCustomConfig(java.io.PrintWriter)
	 */

	private boolean writeConfig() {
		boolean success = false;
		try {
			PrintWriter stream;
			File folder = plugin.getDataFolder();
			if (folder != null) {
				folder.mkdirs();
			}
			String pluginPath = plugin.getDataFolder() + System.getProperty("file.separator");
			PluginDescriptionFile pdfFile = this.plugin.getDescription();
			pluginName = pdfFile.getName();
			pluginVersion = pdfFile.getVersion();
			stream = new PrintWriter(pluginPath + configFile);
//Let's write our config ;)
			stream.println("# " + pluginName + " " + pluginVersion + " by " + pdfFile.getAuthors().toString());
			stream.println("#");
			stream.println("# Configuration File for " + pluginName + ".");
			stream.println("#");
			stream.println("# For detailed assistance please visit: " + pluginSlug);
			stream.println();
			stream.println("#------- Default Configuration");
			stream.println();
			stream.println("# Configuration Version");
			stream.println("configVer: '" + configVer + "'");
			stream.println();
			stream.println("# Error Log Enabled");
			stream.println("# Enable logging to server console");
			stream.println("# Warning and Severe will still be logged.");
			stream.println("errorLogEnabled: " + errorLogEnabled);
			stream.println();
			stream.println("# Debug Log Enabled");
			stream.println("# Enable more logging.. could be messy!");
			stream.println("DebugLogEnabled: " + debugLogEnabled);
			stream.println();
			stream.println("# Check for Update");
			stream.println("# Will check if there is a new version of the plugin out.");
			stream.println("checkForUpdate: " + checkForUpdate);
			stream.println();
			stream.println("# Auto Update Config");
			stream.println("# This will overwrite any changes outside the configuration parameters!");
			stream.println("autoUpdateConfig: " + autoUpdateConfig);
			stream.println();
			stream.println("# Save Config");
			stream.println("# This will overwrite any changes outside the configuration parameters!");
			stream.println("# Only needed if you use ingame commands to change the configuration.");
			stream.println("saveConfig: " + saveConfig);
			stream.println();

// Getting the custom config information from the top of the class
			writeCustomConfig(stream);

			stream.println();

			stream.close();

			success = true;

		} catch (FileNotFoundException e) {
			log.warning("Error saving the " + configFile + ".");
		}
		log.debug("DefaultConfig written", success);
		return success;
	}


// Checking if the configVersions differ

	/**
	 * Method to check if the configuration version are different.
	 * will set #configRequiresUpdate to true if versions are different
	 */
	private void updateNecessary() {
		if (configVer.equalsIgnoreCase(configCurrent)) {
			log.info("Config is up to date");
		} else {
			log.warning("Config is not up to date!");
			log.warning("Config File Version: " + configVer);
			log.warning("Internal Config Version: " + configCurrent);
			log.warning("It is suggested to update the config.yml!");
			configRequiresUpdate = true;
		}
	}

// Checking the Current Version via the Web

	/**
	 * Method to check if there is a newer version of the plugin available.
	 */
	private void versionCheck() {
		String thisVersion = plugin.getDescription().getVersion();
		URL url;
		try {
			url = new URL(versionURL);
			BufferedReader in;
			in = new BufferedReader(new InputStreamReader(url.openStream()));
			String newVersion = "";
			String line;
			while ((line = in.readLine()) != null) {
				newVersion += line;
			}
			in.close();
			if (newVersion.equals(thisVersion)) {
				log.info("is up to date at version "
						+ thisVersion + ".");

			} else {
				log.warning("is out of date!");
				log.warning("This version: " + thisVersion + "; latest version: " + newVersion + ".");
			}
		} catch (MalformedURLException ex) {
			log.warning("Error accessing update URL.", ex);
		} catch (IOException ex) {
			log.warning("Error checking for update.", ex);
		}
	}

// Updating the config

	/**
	 * Method to update the configuration if it is necessary.
	 */
	private void updateConfig() {
		if (configRequiresUpdate) {
			configVer = configCurrent;
			if (writeConfig()) {
				log.info("Configuration was updated with new default values.");
				log.info("Please change them to your liking.");
			} else {
				log.warning("Configuration file could not be auto updated.");
				log.warning("Please rename " + configFile + " and try again.");
			}
		}
	}

// Reloading the config

	/**
	 * Method to reload the configuration.
	 *
	 * @return msg with the status of the reload
	 */

	public String reloadConfig() {
		String msg;
		if (configAvailable) {
			loadConfig();
			log.info("Config reloaded");
			msg = "Config was reloaded";
		} else {
			log.severe("Reloading Config before it exists.");
			log.severe("Flog the developer!");
			msg = "Something terrible terrible did go really really wrong, see console log!";
		}
		return msg;
	}
// Saving the config


	/**
	 * Method to save the config to file.
	 *
	 * @return true if the save was successful
	 */
	public boolean saveConfig() {
		boolean saved = false;
		if (saveConfig) {
			saved = writeConfig();
		}
		return saved;
	}

}
