package dev.demeng.demlib;

import dev.demeng.demlib.command.CustomCommand;
import dev.demeng.demlib.core.DemLib;
import dev.demeng.demlib.inputwaiter.InputWaiter;
import dev.demeng.demlib.inputwaiter.InputWaiterManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.lang.reflect.Field;

/** Utility class for quickly registering commands, listeners, and input waiters. */
public final class Registerer {

  /**
   * Register a custom command.
   *
   * @param command The command to register.
   */
  public static void registerCommand(CustomCommand command)
      throws NoSuchFieldException, IllegalAccessException {

    final Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
    commandMapField.setAccessible(true);

    final CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
    commandMap.register(Common.getName().toLowerCase(), command);
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
