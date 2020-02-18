package com.demeng7215.demlib.api.connections;

import com.demeng7215.demlib.api.Common;
import com.demeng7215.demlib.api.messages.MessageUtils;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class SpigotUpdateChecker {

	@Getter
	private static String spigotPluginVersion;

	@Getter
	private static boolean isUpdated;

	public static boolean checkForUpdates(int resourceId) {

		String localPluginVersion = Common.getVersion();

		try {

			final URL spigot = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceId);
			final BufferedReader in = new BufferedReader(new InputStreamReader(spigot.openStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null) spigotPluginVersion = inputLine;

		} catch (IOException ex) {
			isUpdated = true;
		}

		if (isUpdated) return false;

		isUpdated = localPluginVersion.equals(spigotPluginVersion);

		if (!isUpdated()) {
			MessageUtils.consoleWithoutPrefix("&2" + MessageUtils.CONSOLE_LINE,
					"&aA newer version of " + Common.getName() + " is available!",
					"&aCurrent version: &r" + Common.getVersion(),
					"&aLatest version: &r" + spigotPluginVersion,
					"&aGet the update: &rhttps://spigotmc.org/resources/" + resourceId,
					"&2" + MessageUtils.CONSOLE_LINE);
		}

		return true;
	}
}
