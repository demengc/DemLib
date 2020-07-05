package dev.demeng.demlib.api;

import dev.demeng.demlib.DemLib;
import org.bukkit.Bukkit;

import java.util.Set;

/** A set of common and general utilities. */
public class Common {

  /** @return The name of the plugin. */
  public static String getName() {
    return DemLib.getPlugin().getDescription().getName();
  }

  /** @return The version of the plugin. */
  public static String getVersion() {
    return DemLib.getPlugin().getDescription().getVersion();
  }

  /**
   * Get the nullable value and return it if not null, otherwise return the default.
   *
   * @param nullable The nullable value
   * @param def The default value
   * @param <T>
   * @return nullable if not null, default otherwise
   */
  public static <T> T getOrDefault(T nullable, T def) {
    return nullable != null ? nullable : def;
  }

  /**
   * Convert a boolean to Yes or No.
   *
   * @param bool
   * @return Yes if true, No if false
   */
  public static String booleanToString(boolean bool) {
    if (bool) return "Yes";
    return "No";
  }

  /**
   * Run a task sync or async.
   *
   * @param task The task to run
   * @param async If the task should be run async
   */
  public static void run(Runnable task, boolean async) {

    if (async) {
      Bukkit.getScheduler().runTaskAsynchronously(DemLib.getPlugin(), task);
      return;
    }

    Bukkit.getScheduler().runTask(DemLib.getPlugin(), task);
  }

  /**
   * Repeat a task.
   *
   * @param task The task to run
   * @param startDelay The delay before the first run
   * @param delay The delay between each run
   * @param async If the task should be executed async
   */
  public static void repeatTask(Runnable task, long startDelay, long delay, boolean async) {

    if (async) {
      Bukkit.getScheduler().runTaskTimerAsynchronously(DemLib.getPlugin(), task, startDelay, delay);
      return;
    }

    Bukkit.getScheduler().runTaskTimer(DemLib.getPlugin(), task, startDelay, delay);
  }

  /**
   * Delay a task.
   *
   * @param task The task to run
   * @param delay The delay before the task is run
   * @param async If the task should be executed async
   */
  public static void delayTask(Runnable task, long delay, boolean async) {

    if (async) {
      Bukkit.getScheduler().runTaskLaterAsynchronously(DemLib.getPlugin(), task, delay);
      return;
    }

    Bukkit.getScheduler().runTaskLater(DemLib.getPlugin(), task, delay);
  }

  /**
   * Check if a string contains other mentioned strings, ignores case.
   *
   * @param s The string to search
   * @param toCheck The array of strings to look for
   * @return true if the string contains at least one of the other strings, false otherwise
   */
  public static boolean checkContainsAny(String s, String... toCheck) {

    for (String s1 : toCheck) {
      if (s.toLowerCase().contains(s1.toLowerCase())) {
        return true;
      }
    }

    return false;
  }

  /**
   * Dispatch commands via console sync.
   *
   * @param commands Array of commands to execute, without the / prefix
   */
  public static void dispatch(String... commands) {
    for (String cmd : commands) {
      run(() -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd), false);
    }
  }

  /**
   * Get the index of an object in a set.
   *
   * @param set
   * @param value
   * @return
   */
  public static int getIndex(Set<?> set, Object value) {
    int result = 0;
    for (Object entry : set) {
      if (entry.equals(value)) return result;
      result++;
    }
    return -1;
  }

  /**
   * Check if an array contains the specified object.
   *
   * @param toCheck The object to look for
   * @param array The array to check
   * @return true if the object is found, false otherwise
   */
  public static boolean checkArrayContains(Object toCheck, Object[] array) {

    for (Object o : array) {

      if (o instanceof String) {
        if (toCheck.equals(0)) {
          return true;
        }

      } else {
        if (o == toCheck) {
          return true;
        }
      }
    }

    return false;
  }
}
