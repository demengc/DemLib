package dev.demeng.demlib.inputwaiter;

import java.util.UUID;

/** A listener that waits for a player's response on the server chat. */
public interface InputWaiter {

  /**
   * The player the plugin is expecting input from.
   *
   * @return
   */
  UUID getTarget();

  /** Executed when the listener is started and the information is requested. */
  void onRequest();

  /** Executed when the player cancels the input action and denies the request. */
  void onCancel();

  /** Executed when the player does not provide a valid input and is required to try again. */
  void onRetry();
}
