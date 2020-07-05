package dev.demeng.demlib.api.commands.types;

import org.bukkit.command.CommandSender;

/** The interface for base (root/main) commands. */
public interface BaseCommand {

  /**
   * The name and main identifier of the command.
   *
   * @return
   */
  String getName();

  /**
   * An array of alternative names for this command.
   *
   * @return
   */
  String[] getAliases();

  /**
   * If this player can only be executed by players, or if consoles are permitted as well.
   *
   * @return
   */
  boolean isPlayerCommand();

  /**
   * The permission node required for this command.
   *
   * @return
   */
  String getPermission();

  /**
   * The usage of this command, without the label itself (only arguments).
   *
   * @return
   */
  String getUsage();

  /**
   * The minimum number of arguments required.
   *
   * @return
   */
  int getArgs();

  /**
   * Code run on command execution.
   *
   * @param sender The sender of the command
   * @param args The arguments provided
   */
  void execute(CommandSender sender, String[] args);
}
