package dev.demeng.demlib;

import dev.demeng.demlib.core.DemLib;
import org.bukkit.Bukkit;

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
}
