package dev.demeng.demlib.api.inputwaiter;

import dev.demeng.demlib.api.xseries.messages.Titles;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** Manages current input waiters. */
public class InputWaiterManager {

  @Getter private static final Map<UUID, InputWaiter> waiters = new HashMap<>();

  /**
   * Get the InputWaiter by target UUID.
   *
   * @param target The UUID of the target
   * @return The waiter that is currently awaiting the target
   */
  public static InputWaiter getWaiter(UUID target) {
    return waiters.getOrDefault(target, null);
  }

  /**
   * Register an input waiter.
   *
   * @param waiter
   */
  public static void addWaiter(InputWaiter waiter) {
    waiters.putIfAbsent(waiter.getTarget(), waiter);
  }

  /**
   * Unregister an input waiter based on the target.
   *
   * @param target The UUID of the target
   */
  public static void removeWaiter(UUID target) {
    Titles.clearTitle(Bukkit.getPlayer(target));
    waiters.remove(target);
  }
}
