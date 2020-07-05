package dev.demeng.demlib.api.commands;

import dev.demeng.demlib.DemLib;
import dev.demeng.demlib.api.Common;
import dev.demeng.demlib.api.commands.types.BaseCommand;
import dev.demeng.demlib.api.commands.types.SubCommand;
import dev.demeng.demlib.api.messages.MessageUtils;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Manages registered base commands and sub commands. */
public final class CommandManager implements CommandExecutor {

  @Getter private static final List<BaseCommand> baseCommands = new ArrayList<>();

  @Getter private static final Map<BaseCommand, List<SubCommand>> subCommands = new HashMap<>();

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

    if (!DemLib.getPlugin().isEnabled()) {
      sender.sendMessage(
          ChatColor.RED
              + "This plugin is currently disabled. Perhaps there was an error at startup?");
      return true;
    }

    final CommandErrorMessages cs = DemLib.getCommandSettings();

    BaseCommand base = null;

    for (BaseCommand cmd : baseCommands) {
      if (cmd.getName().equalsIgnoreCase(label)
          || Common.checkArrayContains(label.toLowerCase(), cmd.getAliases())) {
        base = cmd;
        break;
      }
    }

    if (base == null) return true;

    if (args.length > 0) {

      SubCommand sub = null;

      for (SubCommand cmd : subCommands.get(base)) {
        if (cmd.getName().equalsIgnoreCase(args[0])
            || Common.checkArrayContains(args[0].toLowerCase(), cmd.getAliases())) {
          sub = cmd;
          break;
        }
      }

      if (sub != null) {

        if (sub.isPlayerCommand() && !(sender instanceof Player)) {
          MessageUtils.tell(sender, cs.getNotPlayer());
          return true;
        }

        if (sub.getPermission() != null && !sender.hasPermission(sub.getPermission())) {
          MessageUtils.tell(
              sender, cs.getInsufficientPermission().replace("%permission%", sub.getPermission()));
          return true;
        }

        if (args.length < sub.getArgs()) {
          MessageUtils.tell(sender, cs.getIncorrectUsage().replace("%usage%", sub.getUsage()));
          return true;
        }

        sub.execute(sender, args);
        return true;
      }
    }

    if (base.isPlayerCommand() && !(sender instanceof Player)) {
      MessageUtils.tell(sender, cs.getNotPlayer());
      return true;
    }

    if (base.getPermission() != null && !sender.hasPermission(base.getPermission())) {
      MessageUtils.tell(
          sender, cs.getInsufficientPermission().replace("%permission%", base.getPermission()));
      return true;
    }

    if (args.length < base.getArgs()) {
      MessageUtils.tell(sender, cs.getIncorrectUsage().replace("%usage%", base.getUsage()));
      return true;
    }

    base.execute(sender, args);
    return true;
  }
}
