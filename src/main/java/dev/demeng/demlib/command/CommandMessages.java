package dev.demeng.demlib.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.file.FileConfiguration;

/** Class containing several messages that may be sent after command execution. */
@RequiredArgsConstructor
public class CommandMessages {

  /** If the console executes a player-only command. */
  @Getter private final String notPlayer;

  /**
   * If the command sender does not have permission to execute the command. For permission required,
   * use %permission%.
   */
  @Getter private final String noPermission;

  /**
   * If the command sender did not provide the minimum amount of arguments. For valid usage, use
   * %usage%.
   */
  @Getter private final String invalidArgs;

  /**
   * Get the messages from config.
   *
   * @param config The configuration file containing the messages.
   */
  public CommandMessages(FileConfiguration config) {
    this.notPlayer =
        config.getString("not-player", "&cYou must be in-game to execute that command.");
    this.noPermission =
        config.getString("no-permission", "&cYou do not have permission to execute that command.");
    this.invalidArgs =
        config.getString("invalid-args", "&cIncorrect usage! Did you mean: &f%usage%");
  }
}
