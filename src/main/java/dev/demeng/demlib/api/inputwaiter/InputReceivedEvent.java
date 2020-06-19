package dev.demeng.demlib.api.inputwaiter;

import dev.demeng.demlib.api.titles.CustomTitle;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class InputReceivedEvent extends Event {

  private static final HandlerList handlers = new HandlerList();

  private final UUID target;
  @Getter private final String input;
  @Getter private final InputWaiter waiter;

  public InputReceivedEvent(UUID target, String input, InputWaiter waiter) {
    this.target = target;
    this.input = input;
    this.waiter = waiter;
  }

  public Player getPlayer() {
    return Bukkit.getPlayer(target);
  }

  public void setSuccess(boolean success) {

    if (success) {
      CustomTitle.clearTitle(getPlayer());
      InputWaiterManager.removeWaiter(target);
      return;
    }

    InputWaiterManager.getWaiter(target).onRetry();
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }
}
