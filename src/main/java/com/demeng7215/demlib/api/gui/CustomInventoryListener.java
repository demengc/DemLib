package com.demeng7215.demlib.api.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

/**
 * Important GUI managing listeners.
 */
public class CustomInventoryListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();
        UUID playerUUID = player.getUniqueId();
        UUID inventoryUUID = CustomInventory.openInventories.get(playerUUID);

        if (inventoryUUID != null) {
            e.setCancelled(true);
            CustomInventory gui = CustomInventory.getInventoriesByUUID().get(inventoryUUID);
            CustomInventory.InvAction action = gui.getActions().get(e.getSlot());

            if (action != null) {
                action.click(player);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClose(InventoryCloseEvent e) {

        Player player = (Player) e.getPlayer();
        UUID playerUUID = player.getUniqueId();

        CustomInventory.openInventories.remove(playerUUID);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerQuit(PlayerQuitEvent e) {

        Player player = e.getPlayer();
        UUID playerUUID = player.getUniqueId();

        CustomInventory.openInventories.remove(playerUUID);
    }
}
