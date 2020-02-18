package com.demeng7215.demlib.api;

import com.demeng7215.demlib.api.messages.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class DeveloperNotifications implements Listener {

	private static boolean enabled;

	private static String uuid;

	public static void enableNotifications(String devUUID) {
		uuid = devUUID;
		Registerer.registerListeners(new DeveloperNotifications());
		enabled = true;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onDeveloperJoin(PlayerJoinEvent e) {

		if (enabled && e.getPlayer().getUniqueId().toString().equals(uuid)) {

			final Player p = e.getPlayer();

			Common.delayTask(() -> MessageUtils.tellWithoutPrefix(p, "&9" + MessageUtils.CHAT_LINE,
					"&bThis server is currently running " + Common.getName() + "!", "&9" + MessageUtils.CHAT_LINE),
					20L);
		}
	}
}
