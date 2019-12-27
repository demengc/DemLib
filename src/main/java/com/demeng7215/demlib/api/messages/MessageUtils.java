package com.demeng7215.demlib.api.messages;

import com.demeng7215.demlib.DemLib;
import com.demeng7215.demlib.api.Common;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public final class MessageUtils {

	private static final Plugin i = DemLib.getPlugin();

	/**
	 * The prefix of the plugin.
	 * Make sure to set a prefix before using anything in this class!
	 */
	@Getter
	@Setter
	private static String prefix;

	/**
	 * Colors a string containing color codes.
	 *
	 * @param s The string to colorize
	 * @return A colored message
	 */
	public static String colorize(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}

	/**
	 * Formats the string by adding the plugin's prefix and then colorizing it.
	 *
	 * @param s The string to format
	 * @return A formatted message
	 */
	public static String format(String s) {
		return colorize(prefix + s);
	}

	/**
	 * Send a formated message to the console.
	 *
	 * @param messages The messages to be formatted and sent
	 */
	public static void console(String... messages) {
		for (final String msg : messages) Bukkit.getConsoleSender().sendMessage(format(msg));
	}

	/**
	 * Send a colored console message without the plugin's prefix.
	 *
	 * @param messages The messages to be colorized and sent
	 */
	public static void consoleWithoutPrefix(String... messages) {
		for (final String msg : messages) Bukkit.getConsoleSender().sendMessage(colorize(msg));
	}

	/**
	 * Send a simple info log message to the console.
	 *
	 * @param message The message to log
	 */
	public static void log(String message) {
		i.getLogger().info(message);
	}

	/**
	 * Send a simple log message to the console.
	 *
	 * @param level   The level of the log
	 * @param message The message to log
	 */
	public static void log(Level level, String message) {
		i.getLogger().log(level, message);
	}

	/**
	 * Send formatted messages to a CommandSender.
	 *
	 * @param sender   The sender that should receive the messages
	 * @param messages The messages that should be sent
	 */
	public static void tell(CommandSender sender, String... messages) {
		for (final String msg : messages) sender.sendMessage(format(msg));
	}

	/**
	 * Send formatted messages to a Player.
	 *
	 * @param player   The player that should receive the messages
	 * @param messages The messages that should be sent
	 */
	public static void tell(Player player, String... messages) {
		for (final String msg : messages) player.sendMessage(format(msg));
	}

	/**
	 * Send formatted messages to a CommandSender without a prefix.
	 *
	 * @param sender   The sender that should receive the messages
	 * @param messages The messages that should be sent
	 */
	public static void tellWithoutPrefix(CommandSender sender, String... messages) {
		for (final String msg : messages) sender.sendMessage(colorize(msg));
	}

	/**
	 * Send formatted messages to a Player without a prefix..
	 *
	 * @param player   The player that should receive the messages
	 * @param messages The messages that should be sent
	 */
	public static void tellWithoutPrefix(Player player, String... messages) {
		for (final String msg : messages) player.sendMessage(colorize(msg));
	}

	/**
	 * Send a detailed error message.
	 *
	 * @param ex          The Exception of the error
	 * @param id          Any ID that you desire, used to identify the error
	 * @param description A brief description of the error
	 * @param disable     True if the plugin should be disabled due to the error, false if it should continue running
	 */
	public static void error(Exception ex, int id, @NonNull String description, boolean disable) {

		if (ex != null) {
			log(Level.WARNING, "Error! Generating stack trace...");
			ex.printStackTrace();
		}

		console("&4" + consoleLine(),
				"&cUnfortunately, an error has occurred in " + Common.getName() + ".",
				"&cIf you are unable to fix this issue, please contact support.",
				"&cBelow are some important information regarding the error.",
				"&cError Identification Number: &6" + id,
				"&cDescription of the Error: &6" + description,
				"&4" + consoleLine());

		if (disable) Bukkit.getPluginManager().disablePlugin(i);
	}

	/**
	 * Replaces actual chat colors with color codes (&).
	 *
	 * @param message The message to revert colorizing
	 * @return An uncolorized message
	 */
	public static String revertColorizing(String message) {
		return message.replaceAll("(?i)" + ChatColor.COLOR_CHAR + "([0-9a-fk-or])", "&$1");
	}

	/**
	 * Remove all color codes from the message. as well as & letter colors from the message.
	 *
	 * @param message The message to strip
	 * @return A message with colors stripped
	 */
	public static String stripColors(String message) {
		return message == null ? "" : message.replaceAll("(" + ChatColor.COLOR_CHAR + "|&)([0-9a-fk-or])", "");
	}

	/**
	 * Returns a long ------ console line
	 *
	 * @return
	 */
	public static String consoleLine() {
		return "!-----------------------------------------------------!";
	}

	/**
	 * Returns a long ----------- chat line with strike color
	 *
	 * @return
	 */
	public static String chatLine() {
		return ChatColor.STRIKETHROUGH + "――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――――";
	}
}
