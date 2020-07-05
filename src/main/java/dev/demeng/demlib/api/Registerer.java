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

/** Utility class for quickly registering commands, listeners, and input waiters. */
public final class Registerer {

  /**
   * Registers a base command. Note that usual registration in plugin.yml is still required.
   *
   * @param base
   */
  public static void registerCommand(BaseCommand base) {
    CommandManager.getBaseCommands().add(base);
    CommandManager.getSubCommands().put(base, Collections.emptyList());
    DemLib.getPlugin().getCommand(base.getName()).setExecutor(new CommandManager());
  }

  /**
   * Registers a sub command.
   *
   * @param sub
   */
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

  /**
   * Registers a listener.
   *
   * @param listener
   */
  public static void registerListener(Listener listener) {
    Bukkit.getPluginManager().registerEvents(listener, DemLib.getPlugin());
  }

  /**
   * Registers and activates an input waiter.
   *
   * @param waiter
   */
  public static void registerInputWaiter(InputWaiter waiter) {
    InputWaiterManager.addWaiter(waiter);
    waiter.onRequest();
  }
}
