package com.demeng7215.demlib.api;

import com.demeng7215.demlib.DemLib;
import com.demeng7215.demlib.api.messages.MessageUtils;
import lombok.Getter;
import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Fast and efficient update checker.
 */
public class SpigotUpdateChecker {

	private static final Plugin i = DemLib.getPlugin();

	/**
	 * The latest plugin version, the version on Spigot.
	 */
	@Getter
	private static String spigotPluginVersion;

	/**
	 * Checks if the plugin is updated. You can use this to send update notification messages.
	 * Returns true if the plugin is up-to-date, and false if there is an update available.
	 * Will also return true (up-to-date) is there was an error during the update check.
	 * Avoid this by checking if #checkForUpdates(int) returns false.
	 */
	@Getter
	private static boolean isUpdated;

	/**
	 * Checks for updates (compares the local plugin version and the Spigot plugin version).
	 * If there is an update, send a notification message, register a notification event
	 * Use #isUpdated() for the update result.
	 *
	 * @param resourceID The ID of your resource
	 * @see #isUpdated()
	 */
	public static boolean checkForUpdates(int resourceID) {

		MessageUtils.log("Checking for updates...");

		String localPluginVersion = Common.getVersion();

		try {

			// Request the current version of your plugin on SpigotMC.
			final URL spigot = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceID);
			final BufferedReader in = new BufferedReader(new InputStreamReader(spigot.openStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				spigotPluginVersion = inputLine;
			}

		} catch (final IOException ex) {
			isUpdated = true;
		}

		// If there was an error, return false.
		if (isUpdated) {
			// There was an error while checking for updates.
			return false;
		}

		// The update result- true for is up-to-date, false for update available.
		isUpdated = localPluginVersion.equals(spigotPluginVersion);

		// There is a new update available.
		if (!isUpdated()) {
			// Send the update available message.
			MessageUtils.console("&2" + MessageUtils.consoleLine(),
					"&aA newer version of " + Common.getName() + " is available!",
					"&aCurrent version: &f" + Common.getVersion(),
					"&aLatest version: &f" + spigotPluginVersion,
					"&aGet the update: &fhttps://spigotmc.org/resources/" + resourceID,
					"&2" + MessageUtils.consoleLine());
		} else {
			// There is not a new update available. Send the up-to-date message.
			MessageUtils.log("Plugin is up-to-date.");
		}

		// Update check successful!
		return true;
	}
}
