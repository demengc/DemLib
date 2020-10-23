package dev.demeng.demlib.file;

import dev.demeng.demlib.core.DemLib;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/** .yml configuration file with basic reloading, saving, and version checking functions. */
public class YamlFile {

  /** The File object in case you need it for further customization. */
  @Getter public final File configFile;

  /**
   * The FileConfiguration object, which is what you use for actually modifying and accessing the
   * config.
   */
  @Getter public FileConfiguration config;

  /**
   * Create a new YamlFile.
   *
   * @param configName The name of the file, with .yml or .yaml extension
   * @throws Exception
   */
  public YamlFile(String configName) throws Exception {

    final Plugin i = DemLib.getPlugin();

    configFile = new File(i.getDataFolder(), configName);
    if (!configFile.exists()) {
      configFile.getParentFile().mkdirs();
      i.saveResource(configName, false);
    }

    config = new YamlConfiguration();
    config.load(configFile);
  }

  /** Reload configuration changes into memory. */
  public void reloadConfig() {
    config = YamlConfiguration.loadConfiguration(configFile);
  }

  /**
   * Save the file.
   *
   * @throws IOException
   */
  public void saveConfig() throws IOException {
    config.save(configFile);
    reloadConfig();
  }

  /**
   * Checks if the "config-version" integer equals or is greater than the current version.
   *
   * @param currentVersion The expected value of config-version
   * @return true if the config is update to date, false otherwise
   */
  public boolean configUpToDate(int currentVersion) {
    return config.getInt("config-version") >= currentVersion;
  }
}
