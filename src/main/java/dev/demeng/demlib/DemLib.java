package dev.demeng.demlib;

import dev.demeng.demlib.api.Registerer;
import dev.demeng.demlib.api.commands.CommandErrorMessages;
import dev.demeng.demlib.api.menus.MenuListener;
import dev.demeng.demlib.api.messages.MessageUtils;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class of DemLib, a simple Spigot library.
 *
 * @author Demeng
 *     <p>Make sure to use the setPlugin method before using any of DemLib's methods. Use setPrefix
 *     before using any messaging utils. Use setCommandSettings before creating any commands.
 */
public final class DemLib {

  /** The plugin that DemLib is hooked into. */
  @NonNull @Getter private static JavaPlugin plugin;

  /** The command settings that will be used. */
  @Getter private static CommandErrorMessages commandSettings;

  /**
   * Sets the plugin that will be using DemLib.
   *
   * @param newPlugin The main class of the plugin, extending JavaPlugin
   */
  public static void setPlugin(JavaPlugin newPlugin) {
    plugin = newPlugin;
    Registerer.registerListener(new MenuListener());
  }

  /**
   * Sets the prefix that will be shown in front of messages.
   *
   * @param prefix The prefix with color codes
   */
  public static void setPrefix(String prefix) {
    MessageUtils.setPrefix(prefix);
  }
}
