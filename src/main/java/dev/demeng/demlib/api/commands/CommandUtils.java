package dev.demeng.demlib.api.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/** Simple utils for commands. */
public final class CommandUtils {

  public static boolean isPlayer(CommandSender sender) {
    return sender instanceof Player;
  }

  public static boolean hasSufficientArgs(int expectedArgs, String[] args) {
    return args.length >= expectedArgs;
  }

  public static boolean hasExactArgs(int expectedArgs, String[] args) {
    return args.length == expectedArgs;
  }

  public static boolean hasPermission(CommandSender sender, String permission) {
    return sender.hasPermission(permission);
  }
}
