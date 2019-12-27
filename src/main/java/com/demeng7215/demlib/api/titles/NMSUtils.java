package com.demeng7215.demlib.api.titles;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Basic NMS reflection utils, used for sending titles.
 */
public class NMSUtils {

	public static void sendPacket(Player player, Object packet) throws Exception {
		Object handle = player.getClass().getMethod("getHandle").invoke(player);
		Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
		playerConnection.getClass().getMethod("sendPacket",
				getNMSClass("Packet")).invoke(playerConnection, packet);
	}

	public static Class<?> getNMSClass(String name) {
		String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		try {
			return Class.forName("net.minecraft.server." + version + "." + name);
		} catch (final ClassNotFoundException ex) {
			return null;
		}
	}
}