package dev.demeng.demlib.api;

import dev.demeng.demlib.DemLib;
import dev.demeng.demlib.api.commands.CommandManager;
import dev.demeng.demlib.api.commands.types.BaseCommand;
import dev.demeng.demlib.api.commands.types.SubCommand;
import dev.demeng.demlib.api.inputwaiter.InputWaiter;
import dev.demeng.demlib.api.inputwaiter.InputWaiterManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Registerer {

  public static void registerCommand(BaseCommand base) {
    CommandManager.getBaseCommands().add(base);
    CommandManager.getSubCommands().put(base, Collections.emptyList());
    DemLib.getPlugin().getCommand(base.getName()).setExecutor(new CommandManager());
  }

  public static void registerCommand(SubCommand sub) {

    final BaseCommand base =
        CommandManager.getBaseCommands().stream()
            .filter(cmd -> cmd.getName().equals(sub.getBaseCommand()))
            .findFirst()
            .orElse(null);

    if (base == null) throw new IllegalArgumentException("Base command is null");

    final List<SubCommand> tempList = new ArrayList<>(CommandManager.getSubCommands().get(base));
    tempList.add(sub);

    CommandManager.getSubCommands().put(base, tempList);
  }

  public static void registerListener(Listener listener) {
    Bukkit.getPluginManager().registerEvents(listener, DemLib.getPlugin());
  }

  public static void registerInputWaiter(InputWaiter waiter) {
    InputWaiterManager.addWaiter(waiter);
    waiter.onRequest();
  }
}
