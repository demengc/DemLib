package dev.demeng.demlib;

import dev.demeng.demlib.command.CommandManager;
import dev.demeng.demlib.command.model.BaseCommand;
import dev.demeng.demlib.command.model.SubCommand;
import dev.demeng.demlib.core.DemLib;
import dev.demeng.demlib.inputwaiter.InputWaiter;
import dev.demeng.demlib.inputwaiter.InputWaiterManager;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

/** Utility class for quickly registering commands, listeners, and input waiters. */
public final class Registerer {

  /**
   * Register a custom base command.
   *
   * @param command The base command to register.
   */
  public static void registerCommand(BaseCommand command) {
    CommandManager.register(command);
  }

  /**
   * Register a custom sub-command.
   *
   * @param command The sub-command to register.
   */
  public static void registerCommand(SubCommand command) {
    CommandManager.register(command);
  }

  /**
   * Register a listener.
   *
   * @param listener The listener to register.
   */
  public static void registerListener(Listener listener) {
    Bukkit.getPluginManager().registerEvents(listener, DemLib.getPlugin());
  }

  /**
   * Unregister, and then register a listener.
   *
   * @param listener The listener to reregister.
   */
  public static void reregisterListener(Listener listener) {

    HandlerList.getRegisteredListeners(DemLib.getPlugin()).stream()
        .filter(l -> l.getListener().getClass().getName().equals(listener.getClass().getName()))
        .forEach(l -> HandlerList.unregisterAll(l.getListener()));

    registerListener(listener);
  }

  /**
   * Register and activate an input waiter.
   *
   * @param waiter The input waiter to register.
   */
  public static void registerInputWaiter(InputWaiter waiter) {
    InputWaiterManager.addWaiter(waiter);
    waiter.onRequest();
  }
}
