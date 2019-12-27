package com.demeng7215.demlib.api.files;

import com.demeng7215.demlib.DemLib;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * Create multiple YAML configuration files for your plugin with ease!
 * Also contains methods to reload and save your config.
 */
public class CustomConfig {

    /**
     * The configuration FILE.
     * This can be used if you have your own configuration util you would like to use with this.
     */
    @Getter
    public File configFile;

    /**
     * The actual configuration itself.
     * This can be used if you have your own configuration util you would like to use with this.
     */
    @Getter
    public FileConfiguration config;

    /**
     * Creates a configuration file for your plugin.
     *
     * @param configName The name of the config. Must have the file extension ".yml"
     */
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

    /**
     * Reloads the configuration file.
     */
    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    /**
     * Saves the configuration file.
     */
    public void saveConfig() throws IOException {
        config.save(configFile);
        reloadConfig();
    }

    /**
     * Check if the configuration file is up to date.
     *
     * @param currentVersion The expected config version.
     * @return true if the config is updated, false otherwise
     */
    public boolean configUpToDate(int currentVersion) {
        return config.getInt("config-version") == currentVersion;
    }
}
