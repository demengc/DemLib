package dev.demeng.demlib.api.files;

import dev.demeng.demlib.DemLib;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class CustomConfig {

	@Getter
	public final File configFile;

	@Getter
	public FileConfiguration config;

	public CustomConfig(String configName) throws Exception {

		final Plugin i = DemLib.getPlugin();

		configFile = new File(i.getDataFolder(), configName);
		if (!configFile.exists()) {
			configFile.getParentFile().mkdirs();
			i.saveResource(configName, false);
		}

		config = new YamlConfiguration();
		config.load(configFile);
	}

	public void reloadConfig() {
		config = YamlConfiguration.loadConfiguration(configFile);
	}

	public void saveConfig() throws IOException {
		config.save(configFile);
		reloadConfig();
	}

	public boolean configUpToDate(int currentVersion) {
		return config.getInt("config-version") == currentVersion;
	}
}
