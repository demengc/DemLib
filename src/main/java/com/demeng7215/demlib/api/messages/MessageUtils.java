package com.demeng7215.demlib.api.messages;

import com.demeng7215.demlib.DemLib;
import com.demeng7215.demlib.api.Common;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public final class MessageUtils {

	private static final Plugin i = DemLib.getPlugin();

	@Getter
	@Setter
	private static String prefix;

	public static final String CONSOLE_LINE = "!-----------------------------------------------------!";
	public static final String CHAT_LINE = ChatColor.STRIKETHROUGH +
			"-----------------------------------------------------";

	public static String colorize(String... messages) {
		return ChatColor.translateAlternateColorCodes('&', StringUtils.join(messages, "\n"));
	}

	public static List<String> colorize(List<String> list) {

		final List<String> copy = new ArrayList<>();

		for (String s : list) copy.add(colorize(s));

		return copy;
	}

	public static String format(String s) {
		return colorize(prefix + s);
	}

	public static void console(String... messages) {
		for (final String msg : messages) Bukkit.getConsoleSender().sendMessage(format(msg));
	}

	public static void consoleWithoutPrefix(String... messages) {
		for (final String msg : messages) Bukkit.getConsoleSender().sendMessage(colorize(msg));
	}

	public static void log(String message) {
		i.getLogger().info(message);
	}

	public static void log(Level level, String message) {
		i.getLogger().log(level, message);
	}

	public static void tell(CommandSender sender, String... messages) {
		for (final String msg : messages) sender.sendMessage(format(msg));
	}

	public static void tell(Player player, String... messages) {
		for (final String msg : messages) player.sendMessage(format(msg));
	}

	public static void tellWithoutPrefix(CommandSender sender, String... messages) {
		for (final String msg : messages) sender.sendMessage(colorize(msg));
	}

	public static void tellWithoutPrefix(Player player, String... messages) {
		for (final String msg : messages) player.sendMessage(colorize(msg));
	}

	public static void error(Throwable ex, int id, @NonNull String description, boolean disable) {

		if (ex != null) {
			log(Level.WARNING, "Error! Generating stack trace...");
			ex.printStackTrace();
		}

		console("&4" + CONSOLE_LINE,
				"&cUnfortunately, an error has occurred in " + Common.getName() + ".",
				"&cIf you are unable to fix this issue, please contact support.",
				"&cBelow are some important information regarding the error.",
				"&cError Identification Number: &6" + id,
				"&cDescription: &6" + description,
				"&4" + CONSOLE_LINE);

		if (disable) Bukkit.getPluginManager().disablePlugin(i);
	}

	public static String colorAndStrip(String message) {
		return ChatColor.stripColor(colorize(message));
	}

	// Replaces the color code sign with &.
	public static String revertColorizing(String message) {
		return message.replaceAll("(?i)" + ChatColor.COLOR_CHAR + "([0-9a-fk-or])", "&$1");
	}

	// Removes the & sign as well after decolorizing.
	public static String stripColors(String message) {
		return message == null ? "" : message.replaceAll("(" + ChatColor.COLOR_CHAR + "[§&])([0-9a-fk-or])", "");
	}
}
