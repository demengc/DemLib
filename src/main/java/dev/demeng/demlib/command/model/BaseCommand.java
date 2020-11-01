package dev.demeng.demlib.command.model;

import org.bukkit.command.CommandSender;

public interface BaseCommand {

  /**
   * Name of the command- MUST be lowercase.
   *
   * @return
   */
  String getName();

  /**
   * A brief description.
   *
   * @return
   */
  String getDescription();

  /**
   * Array of aliases, must be all lowercase.
   *
   * @return
   */
  String[] getAliases();

  /**
   * The usage of the command, without the slash and the command name. Can be null.
   *
   * @return
   */
  String getUsage();

  /**
   * if true, consoles will not be able to use this command.
   *
   * @return
   */
  boolean isPlayersOnly();

  /**
   * The permission required to execute this command. Can be null.
   *
   * @return
   */
  String getPermission();

  /**
   * The minimum number of arguments that must be provided.
   *
   * @return
   */
  int getMinArgs();

  /**
   * Code to execute after all checks have passed.
   *
   * @param sender
   * @param label
   * @param args
   * @return
   */
  String execute(CommandSender sender, String label, String[] args);
}
