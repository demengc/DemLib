package dev.demeng.demlib.command;

import dev.demeng.demlib.Common;
import dev.demeng.demlib.command.model.BaseCommand;
import dev.demeng.demlib.command.model.SubCommand;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class CommandManager {

  @Getter private static Map<String, BaseCommand> baseCommands = new HashMap<>();
  @Getter private static Map<String, Map<String, SubCommand>> subCommands = new HashMap<>();

  public static void register(BaseCommand command) {
    baseCommands.put(command.getName(), command);
    subCommands.put(command.getName(), new HashMap<>());

    final Field commandMapField;

    try {
      commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");

    } catch (NoSuchFieldException ex) {
      ex.printStackTrace();
      return;
    }

    commandMapField.setAccessible(true);

    final CommandMap commandMap;

    try {
      commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());

    } catch (IllegalAccessException ex) {
      ex.printStackTrace();
      return;
    }

    commandMap.register(Common.getName().toLowerCase(), new CommandHandler(command));
  }

  public static void register(SubCommand command) {

    final Map<String, SubCommand> current = subCommands.get(command.getBaseName());
    final Map<String, SubCommand> updated;

    if (current == null) {
      updated = new HashMap<>();
    } else {
      updated = new HashMap<>(current);
    }

    updated.put(command.getName(), command);
    subCommands.put(command.getBaseName(), updated);
  }

  public static BaseCommand getBaseCommand(String name) {
    return baseCommands.get(name);
  }

  public static SubCommand getSubCommand(String base, String name) {
    final Map<String, SubCommand> nullable = subCommands.get(base);
    return nullable == null ? null : nullable.get(name);
  }

  public static Map<String, SubCommand> getSubCommands(String base) {
    return subCommands.get(base);
  }
}
