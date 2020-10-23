package dev.demeng.demlib.menu;

import dev.demeng.demlib.menu.paged.PagedMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;
import java.util.function.Consumer;

/** All the listeners that manage custom menus. */
public class MenuListener implements Listener {

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onInventoryClick(InventoryClickEvent e) {

    final Player p = (Player) e.getWhoClicked();

    final UUID inventoryUUID = Menu.getOpenInventories().get(p.getUniqueId());

    if (inventoryUUID != null) {
      e.setCancelled(true);

      final Consumer<InventoryClickEvent> actions =
          Menu.getInventories().get(inventoryUUID).getActions().get(e.getSlot());

      if (actions != null) actions.accept(e);
    }
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onInventoryClose(InventoryCloseEvent e) {
    Menu.getOpenInventories().remove(e.getPlayer().getUniqueId());
    PagedMenu.getOpenInventories().remove(e.getPlayer().getUniqueId());
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerQuit(PlayerQuitEvent e) {
    Menu.getOpenInventories().remove(e.getPlayer().getUniqueId());
    PagedMenu.getOpenInventories().remove(e.getPlayer().getUniqueId());
  }
}
