package com.demeng7215.demlib.api.titles;

import com.demeng7215.demlib.api.messages.MessageUtils;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;

/**
 * Easy title sending for all server versions.
 */
public class CustomTitle {

	/**
	 * Send a custom title.
	 *
	 * @param p        The player that will see the title.
	 * @param title    The title content.
	 * @param subtitle The subtitle content.
	 * @param fadeIn   The number of ticks the title/subtitle will take to fade in.
	 * @param stay     The number of ticks the title/subtitle will stay on the screen.
	 * @param fadeOut  The number of ticks the title/subtitle will take to fade out.
	 */
	public static void sendTitle(Player p, String title, String subtitle, int fadeIn, int stay, int fadeOut) {

		final String titleText = MessageUtils.colorize(title);
		final String subtitleText = MessageUtils.colorize(subtitle);

		try {

			Object enumTitle = NMSUtils.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0]
					.getField("TITLE").get(null);
			Object titleChat = NMSUtils.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
					.getMethod("a", String.class).invoke(null, "{\"text\":\"" + titleText + "\"}");

			Object enumSubtitle = NMSUtils.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0]
					.getField("SUBTITLE").get(null);
			Object subtitleChat = NMSUtils.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0]
					.getMethod("a", String.class).invoke(null, "{\"text\":\"" + subtitleText + "\"}");

			Constructor<?> titleConstructor = NMSUtils.getNMSClass("PacketPlayOutTitle")
					.getConstructor(NMSUtils.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0],
							NMSUtils.getNMSClass("IChatBaseComponent"), int.class, int.class, int.class);

			Object titlePacket = titleConstructor.newInstance(enumTitle, titleChat, fadeIn, stay, fadeOut);
			Object subtitlePacket = titleConstructor.newInstance(enumSubtitle, subtitleChat, fadeIn, stay, fadeOut);

			NMSUtils.sendPacket(p, titlePacket);
			NMSUtils.sendPacket(p, subtitlePacket);

		} catch (final Exception ex) {
			ex.printStackTrace();
			MessageUtils.tell(p, "&cFailed to send title.");
		}
	}
}
