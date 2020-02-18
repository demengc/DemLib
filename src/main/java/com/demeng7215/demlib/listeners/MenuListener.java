package com.demeng7215.demlib.listeners;

import com.demeng7215.demlib.api.menus.CustomMenu;
import com.demeng7215.demlib.api.menus.CustomPagedMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;
import java.util.function.Consumer;

public class MenuListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClick(InventoryClickEvent e) {

		final Player p = (Player) e.getWhoClicked();

		final UUID inventoryUUID = CustomMenu.getOpenInventories().get(p.getUniqueId());

		if (inventoryUUID != null) {
			e.setCancelled(true);

			final Consumer<InventoryClickEvent> actions = CustomMenu.getInventories().get(inventoryUUID)
					.getActions().get(e.getSlot());

			if (actions != null) actions.accept(e);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onInventoryClose(InventoryCloseEvent e) {
		CustomMenu.getOpenInventories().remove(e.getPlayer().getUniqueId());
		CustomPagedMenu.getOpenInventories().remove(e.getPlayer().getUniqueId());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerQuit(PlayerQuitEvent e) {
		CustomMenu.getOpenInventories().remove(e.getPlayer().getUniqueId());
		CustomPagedMenu.getOpenInventories().remove(e.getPlayer().getUniqueId());
	}
}
