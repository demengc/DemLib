package dev.demeng.demlib.inputwaiter;

import dev.demeng.demlib.xseries.message.Titles;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

/** The event that is fired when an input is received. */
public class InputReceivedEvent extends Event {

  private static final HandlerList handlers = new HandlerList();

  private final UUID target;

  /** The raw message of the input. */
  @Getter private final String input;

  /** The waiter that fired this event. */
  @Getter private final InputWaiter waiter;

  public InputReceivedEvent(UUID target, String input, InputWaiter waiter) {
    this.target = target;
    this.input = input;
    this.waiter = waiter;
  }

  /**
   * The player that fired the event.
   *
   * @return
   */
  public Player getPlayer() {
    return Bukkit.getPlayer(target);
  }

  /**
   * Whether the input was valid or not.
   *
   * @param success true if the input was valid, false if the user should try again
   */
  public void setSuccess(boolean success) {

    if (success) {
      Titles.clearTitle(getPlayer());
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
