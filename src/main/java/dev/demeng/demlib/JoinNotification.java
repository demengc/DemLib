package dev.demeng.demlib;

import dev.demeng.demlib.message.MessageUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

/** Send yourself a notification when you join a server running your plugin. */
public class JoinNotification implements Listener {

  private final UUID uuid;

  public JoinNotification(UUID uuid) {
    this.uuid = uuid;
    Registerer.registerListener(this);
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onDeveloperJoin(PlayerJoinEvent e) {

    if (e.getPlayer().getUniqueId().toString().equals(uuid.toString())) {

      MessageUtils.tellClean(
          e.getPlayer(),
          "&9" + MessageUtils.CHAT_LINE,
          "&bThis server is currently running " + Common.getName() + "!",
          "&9" + MessageUtils.CHAT_LINE);
    }
  }
}
