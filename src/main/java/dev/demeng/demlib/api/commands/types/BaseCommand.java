package dev.demeng.demlib.api.commands.types;

import dev.demeng.demlib.api.commands.CommandSettings;
import org.bukkit.command.CommandSender;

import java.util.List;

public interface BaseCommand {

  CommandSettings getSettings();

  String getName();

  List<String> getAliases();

  boolean isPlayerCommand();

  String getPermission();

  String getUsage();

  int getArgs();

  void execute(CommandSender sender, String[] args);
}
