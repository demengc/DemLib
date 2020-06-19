/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2020 Crypto Morin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package dev.demeng.demlib.api.titles;

import dev.demeng.demlib.api.messages.MessageUtils;
import dev.demeng.demlib.utils.TitleUtils;
import org.bukkit.entity.Player;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Objects;

/**
 * A reflection API for titles in Minecraft.
 * Fully optimized - Supports 1.8.8+ and above.
 * Requires TitleUtils.
 * Messages are not colorized by default.
 * <p>
 * Titles are text messages that appear in the
 * middle of the players screen: https://minecraft.gamepedia.com/Commands/title
 * PacketPlayOutTitle: https://wiki.vg/Protocol#Title
 *
 * @author Crypto Morin
 */
public class CustomTitle {

	/**
	 * Used for the "stay" feature of titles.
	 */
	private static final Object TIMES;
	private static final Object TITLE;
	private static final Object SUBTITLE;
	private static final Object CLEAR;

	/**
	 * PacketPlayOutTitle Types: TITLE, SUBTITLE, ACTIONBAR, TIMES, CLEAR, RESET;
	 */
	private static final MethodHandle PACKET;

	/**
	 * ChatComponentText JSON message builder.
	 */
	private static final MethodHandle CHAT_COMPONENT_TEXT;

	static {
		Class<?> chatComponentText = TitleUtils.getNMSClass("ChatComponentText");
		Class<?> packet = TitleUtils.getNMSClass("PacketPlayOutTitle");
		Class<?> titleTypes = packet.getDeclaredClasses()[0];
		MethodHandle packetCtor = null;
		MethodHandle chatComp = null;

		Object times = null;
		Object title = null;
		Object subtitle = null;
		Object clear = null;

		for (Object type : titleTypes.getEnumConstants()) {
			switch (type.toString()) {
				case "TIMES":
					times = type;
					break;
				case "TITLE":
					title = type;
					break;
				case "SUBTITLE":
					subtitle = type;
					break;
				case "CLEAR":
					clear = type;
			}
		}

		MethodHandles.Lookup lookup = MethodHandles.lookup();

		try {
			chatComp = lookup.findConstructor(chatComponentText, MethodType.methodType(void.class, String.class));

			packetCtor = lookup.findConstructor(packet,
					MethodType.methodType(void.class, titleTypes,
							TitleUtils.getNMSClass("IChatBaseComponent"), int.class, int.class, int.class));
		} catch (NoSuchMethodException | IllegalAccessException e) {
			e.printStackTrace();
		}

		TITLE = title;
		SUBTITLE = subtitle;
		TIMES = times;
		CLEAR = clear;

		PACKET = packetCtor;
		CHAT_COMPONENT_TEXT = chatComp;
	}

	/**
	 * Sends a title message with title and subtitle to a player.
	 *
	 * @param player   the player to send the title to.
	 * @param fadeIn   the amount of ticks for title to fade in.
	 * @param stay     the amount of ticks for the title to stay.
	 * @param fadeOut  the amount of ticks for the title to fade out.
	 * @param title    the title message.
	 * @param subtitle the subtitle message.
	 * @see #clearTitle(Player)
	 */
	public static void sendTitle(Player player, int fadeIn, int stay, int fadeOut,
								 String title, String subtitle) {

		if (player == null) return;

		String titleText = null;
		String subtitleText = null;

		if (title != null)
			titleText = MessageUtils.colorize(title);

		if (subtitle != null)
			subtitleText = MessageUtils.colorize(subtitle);

		try {
			Object timesPacket = PACKET.invoke(TIMES, CHAT_COMPONENT_TEXT.invoke(titleText), fadeIn, stay, fadeOut);
			TitleUtils.sendPacket(player, timesPacket);

			if (titleText != null) {
				Object titlePacket = PACKET.invoke(TITLE, CHAT_COMPONENT_TEXT.invoke(titleText), fadeIn, stay, fadeOut);
				TitleUtils.sendPacket(player, titlePacket);
			}

			if (subtitleText != null) {
				Object subtitlePacket = PACKET.invoke(SUBTITLE, CHAT_COMPONENT_TEXT.invoke(subtitleText), fadeIn, stay, fadeOut);
				TitleUtils.sendPacket(player, subtitlePacket);
			}

		} catch (Throwable ex) {
			MessageUtils.error(ex, 6, "Failed to send title.", false);
			MessageUtils.tellWithoutPrefix(player, titleText + "&r &8| &r" + subtitleText);
		}
	}

	/**
	 * Sends a title message with title and subtitle with normal
	 * fade in, stay and fade out time to a player.
	 *
	 * @param player   the player to send the title to.
	 * @param title    the title message.
	 * @param subtitle the subtitle message.
	 * @see #sendTitle(Player, int, int, int, String, String)
	 */
	public static void sendTitle(Player player, String title, String subtitle) {
		sendTitle(player, 15, 50, 15, title, subtitle);
	}

	/**
	 * Clears the title and subtitle message from the player's screen.
	 *
	 * @param player the player to clear the title from.
	 * @since 1.0.0
	 */
	public static void clearTitle(Player player) {
		Objects.requireNonNull(player, "Cannot clear title from null player");
		Object clearPacket = null;

		try {
			clearPacket = PACKET.invoke(CLEAR, null, -1, -1, -1);
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}

		TitleUtils.sendPacket(player, clearPacket);
	}
}
