package dev.demeng.demlib.command;

import dev.demeng.demlib.command.model.BaseCommand;
import dev.demeng.demlib.command.model.SubCommand;
import dev.demeng.demlib.core.DemLib;
import dev.demeng.demlib.message.MessageUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CommandHandler extends BukkitCommand {

  private final BaseCommand base;

  public CommandHandler(BaseCommand base) {
    super(
        base.getName(),
        base.getDescription(),
        "/" + base.getName() + (base.getUsage() != null ? " " + base.getUsage() : ""),
        Arrays.asList(base.getAliases()));
    this.base = base;
  }

  @Override
  public boolean execute(CommandSender sender, String label, String[] args) {

    if (args.length < 1) {

      if (!checkBase(sender, label, args)) {
        return true;
      }

      final String response = base.execute(sender, label, args);
      if (response != null) {
        MessageUtils.tell(sender, response);
      }

      return true;
    }

    final Map<String, SubCommand> subs = CommandManager.getSubCommands(base.getName());

    for (Map.Entry<String, SubCommand> entry : subs.entrySet()) {

      if (args[0].equalsIgnoreCase(entry.getKey())
          || Arrays.asList(entry.getValue().getAliases()).contains(args[0].toLowerCase())) {

        final List<String> subArgs = new ArrayList<>(Arrays.asList(args));
        subArgs.remove(0);

        final String[] subArgsArr = subArgs.toArray(new String[0]);

        if (!checkSub(entry.getValue(), sender, label, args[0], subArgsArr)) {
          return true;
        }

        final String response = entry.getValue().execute(sender, label, subArgsArr);
        if (response != null) {
          MessageUtils.tell(sender, response);
        }

        return true;
      }
    }

    if (!checkBase(sender, label, args)) {
      return true;
    }

    final String response = base.execute(sender, label, args);
    if (response != null) {
      MessageUtils.tell(sender, response);
    }

    return true;
  }

  private boolean checkBase(CommandSender sender, String label, String[] args) {

    if (!(sender instanceof Player) && base.isPlayersOnly()) {
      MessageUtils.tell(sender, DemLib.getCommandMessages().getNotPlayer());
      return false;
    }

    if (base.getPermission() != null && !sender.hasPermission(base.getPermission())) {
      MessageUtils.tell(
          sender,
          DemLib.getCommandMessages()
              .getNoPermission()
              .replace("%permission%", base.getPermission()));
      return false;
    }

    if (args.length < base.getMinArgs()) {
      MessageUtils.tell(
          sender,
          DemLib.getCommandMessages()
              .getInvalidArgs()
              .replace(
                  "%usage%", "/" + label + (base.getUsage() != null ? " " + base.getUsage() : "")));
      return false;
    }

    return true;
  }

  private boolean checkSub(
      SubCommand sub, CommandSender sender, String baseLabel, String subLabel, String[] args) {

    if (!(sender instanceof Player) && sub.isPlayersOnly()) {
      MessageUtils.tell(sender, DemLib.getCommandMessages().getNotPlayer());
      return false;
    }

    if (sub.getPermission() != null && !sender.hasPermission(sub.getPermission())) {
      MessageUtils.tell(
          sender,
          DemLib.getCommandMessages()
              .getNoPermission()
              .replace("%permission%", sub.getPermission()));
      return false;
    }

    if (args.length < sub.getMinArgs()) {
      MessageUtils.tell(
          sender,
          DemLib.getCommandMessages()
              .getInvalidArgs()
              .replace(
                  "%usage%",
                  "/"
                      + baseLabel
                      + " "
                      + subLabel
                      + (sub.getUsage() != null ? " " + sub.getUsage() : "")));
      return false;
    }

    return true;
  }
}
