package dev.demeng.demlib.api.inputwaiter;

import dev.demeng.demlib.api.messages.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerInputListener implements Listener {

  @EventHandler
  public void onPlayerProvideChatInput(PlayerChatEvent e) {

    final InputWaiter waiter = InputWaiterManager.getWaiter(e.getPlayer().getUniqueId());

    if (waiter == null) return;

    e.setCancelled(true);

    Bukkit.getPluginManager()
        .callEvent(
            new InputReceivedEvent(
                e.getPlayer().getUniqueId(), MessageUtils.colorAndStrip(e.getMessage()), waiter));
  }

  @EventHandler
  public void onPlayerProvideInteractInput(PlayerInteractEvent e) {

    if (e.getAction() != Action.LEFT_CLICK_AIR && e.getAction() != Action.LEFT_CLICK_BLOCK) return;
    if (InputWaiterManager.getWaiter(e.getPlayer().getUniqueId()) == null) return;

    InputWaiterManager.getWaiter(e.getPlayer().getUniqueId()).onCancel();
    InputWaiterManager.removeWaiter(e.getPlayer().getUniqueId());

    e.setCancelled(true);
  }

  @EventHandler
  public void onPlayerQuitWhilstInputRequest(PlayerQuitEvent e) {
    if (InputWaiterManager.getWaiter(e.getPlayer().getUniqueId()) == null) return;
    InputWaiterManager.removeWaiter(e.getPlayer().getUniqueId());
  }
}
