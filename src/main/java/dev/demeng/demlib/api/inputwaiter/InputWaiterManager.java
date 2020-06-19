package dev.demeng.demlib.api.inputwaiter;

import dev.demeng.demlib.api.titles.CustomTitle;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InputWaiterManager {

  @Getter private static final Map<UUID, InputWaiter> waiters = new HashMap<>();

  public static InputWaiter getWaiter(UUID target) {
    return waiters.getOrDefault(target, null);
  }

  public static void addWaiter(InputWaiter waiter) {
    waiters.putIfAbsent(waiter.getTarget(), waiter);
  }

  public static void removeWaiter(UUID target) {
    CustomTitle.clearTitle(Bukkit.getPlayer(target));
    waiters.remove(target);
  }
}
