package com.demeng7215.demlib.api;

import com.demeng7215.demlib.DemLib;
import com.demeng7215.demlib.api.messages.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * This listener notifies the player with the specified UUID that the server is running the plugin.
 */
public class DeveloperNotifications implements Listener {

    // If developer notifications are enabled.
    private static boolean enabled;

    private static String uuid;

    /**
     * Enables the developer notification listener.
     *
     * @param devUUID Your Minecraft UUID.
     */
    public static void enableNotifications(String devUUID) {

        // Lightly checks if the UUID is valid by checking if it contains dashes.
        if (!devUUID.contains("-")) {
            return;
        }

        uuid = devUUID;
        Registerer.registerListeners(new DeveloperNotifications());
        enabled = true;
    }

    // The actual listener.
    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeveloperJoin(final PlayerJoinEvent e) {

        if (enabled && e.getPlayer().getUniqueId().toString().equals(uuid)) {

            final Player p = e.getPlayer();

            Bukkit.getScheduler().runTaskLaterAsynchronously(DemLib.getPlugin(), () -> {
                MessageUtils.tellWithoutPrefix(p, "&9" + MessageUtils.chatLine(),
                        "&bThis server is currently running " + Common.getName() + "!", "&9" + MessageUtils.chatLine());
            }, 60L);
        }
    }
}
