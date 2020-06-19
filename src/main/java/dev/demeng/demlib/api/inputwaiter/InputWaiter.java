package dev.demeng.demlib.api.inputwaiter;

import java.util.UUID;

public interface InputWaiter {

  UUID getTarget();

  void onRequest();

  void onCancel();

  void onRetry();
}
